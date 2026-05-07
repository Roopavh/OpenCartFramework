package com.qa.opencart.base;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.driverfactory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;

//@Listeners(ChainTestListener.class) //this can be added in the 
public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected Properties prop;
	private static final Logger log = LogManager.getLogger(BaseTest.class);

	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegistrationPage registrationPage;

	@Parameters({ "browser" })//fetching from .xml testNG runner file
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProperties();

		// browserName passed from .xml file
		if (browserName != null) {
			prop.setProperty("browser", browserName);// on the fly setting the browser name in config prop coming from  .xml file											
		}

		driver = df.initDriver(prop);

		loginPage = new LoginPage(driver);

	}

	@AfterMethod // this ll be called after each @Test method
	public void attachScreenShot(ITestResult result) { // ITestResult(I)-which holds status of every test case
		if (!result.isSuccess()) {// only for failure test cases--true
			log.info("------screenshot is taken-------");
			ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
		}
//		ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");//ll take ss for all TCs

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
		log.info("-------Closing the browser-------");
	}
}
