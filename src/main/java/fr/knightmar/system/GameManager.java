package fr.knightmar.system;

import fr.knightmar.utils.AccountUtils;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.*;

import java.util.Objects;

public class GameManager {
    public static void initLaunch(String version) {
        if (!VersionManager.isVersionAvailable(version)) {
            try {
                VersionManager.installVersion(version);
                launch(version);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Version " + version + " is already installed");
            try {
                launch(version);
            } catch (LaunchException e) {
                e.printStackTrace();
            }
        }
    }

    public static void launch(String version) throws LaunchException {
        GameInfos infos;
        if (Objects.equals(version, "1.7.10")) {
            infos = new GameInfos("knightLauncherV2" + "/versions/" + version, new GameVersion(version, GameType.V1_7_10), new GameTweak[]{});
        } else {
            infos = new GameInfos("knightLauncherV2" + "/versions/" + version, new GameVersion(version, GameType.V1_8_HIGHER), new GameTweak[]{});
        }


        AuthInfos authInfos = new AuthInfos(AccountUtils.getPseudo(), AccountUtils.getAccessToken(), AccountUtils.getUuid());

        ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(infos, GameFolder.FLOW_UPDATER, authInfos);
        ExternalLauncher launcher = new ExternalLauncher(profile);

        launcher.launch();
    }
}
