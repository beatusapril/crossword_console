package logic;


// класс ячейки
public class Cell {
    private String letter=" ";

    public int getIndexHorizontal() {
        return indexHorizontal;
    }

    public void setIndexHorizontal(int indexHorizontal) {
        this.indexHorizontal = indexHorizontal;
    }

    public void setIndexVertical(int indexVertical) {
        this.indexVertical = indexVertical;
    }

    private int indexHorizontal = -1;  // номер вопроса, если в этой ячейке начинается вопрос

    public int getIndexVertical() {
        return indexVertical;
    }

    private int indexVertical = -1;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int x;  // стартовая позиция x
    private int y; // стартовая позиция y

    public Cell(String letter, int x, int y, int indexHorizontal, int indexVertical) {
        this.letter = letter;
        this.x = x;
        this.y = y;
        this.indexHorizontal = indexHorizontal;
        this.indexVertical = indexVertical;
    }


    public String draw (boolean vizibel){
        String result = "";
        int cnt = 0;
        if (indexHorizontal != -1){
            cnt++;
            result+= Integer.toString(indexHorizontal);
        }
        if (indexVertical != -1){
            cnt++;
            result+= Integer.toString(indexVertical);
        }
        if ((cnt == 0) && (!vizibel))
            result = "    ";
         if ((cnt == 1) && (!vizibel))
             result = " "+ result+ " ";
        if ((vizibel) && (cnt == 0))
            result = " " + letter + " ";
        if ((vizibel) && (cnt == 1))
            result = result + letter + " ";
        if ((vizibel) && (cnt == 2))
            result = result + letter;
        return result;
    }
}
