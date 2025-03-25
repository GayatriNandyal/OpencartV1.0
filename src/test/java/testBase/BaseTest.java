package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest 
{
public static WebDriver driver;
public Logger logger;
public Properties p;

	
	@SuppressWarnings("deprecation")
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		
		// Loading Config.properties file
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		// For logs generation
		logger = LogManager.getLogger(this.getClass());
		
		// If we have Grid Setup and our execution(local/remote) depends on the execution_env variable of config.properties file
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			// For OS
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if (os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No Matching OS");
				return;
			}
			// For Browser
			switch(br.toLowerCase())
			{
			case "chrome": 
				capabilities.setBrowserName("chrome");
				break;
			case "edge": 
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			case "firefox": 
				capabilities.setBrowserName("firefox");
				break;
			case "safari": 
				capabilities.setBrowserName("safari");
				break;
			default:
				System.out.println("No Matching Browser");
				return;
			}
			
			//URI uri = URI.create("http://localhost:4444/wd/hub");
			//driver = new RemoteWebDriver(uri.toURL(), capabilities);
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
		// To launch a desired browser
		 switch(br.toLowerCase())
		 {
		  case "chrome": 
			driver = new ChromeDriver(); 
			break;
		  case "edge": 
			driver = new EdgeDriver(); 
			break;
		  case "firefox": 
			driver = new FirefoxDriver(); 
			break;
		  default: 
			System.out.println("Invalid Browser"); 
			return;
		 }
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("https://tutorialsninja.com/demo"); // Hard coding the URL
		driver.get(p.getProperty("appURL")); // Getting the url from config.perpoerties file.
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void teardown()
	{
		driver.quit();
	}

	
	public String randomString()
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
	    return generatedstring;
	}
	
	public String randomNumber()
	{
		String generatednumber = RandomStringUtils.randomNumeric(10);
	    return generatednumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedString = RandomStringUtils.randomAlphanumeric(8);
	    return generatedString;
	}
	
	public String captureScreen(String tname) throws IOException 
	{

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}
	
}
