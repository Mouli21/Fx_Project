package fx.pages;

import java.util.ArrayList;
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
			if (displayed = true) {
				locateElement("xpath", "//span[text()='+']").click();
				Thread.sleep(2000);
			}
		} catch (ElementNotInteractableException e) {
			 throw  e;
		}
		return this;

	}
	
	public DashboardToSalesInvoice checkDefaultTT() throws InterruptedException
	{
		
		String text = locateElement("Xpath", "(//input[@role='combobox'])[2]").getText(); //used to get the transaction type text.
		
		String currentUrl = driver.getCurrentUrl();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.open()") ;
		
	for (String handles : driver.getWindowHandles()) {
		if (!handles.equals(currentUrl)) {
			driver.switchTo().window(handles);
		}
		else {
			throw new SessionNotCreatedException("Not able to launch" +currentUrl);
		}
	}
	driver.get(currentUrl);
	try {
		clearAndType(locateElement("xpath", "//input[@type='text']"), "Transaction Type");
		String TTscreen = locateElement("xpath", "//h2").getText();
		System.out.println(TTscreen);
		if (TTscreen.contentEquals(TTscreen)) {
			WebElement element = locateElement("Xpath", "//input[@placeholder='Search']");
			element.click();
			Thread.sleep(2000);
			element.sendKeys("Sales");
			Thread.sleep(2000);
			try {
				boolean enabled = locateElement("xpath", "(//mat-icon[@role='img'])[2]").isEnabled();
				if (enabled=true) {
					locateElement("xpath", "(//mat-icon[@role='img'])[2]").click();
					Thread.sleep(2000);
				}
				else {
					JavascriptExecutor java = (JavascriptExecutor) driver;
					java.executeScript("arguments[0].click()", enabled);
					Thread.sleep(2000);
				}
			} catch (ElementClickInterceptedException a) {
				throw a;
			}
			
			List<WebElement> findElements = driver.findElements(By.xpath("//mat-row[@role='row']"));
		//	WebElement locateElement = locateElement("xpath", "//mat-cell[@class='mat-cell cdk-column-Code mat-column-Code ng-star-inserted']");
			
			for (WebElement elem : findElements) 
			{
				elem.findElement(By.xpath("//mat-cell[@class='mat-cell cdk-column-Code mat-column-Code ng-star-inserted']"));
				String bookType = elem.getText();
				if (bookType.contains(bookType))
				{
					Actions click = new Actions(driver);
					click.doubleClick(elem).perform();
				}
				else {
					throw new ElementClickInterceptedException("Not present / Not clicable " + bookType );
				}
				
			}
		}
		
	}catch (ElementNotInteractableException e) {
		throw e;
	}
	return null;
	
	}
	
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
