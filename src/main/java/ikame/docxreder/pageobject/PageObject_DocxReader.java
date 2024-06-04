package ikame.docxreder.pageobject;

import org.openqa.selenium.By;

import ikame.docxreader.common.commonBase;
import io.appium.java_client.AppiumDriver;

public class PageObject_DocxReader extends commonBase {

	public PageObject_DocxReader(AppiumDriver dr) {
		driver = dr;
	}

	public void sortFileAndNumericalOrder(String nameFile, int typeOfArrangement) {
		By TEXT_TYPE_OF_ARRANGEMENT = By
				.xpath("(//android.widget.TextView[@resource-id='com.happydev.wordoffice:id/tv_doc_name'])[+"
						+ typeOfArrangement + "+]");
		verifyCompare(nameFile, getText(TEXT_TYPE_OF_ARRANGEMENT));
	}
}
