package com.rajat.listners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.rajat.base.TestBase;
import com.rajat.utilites.ExtentManager;
import com.rajat.utilites.MonitoringMail;
import com.rajat.utilites.TestConfig;
import com.rajat.utilites.TestUtil;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {
	/*
	 * private static ExtentReports extent = ExtentManager
	 * .GetExtent(System.getProperty("user.dir") +
	 * "\\target\\surefire-reports\\html\\Extent.html"); public static
	 * ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>(); public
	 * static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>();
	 */
	public String messageBody;

	public synchronized void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		/* extent.flush(); */

	}

	public synchronized void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		/*
		 * ExtentTest test = extent.createTest(arg0.getName()); classLevelLog.set(test);
		 */
	}

	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

	public synchronized void onTestFailure(ITestResult arg0) {
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String exceptionMessage = Arrays.toString(arg0.getThrowable().getStackTrace());
		testLevelLog.get()
				.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
						+ "</font>" + "</b >" + "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</details>"
						+ " \n");
		String failureLogg = "This Test case got Failed";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testLevelLog.get().log(Status.FAIL, m);

		try {
			testLevelLog.get().addScreenCaptureFromPath(TestUtil.Filename);
			// testLevelLog.get().fatal("Screenshot",testLevelLog.get().addScreencastFromPath(TestUtil.Filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// testLevelLog.get().log(Status.FAIL, "fail");

	}

	public synchronized void onTestSkipped(ITestResult arg0) {
		Markup m = MarkupHelper.createLabel("This test Case got Skipped", ExtentColor.YELLOW);
		testLevelLog.get().skip(m);

	}

	public synchronized void onTestStart(ITestResult arg0) {
		System.out.println("******* On Test Start Execution Started********");

		System.out.println("Class Level Extent Object--->" + classLevelLog.get());
		/*
		 * ExtentTest test = classLevelLog.get().createNode(arg0.getName());
		 * testLevelLog.set(test); testLevelLog.get().log(Status.INFO, "<b>" +
		 * " Execution of Test Case:- " + arg0.getName() + " started" + "</b>");
		 */

		// Now Creating Run Mode-:
		if (!TestUtil.isTestRunnable(arg0.getName(), excel)) {
			throw new SkipException("Runmode is NO");
		}

	}

	public synchronized void onTestSuccess(ITestResult arg0) {
		String successMessage = "<b>" + "This Test Case is Passed" + "</b>";
		Markup m = MarkupHelper.createLabel(successMessage, ExtentColor.GREEN);
		testLevelLog.get().pass(m);

	}

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ISuite suite) {
		MonitoringMail mail = new MonitoringMail();

		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDriverLiveProject/Extent_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
