package utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import stepdefinitions.AccuWeatherStepDef;

public class BaseClass {
	public static WebDriver driver;
	public static PropertyHandler propertyHandler;
	public static Logger logger;

	public BaseClass() {
		logger = utils.LoggerFile.logConfig(BaseClass.class.getName());
		propertyHandler = new PropertyHandler();
	}

	public static void initialization() throws Throwable {
        
		String browserName = propertyHandler.readProperty("Browser");
		logger.info(" browserName " +browserName);
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + File.separator + "\\chromedriver.exe");
			driver = new ChromeDriver();

		}

		else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + File.separator + "\\geckodriver.exe");
			driver = new FirefoxDriver();
			 
		}
		
		else if(browserName.equals("msedge")) {
			// Set the driver path
			System.setProperty("webdriver.edge.driver", "C://EdgeDriver.exe");
			System.setProperty("webdriver.edge.driver",System.getProperty("user.dir") + File.separator + "\\msedgedriver.exe");		
			// Start Edge Session
			WebDriver driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Long.valueOf(propertyHandler.readProperty("ImplicitWaitInSeconds")),
				TimeUnit.SECONDS);
		logger.info("Browser opened successfully");
       driver.get(propertyHandler.readProperty("Accu_Weather_Host"));
       logger.info("url  launched successfully");
	}
	
	private static DesiredCapabilities DesiredCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void closeBrowser() throws Throwable{
		driver.quit();
		logger.info("successfully closed browser");
	}

}
