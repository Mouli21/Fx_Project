package fx.pages;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import fx.base.BaseClass;

public class DashboardToSalesInvoice extends BaseClass {
	
	private String TTtext;
	
	public DashboardToSalesInvoice salesInvocieList() throws InterruptedException {
		
		try {
		Thread.sleep(4000);
		clearAndType(locateElement("xpath", "//input[@type='text']"), "sales invoice");
		}catch (ElementNotInteractableException e) {
			throw e;
		}
		
		try {
			
			String salesHeader = locateElement("xpath", "//h2").getText();
			
			if (salesHeader=="Invoice List") {
				
				System.out.println("DashboardToSalesInvoice screen is directed successfully..!");
			}
			
		} catch (NoSuchElementException e) {
			throw e;
		}
		return this;

	}
	
	public DashboardToSalesInvoice invoiceCreation() throws InterruptedException
	{
		try {
			boolean displayed = locateElement("xpath", "//button[@class='FX-btnCreate mat-fab mat-accent']").isDisplayed();
			Thread.sleep(4000);
			if (displayed == true) {
				locateElement("xpath", "//span[text()='+']").click();
				Thread.sleep(4000);
				//WebElement defaultTT = locateElement("xpath", "(//input[@type='text'])[2]"); //Sales field blank area
				WebElement defaultTT = locateElement("xpath", "//button[@aria-label='Clear']");
				defaultTT.click();
				Thread.sleep(2000);
				//defaultTT.click();
				WebElement dropDOwn = locateElement("xpath", "(//div[@class='mat-form-field-flex']//mat-icon)[2]");
				dropDOwn.click();
				
				List<WebElement> var = driver.findElements(By.xpath("//span[@class='mat-option-text']"));
				
     		String db =  dbConnection("Select TransactionTypeMasterCode from FXFAS_TransactionTypeMaster where BookType='C' and IsDeleted=0 and IsDefault=1");
				
				for(WebElement ref : var)
				{
					String text = ref.getText();
					
					System.out.println(text);
					
					//to split the values
					
					String split = text.split("-")[0];
					String trim = split.trim();
					System.out.println(trim);
					
					if(db.contains(trim))
					{
						System.out.println("Fetched the data & DB executed");
						ref.click();
						break;
					}
					else {
						System.out.println("Issue with if condition..! Check if block");
					}
					
				}
				
				try {
					
				  WebElement transDate = locateElement("xpath", "//input[@placeholder='Transaction Date']");
				  transDate.click();
				  String transactionDate = transDate.getAttribute("max");
			  //   String transactionDate	=  transDate.getText();
				
				LocalDate serverDate = LocalDate.now();
				
				
				//Take if required to change the server date
				
				  DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				  
				  String format = serverDate.format(date);
				 
				
			 
			 if(format.equals(transactionDate))
			 {
				 System.out.println("Server date & transaction date are same");
				 
			 }
			 else
			 {
				 System.out.println();
			 }
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		
			}
		} catch (ElementNotInteractableException e) {
			 throw  e;
		}
		return this;

	}
	
	/*
	 * public DashboardToSalesInvoice checkDefaultTT() throws InterruptedException {
	 * 
	 * String text = locateElement("Xpath",
	 * "(//input[@role='combobox'])[2]").getText(); //used to get the transaction
	 * type text.
	 * 
	 * String currentUrl = driver.getCurrentUrl();
	 * 
	 * JavascriptExecutor js = (JavascriptExecutor) driver;
	 * js.executeScript("window.open()") ;
	 * 
	 * for (String handles : driver.getWindowHandles()) { if
	 * (!handles.equals(currentUrl)) { driver.switchTo().window(handles); } else {
	 * throw new SessionNotCreatedException("Not able to launch" +currentUrl); } }
	 * driver.get(currentUrl); try { Thread.sleep(2000);
	 * clearAndType(locateElement("xpath", "//input[@type='text']"),
	 * "Transaction Type"); Thread.sleep(2000); String TTscreen =
	 * locateElement("xpath", "//h2").getText(); System.out.println(TTscreen); if
	 * (TTscreen.contentEquals(TTscreen)) { WebElement element =
	 * locateElement("Xpath", "//input[@placeholder='Search']"); element.click();
	 * Thread.sleep(2000); element.sendKeys("Sales"); Thread.sleep(2000); try {
	 * boolean enabled = locateElement("xpath",
	 * "(//mat-icon[@role='img'])[2]").isEnabled(); if (enabled=true) {
	 * locateElement("xpath", "(//mat-icon[@role='img'])[2]").click();
	 * Thread.sleep(2000); } else { JavascriptExecutor java = (JavascriptExecutor)
	 * driver; java.executeScript("arguments[0].click()", enabled);
	 * Thread.sleep(2000); } } catch (ElementClickInterceptedException a) { throw a;
	 * }
	 * 
	 * List<WebElement> findElements =
	 * driver.findElements(By.xpath("//mat-row[@role='row']")); // WebElement
	 * locateElement = locateElement("xpath",
	 * "//mat-cell[@class='mat-cell cdk-column-Code mat-column-Code ng-star-inserted']"
	 * );
	 * 
	 * for (WebElement elem : findElements) { elem.findElement(By.
	 * xpath("//mat-cell[@class='mat-cell cdk-column-BookType mat-column-BookType ng-star-inserted']"
	 * )); String bookType = elem.getText(); if (bookType.contains("C")) { Actions
	 * click = new Actions(driver); click.doubleClick(elem).perform(); } else {
	 * throw new ElementClickInterceptedException("Not present / Not clicable " +
	 * bookType ); }
	 * 
	 * } }
	 * 
	 * }catch (ElementNotInteractableException e) { throw e; } return this;
	 * 
	 * }
	 */
	
	
	/*
	 * public DashboardToSalesInvoice checkSliderStatus() throws
	 * InterruptedException { Thread.sleep(4000); String s= locateElement("xpath",
	 * "(//span[@class='ng-star-inserted'])[2]").getText(); if
	 * (s.equalsIgnoreCase("yes")) { System.out.println("Slider is active");
	 * WebElement TTname = locateElement("xpath", "//input[@placeholder='Name']");
	 * System.out.println(TTname); if (TTtext.equals(TTname)) { Set<String>
	 * windowHandles = driver.getWindowHandles();
	 * 
	 * List<String> handles = new ArrayList <String>(windowHandles);
	 * 
	 * driver.switchTo().window(handles.get(0)); } else {
	 * System.out.println("Not switched to parent window"); }
	 * 
	 * } else { System.out.println("Slider is Passive" + s); } return this; }
	 */
	 
	/*public DashboardToSalesInvoice trasactionTypeSelection()
	{
		try {
			Thread.sleep(4000);
			 WebElement locateElement = locateElement("Xpath", "(//input[@role='combobox'])[2]");
			 String text = locateElement("Xpath", "(//input[@role='combobox'])[2]").getText();
			boolean enabled = locateElement("xpath", "//mat-icon[@role='img'][text()='close']").isEnabled();
			if (enabled = true) {
				locateElement("xpath", "(//mat-icon[@role='img'][text()='close'])[1]").click();
				Thread.sleep(2000);
				//locateElement("Xpath", "(//input[@type='text'])[2]").click();
				locateElement("xpath", "(//mat-icon[@role='img'][text()='close'])[1]").click();
				
				//using select class to fetch the TT data
				Select sel = new Select(locateElement);
				sel.selectByValue(text);
				
			}else {
				throw new RuntimeException("Error - Else block is executed. TransactionType is not clicked");
			}
			
		} catch (Exception e) 
		{
			System.err.println("Error occured :" + e.getMessage());
		}
        return null;
	}*/

}
