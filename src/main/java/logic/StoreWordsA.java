package logic;

import java.util.*;

public class StoreWordsA {
    private Map<Key, ArrayList<String>> words;

    public Map<Key, ArrayList<String>> getWords() {
        return words;
    }

    public ArrayList<String> get(Key key){
        return words.get(key);
    }

    public int size(){
        return words.size();
    }

    public StoreWordsA() {
        words = new HashMap<Key, ArrayList<String>>();
    }

    public void add(String word) {
        ArrayList<String> newWord = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++)
            newWord.add(word.substring(i, i + 1));
        words.put(new Key(words.size(), false), newWord);

    }

    public Key findMax() {
        int maxLength = 0;
        Key maxKey = null;
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && (item.getValue().size() > maxLength)){
                maxLength = item.getValue().size();
                maxKey = item.getKey();
            }
        }
        return maxKey;
    }



    public ArrayList<String> findWord(Map<Integer, String> sp) {
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && this.helpFindWithLettersAndNumbers(item.getValue(), sp))
                return item.getValue();
        }
        return null;
    }

    public Key findWordK(Map<Integer, String> sp) {
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && this.helpFindWithLettersAndNumbers(item.getValue(), sp))
                return item.getKey();
        }
        return null;
    }

    public ArrayList<String> findWord(String... letters) {
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && this.helpFindWithLetters(item.getValue(), letters))
                return item.getValue();
        }
        return null;
    }

    public Key findWordK(String... letters) {
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && this.helpFindWithLetters(item.getValue(), letters))
                return item.getKey();
        }
        return null;
    }

    public ArrayList<Key> findWordKAr(String... letters) {
        ArrayList<Key> arKey = new ArrayList<Key>();
        for (Map.Entry<Key, ArrayList<String>> item : words.entrySet()) {
            if (!item.getKey().isUsing() && this.helpFindWithLetters(item.getValue(), letters))
                arKey.add(item.getKey());
        }
        return arKey;
    }

    // check one  word with letter and number
    private boolean helpFindWithLettersAndNumbers(ArrayList<String> checkWord, Map<Integer, String> searchWord) {
        Set<Integer> setKeys = searchWord.keySet();
        for (Integer key : setKeys) {
            if (key > checkWord.size() || key < 0)
                return false;
            if (!checkWord.get(key).equals(searchWord.get(key)))
                return false;
        }
        return true;
    }

    private boolean helpFindWithLetters(ArrayList<String> checkWord, String... letters) {
        boolean exist = false;
        for (String let : letters) {
            exist = checkWord.contains(let);
        }

        return exist;
    }
}