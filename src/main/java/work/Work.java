package work;

import logic.Configurator;
import logic.StoreWordsA;
import logic.User;
import logic.Word;
import view.ViewBoard;

import java.util.Scanner;

public class Work {
   private Scanner sc;
   private Configurator con;
   private User user;
   private ViewBoard vb;
   private StoreWordsA st;
   public Work(){
       sc = new Scanner(System.in);

       for (;;){
           menu();
       }

   }
    private void menu(){
        System.out.println("1 - Собрать кроссворд \n2 - Решить кроссворд \n3 - Посмотреть кроссворд");
        int menu = enterInt(1, 3, "Ваш выбор: ");
        switch(menu){
            case 1:
                con = new Configurator(System.in, System.out) ;
                con.enterWord("Введите описание слова: ", "Введите слово: ");
                vb = new ViewBoard(System.out, con.getListWords(),con.getListDesc(), con.getX(), con.getY());
                vb.draw(true);
                break;
            case 2:
                if (con ==  null){
                    System.out.print("Нет доступного кроссворда!!");
                }else {
                    ViewBoard vbUser = new ViewBoard(System.out, Word.form(con.getListWords()),con.getListDesc(), con.getX(), con.getY());
                    user = new User(con.getListWords(), System.out, System.in, vb, vbUser);
                    user.menu();
                }
                break;
            case 3:
                vb.draw(true);
        }
    }

    private int enterInt(int st, int fn, String msg){
        System.out.print(msg);
        String str = sc.next();
        int res = HelpUtils.parseInInt(str, st, fn);
        while (res == HelpUtils.CHIS_ERR){
            System.out.print("Ошибка ввода! Повторите!!" + "\r\n" + msg);
            str = sc.next();
            res = HelpUtils.parseInInt(str, st, fn);
        }
        return res;
    }
}
