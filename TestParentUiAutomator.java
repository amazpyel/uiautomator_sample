package com.samsung.coreapps;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TestParentUiAutomator extends UiAutomatorTestCase{
    protected void clickOnText(String text) throws UiObjectNotFoundException {
        UiObject objectWithText = new UiObject(new UiSelector().text(text));
        while (!objectWithText.exists()) {
            sleep(1);
        }
        objectWithText.click();
    
    }

    protected void clickOnImage(String desciption) throws UiObjectNotFoundException {
        UiObject imageButton = new UiObject(new UiSelector().className("android.widget.TextView").description(desciption));
        while (!imageButton.exists()) {
            sleep(1);
        }
        imageButton.click();
        
    }
    
    protected void clickOnCheckbox(int index) throws UiObjectNotFoundException {
        UiObject checkbox = new UiObject(new UiSelector().className("android.widget.CheckBox").instance(index));
        while (!checkbox.exists()) {
            sleep(1);
        }
        checkbox.click();
    }
}
