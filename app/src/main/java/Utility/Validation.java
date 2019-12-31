package Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utility.Const.SUCCESS;

/**
 * Created by sc-147 on 09-Mar-18.
 */

public class Validation {
    public static String errorMessage;

    public static void validateEmail(String email) {
        if (email.equals("")) {
            errorMessage = "Please enter Email address";
        } else {
            boolean flag = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            if (!flag) {
                errorMessage = "Please enter valid Email address in format abc@abc.com";
            } else {
                errorMessage = SUCCESS;
            }
        }
    }

    public static void validateUserName(String name, String isfor) {
        if (name.equals("")) {
            errorMessage = "Please enter " + isfor;
        } else if (!name.matches("[a-zA-Z ]*")) {
            errorMessage = "Only alphabets and space are allowed";
        } else {
            errorMessage = SUCCESS;
        }
    }

    public static void validateEmoji(String email) {
        String EmojiregexPattern = "[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+";
        Pattern p = Pattern.compile(EmojiregexPattern);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            errorMessage = "Emoji not allowed";
        } else {
            errorMessage = SUCCESS;
        }
    }
}