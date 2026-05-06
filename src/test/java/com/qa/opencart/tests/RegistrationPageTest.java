package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void registrationPageSetup() {
		registrationPage = loginPage.navigateToRegistrationPage();
	}

	@DataProvider
	public Object[][] getRegistrationTestData() {
		return new Object[][] {
			{"Roopa","test","9999999999","test123","yes"},
			{"Vinamyi","test","9999999998","test123","no"},
			{"test","test","9999999997","test123","yes"},
		};
	}
	
	
	
	@DataProvider
	public Object[][] getRegistrationExcelTestData() {
		Object[][] regData = ExcelUtil.getTestData(REGISTRATION_SHEET_NAME);
		return regData;
	}
	
	@Test(dataProvider="getRegistrationExcelTestData")
	public void registrationTest(String firstName, String lastName,String telePhone, String password,
			String subscribe) {
		Assert.assertTrue(registrationPage.userRegistration(firstName, lastName, telePhone, password, subscribe));
	}
}
