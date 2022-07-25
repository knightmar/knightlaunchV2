package fr.knightmar.system;

import com.google.gson.internal.bind.util.ISO8601Utils;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.knightmar.MainGui;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class VersionManager {
    private static Map<String, Boolean> versions = new HashMap<>();

    public static void init() {
        versions.put("1.7.10", false);
        versions.put("1.12.2", false);
        updateVersionsAvailability();
    }

    public static List<String> getVersions() {
        updateVersionsAvailability();
        List<String> list = new ArrayList<>();
        versions.forEach((key, value) -> {
            list.add(key);
        });
        return list;
    }

    public static void updateVersionsAvailability() {
        versions.replaceAll((v, c) -> false);
        File versionDir = MainGui.getInstance().getlauncherDir().resolve("versions").toFile();
        if (versionDir.exists()) {
            File[] files = versionDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.exists()) {
                        if (file.isDirectory()) {
                            String version = file.getName();
                            if (versions.containsKey(version)) {
                                versions.put(version, true);
                            }
                        }
                    }
                }
            }
        }
        versions.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public static boolean isVersionAvailable(String version) {
        updateVersionsAvailability();
        return versions.get(version);
    }

    public static void installVersion(String version) {
        try {
            VanillaVersion gameVersion = new VanillaVersion
                    .VanillaVersionBuilder()
                    .withName(version)
                    .build();
            UpdaterOptions options = new UpdaterOptions
                    .UpdaterOptionsBuilder()
                    .build();
            FlowUpdater updater = new FlowUpdater.FlowUpdaterBuilder()
                    .withVanillaVersion(gameVersion)
                    .withUpdaterOptions(options)
                    .withLogger(MainGui.getInstance().getLogger())
                    .build();

            updater.update(Paths.get(MainGui.getInstance().getlauncherDir() + "\\versions\\" + version));
        } catch (Exception e) {
            System.out.println("Error while installing version " + version);
            throw new RuntimeException(e);
        }

        updateVersionsAvailability();
    }
}
