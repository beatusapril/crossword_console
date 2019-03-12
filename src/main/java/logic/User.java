package logic;

import view.ViewBoard;
import work.HelpUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {

    private ViewBoard vbTrue; // доска с реальным реением
    private ViewBoard vbUser; // доска с текущим решением, заполняемым пользователем
    private List<Word> resultWordsList;
    private Map<Integer, Word> resultWords;
    private PrintStream pr;
    private InputStream in;
    public int countRightSolution = 0;
    private Scanner sc;
    public User(List<Word> resultWordsList,  PrintStream pr, InputStream in, ViewBoard vbTrue, ViewBoard vbUser){
        this.resultWordsList = resultWordsList;
        this.pr = pr;
        this.in = in;
        this.sc = new Scanner(in);
        this.vbTrue = vbTrue;
        this.resultWords = translate();
        this.vbUser = vbUser;
    }

    public boolean checkFinishCrossword(){
        if (countRightSolution == resultWordsList.size()){
            pr.println("Поздравляем!! Кроссворд собран!!");
            return true;
        }
        else{
            pr.println("К сожалению, пока кроссворд не собран...");
            return false;
        }
    }

    public void menu(){
        for(;;){
            vbUser.draw(true);
            pr.print("1 - Ввести слово" + "\r\n" + "2 - Проверить кроссворд на правильность" + "\r\n" + "3 - Сдаться (посмотреть решение)" + "\r\n"+"4 - Назад" + "\r\n" );
            int choice = enterInt(1, 5, "Ваш выбор: ");
            switch (choice){
                case 1:
                    enterWord();
                    break;
                case 2:
                    if (checkFinishCrossword()){
                        return;
                    }
                    break;
                case 3:
                    vbTrue.draw(true);
                    return;
                case 4:
                    return;
            }
        }
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


    public void enterWord(){
        for(;;){
            int indQ = enterInt(1, resultWords.size(), "Введите номер вопроса: ");
            String answer = enter("Введите слово: ");
            if (!checkLength(indQ, answer)){
                pr.print("Неверное кол-во букв в слове!!");
            }else{
                checkAnswer(indQ, answer);
                formWord(indQ, answer);
            }
            String st = enter("Для выхода нажмите 1 (для продолжения - любую другую клавишу): ");
            if (st.equals("1"))
                break;
        }
    }

    // проверка слова на правильный ввод кол-ва
    public boolean checkLength(int numb, String answ){
        int n = resultWords.get(numb).getWord().size();
        int m = answ.trim().length();
        if (n == m)
            return true;
        return false;
    }

    // формирование слова и добавление его в доску
    private void formWord(int numb, String answ){
        Word wrd = new Word(resultWords.get(numb));
        wrd.setWordFromString(answ);
        vbUser.addWordInBoard(wrd);
    }


    // проверка введенного слово на правильность ответа
    private void checkAnswer(int numb, String answ){
        if (resultWords.get(numb).toString().equals(answ))
            countRightSolution++;
    }

    private String enter(String msg){
        pr.print(msg);
        String word = sc.next();
        return word.trim();
    }



    private Map<Integer, Word> translate() {
        Map<Integer, Word> mpResultWord = new HashMap<Integer, Word>();
        for (Word wrd : resultWordsList) {
            int n  = wrd.getIndexQuest();
            mpResultWord.put(++n, wrd);
        }
        return mpResultWord;
    }
}
