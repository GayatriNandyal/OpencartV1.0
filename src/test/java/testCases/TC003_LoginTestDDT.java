package testCases;
/*
   Data is Valid - Login Successful - Test Pass - Logout
   Data is Valid - Login Unsuccessful - Test Fail
   
   Data is Invalid - Login Successful - Test Fail - Logout
   Data is Invalid - Login Unsuccessful - Test Fail
 */

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;


import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;
import utilities.DataProviders;
import utilities.ExcelUtilityFile;

public class TC003_LoginTestDDT extends BaseTest
{
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class, groups="DataDriven") // Getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp) throws IOException
	{
					
		logger.info("***Starting TC003_LoginTestDDT Test case***");
		try
		{
	
		//Home Page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***Clicked on My Account***");
		hp.clickLogin();
		logger.info("***Clicked on Login***");
		
		//Login Page
		LoginPage lp = new LoginPage(driver);
		logger.info("***Providing Login Details***");
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//My Account Page - Validating the Login against the expected result in column3 of data excel 'valid/invalid'
		logger.info("***Validating the Login against the expected result in column3 valid/invalid***");
		MyAccountPage myacc = new MyAccountPage(driver);
		boolean targetpage = myacc.isMyAccountPageExits();
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetpage == true)
			{
				System.out.println("Successful Login");
				logger.info("***Test Passed***");
				myacc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("Unsuccessful Login");
				logger.info("***Test Failed***");
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetpage == true)
			{
				System.out.println("Successful Login");
				logger.info("***Test Failed***");
				myacc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				System.out.println("Unsuccessful Login");
				logger.info("***Test Passed***");
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e)
		{
			logger.info("***Test Failed***");
			Assert.fail();
		}
		
		logger.info("***Finished TC003_LoginTestDDT Test case***"); 
}
}
