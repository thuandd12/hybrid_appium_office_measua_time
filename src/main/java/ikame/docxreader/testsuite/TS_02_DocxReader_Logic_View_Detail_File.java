package ikame.docxreader.testsuite;

import static ikame.docxreader.common.TestLogger.info;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ikame.docxreader.common.Management_Account;
import ikame.docxreader.common.commonBase;
import ikame.docxreader.ui.PageUI_DocxReader;


public final class TS_02_DocxReader_Logic_View_Detail_File extends commonBase {

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
	public void TC_001_Logic_View_Detail_File_Signature() {
		info("TC_001_Logic_View_Detail_File_Signature");
		managementAccount.openHomePage();
		click(PageUI_DocxReader.TAB_PDF);
		
		click(PageUI_DocxReader.TEXT_FILE_NAME_1);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_EDIT, "Home", "View Detail File");
		click(PageUI_DocxReader.BTN_EDIT);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		click(PageUI_DocxReader.IMG_SAVE);
		click(PageUI_DocxReader.BTN_EDIT_COLLAPSE);
		
		click(PageUI_DocxReader.BTN_SIGN_VIEW_DETAIL_FILE);
		long time2 = getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_CLEAR, "View Detail File", "Signature");
		getElementPresent(PageUI_DocxReader.IMG_SIGNATURE);
		singleTab(580, 602);
		pause(2000);
		
		click(PageUI_DocxReader.BTN_APPLY_SIGNATURE);
		long time3 =  getMeasureScreenTransitionTime(PageUI_DocxReader.BTN_SIGN_VIEW_DETAIL_FILE, "Signature", "View Detail File");
		getElementPresent(PageUI_DocxReader.BTN_SIGN_VIEW_DETAIL_FILE);
		
		checkScreenSwitchTime(time1, "Home", "View Detail File");
		checkScreenSwitchTime(time2, "View Detail File", "Signature");
		checkScreenSwitchTime(time3, "Signature", "View Detail File");
		
		System.out.println("đâsdasdasdasdasdasdasdasdadad");
		
	}	
	private Management_Account managementAccount;

}
