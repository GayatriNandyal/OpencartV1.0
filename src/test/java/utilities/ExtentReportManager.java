package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseTest;

public class ExtentReportManager implements ITestListener 
{
	public ExtentSparkReporter sparkReporter; //UI of the Report
	public ExtentReports extent; //Populate Common Information on the Report
	public ExtentTest test; //Creating test case entries in the report and update status of the test methods 
	
	String repName;
	
	public void onStart(ITestContext testContext) 
	{
		/*
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.ss");
		Date dt = new Date();
		String currentdatetimestamp = df.format(dt);
		*/
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Dynamically generates timestamp
		repName = "Test-Report-"+timeStamp+".html"; // Report name assigned with the new report name appended with timestamp as and when it is generated and its extension is .html
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); //Specified Location of the report in the project
		
		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of the Report
		sparkReporter.config().setReportName("Opencart Functional Testing"); //Name of the Report
		sparkReporter.config().setTheme(Theme.DARK); //Sets the theme of the report to DARK.
		//sparkReporter.config().setTheme(Theme.STANDARD); //Sets the theme of the report to DARK.
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application: ","Opencart");
		extent.setSystemInfo("Module: ","Admin");
		extent.setSystemInfo("Sub-Module","Customers");
		extent.setSystemInfo("User Name: ",System.getProperty("user.name")); // Dynamically generates the user name who ever generates the report
		extent.setSystemInfo("Environment: ","QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os"); // Dynamically generates the OS used
		extent.setSystemInfo("Operating System: ",os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser"); //Dynamically generates the Browser used
		extent.setSystemInfo("Browser: ",browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		extent.setSystemInfo("Groups", includedGroups.toString());
	}
	
	/*public void onTestStart(ITestResult result) 
	{
		System.out.println("Test Method Execution Started");
	}*/
	
	public void onTestSuccess(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); //Creates a new entry of the test name in the report
		test.assignCategory(result.getMethod().getGroups()); // To display groups in report
		test.log(Status.PASS, "PASSED: "+result.getTestClass().getName()); //Updates the status as Passed
	}
	
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); //Creates a new entry of the test name in the report
		test.assignCategory(result.getMethod().getGroups()); // To display groups in report
		test.log(Status.FAIL, "FAILED: "+result.getTestClass().getName()); //Updates the status as Failed
		test.log(Status.INFO, "Test Case FAILED Reason: "+result.getThrowable().getMessage()); //Updates the Failure message that appears in our console on to our report
	
		try
		{
			String imgPath = new BaseTest().captureScreen(result.getTestClass().getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	
	}
	
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); //Creates a new entry of the test method in the report
		test.assignCategory(result.getMethod().getGroups()); // To display groups in report
		test.log(Status.SKIP, "SKIPPED: "+result.getTestClass().getName()); //Updates the status as Skipped
		test.log(Status.INFO, "Test Case SKIPPED Reason: "+result.getThrowable().getMessage()); //Updates the Skipped message that appears in our console on to our report
	}
	
	public void onFinish(ITestContext context) 
	{
		extent.flush(); // Consolidates and gets all the results as per above methods into a viewable output report format.
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try 
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		/* Sending out an email with report as an attachment.
	    try 
	    {
		  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
	  
	      // Create the email message 
	      ImageHtmlEmail email = new ImageHtmlEmail();
	      email.setDataSourceResolver(new DataSourceUrlResolver(url));
	      email.setHostName("smtp.googlemail.com"); 
	      email.setSmtpPort(465);
	      email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com","password")); 
	      email.setSSLOnConnect(true);
	      email.setFrom("pavanoltraining@gmail.com"); //Sender
	      email.setSubject("Test Results");
	      email.setMsg("Please find Attached Report....");
	      email.addTo("pavankumar.busyqa@gmail.com"); //Receiver 
	      email.attach(url, "extent report", "please check report..."); 
	      email.send(); // send the email 
	  }
	  catch(Exception e) 
	  { 
		  e.printStackTrace(); 
	  } 
	  */
	}
}
