package com.samsung.coreapps;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class CopyFile extends UiAutomatorTestCase {
    private static final int COUNT = 100;

    public void test() throws UiObjectNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        PrintWriter copyDurationWriter = new PrintWriter("/data/local/tmp/copyfileval.csv", "UTF-8");
        PrintWriter deleteDurationWriter = new PrintWriter("/data/local/tmp/deletefileval.csv", "UTF-8");
        copyDurationWriter.println("Copy file duration (millis);");
        deleteDurationWriter.println("Delete file duration (millis);");
        findAndRunApp();
        for (int i = 0; i < COUNT; i++) {
            System.out.println("NUMBER OF ITERATION: " + (i+1));
            selectFileAndCopy(copyDurationWriter);
            deleteFile(deleteDurationWriter);
            exit();
        }
        copyDurationWriter.close();
        deleteDurationWriter.close();
    }

    // Here will be called for all other functions
    private void findAndRunApp() throws UiObjectNotFoundException {
        // Go to main screen
        getUiDevice().pressHome();
        // Find menu button
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        // Click on menu button and wait new window
        allAppsButton.clickAndWaitForNewWindow();
        // Find App tab
        UiObject appsTab = new UiObject(new UiSelector().text("Apps"));
        // Click on app tab
        appsTab.click();
        // Find scroll object (menu scroll)
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        // Set the swiping mode to horizontal (the default is vertical)
        appViews.setAsHorizontalList();
        // Find Messaging application
        UiObject settingsApp = appViews.getChildByText(new UiSelector().className("android.widget.TextView"), "My Files");
        // Open Messaging application
        settingsApp.clickAndWaitForNewWindow();

        // Validate that the package name is the expected one
        UiObject settingsValidation = new UiObject(new UiSelector().packageName("com.sec.android.app.myfiles"));
        assertTrue("Unable to detect MyFiles", settingsValidation.exists());
    }

    private void selectFileAndCopy(PrintWriter copyDurationWriter) throws UiObjectNotFoundException {
        UiObject homeButton = new UiObject(new UiSelector().description("Home"));
        UiObject shareViaButton = new UiObject(new UiSelector().description("Share via"));
        while (homeButton.exists() || shareViaButton.exists()) {
            getUiDevice().pressBack();
            sleep(100);
        }

        UiObject allFilesButton = new UiObject(new UiSelector().className("android.widget.TextView").text("All files"));
        while (!allFilesButton.exists()) {
            sleep(1);
        }
        allFilesButton.click();
        new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text("abrvalg"));
        new UiObject(new UiSelector().className("android.widget.TextView").text("abrvalg")).click();
        UiObject folderToCopyBox = new UiObject(new UiSelector().className("android.widget.CheckBox").instance(0));
        while (!folderToCopyBox.exists()) {
            sleep(1);
        }
        folderToCopyBox.click();
        UiObject moreOptions = new UiObject(new UiSelector().description("More options"));
        while (!moreOptions.exists()) {
            sleep(1);
        }
        moreOptions.click();
        UiObject copyButton = new UiObject(new UiSelector().className("android.widget.TextView").text("Copy"));
        while (!copyButton.exists()) {
            sleep(1);
        }
        copyButton.click();
        new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text("cys"));
        new UiObject(new UiSelector().text("cys")).click();
        UiObject pasteHereButton = new UiObject(new UiSelector().className("android.widget.TextView").text("Paste here"));
        while (!pasteHereButton.exists()) {
            sleep(1);
        }
        pasteHereButton.click();
        UiObject copy = new UiObject(new UiSelector().className("android.widget.TextView").text("Copy"));
        long timeBeforeMillis = System.currentTimeMillis();
        while (copy.exists()) {
            sleep(1);
        }
        long copyDuration = System.currentTimeMillis() - timeBeforeMillis;
        copyDurationWriter.println(copyDuration + ";");
        System.out.println("COPY DURATION IS: " + copyDuration + " millis");
    }

    private void deleteFile(PrintWriter deleteDurationWriter) throws UiObjectNotFoundException {

        UiObject fileBox = new UiObject(new UiSelector().className("android.widget.CheckBox").instance(0));
        fileBox.clickAndWaitForNewWindow();
        new UiObject(new UiSelector().description("Delete")).clickAndWaitForNewWindow();
        new UiObject(new UiSelector().text("OK")).click();
        UiObject delete = new UiObject(new UiSelector().className("android.widget.TextView").text("Delete"));
        long timeBeforeMillis = System.currentTimeMillis();
        while (delete.exists()) {
            sleep(1);
        }
        long deleteDuration = System.currentTimeMillis() - timeBeforeMillis;
        deleteDurationWriter.println(deleteDuration + ";");
        System.out.println("DELETE DURATION IS: " + deleteDuration + " millis");
    }

    private void exit() throws UiObjectNotFoundException {
        new UiObject(new UiSelector().description("Home")).click();
        sleep(100);
    }
}
