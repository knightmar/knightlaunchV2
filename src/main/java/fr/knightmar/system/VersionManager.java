package fr.knightmar.system;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.flowarg.flowlogger.ILogger;
import fr.flowarg.flowupdater.FlowUpdater;
import fr.flowarg.flowupdater.download.DownloadList;
import fr.flowarg.flowupdater.download.IProgressCallback;
import fr.flowarg.flowupdater.download.Step;
import fr.flowarg.flowupdater.utils.UpdaterOptions;
import fr.flowarg.flowupdater.versions.VanillaVersion;
import fr.knightmar.MainGui;
import fr.knightmar.utils.SendRequest;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VersionManager {
    private static List<String> versions = new ArrayList();

    private static IProgressCallback callback;

    public static void init() {
        callback = new IProgressCallback() {
            private final DecimalFormat decimalFormat = new DecimalFormat("#.#");

            @Override
            public void init(ILogger logger) {
            }

            @Override
            public void step(Step step) {
                Platform.runLater(() -> {
                    MainGui.getInstance().getMainPane().setInfoLabel(step.name());
                });
            }

            @Override
            public void onFileDownloaded(Path path) {
//                Platform.runLater(() -> {
//                    fileLabel.setText(path.getFileName().toString());
//                });
            }

            @Override
            public void update(DownloadList.DownloadInfo info) {
                double progress = (double) info.getDownloadedBytes() / info.getTotalToDownloadBytes();
                Platform.runLater(() -> {
                    MainGui.getInstance().getMainPane().setProgressBar(progress);
                });
            }
        };


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
        if (folder.exists() && folder.listFiles() != null) {
            for (File file : folder.listFiles()) {
                versionsInstalled.add(file.getName());
            }
        }
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
                    .withProgressCallback(callback)
                    .build();

            updater.update(Paths.get(MainGui.getInstance().getlauncherDir() + "\\versions\\" + version));
        } catch (Exception e) {
            System.out.println("Error while installing version " + version);
            throw new RuntimeException(e);
        }

        File file = new File(MainGui.getInstance().getlauncherDir() + "\\versions\\" + version + "\\natives\\" + "natives.kn");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

            if (versionObject.get("type").getAsString().equals("release")) {
                int versionNumber = Integer.parseInt(versionObject.get("id").getAsString().split("\\.")[1]);
                if (versionNumber >= 6) {
                    versionsList.add(versionObject.get("id").getAsString());
                }
            }


        });
        System.out.println("Versions list : " + versionsList);
        return versionsList;
    }
}
