package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.driverfactory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;
	private JavaScriptUtils jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
		jsUtil = new JavaScriptUtils(driver);
	}

	private void nullCheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("-----Value can not be null-----------");
		}
	}

	@Step("Entering value : {1} into element : {0}")
	public void doSendKeys(By locator, String value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	public void doSendKeys(By locator, CharSequence... value) {
		nullCheck(value);
		getElement(locator).clear();
		getElement(locator).sendKeys(value);
	}

	@Step("Clicking on element using : {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	@Step("Fetching the element text using : {0}")
	public String doElementGetText(By locator) {
		String eleText = getElement(locator).getText();
		System.out.println("Element text " + eleText);
		return eleText;
	}

	public String doGetElementDomAttributeValue(By locator, String attName) {
		nullCheck(attName);
		return getElement(locator).getDomAttribute(attName);
	}

	public String doGetElementDomProperty(By locator, String propName) {
		nullCheck(propName);
		return getElement(locator).getDomProperty(propName);
	}

	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present on the page " + locator);
			return false;
		}

	}

	public boolean checkElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("Element " + locator + " displayed on the page only one time");
			return true;
		}
		return false;
	}

	public boolean checkElementDisplayed(By locator, int excEleCount) {
		if (getElements(locator).size() == excEleCount) {
			System.out.println("Element " + locator + " displayed on the page " + excEleCount + " time");
			return true;
		}
		return false;
	}

	public void clickElement(By locator, String value) {

		List<WebElement> eleList = getElements(locator);
		System.out.println("Total number of elements " + eleList.size());

		for (WebElement e : eleList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}

		}
	}

	@Step("Finding the element using :{0}")
	public WebElement getElement(By locator) {
		ChainTestListener.log("Locator : " + locator.toString());
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;
	}

	private void highlightElement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	public WebElement getElementWithWait(By locator, int timeOut) {
		return waitForElementVisible(locator, timeOut);
	}

	// --------------------------findElements Utils---------------------------------

	public List<String> getElementTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.length() != 0) {
				System.out.println(text);
				eleTextList.add(text);
			}
		}
		return eleTextList;
	}

	public int getElementListCount(By locator) {
		int eleCount = getElements(locator).size();
		System.out.println("Element Count: " + eleCount);
		return eleCount;
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// -----------------DropDown utils-Select Based-----------------------------

	public boolean doSelectDropDownByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));

		try {
			select.selectByIndex(index);
			return true;

		} catch (NoSuchElementException e) {
			System.out.println(index + " is not present in the dropdown");
			return false;
		}
	}

	public boolean doSelectDropDownByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByVisibleText(visibleText);
			return true;

		} catch (NoSuchElementException e) {
			System.out.println(visibleText + " is not present in the dropdown");
			return false;
		}
	}

	public boolean doSelectDropDownByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		try {
			select.selectByValue(value);
			return true;

		} catch (NoSuchElementException e) {
			System.out.println(value + " is not present in the dropdown");
			return false;
		}

	}

	public boolean selectDropDownOption(By locator, String value) {

		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		System.out.println(optionsList.size());

		boolean flag = false;

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(value + " is selected");
			return true;
		} else {
			System.out.println(value + " is not selected");
			return false;
		}
	}

	public List<String> getSelectDropDownOptionsList(By locator) {

		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValList.add(text);
		}
		return optionsValList;
	}

	public boolean getSelectDropDownOptionsList(By locator, List<String> excOptionsList) {

		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValList = new ArrayList<String>();
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValList.add(text);
		}
		if (optionsValList.containsAll(excOptionsList)) {
			return true;
		} else {
			return false;
		}
	}

	// -------------Drop Down utils-Non select based------------------------------

	/**
	 * This method is used to select drop down choices with three different use
	 * cases 1. Single Selection: selectChoice(choice, choices, "choice 6"); 2.
	 * Multi selection: selectChoice(choice, choicesList, "choice 6", "choice 2",
	 * "choice 2 1", "choice 1"); 3. All selection: Use "all/All/ALL" to select all
	 * the choices : selectChoice(choice, choicesList, "all");
	 * 
	 * @param choice
	 * @param choicesList
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choice, By choicesList, String... choiceValue) throws InterruptedException {

		doClick(choice);
		Thread.sleep(2000);

		List<WebElement> choices = getElements(choicesList);

		if (choiceValue[0].equalsIgnoreCase("all")) {
			// logic to select all the choices
			for (WebElement e : choices) {
				e.click();
			}
		} else {
			for (WebElement e : choices) {
				String text = e.getText();

				for (String value : choiceValue) {
					if (text.trim().equals(value)) {
						e.click();
						break;
					}
				}
			}

		}
	}

	// -----------------------Actions Utils------------------------//

	public void doMoveToElement(By locator) throws InterruptedException {
		act.moveToElement(getElement(locator)).build().perform();
		Thread.sleep(2000);
	}

	public void doActionsSendKeys(By locator, String value) {
		act.sendKeys(getElement(locator), value).perform();
	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).perform();
	}

	public void doActionsSendKeysWithPause(By locator, String value, long pauseTime) {

		char val[] = value.toCharArray();
		for (char ch : val) { // c->"c"
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}
	}

	// handles only 2 levels parent and single sub menus
	public void handleParentAndSubMenu(By parentMenu, By subMenu) throws InterruptedException {
		doMoveToElement(parentMenu);
		Thread.sleep(2000);
		doClick(subMenu);
	}

	// Handling multilevel parent and sub menus
	public void handle4LevelMenu(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {

		doClick(level1Menu);
		Thread.sleep(2000);
		doMoveToElement(level2Menu);
		Thread.sleep(2000);
		doMoveToElement(level3Menu);
		Thread.sleep(2000);
		doClick(level4Menu);
	}

	// -----------------Wait utils-Element Visibility-----------------------

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 *
	 * @param locator used to find the element
	 * @return the WebElement once it is located
	 */

	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 *
	 * @param locator used to find the element
	 * @return the WebElement once it is located and visible
	 */

	@Step("waiting for element using : {0} with timeout : {1}")
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	/**
	 * An expectation for checking that there is at least one element present on a
	 * web page.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return the list of WebElements once they are located
	 */

	public List<WebElement> waitForAllElementsPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	/**
	 * An expectation for checking that all elements present on the web page that
	 * match the locator are visible. Visibility means that the elements are not
	 * only displayed but also have a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return the list of WebElements once they are located
	 */

	@Step("waiting for element using : {0} with timeout : {1}")
	public List<WebElement> waitForAllElementsVisisble(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		} catch (TimeoutException e) {
			return Collections.EMPTY_LIST;
		}
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 *
	 * @param locator used to find the element
	 * @return the WebElement once it is located and clickable (visible and enabled)
	 */

	@Step("waiting for element and click on it using : {0} with timeout : {1}")
	public void clickWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	
	public void clickWithWait(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}

	public void sendKeysWithWait(By locator, int timeOut, CharSequence... value) {
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}

	// -----------Alert waits(JS alerts)----------------

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(int timeOut) {
		waitForAlert(timeOut).accept();
	}

	public void dismissAlert(int timeOut) {
		waitForAlert(timeOut).dismiss();
	}

	public String getAlertText(int timeOUt) {
		return waitForAlert(timeOUt).getText();
	}

	public void sendKeysAlert(int timeOUt, String value) {
		waitForAlert(timeOUt).sendKeys(value);
	}

	// --------Wait for Title--------------------
	@Step("waiting for element contains using : {0} with timeout : {1}")
	public String waitForTitleContains(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();
		} catch (TimeoutException e) {
			return null;
		}
	}

	@Step("waiting for title : {0} with timeout: {1}")
	public String waitForTitleIs(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleIs(title));
			return driver.getTitle();

		} catch (TimeoutException e) {
			return null;
		}

	}

	// --------Wait for URL -------------

	public String waitForURLContains(String fractionURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;
		}
	}

	public String waitForURLToBe(String URL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			wait.until(ExpectedConditions.urlToBe(URL));
			return driver.getCurrentUrl();

		} catch (TimeoutException e) {
			return null;
		}
	}

	// --------wait for windows--------------

	public boolean waitForWindows(int expectedNoOfWindowsToBe, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNoOfWindowsToBe));

		} catch (Exception e) {
			System.out.println("expectedNoOfWindowsToBe not correct");
			return false;
		}
	}

	// ---------Fluent wait for Element ---------

	public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("-------Element is not found----------");

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}

	public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("-------Element is not found----------");

		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

}
