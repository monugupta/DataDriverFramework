package com.rajat.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.rajat.utilites.ExcelReader;
import com.rajat.utilites.ExtentManager;
import com.rajat.utilites.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

//Interagation og CI CD ass well in this project
//using git plugin also but fails test fff
public class TestBase {
	public static WebDriver driver;
	public static String browser;
	public static String testsiteurl;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>();

	// what to do in TestBase class
	/*
	 * Initializing following things-: WebDriver properties file Excel Mail Extent
	 * report DB LOGS
	 */
	@BeforeSuite
	public void setUp() throws IOException {
		log.debug("starting");
		if (driver == null) {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			config.load(fis);
			System.out.println(config.getProperty("browser"));
			fis = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			OR.load(fis);
			// System.out.println(OR.getProperty("browser"));

			// Now for parameters in jenkins build in case of browser-:

			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
				browser = System.getenv("browser");
			} else {
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);

			// Now for parameters in jenkins build in case of url-:
			if (System.getenv("testsiteurl") != null && !System.getenv("testsiteurl").isEmpty()) {
				testsiteurl = System.getenv("testsiteurl");
			} else {
				testsiteurl = config.getProperty("testsiteurl");
			}
			config.setProperty("testsiteurl", testsiteurl);
			if (config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("firefox launch");
			} else if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome launch");
			}
			extent = ExtentManager
					.GetExtent(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\Extent.html");
		}
		driver.navigate().to(config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 5);
	}

	@BeforeClass
	public synchronized void beforeClass() {
		ExtentTest test = extent.createTest(getClass().getSimpleName());
		classLevelLog.set(test);

	}

	@BeforeMethod
	public synchronized void beforeMethod(Method method) {
		ExtentTest test = classLevelLog.get().createNode(method.getName());
		testLevelLog.set(test);
		testLevelLog.get().log(Status.INFO,
				"<b>" + " Execution of Test Case:- " + method.getName() + " started" + "</b>");
		testLevelLog.get().assignAuthor("rajat Gupta");
	}

	@AfterSuite
	public synchronized void tearDown() {

		if (driver != null) {
			driver.quit();
			log.debug("quiting the driver");
			extent.flush();
		}
	}

	public static boolean isElementPresent(String locator) {
		try {
			driver.findElement(By.xpath(locator));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static void verfiyEquals(String expected, String actual) throws IOException {
		try {
			Assert.assertEquals(actual, expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			// reportng-:
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Reporter.log("<a target="\"_blank\" href="+TestUtil.Filename+"height=200
			// width=200></img></a>");

			// for extent report-:
			String exceptionMessage = Arrays.toString(t.getStackTrace());
			testLevelLog.get()
					.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
							+ "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>")
							+ "</details>" + " \n");
			String failureLogg = "This Test case got Failed";
			Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			testLevelLog.get().log(Status.FAIL, m);

			try {
				testLevelLog.get().addScreenCaptureFromPath(TestUtil.Filename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}
	}

	public void click(String locator) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}
		testLevelLog.get().info("Clicking on :" + locator);
	}

	public void type(String locator, String value) {
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		testLevelLog.get().info("Typing in :" + locator + "entereing value" + value);
	}

	public static WebElement dropdown;

	public void GetDrpdown(String locator, String name) {
		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		}
		Select select = new Select(dropdown);
		List<WebElement> links = driver.findElements(By.tagName("option"));
		for (WebElement l : links) {
			if (l.getText().equals(name)) {
				break;
			}
		}
		select.selectByVisibleText(name);
		testLevelLog.get().info("Selecting from dropDown -:" + locator + " Value is " + name);
	}
}
