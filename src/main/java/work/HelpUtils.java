package work;


public  class HelpUtils {
    public static final int CHIS_ERR = -600;
    public static int parseInInt(String str, int start, int finish){
        int res;
        try{
            res = Integer.parseInt(str.trim());
            if ((res < start) || (res > finish))
                return CHIS_ERR;

        }catch(NumberFormatException e){
            return CHIS_ERR;
        }
        return res;
    }
}
