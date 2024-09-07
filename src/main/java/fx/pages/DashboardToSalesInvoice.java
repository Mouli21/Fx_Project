package fx.pages;

import java.util.NoSuchElementException;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebElement;
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
			}
		} catch (ElementNotInteractableException e) {
			 throw  e;
		}
		return this;

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
