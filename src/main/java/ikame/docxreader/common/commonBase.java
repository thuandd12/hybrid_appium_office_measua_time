package ikame.docxreader.common;

import static ikame.docxreader.common.TestLogger.info;
import static org.testng.Assert.assertFalse;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class commonBase {
	public AppiumDriver driver;
	public WebDriver driverWeb;
	protected int DEFAULT_TIMEOUT = 40000;
	protected int WAIT_INTERVAL = 500;
	public int loopCount = 0;
	public final int ACTION_REPEAT = 5;
	public Actions action;
	public SoftAssert softAssert;

	public AppiumDriver initDriverTestAndroid(String... params) {

		String device = System.getProperty("Device");
		String platForm = System.getProperty("Platform");
		String version = System.getProperty("PlatformVersion");
		String pack = System.getProperty("Package");
		String activity = System.getProperty("Activity");
		String port = System.getProperty("Port");

		AppiumDriver dr = null;

		String dev = params.length > 0 ? params[0] : device;
		String pla = params.length > 1 ? params[1] : platForm;
		String ver = params.length > 2 ? params[2] : version;
		String PACKAGE = params.length > 3 ? params[3] : pack;
		String ACTIVITY = params.length > 4 ? params[4] : activity;
		String PORT = params.length > 5 ? params[5] : port;

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("resetKeyboard", true);
		caps.setCapability("settings[enforceXPath1]", true);
		caps.setCapability("newCommandTimeout", 90);
		caps.setCapability("ignoreUnimportantViews", false);
		caps.setCapability("skipDeviceInitialization", true);
		caps.setCapability("settings[waitForldleTimeout]", 100);
		caps.setCapability("appium:deviceName", dev);
		caps.setCapability("appium:port", PORT);
		caps.setCapability("appium:automationName", "uiautomator2");
		caps.setCapability("appium:platformVersion", ver);
		if (pla.equalsIgnoreCase("Android")) {
			caps.setCapability("appium:appPackage", PACKAGE);
			caps.setCapability("appium:appActivity", ACTIVITY);
		}
		try {
			dr = new AndroidDriver(new URL("http://localhost:4723/"), caps);
		} catch (MalformedURLException e) {
			assertFalse(true, "Loi khoi tao driver");
		}
		return dr;
	}

	/*
	 * tao driver param[0]: app can test param[1]: name device/simulator chay test
	 * param[2]: platform chay test param[3]: version platform chay test param[4]:
	 * package khoi tao param[5]: activity khoi tao
	 * 
	 */
	public AppiumDriver initDriverTest(String... params) {
		String device = System.getProperty("Device");
		String platForm = System.getProperty("PlatForm");
		String app = System.getProperty("App");
		String version = System.getProperty("PlatformVersion");
		String pack = System.getProperty("Package");
		String activity = System.getProperty("Activity");
		String uuid = System.getProperty("UUID");
		String port = System.getProperty("Port");

		AppiumDriver dr = null;
		String ap = params.length > 0 ? getAbsoluteFilePath(File.separator + "app" + File.separator + params[0])
				: getAbsoluteFilePath(File.separator + "app" + File.separator + app);
		String dev = params.length > 1 ? params[1] : device;
		String pla = params.length > 2 ? params[2] : platForm;
		String ver = params.length > 3 ? params[3] : version;
		String PACKAGE = params.length > 4 ? params[4] : pack;
		String ACTIVITY = params.length > 5 ? params[5] : activity;

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:app", ap);
		if (dev != null && dev != "") {
			caps.setCapability("appium:deviceName", dev);
		}
		caps.setCapability("platformName", pla);
		caps.setCapability("resetKeyboard", true);
		caps.setCapability("settings[enforceXPath1]", true);
		caps.setCapability("newCommandTimeout", 90);
		caps.setCapability("ignoreUnimportantViews", false);
		caps.setCapability("skipDeviceInitialization", true);
		caps.setCapability("settings[waitForldleTimeout]", 100);
		if (pla.equalsIgnoreCase("Android")) {
			caps.setCapability("automationName", "uiautomator2");
			caps.setCapability("appium:udid", dev);
			if (ver != null && ver != "") {
				int v;
				if (ver.contains(".")) {
					v = Integer.parseInt(ver.substring(0, ver.indexOf(".")));
				} else {
					v = Integer.parseInt(ver);
				}
				if (v >= 10) {
					caps.setCapability("autoGrantPermissions", true);
				}
			}
			if (PACKAGE != null && ACTIVITY != null) {
				caps.setCapability("appPackage", PACKAGE);
				caps.setCapability("appActivity", ACTIVITY);
			}
			try {
				dr = new AndroidDriver(new URL("http://localhost:4723/"), caps);
			} catch (MalformedURLException e) {
				assertFalse(true, "Loi khoi tao driver");
			}
		} else {
			if (uuid != null) {
				caps.setCapability("appium:udid", uuid);
			}
			caps.setCapability("useNewWDA", false);
			if (port != null && port != "") {
				caps.setCapability("wdaLocalPort", port);
			}
			caps.setCapability("automationName", "XCUITest");
			try {
				dr = new IOSDriver(new URL("http://localhost:4723/"), caps);
			} catch (Exception e) {
				assertFalse(true, "Loi khoi tao driver");
			}
		}
		softAssert = new SoftAssert();
		return dr;
	}

	public void pause(long timeInMillis) {
		try {
			Thread.sleep(timeInMillis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void sendKeyToElement(Object locator, String keys) {
        try {
        	WebElement element = getElementPresent(locator, 1000, 0);
            element.clear(); 
            element.sendKeys(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public long getMeasureScreenTransitionTime(By locator,String screen1, String screen2) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		Instant startTime = Instant.now();

		wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		Instant endTime = Instant.now();

		long transitionTime = Duration.between(startTime, endTime).toMillis();

		info("-----------------------------------------------------------------------------------------------");
		
		info("Thoi gian bat dau do la: " + startTime);

		info("Thoi gian ket thuc do la: " + endTime);

		info("Thoi gian chuyen tu man hinh " + screen1 + " den man hinh " + screen2 + " la: " + transitionTime + " milliseconds");

		info("Thoi gian chuyen tu man hinh " + screen1 + " den man hinh " + screen2 + " la: " + transitionTime/1000.0 + " seconds");

		info("-----------------------------------------------------------------------------------------------");
		return transitionTime;
	}
	 public void checkScreenSwitchTime(long number, String screen1, String screen2) {
		 if (number/1000.0 >= 1.0) {
			 info("******************************************************************************************");
			 info("Thoi gian chuyen tu man hinh " + screen1 + " den man hinh " + screen2 + " la: " + number/1000.0 + " seconds thoi gian phan hoi nhieu hon 1s" );
	        }
	    }
	
	
	
	
	public void scrollDown(int numberOfScrolls) {
        // Lấy kích thước của màn hình
        int screenWidth = driver.manage().window().getSize().width;
        int screenHeight = driver.manage().window().getSize().height;

        // Tính toán các điểm bắt đầu và kết thúc cho thao tác cuộn
        int startX = screenWidth / 2; // Điểm giữa màn hình theo chiều ngang
        int startY = (int) (screenHeight * 0.5); // 50% chiều cao màn hình từ trên xuống
        int endY = (int) (screenHeight * 0.2); // 20% chiều cao màn hình từ trên xuống

        // Tạo một PointerInput để mô phỏng thao tác chạm
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        for (int i = 0; i < numberOfScrolls; i++) {
            // Tạo một chuỗi hành động (Sequence) để mô phỏng thao tác cuộn
            Sequence scrollSequence = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(1000), PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            // Thực thi chuỗi hành động
            driver.perform(Arrays.asList(scrollSequence));
        }
	}
	public WebElement waitForElementNotPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;

		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (elem == null) {
				return null;
			}
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1) {
			assert false : ("Timeout after " + timeout + "ms waiting for element not present: " + locator);
		}
		return elem;
	}

	public void checkElementNotPresent(Object locator, int... opParams) {
		WebElement e = null;
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;

		for (int tick = 1; tick <= timeout / WAIT_INTERVAL; tick++) {
			try {
				e = driver.findElement(by);
				if (e != null && tick == timeout / WAIT_INTERVAL) {
					Assert.fail("Element " + locator + " is present");
				}
				info("Wait element not present with time: " + tick + 1);
			} catch (NoSuchElementException ex) {
				pause(WAIT_INTERVAL);
				if (tick == timeout / WAIT_INTERVAL) {
					return;
				}
			}
		}
	}

	public WebElement getElement(Object locator, WebDriver... dr) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebDriver driver_web = dr.length > 0 ? dr[0] : driver;
		WebElement elem = null;
		try {
			elem = driver_web.findElement(by);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getElement(locator);
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getElement(locator);
		} catch (WebDriverException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getElement(locator);
		}

		return elem;
	}

	public String getSizeOfListElement(Object locator) {
		return String.valueOf(getListElement(locator).size());
	}

	/**
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> getListElement(Object locator) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		List<WebElement> elementOptions;
		try {
			elementOptions = driver.findElements(by);
			return elementOptions;
		} catch (NoSuchElementException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			getListElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;

	}

	/**
	 * lay cac gia tri thuoc tinh cua 1 mang doi tuong element
	 * 
	 * @param locator
	 * @param attribute
	 * @return
	 */
	public String[] getAttOfListElement(Object locator, String attribute) {
		String[] att = new String[20];
		List<WebElement> list;
		list = getListElement(locator);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				att[i] = list.get(i).getAttribute(attribute);
			}
		}
		return att;
	}

	public void checkCycling(Exception e, int loopCountAllowed) {
		//info("Co exception xay ra: " + e.getClass().getName());
		if (loopCount > loopCountAllowed) {
			Assert.fail("Qua thoi gian nhung khong thay hoac thay doi tuong " + e.getMessage());
		}
	//info("Lap lai lan thu " + loopCount);
		loopCount++;
	}

	public WebElement getElementPresent(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		int isAssert = opParams.length > 1 ? opParams[1] : 1;
		int notDisplayE = opParams.length > 2 ? opParams[2] : 0;
		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			if (notDisplayE == 2) {
				elem = getElement(locator);
			} else {
				elem = getDisplayedElement(locator);
			}
			if (null != elem)
				return elem;
			pause(WAIT_INTERVAL);
		}
		if (isAssert == 1) {
			String date = getDateTime("yyyyMMddHHmmss");
			info("date");
			captureScreen(driver, "Loi_" + date + ".jpg");
			assert false : ("Qua thoi gian " + timeout + "ma khong tim thay doi tuong " + locator);
			quitDriver(driver);
		}
		return null;
	}

	public WebElement getElementPresentNoAssert(Object locator, int... opParams) {
		WebElement elem = null;
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		for (int tick = 0; tick < timeout / WAIT_INTERVAL; tick++) {
			try {
				elem = driver.findElement(by);
				if (null != elem)
					return elem;
				pause(WAIT_INTERVAL);
				info("Lap lai lan thu " + tick + 1);
			} catch (NoSuchElementException ex) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			} catch (WebDriverException e) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			} catch (IllegalStateException e) {
				if (tick == timeout / WAIT_INTERVAL) {
					return null;
				}
			}
		}
		return elem;
	}

	/**
	 * get a display element in web page
	 * 
	 * @param locator @ return
	 */
	public WebElement getDisplayedElement(Object locator, WebDriver... dr) {
		By by = locator instanceof By ? (By) locator : By.xpath(locator.toString());
		WebDriver driver_web = dr.length > 0 ? dr[0] : driver;
		WebElement e = null;
		try {
			if (by != null)
				e = driver_web.findElement(by);
			if (e != null) {
				if (isDisplay(by))
					return e;
			}
		} catch (NoSuchElementException ex) {
			checkCycling(ex, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		} catch (WebDriverException ex) {
			checkCycling(ex, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			getDisplayedElement(locator);
		} finally {
			loopCount = 0;
		}
		return null;
	}

	/**
	 * checking an element is displayed in web page
	 * 
	 * @param locator
	 * @return
	 */
	public boolean isDisplay(Object locator) {
		boolean bool = false;
		WebElement e = getElement(locator);
		try {
			if (e != null)
				bool = e.isDisplayed();
		} catch (StaleElementReferenceException ex) {
			checkCycling(ex, 10);
			pause(WAIT_INTERVAL);
			isDisplay(locator);
		} finally {
			loopCount = 0;
		}
		return bool;
	}

	/**
	 * @param driver
	 */
	public void captureScreen(AppiumDriver driver, String fileName) {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String dir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(dir + "\\capture_screen\\" + fileName + ".jpg"));
			info("Capture man hinh to file " + fileName);
		} catch (Exception e) {
			info("Khong capture duoc man hinh");
		}
	}

	/**
	 * get datetime
	 * 
	 * @param format
	 */
	public String getDateTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		String dateTime = dateFormat.format(cal.getTime());
		info("time at moment is " + dateTime);
		return dateTime;
	}

	public By convertStringToBy(Object locator, String... option) {
		By by = null;
		String loc = locator.toString();
		if (option.length > 0) {
			loc = loc.replaceAll("&option", option[0]);
		}
		if (loc.startsWith("//")) {
			by = By.xpath(loc);
		} else {
			by = AppiumBy.iOSClassChain(loc);
		}
		return by;
	}

	/**
	 * click on an element
	 * 
	 * @param locator
	 * @param opParams
	 */
	public void click(Object locator, Object... opParams) {
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		String opt = (String) (opParams.length > 1 ? opParams[1] : "");
		if (opt != "") {
			locator = convertStringToBy(locator, opt);
		}
		try {
			WebElement element = getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay);
			if (element != null) {
				element.click();
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} catch (Exception e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			click(locator, notDisplay);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * Click on an element of list element
	 * 
	 * @param locator
	 * @param index
	 */
	public void clickListElement(Object locator, int index) {
		List<WebElement> list = getListElement(locator);
		if (list.size() > 0) {
			info("So luong element tim duoc: " + list.size());
			list.get(index).click();
		}
	}

	/**
	 * input data to element
	 * 
	 * @param locator
	 * @param value
	 * @param validate
	 */
	public void type(Object locator, String value, boolean validate, boolean... clear) {
		boolean clean = clear.length > 0 ? clear[0] : true;
		try {
			for (int loop = 1;; loop++) {
				if (loop >= ACTION_REPEAT) {
					Assert.fail("Qua thoi gian khi input du lieu: " + value + " vao doi tuong " + locator);
				}
				WebElement element = getElementPresent(locator, 1000, 0);
				if (element != null) {
					if (clean)
						element.clear();
					element.click();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				info("Lap lai tac dong input text lan thu " + loop);
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} catch (Exception e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			type(locator, value, validate);
		} finally {
			loopCount = 0;
		}
		driver.navigate().back();
	}

	public void typeHinput(Object locator, String text, boolean validate) {
		if (text != null) {
			if (getElementPresent(locator) != null) {
				try {
					WebElement e = getElementPresent(locator);
					e.click();
					e.sendKeys(Keys.CONTROL + "a");
					e.sendKeys(text);
					for (int i = 0; i < 5; i++) {
						String am = getValue(locator, 2);
						if (am != null) {
							if (am.equalsIgnoreCase(text)) {
								break;
							} else {
								e.sendKeys(Keys.CONTROL + "a");
								e.sendKeys(text);
							}
						}
					}
				} catch (StaleElementReferenceException ex) {
					typeHinput(locator, text, validate);
				}
			}
		}
	}

	/**
	 * get value of element in web page
	 * 
	 * @param locator
	 * @return
	 */
	public String getValue(Object locator, Object... opParams) {
		int notDisplay = (Integer) (opParams.length > 0 ? opParams[0] : 0);
		try {
			String platform = driver.getCapabilities().getCapability("platformName").toString();
			String plat = platform != null ? platform : "android";
			if (plat.equalsIgnoreCase("android")) {
				return getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay).getAttribute("text");
			} else {
				return getElementPresent(locator, DEFAULT_TIMEOUT, 1, notDisplay).getAttribute("value");
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * get text of element
	 * 
	 * @param locator
	 * @return
	 */
	public String getText(Object locator) {
		WebElement element = null;
		try {
			element = getElementPresent(locator);
			return element.getText();
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getText(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * get absolute path of file
	 * 
	 * @param relativeFilePath
	 * @return
	 */
	public String getAbsoluteFilePath(String relativeFilePath) {
		String curDir = System.getProperty("user.dir");
		String absolutePath = curDir + relativeFilePath;
		return absolutePath;
	}

	/**
	 * compare 2 string
	 * 
	 * @param s1
	 * @param s2
	 */
	public void verifyCompare(String s1, String s2) {
		if (s1 != "" && s1 != null && s2 != null && s2 != "") {
			Assert.assertFalse(!s1.equalsIgnoreCase(s2), "So sanh khong bang nhau: " + s1 + " va " + s2);
		} else if ((s1 == "" || s1 == null) && (s2 == "" || s2 == null)) {
			info("2 truong du lieu can so sanh deu null");
		} else {
			Assert.fail("Du lieu so sanh co 1 truong bi null");
		}
	}

	/**
	 * 
	 * @param dateBefore
	 * @param dateAfter
	 */
	public void compareDateBeforeDate(String dateBefore, String dateAfter) {
		Boolean compare = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			compare = sdf.parse(dateBefore).before(sdf.parse(dateAfter));
			info("Ket qua so sanh thoi gian: " + compare);
		} catch (Throwable e) {
			info("Loi:" + e);
		}
		Assert.assertFalse(!compare, "Moc thoi gian " + dateAfter + " khong lon hơn " + dateBefore);
	}

	public void verifyContains(String s1, String s2) {
		if (s1 != null && s2 != null) {
			Assert.assertFalse(!s2.contains(s1), "Chuỗi " + s1 + " không nằm trong chuỗi " + s2);
		}
	}

	public void verifyNotContains(String s1, String s2) {
		if (s1 != null && s2 != null) {
			Assert.assertFalse(s2.contains(s1), "Fail do chuoi " + s2 + "  van chua chuoi " + s1);
		}
	}

	/**
	 * scroll to text in screen
	 * 
	 * @param text
	 */
	public void scrollToText(String text) {
		try {
			driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()"
					+ ".resourceId(\"com.android.settings:id/content\")).scrollIntoView(" + "new UiSelector().text("
					+ text + "));"));
		} catch (NoSuchElementException e) {
			checkCycling(e, 10000 / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			scrollToText(text);
		} finally {
			loopCount = 0;
		}
	}

	public void typeNotClick(Object locator, String value, boolean validate, boolean... clear) {
		boolean clean = clear.length > 0 ? clear[0] : true;
		try {
			for (int loop = 1;; loop++) {
				if (loop >= ACTION_REPEAT) {
					Assert.fail("Qua thoi gian khi input du lieu: " + value + " vao doi tuong " + locator);
				}
				WebElement element = getElementPresent(locator, 10000, 0);
				if (element != null) {
					if (clean)
						element.clear();
					element.sendKeys(value);
					if (!validate || value.equals(getValue(locator))) {
						break;
					}
				}
				info("Lap lai tac dong input text lan thu " + loop);
				pause(WAIT_INTERVAL);
			}
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			typeNotClick(locator, value, validate);
		} catch (NoSuchElementException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			typeNotClick(locator, value, validate);
		} catch (Exception e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			typeNotClick(locator, value, validate);
		} finally {
			loopCount = 0;
		}

	}

	/**
	 * parse to object from a xpath contains option
	 * 
	 * @param xpathOption
	 * @param option
	 * @return
	 */
	public String parseStringToObject(String xpathOption, String option) {
		return xpathOption.replaceAll("&option", option);
	}

	public String trimCharactor(String input, String trim) {
		info("Xau can xu ly trim: " + input);
		if (input != "" && input != null && trim != "") {
			if (trim == ".") {
				return input.replaceAll("\\.", "");
			} else {
				return input.replaceAll(trim, "");
			}
		} else
			return "";
	}

	/**
	 * get first day of month of next month
	 * 
	 * @param addMonth
	 * @return
	 */
	public String getFirstDayOfMonth(int addMonth) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		info("Current date: " + df.format(cal.getTime()));
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return df.format(cal.getTime());
	}

	public String getExpireDate(int addMonth, int index) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		info("Current date: " + df.format(cal.getTime()));
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		String dateStr = df.format(cal.getTime());

		String[] expireDate = null;
		expireDate = dateStr.split("/");
		return expireDate[index];

	}

	/**
	 * 
	 * @param tbSearch
	 * @param textSearch
	 * @param xpath
	 */
	public void selectOptionFromComBoxSearch(Object tbSearch, String textSearch, String xpath) {
		type(tbSearch, textSearch, false, false);
		pause(500);
		click(By.xpath(xpath.replaceAll("&option", textSearch)));
		pause(1000);
	}

	public void selectOptionFromCombobox(String xpath, String option) {
		if (option != null) {
			String locator = xpath.replaceAll("&option", option);
			click(locator);
		}
	}

	/**
	 * get message from Toast object
	 * 
	 * @return
	 */
	public String getMessageToast() {
		WebElement toast = getElementPresent(By.xpath("//android.widget.Toast[1]"), DEFAULT_TIMEOUT, 1, 2);
		String message = toast.getAttribute("name");
		info("message Toast is: " + message);
		return message;
	}

	public void scrollToElementUsingTouchAction(Object element, String... direct) {
		String dir = direct.length > 0 ? direct[0] : "DOWN";
		if (dir.equalsIgnoreCase("DOWN")) {
			scrollToElementWithTouchAction(element, "V", (double) 4 / 5, (double) 1 / 8);
		} else {
			scrollToElementWithTouchAction(element, "V", (double) 1 / 8, (double) 4 / 5);
		}
	}

	public void scrollOnUIPickerView(Object element, int... soLanScroll) {
		scrollToElementWithTouchAction(element, "V", 0.9, 0.75, soLanScroll);
	}

	/**
	 * Dung cho scroll tren UI Picker tung dong
	 * 
	 * @param element
	 * @param soLanScroll
	 */
	public void scrollOnUIPickerViewTab(Object element, int... soLanScroll) {
		scrollToElementWithTouchAction(element, "V", 0.9, 0.85, soLanScroll);
	}

	public void selectValueFromUIPickerWheel(Object uiPicker, String value) {
		WebElement pick = getElementPresent(uiPicker);
		pick.sendKeys(value);
	}

	/**
	 * Su dung voi nhung popup hien thi ko biet truoc
	 * 
	 * @param obj
	 * @param opParams
	 */
	public void clickElementIfDisplay(Object obj, int... opParams) {
		int timeout = opParams.length > 0 ? opParams[0] : DEFAULT_TIMEOUT;
		if (getElementPresentNoAssert(obj, timeout) != null) {
			click(obj);
		}
	}

	public void verifyNotNull(String s) {
		if (s == null || s == "") {
			Assert.fail("Du lieu  null");
		}
	}

	public void verifyCompareNotEqual(String s1, String s2) {
		if (s1 != "" && s1 != null && s2 != null && s2 != "") {
			Assert.assertTrue(!s1.equalsIgnoreCase(s2),
					"Pass neu 2 truong so sanh khong bang nhau: " + s1 + " va " + s2);
		} else if ((s1 == "" || s1 == null) && (s2 == "" || s2 == null)) {
			info("2 truong du lieu can so sanh deu null");
		} else {
			Assert.fail("Du lieu so sanh co 1 truong bi null");
		}
	}

	/**
	 * (from > to)
	 * 
	 * @param element
	 * @param direct
	 * @param from
	 * @param to
	 * @param soLanScroll
	 */
	public void scrollToElementWithTouchAction(Object element, String direct, double from, double to,
			int... soLanScroll) {
		int soLan = (Integer) (soLanScroll.length > 0 ? soLanScroll[0] : 10);
		int denominator = (Integer) (soLanScroll.length > 1 ? soLanScroll[1] : 2);
		int numerator = (Integer) (soLanScroll.length > 2 ? soLanScroll[2] : 1);
		int startX;
		int endX;
		int topY;
		int bottomY;
		double de = denominator;
		double num = numerator;

		WebElement el = getElementPresentNoAssert(element, 5000);
		if (direct.equalsIgnoreCase("V")) {
			startX = endX = (int) ((driver.manage().window().getSize().getWidth()) * (num / de));
			topY = (int) ((driver.manage().window().getSize().getHeight()) * to);
			bottomY = (int) ((driver.manage().window().getSize().getHeight()) * from);
		} else {
			startX = (int) ((driver.manage().window().getSize().getWidth()) * from);
			endX = (int) ((driver.manage().window().getSize().getWidth()) * to);
			topY = bottomY = (int) ((driver.manage().window().getSize().getHeight()) * (num / de));
		}
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

		int i = 0;
		while ((el == null) || (!el.isDisplayed())) {
			i++;
			Sequence scroll = new Sequence(finger, i);
			scroll.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, bottomY));
			scroll.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
			scroll.addAction(
					finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, topY));
			scroll.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
			driver.perform(List.of(scroll));
			if (i == soLan) {
				break;
			}
			el = getElementPresentNoAssert(element, 5000);
		}
	}

	/**
	 * @param locator
	 * @param att
	 * @return
	 */
	public String getAttribute(Object locator, String att, int... opParams) {
		try {
			return getElementPresent(locator, opParams).getAttribute(att);
		} catch (StaleElementReferenceException e) {
			checkCycling(e, DEFAULT_TIMEOUT / WAIT_INTERVAL);
			pause(WAIT_INTERVAL);
			return getValue(locator);
		} finally {
			loopCount = 0;
		}
	}

	/**
	 * 
	 * @return dinh dang Tháng 1
	 */
	public String getAndFormatMonth(String... delta) {
		String del = delta.length > 0 ? delta[0] : "0";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -Integer.parseInt(del));
		int month = cal.get(Calendar.MONTH) + 1;
		return "Tháng " + month;
	}

	public String[] shouldMatchRegex(String pattern, String message) {
		String[] result = new String[10];
		if (message == "" || message == null) {
			Assert.fail("message bi null");
		}
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(message);
		if (matcher.find()) {
			/*
			 * Tra ve chuoi match patten result[0] => toan bo chuoi match patten Cac phan tu
			 * tiep theo la cac gia tri muon lay ra trong patten
			 */
			info("Patten match String");
			for (int i = 0; i < matcher.groupCount() + 1; i++) {
				result[i] = matcher.group(i);
			}
		} else {
			Assert.fail("String, patten does not match");
		}

		return result;
	}

	/* Evaluates the given expression and returns the result in String format. */
	public String evaluate(String expression) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		String result = null;
		try {
			result = engine.eval(expression).toString();
			if (result.endsWith(".0")) {
				System.out.println("Ket qua cua bieu thuc " + expression + " la: " + result + " Return phan nguyen");
				for (int i = 0; i < result.length(); i++) {
					if (result.charAt(i) == '.') {
						return result.substring(0, i);
					}
				}
			}
			System.out.println("Ket qua cua bieu thuc " + expression + " la: " + result);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String replaceCharacter(String input, String char1, String char2) {
		if (input != "" && input != null) {
			return input.replaceAll(char1, char2);
		} else
			return "";
	}

	public String getMinOfArray(String[] array) {
		Long[] a = sortArray(array);
		if (a.length > 0) {
			info("Min value is" + a[0].toString());
			return a[0].toString();
		} else {
			return "";
		}
	}

	public String getMaxOfArray(String[] array) {
		Long[] a = sortArray(array);
		if (a.length > 0) {
			info("Max value is" + a[a.length - 1].toString());
			return a[a.length - 1].toString();
		} else {
			return "";
		}
	}

	public Long[] sortArray(String[] array) {
		Long[] a = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i] != "") {
				a[i] = Long.parseLong(array[i]);
			}
		}
		Arrays.sort(a);
		info("Sorted Array: " + Arrays.toString(a));
		return a;
	}

	/**
	 * get key having min value from output of SOAP service
	 * 
	 * @param xml
	 * @param keys
	 * @param value
	 * @param conditions
	 * @return
	 */

	public void singleTab(int x, int y) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(List.of(tap));
	}

	public void doubleTab(int x, int y) {
		PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
		Sequence tap = new Sequence(finger, 1);
		tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(100)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(50)));
		tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		tap.addAction(new Pause(finger, Duration.ofMillis(100)));
		tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		driver.perform(List.of(tap));
	}

	public void tabElement(Object element) {
		WebElement e = getElement(element, driver);
		if (e != null) {
			Point sourceLocation = e.getLocation();
			Dimension sourceSize = e.getSize();
			int centerX = sourceLocation.getX() + sourceSize.getWidth() / 2;
			int centerY = sourceLocation.getY() + sourceSize.getHeight() / 2;
			singleTab(centerX, centerY);
		}
	}

	public String formatCurrency(String number, String... param) {
		String languageCountry = param.length > 0 ? param[0] : "vi-VN"; // en-US
		String[] p = languageCountry.split("-");
		Locale locale = new Locale(p[0], p[1]);
		NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
		info(formatter.format(Integer.parseInt(number)));
		return formatter.format(Integer.parseInt(number)).replace(" ₫", "");
	}

	public String concatString(String s1, String s2) {
		return s1.concat(s2);
	}

	/**
	 * check field is null = ""
	 * 
	 * @param s
	 */
	public void verifyNull(String s) {
		if (!s.equalsIgnoreCase("")) {
			Assert.fail("Du lieu khong null");
		}
	}

	public void quitDriver(AppiumDriver dr) {
		softAssert.assertAll();
		if (dr.toString().contains("null")) {
			System.out.print("All Browser windows are closed ");
		} else {
			dr.quit();
		}
	}

}
