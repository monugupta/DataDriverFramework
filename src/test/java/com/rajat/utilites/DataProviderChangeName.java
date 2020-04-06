package com.rajat.utilites;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.ITest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.rajat.base.TestBase;

public class DataProviderChangeName extends TestBase implements ITest {

	private ThreadLocal<String> testName = new ThreadLocal<>();

	@DataProvider(name = "dph") // name of data provider
	public static Object[][] getDataHashtable(Method m) {
		System.out.println(m.getName());
		String sheetName = m.getName();// this can pick the test name so please keep sheetname same as test name.
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		Object[][] data = new Object[rows - 1][1];
		Hashtable<String, String> table = null;
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			table = new Hashtable<String, String>();
			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
				data[rowNum - 2][0] = table;
				// data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}

	@BeforeMethod
	public void BeforeMethod(Method method, Object[] testData) {
		testName.set(method.getName() + "_" + testData[0]);
	}

	@Override
	public String getTestName() {
		return testName.get();
	}
}
