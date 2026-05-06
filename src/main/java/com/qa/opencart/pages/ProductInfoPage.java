package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	Map<String, String> productMap;

	// 1. private by locators

	private final By productHeader = By.tagName("h1");
	private final By productImagecCount = By.cssSelector("ul.thumbnails img");
	private final By productData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By priceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");

	// 2. public constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. public page methods/actions

	public String getProductHeader() {
		String header = eleUtil.waitForElementVisible(productHeader, DEFAULT_TIMEOUT).getText();
		System.out.println("Product header : " + header);
		return header;
	}

	public int getProductImagesCount() {
		int imageCount = eleUtil.waitForAllElementsVisisble(productImagecCount, DEFAULT_TIMEOUT).size();
		System.out.println("Total number of images " + imageCount);
		return imageCount;
	}

	public Map<String, String> getProductDetailsMap() {
		
		productMap = new LinkedHashMap<String, String>();
		
		productMap.put("productheader", getProductHeader());
		productMap.put("imagecount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getPriceMetaData();
		System.out.println("Full product details: " + productMap);
		
		return productMap;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock

	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForAllElementsVisisble(productData, DEFAULT_TIMEOUT);
		for (WebElement e : metaList) {
			String metaData = e.getText();
			String meta[] = metaData.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productMap.put(metaKey, metaValue);

		}
	}

//	$2,000.00
//	Ex Tax: $2,000.00

	private void getPriceMetaData() {
		List<WebElement> priceList = eleUtil.waitForAllElementsVisisble(priceData, DEFAULT_TIMEOUT);
		String price = priceList.get(0).getText();
		String exTaxprice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", price);
		productMap.put("excludetaxprice", exTaxprice);
	}

}
