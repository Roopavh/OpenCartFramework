package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.ACCOUNT_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.LOGIN_PAGE_TITLE;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;


@Feature("F: 50 : Opencart -Login feature")
@Epic("Epic : 001 : Design pages for Opencart application")
@Story("US: 11: Implement login page for opencart application ")
@Owner("Roopa")

public class LoginPageTest extends BaseTest{
	
	@Description("Checking opencart login page title...")
	@Severity(SeverityLevel.MINOR)
	@Test(description="checking login page title")
	public void loginPageTilteTest() {
		String actTitle= loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, LOGIN_PAGE_TITLE);
	}

	@Description("Checking opencart login page URL...")
	@Severity(SeverityLevel.MINOR)
	@Test(description="checking login page url")
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(LOGIN_PAGE_FRACTION_URL));
	}
	
	
	@Description("Checking opencart login page forgot password link...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description="checking login page forgot password exists")
	public void forgotPWDLinkExistsTest() {
		Assert.assertTrue(loginPage.forgotPassowrdLinkExists());
	}
	
	
	@Description("Checking user is able to login with valid credentials..")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Short.MAX_VALUE, description="login with valid credentials")
	public void doLoginTest() {
		 accPage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccPageTitle(),ACCOUNT_PAGE_TITLE);
	}
}
