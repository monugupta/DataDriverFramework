package com.rajat.Testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.rajat.base.TestBase;

public class BankManagerLoginTest extends TestBase {
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException {
		// Now add reporting part in lisleners-:
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		log.debug("inside login test");
		verfiyEquals("rajat", "rajat");
		// Assert.fail();//--Now if it fails rest of the code terminate so we can use
		// soft assertion
		click("bnkmg_XPATH");
		log.debug("login successfully executed");

		Assert.assertTrue(isElementPresent(OR.getProperty("addcusttomer_XPATH")), "login successfully");
		// Thread.sleep(1000);
		// now we can implements this on listeners-:
		Reporter.log("login successfully");
		Assert.fail();

		/*
		 * Reporter.log(
		 * "<a target=\"_blank\" href=\"C:\\Users\\Rajat.Gupta2\\Desktop\\Capture_rajatte.png\">Screenshoot</a>"
		 * );
		 */
	}

	@Test
	public void bankManagerLoginTestt() throws InterruptedException {
		log.debug("inside login test");
		click("home_btm_XPATH");
		click("bnkmg_XPATH");
		log.debug("login successfully executed");
		throw new SkipException("test skip");
		// Assert.assertTrue(isElementPresent(OR.getProperty("openaccount_XPATH")),
		// "login not successfully");

	}
}
