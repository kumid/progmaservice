package com.ru.progmaservice.progmalab;

public class utils {

    public static int convertStringToInt(String str){
        int res = 0;

        try {
            res = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {

            //System.out.println("Could not parse " + nfe);
        }
        return res;
    }
}
