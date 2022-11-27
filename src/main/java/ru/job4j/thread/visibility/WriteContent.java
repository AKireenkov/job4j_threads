package ru.job4j.thread.visibility;

import java.io.*;

public final class WriteContent {
    private final File file;
    public WriteContent(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i++) {
                o.write(content.getBytes());
            }
        }
    }
}
