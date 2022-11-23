package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import static java.lang.System.currentTimeMillis;
import static java.lang.Thread.sleep;

public class Wget implements Runnable {
    public static final int ONE_SECOND = 1000;

    public static final int NUMBER_OF_PARAMETERS = 3;
    private final String url;
    private final String fileName;
    private final int speed;

    public Wget(String url, String fileName, int speed) {
        this.url = url;
        this.fileName = fileName;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = currentTimeMillis();
            int downloadData = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadData += bytesRead;
                if (downloadData >= speed) {
                    long operationTime = currentTimeMillis() - startTime;
                    if (operationTime < ONE_SECOND) {
                        sleep(ONE_SECOND - operationTime);
                    }
                    downloadData = 0;
                    startTime = currentTimeMillis();
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    private static void checkValidate(String[] args) {
        if (args.length < NUMBER_OF_PARAMETERS) {
            throw new IllegalArgumentException("the number of arguments is not the same expected");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        checkValidate(args);
        String url = args[0];
        String fileName = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, fileName, speed));
        wget.start();
        wget.join();
    }
}
