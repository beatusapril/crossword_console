package logic;

import work.HelpUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configurator {
    public StoreWordsA getStoreWords() {
        return storeWords;
    }

    public List<String> getListDesc() {
        return listDesc;
    }

    private static final String stopSymbol = ".";
    private static final int  MAX_WIDTH_CROSSWORD = 70;
    private static final int  MAX_HEIGHT_CROSSWORD = 70;
    private static final int maxSolution = 100;
    private final InputStream in;
    private final PrintStream pr;
    private int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int y;
    private StoreWordsA storeWords;



    private List<String> listDesc;

    public List<Word> getListWords() {
        return listWords;
    }

    private List<Word> listWords;
    private Scanner sc;
    public Configurator(InputStream in, PrintStream pr){
        this.in = in;
        this.pr = pr;
        listDesc = new ArrayList<String>();
        storeWords = new StoreWordsA();
        sc = new Scanner(in);
    }

    private int enterInt(int st, int fn, String msg){
        pr.print(msg);
        String str = sc.next();
        int res = HelpUtils.parseInInt(str, st, fn);
        while (res == HelpUtils.CHIS_ERR){
            pr.print("Ошибка ввода! Повторите!!" + "\r\n" + msg);
            str = sc.next();
            res = HelpUtils.parseInInt(str, st, fn);
        }
        return res;
    }
    public void enterWord(String message, String messagetwo){
        String word = enter(messagetwo);
        String description = enter(message);
        while (!word.trim().equals(stopSymbol)) {
            storeWords.add(word);
            String str = " " + word.length() + " букв";
            listDesc.add(description+str);
            String st = enter("Для выхода нажмите 1 (для продолжения - любую другую клавишу) ");
            if (st.equals("1"))
                break;
            word = enter(messagetwo);
            description = enter(message);
        }

        x = enterInt(1, MAX_WIDTH_CROSSWORD, "Введите ширину кроссворда: ");
        y = enterInt(1, MAX_HEIGHT_CROSSWORD, "Введите высоту кроссворда: ");

        Pucker pk = new Pucker(storeWords, x, y, maxSolution);
        pk.start();
        listWords = pk.getResultListWord();

    }

    private String enter(String msg){
        pr.print(msg);
        String word = sc.nextLine();
        return word.trim();
    }
}
