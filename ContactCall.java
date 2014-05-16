package com.samsung.coreapps;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * @author Oleksandr Pylkevych <br>
 * @author o.pylkevych@gmail.com <br>
 * <br>
 *         <b>Steps:</b><br>
 *         1. Launch Contacts app<br>
 *         2. Create new Contact<br>
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
 * 13. Tap on OK<br>
 * <br>
 *         <b>For run test you can uses next command: </b></br> adb shell
 *         uiautomator runtest /data/local/tmp/AndroidPerformance.jar -c
 *         com.coreapps.SendMessage<br>
 * <br>
 *         <b>Test result in the following path:</b><br>
 *         1. /data/local/tmp/draftfulltestval.dat<br>
 *         2. /data/local/tmp/draftdelval.dat
 */
public class ContactCall extends UiAutomatorTestCase {

    private static final int COUNT = 100;

    public void test() throws UiObjectNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        // Default parameters
        String name = "Alan";
        String number = "123456";
        for(int i = 0; i < COUNT; i++) {
        findAndRunApp();
        createContact(name, number);
        call(number);
        deleteContact(name);
        }
    }

    private void findAndRunApp() throws UiObjectNotFoundException {
        
        getUiDevice().pressHome();
        UiObject phoneApp = new UiObject(new UiSelector().className("android.widget.TextView").description("Contacts"));
        phoneApp.click();
    }

    private void createContact(String name, String number) throws UiObjectNotFoundException {
        UiObject contactsTab = new UiObject(new UiSelector().className("android.widget.TextView").text("Contacts"));
        while (!contactsTab.exists()) {
            sleep(1);
        }
        contactsTab.click();
        UiObject newMessageButton = new UiObject(new UiSelector().className("android.widget.ImageButton").description("Create new contact"));
        while (!newMessageButton.exists()) {
            sleep(1);
        }
        newMessageButton.click();
        long timeBeforeMillis = System.currentTimeMillis();
        UiObject toBox = new UiObject(new UiSelector().text("Name"));
        UiObject textBox = new UiObject(new UiSelector().text("Phone number"));
        while (!toBox.isFocused()) {
            sleep(0);
        }
        long createContactWindow = System.currentTimeMillis() - timeBeforeMillis;
        System.out.println("CREATE WINDOW DURATION IS: " + createContactWindow + " millis");
        toBox.setText(name);
        textBox.setText(number);

        new UiObject(new UiSelector().text("Save")).click();
    }

    private void call(String number) throws UiObjectNotFoundException {
        UiObject numberToCall = new UiObject(new UiSelector().text(number));
        while (!numberToCall.exists()) {
            sleep(1);
        }
        numberToCall.clickAndWaitForNewWindow();
    }

    private void deleteContact(String name) throws UiObjectNotFoundException {
        new UiObject(new UiSelector().className("android.widget.Button").text("End call")).clickAndWaitForNewWindow();
        UiObject favoritesButton = new UiObject(new UiSelector().className("android.widget.TextView").description("Edit"));
        while(!favoritesButton.exists()) {
            sleep(1);
        }
        getUiDevice().pressMenu();
        new UiObject(new UiSelector().text("Delete")).click();
        UiObject okButton = new UiObject(new UiSelector().text("OK"));
        while (!okButton.exists()) {
            sleep(1);
        }
        okButton.click();
    }
}
