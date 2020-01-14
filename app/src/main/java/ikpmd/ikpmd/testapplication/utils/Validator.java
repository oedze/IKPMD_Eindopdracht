package ikpmd.ikpmd.testapplication.utils;

import android.text.Editable;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {


    public static boolean isEmail(String text){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public static boolean isEmail(Editable text){
        return isEmail(text.toString());
    }


    public static boolean isName(String text){

        if(text.length() < 2){
            return false;
        }

        Pattern pattern = Pattern.compile("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static boolean isName(Editable text){
        return isName(text.toString());
    }


    public static boolean isPassword(String text){
        return text.length() >= 8;
    }

    public static boolean isPassword(Editable text){
        return isPassword(text.toString());
    }



}
