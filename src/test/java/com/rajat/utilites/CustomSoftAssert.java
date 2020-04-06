package com.rajat.utilites;

import java.io.IOException;

import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import com.rajat.base.TestBase;

public class CustomSoftAssert extends SoftAssert {

    @Override
    public void onAssertFailure(IAssert a, AssertionError ex) {
    	try {
			TestUtil.captureScreenshot();
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}