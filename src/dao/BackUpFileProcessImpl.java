package dao;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackUpFileProcessImpl implements BackUpFileProcess{
    @Override
    public void performBackup(String sourcePath, String targetFolder) {
        Path source = Paths.get(sourcePath);
        String fileName = "BackUp";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        //check th
        File folder = new File(targetFolder);
        File[] backupFiles = folder.listFiles(new BackupFilenameFilter());

        int latestVersion = getLatestBackupVersion(backupFiles);
        int newVersion = latestVersion + 1;

        String backupFileName = targetFolder + "/" + fileName + "_v" + newVersion + "_" + timestamp + ".txt";

        Path target = Paths.get(backupFileName);

        try {
            Files.copy(source, target);
            System.out.println("✅ File backup completed successfully. \n\t➡️ Backup saved in: " + backupFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class BackupFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.matches(".*_v\\d+_\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\.txt");
        }
    }

    private static int getLatestBackupVersion(File[] backupFiles) {
        int latestVersion = 0;
        for (File file : backupFiles) {
            String filename = file.getName();
            String[] parts = filename.split("_v");
            if (parts.length == 2) {
                try {
                    int version = Integer.parseInt(parts[1].split("_")[0]);
                    latestVersion = Math.max(latestVersion, version);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return latestVersion;
    }

}
