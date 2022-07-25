package fr.knightmar.system;

public class GameManager {
    public static void launchGame(String version) {
        VersionManager.updateVersionsAvailability();
        if (!VersionManager.isVersionAvailable(version)) {
            try {
                VersionManager.installVersion(version);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("Version " + version + " is already installed");
        }
    }
}
