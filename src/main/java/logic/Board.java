package logic;


import java.util.List;

// класс  доски для отрисовки
public class Board {
    private Cell[][]  board;

    public List<String> getLstDesc() {
        return lstDesc;
    }

    private List<String> lstDesc;


    public Board(List<Word> lstWord, List<String> lstDesc, int sizeX, int sizeY){
        board = new Cell[sizeY][];
        this.lstDesc = lstDesc;
        for (int i = 0; i < sizeY; i++){
            board[i] = new Cell[sizeX];
        }

        for(int i = 0; i < sizeY; i++){
            for (int j = 0; j < sizeX; j++){
                board[i][j] = new Cell(" ", j, i, -1, -1);
            }
        }

        for(Word wrd:lstWord)
            addWordInBoard(wrd);
    }

    public Cell[][] getBoard() {
        return board;
    }

    // добавление слова в доску
    public void addWordInBoard(Word word){
        int currentX = word.getStartX();
        int currentY = word.getStartY();
        int numb = word.getIndexQuest();
        if (word.isHorizontal())
            board[currentY][currentX].setIndexHorizontal(++numb);
        else
            board[currentY][currentX].setIndexVertical(++numb);
        for (int i = 0; i < word.size(); i++) {
            board[currentY][currentX].setLetter(word.get(i));
            if (word.isHorizontal())
                ++currentX;
            else
                ++currentY;
        }
    }
}
