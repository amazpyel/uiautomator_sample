package com.samsung.coreapps;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

/**
 * @author Oleksandr Pylkevych <br>
 * @author o.pylkevych@gmail.com <br>
 * <br>
 *         <b>Steps:</b><br>
 *         1. Launch Messaging app<br>
 *         2. Compose new message<br>
 *         3. Input recipeint number<br>
 *         4. Input text<br>
 *         5. Tap on back button<br>
 *         6. Tap on back button<br>
 *         7. Tap on menu<br>
 *         8. Select draft messages<br>
 *         9. Tap on menu<br>
 *         10. Select Delete<br>
 *         11. Check item<br>
 *         12. Tap on Delete<br>
 *         13. Tap on OK<br>
 * <br>
 *         <b>For run test you can uses next command: </b></br> adb shell
 *         uiautomator runtest /data/local/tmp/AndroidPerformance.jar -c
 *         com.coreapps.SendMessage<br>
 * <br>
 *         <b>Test result in the following path:</b><br>
 *         1. /data/local/tmp/draftfulltestval.dat<br>
 *         2. /data/local/tmp/draftdelval.dat
 */
public class SendMessage extends TestParentUiAutomator {
    private static final int COUNT = 100;

    public void test() throws UiObjectNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        // Default parameters
        String toNumber = "123";
        String text = "Tizen";
        PrintWriter testDurationWriter = new PrintWriter("/data/local/tmp/draftfulltestval.csv", "UTF-8");
        PrintWriter deleteDraftDurationWriter = new PrintWriter("/data/local/tmp/draftdelval.csv", "UTF-8");
        testDurationWriter.println("Test duration (millis);");
        deleteDraftDurationWriter.println("Delete draft duration (millis);");
        for (int i = 0; i < COUNT; i++) {
            long startTest = findAndRunApp();
            createMessage(toNumber, text);
            exitToMainWindow();
            System.out.println("NUMBER OF ITERATION: " + (i + 1));
            deleteDraft(deleteDraftDurationWriter);
            getUiDevice().pressBack();
            getUiDevice().pressBack();
            long testDuration = System.currentTimeMillis() - startTest;
            testDurationWriter.println(testDuration + ";");
            System.out.println("TEST DURATION IS: " + testDuration + " millis");
        }
        testDurationWriter.close();
        deleteDraftDurationWriter.close();
    }

    private long findAndRunApp() throws UiObjectNotFoundException {
        getUiDevice().pressHome();
        clickOnText("Messaging");
        UiObject appValidation = new UiObject(new UiSelector().packageName("com.android.mms"));
        assertTrue("Unable to detect Messaging", appValidation.exists());
        return System.currentTimeMillis();
    }

    private void createMessage(String toNumber, String text) throws UiObjectNotFoundException {
        clickOnImage("Compose");
        UiObject toBox = new UiObject(new UiSelector().text("Enter recipient"));
        toBox.setText(toNumber);

        UiObject textBox = new UiObject(new UiSelector().text("Enter message"));
        textBox.setText(text);
    }

    private void deleteDraft(PrintWriter deleteDraftDurationWriter) throws UiObjectNotFoundException {
        getUiDevice().pressMenu();
        clickOnText("Draft messages");
        getUiDevice().pressMenu();
        clickOnText("Delete messages");
        clickOnCheckbox(1);
        clickOnText("Delete");
        clickOnText("OK");
        long timeBeforeMillis = System.currentTimeMillis();
        UiObject deleted = new UiObject(new UiSelector().className("android.widget.TextView").text("No messages"));
        while (!deleted.exists()) {
            sleep(1);
        }
        long deleteDuration = System.currentTimeMillis() - timeBeforeMillis;
        deleteDraftDurationWriter.println(deleteDuration + ";");
        System.out.println("DELETE TEXT DRAFT DURATION IS: " + deleteDuration + " millis");
    }

    private void exitToMainWindow() {
        // Find New message button
        UiObject newMessageButton = new UiObject(new UiSelector().className("android.widget.TextView").description("Compose"));

        // Press back button while new message button doesn't exist
        while (!newMessageButton.exists()) {
            getUiDevice().pressBack();
            sleep(500);
        }
    }
}
