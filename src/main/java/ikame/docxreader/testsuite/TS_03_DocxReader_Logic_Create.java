package ikame.docxreader.testsuite;


import static ikame.docxreader.common.TestLogger.info;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ikame.docxreader.common.Management_Account;
import ikame.docxreader.common.commonBase;
import ikame.docxreader.ui.PageUI_DocxReader;


public final class TS_03_DocxReader_Logic_Create extends commonBase {

	@BeforeMethod
	public void init() {
		driver = initDriverTest("WorkOffice.apk", "5t4haexcqwlzypd6", "Android", "13.0", "com.happydev.wordoffice",
				"com.happydev.wordoffice.business.splash.SplashActivity", "windows", "8201");
		managementAccount = new Management_Account(driver);
	}

	@AfterMethod
	public void after() {
		quitDriver(driver);
	}

	@Test
	public void TC_001_Logic_Create_Docx() {
		info("TC_001_Logic_Create_Docx");
		managementAccount.openHomePage();

		click(PageUI_DocxReader.ICON_CREATE);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.TAB_SCANNER, "Home", "Create New");

		click(PageUI_DocxReader.TAB_DOCX);
		long time2 =getMeasureScreenTransitionTime(PageUI_DocxReader.TITLE_CREATE_NEW, "Create New", "Create DOCX");
		
		click(PageUI_DocxReader.TEXT_DOCUMENT_SAMPLE_1);
		click(PageUI_DocxReader.BTN_EDIT);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		click(PageUI_DocxReader.IMG_SAVE);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		
		click(PageUI_DocxReader.BTN_DONE_CREATE_FILE);
		long time3 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_GO_TO_PAGE, "Create DOCX", "View Detail File");
		getElementPresent(PageUI_DocxReader.BTN_GO_TO_PAGE);
		
		click(PageUI_DocxReader.BTN_SAVE_DETAIL_FILE);
		long time4 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_EDIT_FILE_NAME, "View Detail File", "Save As");
		getElementPresent(PageUI_DocxReader.BTN_EDIT_FILE_NAME);
		
		checkScreenSwitchTime(time1, "Home", "Create New");
		checkScreenSwitchTime(time2, "Create New", "Create DOCX");
		checkScreenSwitchTime(time3, "Create DOCX", "View Detail File");
		checkScreenSwitchTime(time4, "View Detail File", "Save As");
	}	
	@Test
	public void TC_002_Logic_Create_Docx() {
		info("TC_002_Logic_Create_Docx");
		managementAccount.openHomePage();

		click(PageUI_DocxReader.ICON_CREATE);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.TAB_SCANNER, "Home", "Create New");

		click(PageUI_DocxReader.TAB_XLSX);
		long time2 = getMeasureScreenTransitionTime(PageUI_DocxReader.TITLE_CREATE_NEW, "Create New", "Create XLSX");
		click(PageUI_DocxReader.TEXT_DOCUMENT_SAMPLE_1);
		
		click(PageUI_DocxReader.BTN_EDIT);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		click(PageUI_DocxReader.IMG_SAVE);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		
		click(PageUI_DocxReader.BTN_DONE_CREATE_FILE);
		long time3 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_GO_TO_PAGE, "Create XLSX", "View Detail File");
		getElementPresent(PageUI_DocxReader.BTN_GO_TO_PAGE);
		
		click(PageUI_DocxReader.BTN_SAVE_DETAIL_FILE);
		long time4 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_EDIT_FILE_NAME, "View Detail File", "Save As");
		getElementPresent(PageUI_DocxReader.BTN_EDIT_FILE_NAME);
		
		checkScreenSwitchTime(time1, "Home", "Create New");
		checkScreenSwitchTime(time2, "Create New", "Create XLSX");
		checkScreenSwitchTime(time3, "Create XLSX", "View Detail File");
		checkScreenSwitchTime(time4, "View Detail File", "Save As");
	}	
	@Test
	public void TC_003_Logic_Create_PPTX() {
		info("TC_003_Logic_Create_PPTX");
		managementAccount.openHomePage();

		click(PageUI_DocxReader.ICON_CREATE);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.TAB_SCANNER, "Home", "Create New");

		click(PageUI_DocxReader.TAB_PPTX);
		long time2 = getMeasureScreenTransitionTime(PageUI_DocxReader.TITLE_CREATE_NEW, "Create New", "Create PPTX");
		click(PageUI_DocxReader.TEXT_DOCUMENT_SAMPLE_1);
		
		click(PageUI_DocxReader.BTN_EDIT);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		click(PageUI_DocxReader.IMG_SAVE);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		
		click(PageUI_DocxReader.BTN_DONE_CREATE_FILE);
		long time3 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_GO_TO_PAGE, "Create PPTX", "View Detail File");
		getElementPresent(PageUI_DocxReader.BTN_GO_TO_PAGE);
		
		click(PageUI_DocxReader.BTN_SAVE_DETAIL_FILE);
		long time4 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_EDIT_FILE_NAME,  "Create PPTX", "View Detail File");
		getElementPresent(PageUI_DocxReader.BTN_EDIT_FILE_NAME);
		
		checkScreenSwitchTime(time1,"Home", "Create New");
		checkScreenSwitchTime(time2, "Create New", "Create PPTX");
		checkScreenSwitchTime(time3,  "Create PPTX", "View Detail File");
		checkScreenSwitchTime(time4, "Create PPTX", "View Detail File");
	}	
	private Management_Account managementAccount;

}
