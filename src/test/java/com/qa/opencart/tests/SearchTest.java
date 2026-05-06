package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchTest extends BaseTest{

	@BeforeClass
	public void searchSetup() {
		accPage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void doSearchTest() {
		searchResultsPage = accPage.doSearch("Roopa");
		int resultsProductCount = searchResultsPage.getResultProductsCount();
		Assert.assertEquals(resultsProductCount, 0);
	}
}
