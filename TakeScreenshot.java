package com.samsung.coreapps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class TakeScreenshot extends UiAutomatorTestCase{
    public void test() throws UiObjectNotFoundException, FileNotFoundException, UnsupportedEncodingException {
        File path = new File("/sdcard/filename.png");
        getUiDevice().takeScreenshot(path);
        }
}
