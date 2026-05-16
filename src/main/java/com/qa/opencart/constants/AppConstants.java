package com.qa.opencart.constants;

import java.util.List;

public class AppConstants {

	public static final int DEFAULT_TIMEOUT = 5;
	public static final int MEDIUM_DEFAULT_TIMEOUT = 10;
	public static final int LONG_DEFAULT_TIMEOUT = 15;

	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String ACCOUNT_PAGE_FRACTION_URL = "route=account/account";

	public static List<String> expectedAccPageHeaderList = List.of("My Account", "My Orders", "My Affiliate Account",
			"Newsletter");

	public static String REGISTRATION_SUCCESS_MESG = "Your Account Has Been Created!";

	// *******************Excel test data sheet names*************

	public static final String REGISTRATION_SHEET_NAME = "registration";
	public static final String PRODUCT_SHEET_NAME = "product";

	public static final String CSV_PRODUCT_SHEET_NAME = "TestData";

	

}
