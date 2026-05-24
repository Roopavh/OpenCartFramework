package com.qa.opencart.driverfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptinonsManager optinonsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	public static String highlight;
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	/**
	 * This method is used to initialize the driver on the basis of given browser
	 * name
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {

		log.info("Properties used  " + prop);
		ChainTestListener.log("Properties used  " + prop);

		String browserName = prop.getProperty("browser");

		// System.out.println("Browser name : " + browserName);
		log.info("Browser name  : " + browserName);

		ChainTestListener.log("Browser name  " + browserName);

		optinonsManager = new OptinonsManager(prop);

		highlight = prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {
		case "chrome":

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run on selenium grid /server/aws/machine
				intitRemoteDriver("chrome");
			} else {
				// run on local
				tlDriver.set(new ChromeDriver(optinonsManager.getChomeOptions()));// creating thread local copy of driver																	// driver
			}
			break;

		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run on selenium grid /server/aws/machine
				intitRemoteDriver("firefox");
			} else {
				// run on local
				tlDriver.set(new FirefoxDriver(optinonsManager.getFireFoxOptions()));
			}
			break;

		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run on selenium grid /server/aws/machine
				intitRemoteDriver("edge");
			} else {
				// run on local
				tlDriver.set(new EdgeDriver(optinonsManager.getEdgeOptions()));
			}
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println("Please pass the valid browser" + browserName);
			log.error("Please pass the valid browser" + browserName);
			throw new BrowserException("-----------Invalid Browser--------");

		}

		getDriver().get(prop.getProperty("url"));
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();

		return getDriver();
	}

	private void intitRemoteDriver(String browserName) {
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optinonsManager.getChomeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;
		case "firefox":
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optinonsManager.getFireFoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;
		case "edge":
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optinonsManager.getEdgeOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;

		default:
			log.error("This browser is not supported on selenium grid " + browserName);
			throw new BrowserException("-----------Invalid Browser--------");
		}
		
	}

	/**
	 * This method is used to initialize the config properties
	 * 
	 * @return
	 */

	// mvn clean install -Denv

	public Properties initProperties() {
		prop = new Properties();
		FileInputStream fip = null;

		// reading the system env variables coming from maven command
		String envName = System.getProperty("env");

		try {

			if (envName == null) {
				// System.out.println("Env is null, hence running the tests on QA env...");
				log.warn("Env is null, hence running the tests on QA env...");
				fip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				// System.out.println("Running the test on : " + envName);
				log.info("Running the test on : " + envName);

				switch (envName) {
				case "qa":
					fip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;

				case "stage":
					fip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;

				case "dev":
					fip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;

				case "uat":
					fip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					fip = new FileInputStream("./src/test/resources/config/prod.config.properties");
					break;
				default:
					// System.out.println("Please pass the valid env...");
					log.error("Please pass the valid env... " + envName);
					throw new FrameworkException("=======INVALID ENV NAME======= : " + envName);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(fip);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return prop;

	}

	/**
	 * get the the local thread copy of the driver
	 * 
	 * @return
	 */

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * take screenshots in different ways
	 * 
	 * @return
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
	}
}
