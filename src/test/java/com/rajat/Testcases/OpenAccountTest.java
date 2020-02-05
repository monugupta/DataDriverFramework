package com.rajat.Testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.rajat.base.TestBase;
import com.rajat.utilites.TestUtil;

public class OpenAccountTest extends TestBase {
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(String Customer, String Currency) {
		click("openaccount_XPATH");
		GetDrpdown("Customer_XPATH", Customer);
		GetDrpdown("Currency_XPATH", Currency);
		click("process_XPATH");
		Alert alerrt = wait.until(ExpectedConditions.alertIsPresent());
		alerrt = driver.switchTo().alert();
		Assert.assertTrue(alerrt.getText().contains("Account created successfully "));
		alerrt.accept();

	}

}
