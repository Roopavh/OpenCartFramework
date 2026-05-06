package com.qa.opencart.pages;

import static com.qa.opencart.constants.AppConstants.ACCOUNT_PAGE_FRACTION_URL;
import static com.qa.opencart.constants.AppConstants.ACCOUNT_PAGE_TITLE;
import static com.qa.opencart.constants.AppConstants.DEFAULT_TIMEOUT;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	private static final Logger log = LogManager.getLogger(AccountsPage.class);

	private final By headers = By.tagName("h2");
	private final By search = By.name("search");
	private final By searchIcon = By.cssSelector("div#search button");

	// public constructor

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// public page methods/actions

	public String getAccPageTitle() {
		String title = eleUtil.waitForTitleIs(ACCOUNT_PAGE_TITLE, DEFAULT_TIMEOUT);
		log.info("Account page title: " + title);
		return title;
	}

	public String getAccPageURL() {
		String url = eleUtil.waitForURLContains(ACCOUNT_PAGE_FRACTION_URL, DEFAULT_TIMEOUT);
		log.info("Account page url: " + url);
		return url;
	}

	public List<String> accPageHeaders() {
		List<WebElement> headerList = eleUtil.waitForAllElementsVisisble(headers, DEFAULT_TIMEOUT);
		List<String> headerValList = new ArrayList<String>();
		for (WebElement e : headerList) {
			String text = e.getText();
			headerValList.add(text);
		}
		log.info("Account Page headers : " + headerValList);
		return headerValList;
	}

	public SearchResultsPage doSearch(String searchKey) {
		log.info("Search key : " + searchKey);
		eleUtil.waitForElementVisible(search, DEFAULT_TIMEOUT);
		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);

		return new SearchResultsPage(driver);
	}

}
