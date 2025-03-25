package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseTest;

public class TC001_AccountRegistrationTest extends BaseTest
{
		
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("***Starting 'TC001_AccountRegistrationTest' Test case***");
		
		try
		{
		//Home Page 
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("***Clicked on My Account Link***");
		hp.clickRegister();
		logger.info("***Clicked on Register Link***");
		
		//Account Registration Page
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		logger.info("***Providing the Customer Details***");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password = randomAlphaNumeric();
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		
		logger.info("***Validating the Expected Message***");
		
		String confmsg = regpage.getConfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			logger.info("***Test Passed***");
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("***Test Failed***");
			logger.debug("***Debug Logs***");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		
		catch(Exception e)
		{
			Assert.fail();
		}
		
		logger.info("***Finished 'TC001_AccountRegistrationTest' Test case***");
	}

}
