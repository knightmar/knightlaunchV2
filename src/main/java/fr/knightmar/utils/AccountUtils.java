package fr.knightmar.utils;

import fr.knightmar.MainGui;

public class AccountUtils {
    public static boolean checkValidPseudo(String pseudo) {
        return pseudo.matches("^[a-zA-Z0-9_]{3,16}$") && pseudo.length() > 2 && pseudo.length() < 17;
    }

    public static String getPseudo() {
        MainGui.getInstance().getSaver().load();
        return MainGui.getInstance().getSaver().get("pseudo");
    }

    public static String getRefreshToken() {
        MainGui.getInstance().getSaver().load();
        return MainGui.getInstance().getSaver().get("refreshToken");
    }

    public static String getUuid() {
        MainGui.getInstance().getSaver().load();
        return MainGui.getInstance().getSaver().get("uuid");
    }

    public static String getAccessToken() {
        MainGui.getInstance().getSaver().load();
        return MainGui.getInstance().getSaver().get("accessToken");
    }
}
