package com.rajat.Testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.rajat.base.TestBase;
import com.rajat.utilites.TestUtil;

public class AddNewCustomerTest extends TestBase {
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dph")
	public void addNewCustomerTest(Hashtable<String, String> data) {
		// driver.findElement(By.xpath(OR.getProperty("bnkmg"))).click();

		if (!data.get("runmode").equalsIgnoreCase("Y")) {
			throw new SkipException("RunMode for this data provider is Off");
		}
		// click("bnkmg_XPATH");
		click("addcusttomer_XPATH");
		type("firstname_XPATH", data.get("first_name"));
		type("lastname_XPATH", data.get("last_name"));
		type("postalcode_XPATH", data.get("postCode"));
		click("add_XPATH");
		Alert alerrt = wait.until(ExpectedConditions.alertIsPresent());
		alerrt = driver.switchTo().alert();
		Assert.assertTrue(alerrt.getText().contains(data.get("alert")));
		alerrt.accept();

		// Assert.fail();
	}
}
