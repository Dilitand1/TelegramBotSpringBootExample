package ru.litvinov.telegram.utils;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileUtilsTest {

    static File fileToRead;

    @BeforeClass
    public static void setup() {
        fileToRead = new File(FileUtilsTest.class.getClassLoader().getResource("testFileToRead.txt").getPath());
    }

    @Test
    public void addStringToTheFileTest() {
        //RandomAccessFile fr = new RandomAccessFile(this.getClass().getClassLoader().getResource("testFile").getPath(),"r");
    }


    @Test
    public void addStringToTheEndOdFile() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileToRead, "r");
        String needLine = "2";
        String tempLine = "";
        long expectedPosition = 2l;
        long realPosition = 0;
        while ((tempLine = randomAccessFile.readLine()) != null) {
            if (tempLine.equals(needLine)) {
                realPosition = randomAccessFile.getFilePointer();
            }
        }
        Assert.assertEquals(expectedPosition, realPosition);
    }

    @Test
    public void readStringFromFileTest() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(fileToRead, "r");
        String needLine = "2";
        long expectedPosition = FileUtils.findStringInFile(needLine, fileToRead);
        long realPosition = 0;
        String tempLine = "";
        while ((tempLine = randomAccessFile.readLine()) != null) {
            if (tempLine.equals(needLine)) {
                realPosition = randomAccessFile.getFilePointer();
            }
        }
        Assert.assertEquals(expectedPosition, realPosition);
    }
}
