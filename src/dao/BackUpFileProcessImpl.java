package dao;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static views.BoxBorder.blue;
import static views.BoxBorder.reset;

public class BackUpFileProcessImpl implements BackUpFileProcess{
    BackgroundProcessImpl backgroundProcess = BackgroundProcessImpl.createObject(); ;

    public BackUpFileProcessImpl(){

    }


    @Override
    public void performBackup(String sourcePath, String targetFolder) throws IOException {
        Path source = Paths.get(sourcePath);
        String fileName = "BackUp";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timestamp = dateFormat.format(new Date());

        File folder = new File(targetFolder);
        File[] backupFiles = folder.listFiles(new BackupFilenameFilter());

        int latestVersion = getLatestBackupVersion(backupFiles);
        int newVersion = latestVersion + 1;
        int n = (int) backgroundProcess.countLines(sourcePath);
        String stDigit = Integer.toString(n);
        int digit = stDigit.length();
        int divi = (digit > 3) ? (int) Math.pow(10, digit - 3) : 1;
        int remain = n % divi;
        int repeatNumber ;
        String backupFileName = targetFolder + "/" + fileName + "_v" + newVersion + "_" + timestamp + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(source.toFile()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(backupFileName))) {

            String line;
            int i =0;
            while ((line = reader.readLine()) != null) {
                if (i % divi == remain) {
                    repeatNumber = (int) (i / (n/ 100f));
                    System.out.printf( "\r\u001B[31m[ %d/%d ] %s%s[ %.2f%% ]", i, n,"█".repeat(repeatNumber),"\u001B[37m▒".repeat(100 - repeatNumber), i / (n / 100f));
                }
                writer.write(line);
                writer.newLine();
                i++;
            }
            System.out.printf(blue + "\r[ %d/%d ] %s\u001B[34m [%.2f%% ]", n,n, "\u001B[35m█".repeat(100), 100f);
            System.out.println("\n\n✅ File backup completed successfully. \n\t➡️ Backup saved in: " + backupFileName + reset);
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
