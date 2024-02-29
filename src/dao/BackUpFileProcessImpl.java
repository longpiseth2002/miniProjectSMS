package dao;

import java.io.*;
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

        File folder = new File(targetFolder);
        File[] backupFiles = folder.listFiles(new BackupFilenameFilter());

        int latestVersion = getLatestBackupVersion(backupFiles);
        int newVersion = latestVersion + 1;

        String backupFileName = targetFolder + "/" + fileName + "_v" + newVersion + "_" + timestamp + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(source.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }

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
