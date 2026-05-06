package com.qa.opencart.driverfactory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariOptions;

public class OptinonsManager {

	private Properties prop;

	public OptinonsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChomeOptions() {
		ChromeOptions oc = new ChromeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("-----Running in headless mode--------");
			oc.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("-----Running in incognito mode--------");
			oc.addArguments("--incognito");
		}

		return oc;
	}

	public FirefoxOptions getFireFoxOptions() {
		FirefoxOptions of = new FirefoxOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("-----Running in headless mode--------");
			of.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("-----Running in incognito mode--------");
			of.addArguments("-private");
		}

		return of;
	}

	public EdgeOptions getEdgeOptions() {
		EdgeOptions oe = new EdgeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			System.out.println("-----Running in headless mode--------");
			oe.addArguments("--headless");
		}

		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			System.out.println("-----Running in incognito mode--------");
			oe.addArguments("--inprivate");
		}

		return oe;
	}
	
	

}
