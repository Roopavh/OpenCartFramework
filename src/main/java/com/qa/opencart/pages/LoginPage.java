package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;




public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private static final Logger log=LogManager.getLogger(LoginPage.class);

	// 1.private By locators

	private final By email = By.id("input-email");
	private final By password = By.id("input-password");
	private final By login = By.xpath("//input[@value='Login']");
	private final By forgotPWDLink = By.linkText("Forgotten Password");
	
	private final By registerLink=By.linkText("Register");

	// 2. public page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}

	// 3. public page actions/method

	@Step("checking login page title")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleIs(LOGIN_PAGE_TITLE, DEFAULT_TIMEOUT);
		log.info("Login page title: " + title);
		return title;
	}

	@Step("checking login page url")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContains(LOGIN_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		log.info("Login page url: " + url);
		return url;
	}

	@Step("checking login page forgot password link")
	public boolean forgotPassowrdLinkExists() {
		return eleUtil.isElementDisplayed(forgotPWDLink);	
	}
	
	
	@Step("checking login page with valid username: {0} and password: {1}")
	public AccountsPage doLogin(String username, String pwd) {
		log.info("User credentials : " + " Username: " + username + "  Password: " + pwd);
		
		eleUtil.waitForElementVisible(email, DEFAULT_TIMEOUT).sendKeys(username);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(login);
		return new AccountsPage(driver);
	}
	
	@Step("checking navigation to registration page")
	public RegistrationPage navigateToRegistrationPage() {
		eleUtil.clickWhenReady(registerLink, DEFAULT_TIMEOUT);
		return new RegistrationPage(driver);
	}
}
