package fx.pages;

//import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	
	private String transactionDate;
	
	private  String invoiceDate;
	
	private  String hotelState;
	
	private String attributeplaceOfSupply;
	
	
	
//	private int invoDate;
//	
//	private int transaDate;
	
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
				
     		String db =  dbConnection("Select * from FXFAS_TransactionTypeMaster where BookType='C' and IsDeleted=0 and IsDefault=1"+"and PmsCustCode="+pmscustcode,"TransactionTypeMasterCode");
				
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
				  transactionDate = transDate.getAttribute("max");
				  
			//	  int transaDate = Integer.parseInt(transactionDate);
			  //   String transactionDate	=  transDate.getText();
				
				//trnDateComparision = (Date) dateComparision(transactionDate);
				  
				
				LocalDate serverDate = LocalDate.now();
				
				
				//Take if required to change the server date
				
				  DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				  
				  String format = serverDate.format(date);
				 
	
				
			 
			 if(format.equals(transactionDate))
			 {
				 System.out.println("Server date & transaction date are same");
				// dateChange(); // if u need to change the date then use this method
				 
			 }
			 else
			 {
				 System.out.println("Dates are not equal");
			 }
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		
			}
		} catch (ElementNotInteractableException e)
		{
			 throw  e;
		}
		
		return this;
		

	}
	public DashboardToSalesInvoice companySelection()
	{
		locateElement("xpath", "//input[@placeholder='Company']").click();
		
		List<WebElement> findElements = driver.findElements(By.xpath("//div[@role='listbox']//mat-option"));
		
		String taxCompany = dbConnection("Select * from Company where isdeleted=0 and [status]=1 and isgstapplicable=1"+"and pmscustcode="+pmscustcode, "CompanyCode");
		
		
		for (WebElement company : findElements) 
		{
			String text = company.getText();
			
			if (text.contains(taxCompany)) 
			{
				click(company);
				break;
			}
		}
		
       return this;
	}
	
	public DashboardToSalesInvoice invoiceDateCheck() throws ParseException 
	{
		try {
			
			  WebElement invDate = locateElement("xpath", "//input[@placeholder='Invoice Date']");
			  invDate.click();
		      invoiceDate = invDate.getAttribute("max");
			
			System.out.println(invoiceDate);
			
		//	int invoDate = Integer.parseInt(invoiceDate);
			  
			 //  invDateComparision = (Date) dateComparision(invoiceDate);
			  
		}catch(Exception e)
		{
			System.err.println();
		}
		
		
		if (invoiceDate.equals(transactionDate))
		{
			System.out.println("Invoice date is :"+ invoiceDate +" and Transaction date is :"+ transactionDate);
			
		}
	
			return this;  

	}
	
		public DashboardToSalesInvoice currencySelection() throws InterruptedException 
		{
			
			WebElement currency = locateElement("Xpath", "//input[@formcontrolname='Currency']");
			currency.getText();
			
			WebElement currencyClear = locateElement("xpath", "(//button[@aria-label='Clear'])[3]");
			currencyClear.click();
			Thread.sleep(2000);
			
			WebElement currencyDropDown = locateElement("xpath", "(//div[@class='mat-form-field-flex']//mat-icon)[4]");
			currencyDropDown.click();
			
			List<WebElement> currencies = driver.findElements(By.xpath("//span[@class='mat-option-text']"));
			
			try {
				
				String currencyDefault = dbConnection("Select * from Currency where Isdeleted=0 and IsDefault=1 and PmsCustCode="+pmscustcode, "CurrencyName");
					
				
			for(WebElement curren: currencies)
			{
				//System.out.println(curren.getText());
				if(curren.getText().contains(currencyDefault))
				{
					curren.click();
				
				}
			}
			
			}
			catch(Exception x)
			{
				System.err.println();
				System.out.println(x);
			}

			return this;
		}
		
		public DashboardToSalesInvoice placeOfSupply() throws InterruptedException 
		{
			WebElement placeOfSupply = locateElement("xpath", "//input[@placeholder='Place of supply']");
			
			
			if (placeOfSupply.isEnabled())
			{
				placeOfSupply.click();
			  attributeplaceOfSupply = placeOfSupply.getAttribute("placeholder");
				
			}
//			JavascriptExecutor exe = (JavascriptExecutor)driver;
//			String place = (String) exe.executeScript("return document.getelementbyXpath('//input[@placeholder='Place of supply']').");
			
			
		 hotelState =	dbConnection("Select * from HotelInfo where isDeleted=0 and PmsCustCode="+pmscustcode, "StateCode");
		 
		 if (attributeplaceOfSupply.equalsIgnoreCase(hotelState))
		 {
			System.out.println("Property state & Sales invoice state are same..!");
		 } else
		 {
			  locateElement("Xpath", "(//button[@aria-label='Clear'])[3]").click();
			  
			   locateElement("XPATH", "(//div[@class='mat-form-field-flex']//mat-icon)[5]").click();
			   
			   Thread.sleep(2000);
			   
			   try {
			   
			List<WebElement> placeOfSupplyDropDown =   driver.findElements(By.xpath("//div[@role='listbox']//div"));
			
			for (WebElement dd : placeOfSupplyDropDown) 
			{
				if (dd.getText().equalsIgnoreCase(attributeplaceOfSupply)) 
				{
					dd.click();
				}
				
			}
			 
			   }catch(Exception exc)
			   {
				   System.out.println("Inside place of Supply catch block"+ exc);
			   }
			 
		 }
			return this;

		}
	
		public DashboardToSalesInvoice termsAndDueDate() throws InterruptedException
		{
			locateElement("XPATH", "//input[@placeholder='Terms']").click();
			
			Thread.sleep(2000);
			
			locateElement("Xpath", "((//div[@role='listbox'])//div)[2]").click();
			
			//dueDateField
			WebElement dueDateField = locateElement("Xpath", "//input[@placeholder='Due Date']");
			
			
			String date = dueDateField.getText();
			
			System.out.println("Due date is "+ date);
			
		//	List<WebElement> termsDropDown =   driver.findElements(By.xpath("//div[@role='listbox']//div"));
			
			

			return this;
		}
}
