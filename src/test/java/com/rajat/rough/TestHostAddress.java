package com.rajat.rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.rajat.utilites.MonitoringMail;
import com.rajat.utilites.TestConfig;

public class TestHostAddress {
	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {

		MonitoringMail mail = new MonitoringMail();
		String messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
				+ ":8080/job/DataDriverLiveProject/Extent_20Report/";
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		//https://myaccount.google.com/lesssecureapps
		//now add all this  in listeners
	}
}