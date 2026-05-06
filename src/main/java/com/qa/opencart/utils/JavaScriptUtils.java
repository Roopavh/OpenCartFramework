package com.qa.opencart.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtils {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavaScriptUtils(WebDriver driver) {
		this.driver = driver;
		js = (JavascriptExecutor) this.driver;

	}

	public String getTitleByJS() {
		return js.executeScript("return document.title;").toString();
	}

	public String getURLByJS() {
		return js.executeScript("return document.URL;").toString();
	}

	// ------Browser Navigations-------

	public void navigateToBackpage() {
		js.executeScript("history.go(-1)");
	}

	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}

	public void refreshBrowserByJS() {
		js.executeScript("history.go(0)");
	}

	// --------alert(JS pop up)--------------------

	public void generateJSAlert(String message) {
		js.executeScript("alert('" + message + "')");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.switchTo().alert().dismiss();
	}

	// -------fetching the all text on the page-------

	public String getPageInnerText() {
		return js.executeScript("return document.documentElement.innerText;").toString();
	}

	// --------scrolling utils---------------

	public void scrollPageDown() {
		js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public void scrollPageUp() {
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	public void scrollPageDown(String height) {
		js.executeScript("window.scrollTo(0,'" + height + "')");
	}

	public void scrollPageUp(String height) {
		js.executeScript("window.scrollTo('" + height + "',0)");
	}
	
	public void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)",element);
	}
	
	//-----------Zoom utils->responsive testing---------
	
	public void zoomChromeEdgeFirefox(String zoomPercantage) {
		js.executeScript("document.body.style.zoom='"+zoomPercantage+"%'");
	}
	
	
	//------custom utils for flashing and draw border on the browser----------
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");//blue
		for (int i = 0; i < 2; i++) {
			changeColor("rgb(0,200,0)", element);// green
			changeColor(bgcolor, element);// blue
		}
	}

	private void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);//GBGBGBGBGBGB

		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
	}
	
	
	//------click and sendKeys by JS--------------------
	
	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();",element);
	}
	
	public void sendKeysWithUsingIdByJS(String id,String value) {
		js.executeScript("document.getElementById('"+id+"').value='"+value+"'");
	}
	
	
}
