package core;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Linear {
	static Properties p = new Properties();
	static Writer report;
	static String ls = System.getProperty("line.separator");
	static WebDriver driver;

	public static boolean isElementPresent(By by) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty())
			return true;
		else
			return false;
	}
	public static void setValue(By by, String value) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (!driver.findElements(by).isEmpty() && driver.findElement(by).isDisplayed())
			driver.findElement(by).sendKeys(p.getProperty(value));
	}
	
	public static void main(String[] args) throws Exception {
		//settings
		Logger.getLogger("").setLevel(Level.OFF);
		p.load(new FileInputStream("./input.properties"));
		report = new FileWriter("./report_firefox_02.csv", false);
		String driverPath = "";
		if (System.getProperty("os.name").toUpperCase().contains("MAC"))
			driverPath = "./resources/webdrivers/mac/geckodriver.sh";
		else if (System.getProperty("os.name").toUpperCase().contains("WINDOWS"))
			driverPath = "./resources/webdrivers/pc/geckodriver.exe";
		else
			throw new IllegalArgumentException("Unknown OS");
		System.setProperty("webdriver.gecko.driver", driverPath);
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get(p.getProperty("url"));
		
		
		//header to write our report
		System.out.println("#,Browser,Page,Field,isPresent,Value");
		report.write("#,Browser,Page,Field,isPresent,Value");
		report.write(ls);
		
		// Page SignUp
		// 01 :: First Name
		report.write("01,Firefox,index.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "\n");
		
		System.out.print("01,Firefox,index.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "\n");
		setValue(By.id(p.getProperty("fname_id")), "fname_value");

		
		// 02 :: Last Name
		report.write("02,Firefox,index.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "\n");
		
		System.out.print("02,Firefox,index.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "\n");
		setValue(By.id(p.getProperty("lname_id")), "lname_value");
		
		
		// 03 :: Email
		report.write("03,Firefox,index.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value") + "\n");
		
		System.out.print("03,Firefox,index.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value") + "\n");
		setValue(By.id(p.getProperty("email_id")), "email_value");
		
		
		// 04 :: Phone
		report.write("04,Firefox,index.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value") + "\n");
		
		System.out.print("04,Firefox,index.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value") + "\n");
		setValue(By.id(p.getProperty("phone_id")), "phone_value");
		
		// SUBMIT	

		driver.findElement(By.id(p.getProperty("submit_id"))).submit();
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.titleIs("Confirmation"));		
		
		//Confirmation Page
		// 05 :: First Name
		report.write("05,Firefox,confirmation.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "\n");
		
		System.out.print("05,Firefox,confirmation.php,First Name," + isElementPresent(By.id(p.getProperty("fname_id"))) + ","
				+ p.getProperty("fname_value") + "\n");
		
		// 06 :: Last Name
		report.write("06,Firefox,confirmation.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "\n");
		
		System.out.print("06,Firefox,confirmation.php,Last Name," + isElementPresent(By.id(p.getProperty("lname_id"))) + ","
				+ p.getProperty("lname_value") + "\n");
		
		// 07 :: Email
		report.write("07,Firefox,confirmation.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value") + "\n");
		
		System.out.print("07,Firefox,confirmation.php,Email," + isElementPresent(By.id(p.getProperty("email_id"))) + ","
				+ p.getProperty("email_value") + "\n");
		// 08 :: Phone
		report.write("08,Firefox,confirmation.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value") + "\n");
		
		System.out.print("08,Firefox,confirmation.php,Phone," + isElementPresent(By.id(p.getProperty("phone_id"))) + ","
				+ p.getProperty("phone_value") + "\n");
		
		report.flush();
		report.close();
		driver.quit();
	}
	
}
