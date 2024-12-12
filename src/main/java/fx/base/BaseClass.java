package fx.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	
	public static RemoteWebDriver driver;
	public WebDriverWait wait;
	private String lowerCase;
	public String pmscustcode = "30002";
	
	
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
			throw new RuntimeException(e);
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
	public WebElement click(WebElement element) {

		
		try {
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			
			element.click();
		}
		catch (Exception e) {
			
			System.err.print(e);
		}
		return element;

	}
	
	public void pageTitle() {
		String title = driver.getTitle();

	}
	
	public String dbConnection(String query, String columnName) {
	
		String str = null;
			
       try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Driver connected");
			
			String connection = "jdbc:sqlserver://fxfrontoffice.database.windows.net;user=fxfrontofficeadmin;password=sK9c$3^mZ5Ah?HsX;databaseName=Training";
			Connection connect = DriverManager.getConnection(connection);
			
			System.out.println("Databsae is connected");
			
			Statement var = connect.createStatement();
			ResultSet executeQuery = var.executeQuery(query);
			if(executeQuery.next()) 
			{
			str = executeQuery.getString(columnName);
			System.out.println(str);
			}
			var.close();
	}
       catch(Exception e)
       {
    	   System.err.println();
       }
	return str;
	
}
	public void dateChange()
	{
		locateElement("xpath", "(//button[@aria-label='Open calendar'])").click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); //waiting for 10 seconds
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='mat-calendar-content']"))); //waiting explicitly
		
	  String currentYrMonth =	locateElement("Xpath", "//button[@aria-label='Choose month and year']//span").getText().toLowerCase(); //retrieve month & year
	  
	  WebElement nextMonth = locateElement("XPAth", "//button[@aria-label='Next month']");
	  
	  WebElement prevMonth = locateElement("XPAth", "//button[@aria-label='Previous month']");
	  
	 
	  
	  try {
		  if (!nextMonth.isEnabled()) 
		  {
			if (prevMonth.isEnabled())
			{
				while(!currentYrMonth.toLowerCase().equalsIgnoreCase("Apr 2024"))
				{
					prevMonth.click();
					currentYrMonth =locateElement("Xpath", "//button[@aria-label='Choose month and year']//span").getText();
				}
				locateElement("xpath", "(//tr[@role='row'])[4] //td[@aria-label='26 April 2024']").click();
			}
		  }
		  else {
			System.out.println("Next month is enabled for the current month & year");
		}
	  }
	  catch (Exception e) 
	  {
		System.err.print(e);
	}
		
		
		

	}
	
	public Date dateComparision(String date1) throws ParseException 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date d1 = sdf.parse(date1);
		
		//Date d2 = sdf.parse(date2);
		
		return d1;

	}
	
	public void mandatoryFields() {
		locateElement("XPATH", "//span[@class='mat-form-field-label-wrapper']//label//span");

	}
	
	public void scrollWebPage(int x, int y) 
	{
		Actions action = new Actions(driver);
		action.scrollByAmount(x, y).build().perform();
		

	}
}