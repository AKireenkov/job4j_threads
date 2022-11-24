package ru.job4j.thread.visibility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class WriteContent {
    private final File file;

    public WriteContent(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (OutputStream o = new FileOutputStream(file)) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        }
    }
}
