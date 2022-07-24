package fr.knightmar.utils;

public class AccountUtils {
    public static boolean checkValidPseudo(String pseudo) {
        return pseudo.matches("^[a-zA-Z0-9_]{3,16}$") && pseudo.length() > 2 && pseudo.length() < 17;
    }
}
