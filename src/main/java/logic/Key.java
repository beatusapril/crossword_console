package logic;

public class Key {
    private int index; // номер вопроса
    private boolean isUsing;  // использование

    public Key(int index, boolean isUsing) {
        this.index = index;
        this.isUsing = isUsing;
    }

    public int getIndex() {
        return index;
    }

    public void use(){
        this.isUsing = true;
    }

    public void notUse(){
        this.isUsing = false;
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isUsing() {
        return isUsing;
    }

    public void setUsing(boolean using) {
        isUsing = using;
    }
}
