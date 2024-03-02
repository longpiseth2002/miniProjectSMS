package dao;

import java.io.IOException;

public interface BackUpFileProcess {
    void performBackup(String sourcePath, String targetFolder) throws IOException;

}

