package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseTest;

public class TC002_LoginTest extends BaseTest
{
	@Test(groups={"Sanity","Master"})
	public void verify_login()
	{
		logger.info("***Starting TC002_LoginTest Test case***");
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
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLogin();
		
		//My Account Page - Validation via two approaches. Both are working
		logger.info("***Validating the Login***");
		MyAccountPage myacc = new MyAccountPage(driver);
		/*
		if (myacc.isMyAccountPageExits() == true)
		{
			System.out.println("Successful Login");
			logger.info("***Test Passed***");
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("Unsuccessful Login");
			logger.info("***Test Failed***");
			logger.debug("***Debug Logs***");
			Assert.assertTrue(false);
		}
		}
		*/
		
		boolean targetpage = myacc.isMyAccountPageExits();
		Assert.assertEquals(targetpage, true, "Login Passed");
		//Assert.assertTrue(targetpage);
		logger.info("***Test Passed***");
		}
		
		catch(Exception e)
		{
			logger.info("***Test Failed***");
			Assert.fail();
		}
		
		logger.info("***Finished TC002_LoginTest Test case***");
		
		
	}

}
