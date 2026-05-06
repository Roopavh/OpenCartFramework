package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.StringUtils;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1.private By locators

	private final By firstName = By.id("input-firstname");
	private final By lastName = By.id("input-lastname");
	private final By email = By.id("input-email");
	private final By telePhone = By.id("input-telephone");
	private final By password = By.id("input-password");
	private final By confirmPassword = By.id("input-confirm");

	private final By subScribeNo = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private final By subScribeYes = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");

	private final By privacyPolicy = By.name("agree");
	private final By continueBtn = By.xpath("//input[@type='submit' and @value='Continue']");

	private final By successMsg = By.cssSelector("div#content h1");

	private final By logout = By.linkText("Logout");
	private final By register = By.linkText("Register");

	// 2. public page constructor
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. public page methods

	public boolean userRegistration(String firstName, String lastName, String telePhone, String password,
			String subscribe) {
		eleUtil.waitForElementVisible(this.firstName, MEDIUM_DEFAULT_TIMEOUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, StringUtils.getRandomEmailID());
		eleUtil.doSendKeys(this.telePhone, telePhone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		
		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subScribeYes);
		} else {
			eleUtil.doClick(subScribeNo);
		}

		eleUtil.doClick(privacyPolicy);
		eleUtil.doClick(continueBtn);

		if (eleUtil.waitForElementVisible(successMsg, LONG_DEFAULT_TIMEOUT).getText()
				.contains(REGISTRATION_SUCCESS_MESG)) {
			eleUtil.doClick(logout);
			eleUtil.doClick(register);
			return true;
		}
		return false;
	}

}