package com.samsung.coreapps;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Contacts extends UiAutomatorTestCase {
    private static final int COUNT = 50;

    public void test() throws UiObjectNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        // Default parameters
        String name = "alan";
        for (int i = 0; i < COUNT; i++) {
            findAndRunApp();
            searchContact(name);
            viewContactHistory();
            returnToMainScreen();
        }
    }

    private void findAndRunApp() throws UiObjectNotFoundException {
        getUiDevice().pressHome();
        UiObject phoneApp = new UiObject(new UiSelector().className("android.widget.TextView").description("Contacts"));
        phoneApp.click();
    }

    private void searchContact(String contactName) throws UiObjectNotFoundException {
        int aX = 70;
        int aY = 1000;
        int lX = 645;
        int lY = aY;
        int nX = 500;
        int nY = 1115;
        sleep(200);

        UiObject searchTextField = new UiObject(new UiSelector().className("android.widget.EditText").description("Search query"));
        while (!searchTextField.exists()) {
            sleep(1);
        }
        searchTextField.click();
        sleep(200);
        getUiDevice().click(aX, aY);
        sleep(100);
        getUiDevice().click(lX, lY);
        sleep(100);
        getUiDevice().click(aX, aY);
        sleep(100);
        getUiDevice().click(nX, nY);
        sleep(100);
    }

    private void viewContactHistory() throws UiObjectNotFoundException {
        int startX = 380;
        int startY = 1240;
        int endX = startX;
        int endY = 200;
        int steps = 80;

        UiObject contact = new UiObject(new UiSelector().className("android.widget.TextView").text("Alan"));
        while (!contact.exists()) {
            sleep(1);
        }
        contact.clickAndWaitForNewWindow();
        getUiDevice().pressMenu();
        new UiObject(new UiSelector().className("android.widget.TextView").text("History")).click();
        sleep(400);
        getUiDevice().swipe(startX, startY, endX, endY, steps);
        sleep(200);
        getUiDevice().swipe(endX, endY, startX, startY, steps);
    }

    private void returnToMainScreen() throws UiObjectNotFoundException {
        // Find New contact button
        UiObject newContactButton = new UiObject(new UiSelector().className("android.widget.ImageButton").description("Create new contact"));

        // Press back button while new contact button doesn't exist
        while (!newContactButton.exists()) {
            getUiDevice().pressBack();
            sleep(50);
        }
        UiObject clearQuery = new UiObject(new UiSelector().className("android.widget.ImageView").description("Clear query"));
        clearQuery.click();
        getUiDevice().pressBack();
    }
}
