package com.rajat.rough;

import java.io.IOException;

import org.testng.annotations.Test;

import com.rajat.utilites.CustomSoftAssert;
import com.rajat.utilites.TestUtil;

public class SoftAssert  {
	/*
	 public void assertEquals(Object actual, Object expected, Object message) {
	    if((expected == null) && (actual == null)) {
	      return;
	    }
	    if(expected != null) {
	      if (expected.getClass().isArray()) {
	        assertArrayEquals(actual, expected, message);
	        return;
	      } else if (expected.equals(actual)) {
	        return;
	      }
	    }
	    failNotEquals(actual, expected, message);
	  }
*/
@Test
	public void doTest() throws IOException
	{
	CustomSoftAssert c= new CustomSoftAssert();
	c.assertEquals("rajat", "rajat");
	
	c.assertAll();
	  
	}

}
