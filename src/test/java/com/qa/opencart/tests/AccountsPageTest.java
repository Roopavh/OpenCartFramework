package com.qa.opencart.tests;

import static com.qa.opencart.constants.AppConstants.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class AccountsPageTest extends BaseTest {
	
	@BeforeClass
	public void accPageSetup() {
		 accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void accPageTitleTest() {
		 Assert.assertEquals(accPage.getAccPageTitle(),ACCOUNT_PAGE_TITLE);
		
	}
	
	@Test
	public void accPageURLTest() {
		Assert.assertTrue(accPage.getAccPageURL().contains(ACCOUNT_PAGE_FRACTION_URL));
	}
	
	@Test
	public void accPageHeadersListTest() {
		List<String> actAccPageHeaders = accPage.accPageHeaders();
		Assert.assertEquals(actAccPageHeaders, expectedAccPageHeaderList);
	}
	
	

}
