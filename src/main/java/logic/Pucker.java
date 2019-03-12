package logic;

import java.util.*;

public class Pucker {
    private StoreWordsA sw;
    private int maxSolution;  // максимальное количество хранимых решений
    private int maxSizeY;
    private int maxSizeX;

    private List<Word> listWords; // список слов
    private List<List<Word>> maxListWords; // список слов
    private Random rand;
    private String[][] board;
    private String emptySymbol = "";
    private int maxWord;
    private int currentSolution = 0;

    public List<Word> getResultListWord(){
        return maxListWords.get(0);
    }

    public Pucker(StoreWordsA sw, int maxSizeX, int maxSizeY, int maxSolution){
        this.sw = sw;
        this.maxSizeY = maxSizeY;
        this.maxSizeX = maxSizeX;
        rand = new Random();
        this.listWords = new ArrayList<Word>();
        this.maxSolution = maxSolution;

        this.maxListWords = new ArrayList<List<Word>>();

        board = new String[maxSizeY][];
        for (int i = 0; i < maxSizeY; i++){
            board[i] = new String[maxSizeX];
        }

        // инициализация пустыми строчками
        for (int i = 0; i < maxSizeY; i++){
            for (int j = 0; j < maxSizeX; j++){
                board[i][j] = "";
            }
        }
    }

    public void start(){
        Key start = sw.findMax();
        int x = 0;
        int y = 0;
        // первое слово горизонтальное, самое длинное
        if ((maxSizeX > 0) && (maxSizeY > 0)){
            x = 1;
            y = 1;
        }
        Word startWord = new Word(true, x, y, start.getIndex(), sw.get(start), start);
        registerWord(startWord);
        maxListWords.add(Word.cloneListWord(listWords));
        alg();
    }



    // нечетные горизонтальные четные вертикальные
    // первое слово будет горизонтальным
    private void alg(){
        // ищем по горизонтальным устанавливаем вертикально
        while ((listWords.size() < sw.size()) || (currentSolution < maxSolution)){
            Word currentWord = searchWord();// поиск подходящего слова
            // если не нашли выходим из функции
            if (currentWord == null)
                return;
            // добавление
            registerWord(currentWord);
            currentSolution++;
            if (currentSolution == maxSolution)
                return;
            // если больше очищаем и копируем
            if (listWords.size() > maxWord){
                maxWord = listWords.size();
                maxListWords.clear();
                maxListWords.add(Word.cloneListWord(listWords));
            }else if (listWords.size() == maxWord){
                maxListWords.add(Word.cloneListWord(listWords));
            }
            alg();

        }
    }


    // 1 - гориз
    private Word searchWord(){
        boolean typeSearch = true;
        // было вертикальное сначала ищем по вертикальным ставим горизонтально
        if (listWords.size() % 2 == 0)
            typeSearch = false;
        Word newWord = poisk(typeSearch);
        // если не нашли меняем тип поиска
        if (newWord == null){
            newWord = poisk(!typeSearch);
        }
        return newWord;
    }
    // typeSearch - true ищем по горизонтальным, false - о вертикальным
    // поиск слова
    private Word poisk(boolean typeSearch){
        for (int i = listWords.size()-1; i >= 0; i--) {
            if (listWords.get(i).isHorizontal() == typeSearch) {
                Word newWord = helpSearchWord(listWords.get(i), !typeSearch);
                // если нашли слово, то его возвращаем
                if (newWord != null)
                    return newWord;
            }
        }
        return null;
    }

    // поиск слова
    private Word helpSearchWord(Word wrd, boolean ortNewWord){
        for (int i = 0; i < wrd.size(); i++){
            ArrayList<Key> ky = sw.findWordKAr(wrd.get(i));
            Word newWord = searchHelpWordTwo(wrd, ortNewWord, ky, i);
            if (newWord!= null)
                return newWord;
        }
        return null;
    }

    // вспомогательная функция поиска слова
    private Word searchHelpWordTwo(Word wrd, boolean ortNewWord, ArrayList<Key> ky, int i){
        for (int j = 0; j < ky.size(); j++){
            ArrayList<String> newWord = sw.get(ky.get(j));
            if (newWord == null)
                continue;
            int numb = newWord.indexOf(wrd.get(i));
            int x, y;
            if (wrd.isHorizontal()){
                x = wrd.getStartX() + i;
                y = wrd.getStartY();
            }else{
                x = wrd.getStartX();
                y = wrd.getStartY()+i;
            }
            if (checkEnvironment(x, y, numb, newWord, ortNewWord)){
                int startX, startY;
                if (ortNewWord){
                    startX = x-numb;
                    startY = y;
                }else{
                    startX = x;
                    startY = y-numb;
                }
                Word word = new Word(ortNewWord, startX, startY, ky.get(j).getIndex(), newWord, ky.get(j));

                return word;
            }

        }
        return null;
    }

    // проверка окружения слова
    private boolean checkEnvironment(int x, int y, int numb, ArrayList<String> wrd, boolean isHoriz){
        // если новое слово должно быть горизонтальным
        boolean result = true;
        if (isHoriz){
            result = helpCheckEnvironmentHorizontal(x, y, numb, wrd);
        }else{
            result = helpCheckEnvironmentVertical(x, y, numb, wrd);
        }
        return result;
    }

    // проверка окружения слова по вертикали
    private boolean helpCheckEnvironmentHorizontal(int x, int y, int numb, ArrayList<String> wrd){
        // идем влевл
        for (int j = x, i = y, m = numb; m >= 0; j--, m--){
            if (!checkCoordinates(j, i))
                return false;
            if (!wrd.get(m).equals(board[i][j]) && !board[i][j].equals(emptySymbol))
                return false;
        }
        // идем вправо
        for (int j = x+1, i = y, m = numb; m < wrd.size(); j++, m++){
            if (!checkCoordinates(j, i))
                return false;
            if (!wrd.get(m).equals(board[i][j]) && !board[i][j].equals(emptySymbol))
                return false;
        }
        return true;
    }

    private boolean helpCheckEnvironmentVertical(int x, int y, int numb, ArrayList<String> wrd){
        // идем вверх
        for (int j = x, i = y, m = numb; m  >= 0; i--, m--){
            if (!checkCoordinates(j, i))
                return false;
            if (!wrd.get(m).equals(board[i][j]) && !board[i][j].equals(emptySymbol))
                return false;
        }
        // идем вниз
        for (int j = x, i = y+1, m = numb+1; m < wrd.size(); i++, m++){
            if (!checkCoordinates(j, i))
                return false;
            if (!wrd.get(m).equals(board[i][j]) && !board[i][j].equals(emptySymbol))
                return false;
        }
        return true;
    }




     private void registerWord(Word word){
         if (addWordInBoard(word)){
             // отмечаем слово как использованное
             word.getKeyWordInStore().use();
             listWords.add(word);
         }
     }

    private void unregisterWord(Word word){
        deleteWordInBoard(word);
        // отмечаем слово как неиспользованное
        word.getKeyWordInStore().notUse();
        listWords.remove(listWords.size()-1);
    }


     private boolean addWordInBoard(Word word){
         int currentX = word.getStartX();
         int currentY = word.getStartY();
         for (int i = 0; i < word.size(); i++) {
             if (!checkCoordinates(currentX, currentY))
                 return false;
             board[currentY][currentX] = word.get(i);
             if (word.isHorizontal())
                 ++currentX;
             else
                 ++currentY;
         }
         return true;
     }

     private boolean checkCoordinates(int x, int y){
         boolean result = true;
         if ((x < 0) || (x >= maxSizeX))
             return false;

         if ((y < 0) || (y >= maxSizeY))
             return false;
         return result;
     }


    private void deleteWordInBoard(Word word){
        int currentX = word.getStartX();
        int currentY = word.getStartY();
        for (int i = 0; i < word.size(); i++) {
            board[currentY][currentX] = emptySymbol;
            if (word.isHorizontal())
                ++currentX;
            else
                ++currentY;
        }
    }

}
