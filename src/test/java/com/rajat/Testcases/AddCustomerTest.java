package com.rajat.Testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rajat.base.TestBase;
import com.rajat.utilites.TestUtil;

public class AddCustomerTest extends TestBase {
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(String first_name, String last_name, String postCode, String alert, String runmode) {
		// driver.findElement(By.xpath(OR.getProperty("bnkmg"))).click();

		if (!runmode.equalsIgnoreCase("Y")) {
			throw new SkipException("RunMode for this data provider is Off");
		}

		click("addcusttomer_XPATH");
		type("firstname_XPATH", first_name);
		type("lastname_XPATH", last_name);
		type("postalcode_XPATH", postCode);
		click("add_XPATH");
		Alert alerrt = wait.until(ExpectedConditions.alertIsPresent());
		alerrt = driver.switchTo().alert();
		Assert.assertTrue(alerrt.getText().contains(alert));
		alerrt.accept();

		// Assert.fail();
	}

}
