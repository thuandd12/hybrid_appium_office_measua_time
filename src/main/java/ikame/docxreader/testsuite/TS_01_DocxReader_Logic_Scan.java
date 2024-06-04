package ikame.docxreader.testsuite;

import static ikame.docxreader.common.TestLogger.info;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ikame.docxreader.common.Management_Account;
import ikame.docxreader.common.commonBase;
import ikame.docxreader.ui.PageUI_DocxReader;
import ikame.docxreader.ui.PageUI_DocxReader_Scanner;
public final class TS_01_DocxReader_Logic_Scan extends commonBase {

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
	public void TC_001_Logic_AddButton_From_Camera_BtnDone() {
		info("TC_001_Logic_AddButton_From_Camera_BtnDone");
		managementAccount.openHomePage();

		click(PageUI_DocxReader.ICON_CREATE);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.TAB_SCANNER, "Home", "Create New");

		click(PageUI_DocxReader.TAB_SCANNER);
		long time2 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_TAKE_PHOTO, "Create New", "Camera");
		
		click(PageUI_DocxReader_Scanner.BTN_MULTIPLE);
		click(PageUI_DocxReader_Scanner.BTN_TAKE_PHOTO);
		pause(3000);
		verifyCompare(getText(PageUI_DocxReader_Scanner.TEXT_NUMBER_OF_PHOTO_TAKEN),"1");
		click(PageUI_DocxReader_Scanner.BTN_NEXT);
		long time3 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_DONE, "Camera", "Edit");
		
		click(PageUI_DocxReader_Scanner.BTN_DONE);
		long time4 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_ADD, "Edit", "Edit Detail");
		
		click(PageUI_DocxReader_Scanner.BTN_ADD);
		long time5 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.TEXT_FEATURE, "Edit Detail", "Add Image");
		
		click(PageUI_DocxReader_Scanner.TAB_FROM_CAMERA);
		long time6 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_ADD, "Add Image", "From Camera");
		
		click(PageUI_DocxReader_Scanner.BTN_TAKE_PICTURE);
		
		click(PageUI_DocxReader_Scanner.ICON_DONE);
		long time7 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_ADD, "From Camera", "Edit");
		verifyNotContains("2",getText(PageUI_DocxReader_Scanner.BTN_TOTAL_OF_PHOTO_TAKEN));
		
		checkScreenSwitchTime(time1, "Home", "Create New");
		checkScreenSwitchTime(time2, "Create New", "Camera");
		checkScreenSwitchTime(time3, "Camera", "Edit");
		checkScreenSwitchTime(time4, "Edit", "Edit Detail");
		checkScreenSwitchTime(time5, "Edit Detail", "Add Image");
		checkScreenSwitchTime(time6, "Add Image", "From Camera");
		checkScreenSwitchTime(time7, "From Camera", "Edit");
	}
	@Test
	public void TC_002_Logic_Edit_Save_PDF_Quality_Max() {
		info("TC_002_Logic_Edit_Save_PDF_Quality_Max");
		managementAccount.openHomePage();

		click(PageUI_DocxReader.TEXT_TOOL);
		long time1 = getMeasureScreenTransitionTime(PageUI_DocxReader.TAB_SCAN_FILE, "Home", "Tool");

		click(PageUI_DocxReader.TAB_SCAN_FILE);
		long time2 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_TAKE_PHOTO, "Tool", "Camera");

		click(PageUI_DocxReader_Scanner.BTN_MULTIPLE);
		click(PageUI_DocxReader_Scanner.BTN_TAKE_PHOTO);
		click(PageUI_DocxReader_Scanner.TEXT_NUMBER_OF_PHOTO_TAKEN);
		long time3 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_CONFIRM, "Camera", "Gallary");
		
		click(PageUI_DocxReader_Scanner.BTN_CONFIRM);
		long time4 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_DONE, "Gallary", "Edit");
		
		click(PageUI_DocxReader_Scanner.BTN_DONE);
		long time5 =getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_SAVE, "Edit", "Edit Detail");
		
		click(PageUI_DocxReader_Scanner.BTN_SAVE);
		long time6 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.TEXTBOX_RENAME, "Edit Detail", "Save PDF");
		
		String value = managementAccount.getFileName(fileName);
		sendKeyToElement(PageUI_DocxReader_Scanner.TEXTBOX_RENAME, value);
		singleTab(999, 2331);
		pause(3000);
		singleTab(386, 2030);
		click(PageUI_DocxReader_Scanner.BTN_CONFIRM);
		long time7 = getMeasureScreenTransitionTime(PageUI_DocxReader_Scanner.BTN_SAVE_TO_GALLERY, "Save PDF", "Save Document");
		
		verifyContains(fileName, getText(PageUI_DocxReader_Scanner.TEXTBOX_RENAME));
		click(PageUI_DocxReader_Scanner.BTN_SAVE_TO_GALLERY);
		
		click(PageUI_DocxReader_Scanner.BTN_HOME);
		long time8  =getMeasureScreenTransitionTime(PageUI_DocxReader.TEXT_DOCX_READER, "Save Document", "Home");
		String value2 = parseStringToObject(PageUI_DocxReader_Scanner.TEXT_FILE_NAME, fileName);
		getElementPresent(By.xpath(value2));
		
		checkScreenSwitchTime(time2, "Tool", "Camera");
		checkScreenSwitchTime(time3, "Camera", "Gallary");
		checkScreenSwitchTime(time4, "Gallary", "Edit");
		checkScreenSwitchTime(time5, "Edit", "Edit Detail");
		checkScreenSwitchTime(time6, "Edit Detail", "Save PDF");
		checkScreenSwitchTime(time7, "Save PDF", "Save Document");
		checkScreenSwitchTime(time8, "Save Document", "Home");
	}
	
	
	private String fileName ="ikame";
	private Management_Account managementAccount;

}
