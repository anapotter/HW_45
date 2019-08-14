package core;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.*;

public class Function {

	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");
	static WebDriver driver;

	static void getWebDriver(String browser) {
		Logger.getLogger("").setLevel(Level.OFF);
		String driverPath = "";
		if (browser.equalsIgnoreCase("firefox")) {
			if (System.getProperty("os.name").toUpperCase().contains("MAC"))
				driverPath = "./resources/webdrivers/mac/geckodriver.sh";
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
				driverPath = "./resources/webdrivers/pc/geckodriver.exe";
			else
				throw new IllegalArgumentException("Unknown OS");
			System.setProperty("webdriver.gecko.driver", driverPath);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} else if (browser.equalsIgnoreCase("chrome")) {
			if (System.getProperty("os.name").toUpperCase().contains("MAC"))
				driverPath = "./resources/webdrivers/mac/chromedriver";
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
				driverPath = "./resources/webdrivers/pc/chromedriver.exe";
			else
				throw new IllegalArgumentException("Unknown OS");
			System.setProperty("webdriver.chrome.driver", driverPath);
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions option = new ChromeOptions();
			option.addArguments("disable-infobars");
			option.addArguments("--disable-notifications");
			if (System.getProperty("os.name").toUpperCase().contains("MAC"))
				option.addArguments("-start-fullscreen");
			else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
				option.addArguments("--start-maximized");
			else
				throw new IllegalArgumentException("Unknown OS");
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} else if (browser.equalsIgnoreCase("safari")) {
			if (!System.getProperty("os.name").contains("Mac")) {
				throw new IllegalArgumentException("Safari is available only on Mac");
			}
			driver = new SafariDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} else if (browser.equalsIgnoreCase("edge")) {
			if (!System.getProperty("os.name").contains("Windows"))
				throw new IllegalArgumentException("MS Edge is available only on Windows");
			System.setProperty("webdriver.edge.driver", "./resources/webdrivers/pc/MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} else {
			throw new WebDriverException("Unknown WebDriver");
		}
	}

	public static void open(String browser, String url) {
		getWebDriver(browser);
		driver.get(url);
	}

	public static boolean isElementPresent(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty())
			return true;
		else
			return false;
	}

	public static String getSize(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getRect().getDimension().toString().replace(", ", "x");
		else
			return "null";
	}

	public static String getLocation(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getRect().getPoint().toString().replace(", ", "x");
		else
			return "null";
	}

	public static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(p.getProperty(value));
	}

	public static String getValue(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			return driver.findElement(by).getText();
		else
			return "null";
	}

	static void submit(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).submit();
	}

	static String getOS() {
		return ((RemoteWebDriver) driver).getCapabilities().getPlatform().toString().trim();
	}

	static String getBrowser() {
		String browser = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toString().trim();
		return browser.replaceFirst(String.valueOf(browser.charAt(0)), String.valueOf(browser.charAt(0)).toUpperCase());
		// return browser.substring(0,1).toUpperCase() +
		// browser.substring(1).toLowerCase();
	}

	static String getFileName() {
		String file = driver.getCurrentUrl().toString().trim();
		return file.substring(file.lastIndexOf('/') + 1);
	}

	static void waitTitlePage(String title) {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.titleIs(title));
	}

	static void writeReportHeader(Writer report) throws IOException {
		report.write("#,Browser,Page,Field,isPresent,Value,Size,Location" + "\n");
		System.out.print("#,Browser,Page,Field,isPresent,Value,Size,Location" + "\n");
	}

	static void writeReportLine(String index, String fieldName, By by, Writer report) throws IOException {

		report.write(index + "," + Common.getBrowser() + "," + Common.getFileName() + "," + fieldName + ","
				+ Common.isElementPresent(by) + "," + Common.getValue(by) + "," + Common.getSize(by) + ","
				+ Common.getLocation(by) + "\n");

		System.out.print(index + "," + Common.getBrowser() + "," + Common.getFileName() + "," + fieldName + ","
				+ Common.isElementPresent(by) + "," + Common.getValue(by) + "," + Common.getSize(by) + ","
				+ Common.getLocation(by) + "\n");
	}

	static void quit() {
		driver.quit();
	}
	
	/*public static void main(String[] args)
	{
		open("firefox", p.getProperty("url"));
		
		quit();
	}*/

}
