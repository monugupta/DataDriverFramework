package com.rajat.rough;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class screenshot {
	public static String Filename;

	public static void captureScreenshot() throws IOException {
		// call interface takescreenshot
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.navigate().to("https://www.google.com");
		Date d = new Date();
		Filename = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		// now copy abolw file to your location
		FileUtils.copyDirectory(file,
				new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + Filename));
		driver.close();
	}

	public static void main(String[] args) throws IOException {
		screenshot.captureScreenshot();
	}
}