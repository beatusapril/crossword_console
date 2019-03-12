package view;

import logic.Board;
import logic.Cell;
import logic.Word;

import java.io.PrintStream;
import java.util.List;

public class ViewBoard extends Board {

    private PrintStream pr;
    private static final int COUNT_WORDS = 5;
    private String str = "";

    public ViewBoard(PrintStream pr, List<Word> wrds,List<String> lstDescription, int sizeX, int sizeY){
        super(wrds, lstDescription, sizeX, sizeY);
        this.pr = pr;
        this.str = reformListToString();
    }

    public void draw (boolean visib){
        String line ="";
        for (int k = 0; k < super.getBoard()[0].length; k++)
            line += "----";

        for (int i = 0; i < super.getBoard().length; i++){
            for (int j = 0; j < super.getBoard()[0].length; j++){
                String c =  super.getBoard()[i][j].draw(visib);
                pr.print("|" + c + "");
            }
            pr.println();
            pr.println(line);
        }

        pr.println(str);

    }

    // вопросы в строку
    public String reformListToString(){
        String result = "";
        for (int i = 0; i < super.getLstDesc().size(); i++){
            result += Integer.toString(i+1) + " " + super.getLstDesc().get(i) + " ";
            if ((i%(COUNT_WORDS-1) == 0) && i != 0)
                result += "\r\n";
        }
        return result;
    }
}
