package ikame.docxreader.common.constant;

import org.openqa.selenium.By;

public class CT_Account {
	public static final By BUTTON_CONTINUE = By.xpath("//*[@text='Continue to app']");
	public static final By BUTTON_SELECT = By.id("com.happydev.wordoffice:id/splashLanguageCheck");
	public static final By BUTTON_SKIP = By.id("com.happydev.wordoffice:id/btnSkip");
	public static final By BUTTON_CLOSE_PREMIUM = By.id("com.happydev.wordoffice:id/iv_close_premium");
	public static final By TEXT_LANGUAGE = By.xpath("//android.widget.TextView[@resource-id='com.happydev.wordoffice:id/itFirstLanguage_tvName' and @text='&s']");
}
