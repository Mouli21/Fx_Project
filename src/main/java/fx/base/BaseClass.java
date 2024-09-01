package fx.base;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static RemoteWebDriver driver;
	public WebDriverWait wait;
	
	
 	public RemoteWebDriver launchBrowser(String browser, String url) {
		
		try {
		
		if (browser.equalsIgnoreCase("chrome")) {
			
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("Edge")) {
			
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if (browser.equalsIgnoreCase("firefox")) {
			
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if (browser.equalsIgnoreCase("safari")) {
			
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}
		
		driver.navigate().to(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	} catch (Exception e) {
		throw new RuntimeException();
	} 
		return driver;
 }
	
	public WebElement locateElement(String locator, String value) {
		
		try {
			switch (locator.toLowerCase()) {
			case "id"     :return driver.findElement(By.id(value));
			case "name"   :return driver.findElement(By.name(value));
			case "class"  :return driver.findElement(By.className(value));
			case "xpath"  :return driver.findElement(By.xpath(value));
			case "link"   :return driver.findElement(By.linkText(value));
			}
			
		} catch (NoSuchElementException e) {
			throw new RuntimeException();
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return null;

	}
	public void clearAndType(WebElement ele,String data) {//to clear the field using webelement
		try {
		     
			ele.clear();
			ele.sendKeys(data);
			
		} catch (ElementNotInteractableException e) {
			System.err.print(e);
		}
		
	}
	public void click(WebElement element) {

		
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			
			element.click();
		}
		catch (Exception e) {
			
			System.err.print(e);
		}


	}
	
	public void pageTitle() {
		String title = driver.getTitle();

	}
}
