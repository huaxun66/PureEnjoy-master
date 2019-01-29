package com.watson.pureenjoy.app.app.utils.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CrashSDCardWriter {

    protected PrintWriter writer;

    public CrashSDCardWriter() {
        initWriter();
    }

    public void write(CrashLog log) {
        checkIsValid(log);
        if (writer == null)
            return;
        writer.println(log.toString());
        writer.flush();
    }

    public void initWriter() {
        try {
            String path = CrashUtils.CRASH_LOG_SD_CARD_DIR.getAbsolutePath();

            File file = CrashUtils.createFileIfNotExist(path, "crash_" + getFileName());
            writer = new PrintWriter(new FileOutputStream(file, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkIsValid(CrashLog log) {
        if (writer == null) {
            initWriter();
            return;
        }
        if ((System.currentTimeMillis() / 1000 / 60 / 60 / 24) != (log.getTime() / 1000 / 60 / 60 / 24)) {
            writer = null;
            initWriter();
        }
    }

    protected String getFileName() {
        String fileName = CrashUtils.getLogTime() + ".txt";
        return fileName.replaceAll("-", "");
    }
}
