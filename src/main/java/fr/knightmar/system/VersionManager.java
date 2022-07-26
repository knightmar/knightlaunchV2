package fr.knightmar.system;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.knightmar.MainGui;
import fr.knightmar.utils.SendRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class VersionManager {
    private static List<String> versions = new ArrayList();

    public static void init() {
        versions.addAll(VersionManager.getMinecraftVersions(false));
        for (String s : getVersionsInstalled()) {
            System.out.println("Version " + s + " is already installed");
        }
    }

    public static List<String> getVersionsInstalled() {
        versions.clear();
        List<String> versionsInstalled = new ArrayList<>();
        System.out.println("Getting versions installed");
        File folder = MainGui.getInstance().getlauncherDir().resolve("versions").toFile();
        Arrays.stream(Objects.requireNonNull(folder.listFiles())).forEach((file -> {
            if (file.isDirectory()) {
                versionsInstalled.add(file.getName());
            }
        }));
        return versionsInstalled;
    }


    public static boolean isVersionAvailable(String version) {
        return getVersionsInstalled().contains(version);
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
        getVersionsInstalled();
    }

    public static List<String> getMinecraftVersions(boolean withSnapshot) {
        String[] tab;
        List<String> versionsList = new ArrayList<>();
        try {
            tab = SendRequest.sendRequest("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Map versions = gson.fromJson(tab[0], Map.class); // parse

        JsonObject allVersionTree = (JsonObject) gson.toJsonTree(versions);
        JsonArray oldVersionTree = (JsonArray) gson.toJsonTree(allVersionTree.get("versions"));

        oldVersionTree.forEach(version -> {

            JsonObject versionObject = (JsonObject) version;
            if (withSnapshot) {
                versionsList.add(versionObject.get("id").toString().substring(1, versionObject.get("id").toString().length() - 1));
            } else {
                if (versionObject.get("type").toString().contains("release")) {
                    versionsList.add(versionObject.get("id").toString().substring(1, versionObject.get("id").toString().length() - 1));
                }
            }
        });

        return versionsList;
    }
}
