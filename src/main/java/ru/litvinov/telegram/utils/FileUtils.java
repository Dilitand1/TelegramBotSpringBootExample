package ru.litvinov.telegram.utils;

import java.io.*;

public class FileUtils {

    public static Long findStringInFile(String s, File file) throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            String tempLine = "";
            while ((tempLine = randomAccessFile.readLine()) != null) {
                if (tempLine.equals(s)) {
                    return randomAccessFile.getFilePointer();
                }
            }
        }
        return null;
    }

    public static boolean deleteStringFromFile(String s, File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String tempLine = "";
            while ((tempLine = reader.readLine()) != null) {
                if (tempLine.equalsIgnoreCase(s)) {
                    writer.newLine();
                    writer.write("");
                    writer.close();
                    return true;
                }
            }
        }
        return false;
    }

    public static void addStringToTheEndOfFile(String s, File file) throws IOException {
        if (findStringInFile(s, file) == null) {
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.append(s).append("\n");
            }
        }
    }

    public static void addStringToTheEndOfRandomFile(String s, File file) throws IOException {
        if (findStringInFile(s, file) == null) {
            try (RandomAccessFile myFile = new RandomAccessFile(file, "rw")) {
                myFile.seek(myFile.length());
                myFile.writeUTF(s + "\n");
            }
        }
    }

    public static void wrtiteAllLinesToFile(String s, File file) {

    }

}
