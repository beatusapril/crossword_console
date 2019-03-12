package logic;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private boolean horizontalFl; // true - горизонтальный, false - вертикальный

    public ArrayList<String> getWord() {
        return word;
    }

    private ArrayList<String> word;

    public int getIndexQuest() {
        return indexQuest;
    }

    private int indexQuest;

    public Key getKeyWordInStore() {
        return keyWordInStore;
    }

    private Key keyWordInStore;
    private int startX;
    private int startY;

    public boolean isHorizontalFl() {
        return horizontalFl;
    }

    public void horizontal(){
        horizontalFl = true;
    }


    public void vertical(){
        horizontalFl = false;
    }

    public boolean isHorizontal(){
        return horizontalFl;
    }

    public boolean isVertical(){
        return !horizontalFl;
    }

    public Word(boolean isHoriz, int x, int y, int indexQuest, ArrayList<String> wordFromStore, Key ky){
        this.horizontalFl = isHoriz;
        this.startX = x;
        this.startY = y;
        this.indexQuest = indexQuest;
        this.word = wordFromStore;
        this.keyWordInStore = ky;

    }
    // конструктор для клонирования
    public Word(Word word){
        this.horizontalFl = word.isHorizontalFl();
        this.startX = word.getStartX();
        this.startY = word.getStartY();
        this.indexQuest = word.getIndexQuest();
        //this.word = word.getWord();
        this.word = Word.cloneWrd(word.getWord());
        this.keyWordInStore = word.getKeyWordInStore();
    }

    public static ArrayList<String> cloneWrd(ArrayList<String> bf){
        ArrayList<String> dr = new ArrayList<String>();
        for (String item : bf)
            dr.add(item);
        return dr;
    }

    public int size(){
        return word.size();
    }

    public String get(int index){
        return word.get(index);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }


    public static List<Word> cloneListWord(List<Word> list) {
        List<Word> clone = new ArrayList<Word>(list.size());
        for (Word item : list)
            clone.add(new Word(item));
        return clone;
    }

    public void setWordFromString(String str){
        ArrayList<String> newWord = new ArrayList<String>();
        for (int i = 0; i < str.length(); i++)
            newWord.add(str.substring(i, i + 1));
        word = newWord;
    }


    public static List<Word> form(List<Word> words){
        List<Word> newLst = Word.cloneListWord(words);
        for (Word wrd: newLst) {
            wrd.getWord().clear();
        }
        return newLst;
    }

    @Override
    public String toString() {
        String result = "";
        for (String item : word)
            result += item;
        return result;
    }
}
