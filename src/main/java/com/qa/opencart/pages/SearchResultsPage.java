package com.qa.opencart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static com.qa.opencart.constants.AppConstants.*;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private static final Logger log=LogManager.getLogger(SearchResultsPage.class);


	// 1. Private by locators
	private final By resultProductsCount = By.cssSelector("div.product-thumb");

	// 2. public constructor
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. public page methods

	public int getResultProductsCount() {
		int count = eleUtil.waitForAllElementsVisisble(resultProductsCount, DEFAULT_TIMEOUT).size();
		log.info("Total number of products : " + count);
		return count;
	}

	public ProductInfoPage selectProduct(String productName) {
		log.info("Product selected: " + productName);
		eleUtil.doClick(By.linkText(productName));

		return new ProductInfoPage(driver);
	}

}
