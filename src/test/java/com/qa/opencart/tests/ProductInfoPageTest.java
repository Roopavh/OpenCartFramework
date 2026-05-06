package com.qa.opencart.tests;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.util.Map;
import static com.qa.opencart.constants.AppConstants.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][]  productHeaderTestData() {
		return new Object[][] {
			{"MacBook","MacBook Pro"},
			{"Samsung","Samsung SyncMaster 941BW"},
			{"sony","Sony VAIO"},
			{"Apple","Apple Cinema 30\""},
		};
	}

	
	@Test(dataProvider = "productHeaderTestData")
	public void productHeaderTest(String searchKey, String ProductName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(ProductName);
		String actHeader = productInfoPage.getProductHeader();
		Assert.assertEquals(actHeader, ProductName);
	}

	@DataProvider
	public Object[][]  productImageCountTestData() {
		return new Object[][] {
			{"MacBook","MacBook Pro",4},
			{"Samsung","Samsung SyncMaster 941BW",1},
			{"Sony","Sony VAIO",5},
			{"Apple","Apple Cinema 30\"",6},
		};
	}
	
	@DataProvider
	public Object[][] getProductImageCountTestData() {
		Object[][] productData = ExcelUtil.getTestData(PRODUCT_SHEET_NAME);
		return productData;
	}
	
	@DataProvider
	public Object[][] getCSVImageCountTestData() {
		Object[][] csvProductData = CSVUtil.getCSVTestData(CSV_PRODUCT_SHEET_NAME);
		return csvProductData;
	}
	
	@Test(dataProvider = "getProductImageCountTestData")
	public void productImagesCountTest(String searchKey,String productNmae,String expImgCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productInfoPage = searchResultsPage.selectProduct(productNmae);
		int actImagesCount = productInfoPage.getProductImagesCount();
		Assert.assertEquals(String.valueOf(actImagesCount), expImgCount);
	}

	@Test
	public void productDetailsTest() {
		searchResultsPage = accPage.doSearch("MacBook");
		productInfoPage = searchResultsPage.selectProduct("MacBook Pro");

		Map<String, String> actProductDetails = productInfoPage.getProductDetailsMap();

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actProductDetails.get("Brand"), "Apple");
		softAssert.assertEquals(actProductDetails.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductDetails.get("Reward Points"), "800");
		softAssert.assertEquals(actProductDetails.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actProductDetails.get("productprice"), "$2,000.00");
		softAssert.assertEquals(actProductDetails.get("excludetaxprice"), "$2,000.00");

		softAssert.assertAll();

	}
}
