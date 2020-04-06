package com.rajat.utilites;

import java.io.IOException;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;

public class CustomHardAssert extends Assertion{

        @Override
        public void onAssertFailure(IAssert assertCommand, AssertionError ex) {
             try {
				TestUtil.captureScreenshot();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
}