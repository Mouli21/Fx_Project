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
import java.util.Random;
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
			WebElement placeOfSupply = locateElement("xpath", "(//button[@aria-label='Clear'])[4]");
			
			
			if (placeOfSupply.isEnabled())
			{
				placeOfSupply.click();
			//  attributeplaceOfSupply = placeOfSupply.getAttribute("placeholder");
				
			}
//			JavascriptExecutor exe = (JavascriptExecutor)driver;
//			String place = (String) exe.executeScript("return document.getelementbyXpath('//input[@placeholder='Place of supply']').");
			
			
		 hotelState =	dbConnection("Select * from HotelInfo where isDeleted=0 and PmsCustCode="+pmscustcode, "StateCode");
		 
		 
//		 
//		 if (placeOfSupply.getText().equalsIgnoreCase(hotelState))
//		 {
//			System.out.println("Property state & Sales invoice state are same..!");
//			placeOfSupply.click();
//		 } else
		 try
		 {
			  locateElement("Xpath", "(//button[@aria-label='Clear'])[4]").click();
			  
			   locateElement("XPATH", "(//div[@class='mat-form-field-flex']//mat-icon)[5]").click();
			   
			   Thread.sleep(2000);
			   
			   try {
			   
			List<WebElement> placeOfSupplyDropDown =   driver.findElements(By.xpath("//div[@role='listbox']//span"));
			
			for (WebElement dd : placeOfSupplyDropDown) 
			{
				String place = dd.getText();
			//	System.out.println(place);
				
				if (place.contains(hotelState)) 
				{
					click(dd);
				}
				
			}
			   }catch(Exception exc)
			   {
				   System.out.println("Inside place of Supply catch block"+ exc);
			   }
			 
		 }
		 catch(Exception e)
		 {
			 System.err.print(e);
		 }
			return this;

		}
	
		public DashboardToSalesInvoice termsField() throws InterruptedException
		{
			locateElement("XPATH", "(//div[@class='mat-form-field-flex']//mat-icon)[6]").click();
			
			Thread.sleep(2000);
			
			locateElement("Xpath", "((//div[@role='listbox'])//span)[3]").click();
			
			

			return this;
		}
		
		public DashboardToSalesInvoice taxTypeField() 
		{
			locateElement("XPATH", "//div[@class='mat-select-trigger']").click();
			
			locateElement("Xpath", "//mat-option[@role='option']//span").click();//Inclusive Tax if want exclusive then add index value.
			
			scrollWebPage(550, 0);
			
			return this;

		}
		
		public DashboardToSalesInvoice revenueSelection() throws InterruptedException
		{
			scrollWebPage(550, 0);
			
			
		locateElement("Xpath", "(//mat-icon[@role='img'][@style='color:#ff8e00'][text()='search'])[2]").click();
			
		List<WebElement> revenue=	driver.findElements(By.xpath("//mat-option[@role='option']//span"));
		
		String revenueCode = dbConnection("Select top 1 * from RevenueCode where PmsCustcode="+pmscustcode+" and IsDeleted=0 and Module='FrontOffice' ", "RevenueCodeName");
		
		for(WebElement rev : revenue)
		{
			
			String listRevenue = rev.getText();
			System.out.println(listRevenue);
			//Thread.sleep(2000)
			if (listRevenue.startsWith(revenueCode)) 
			{
				click(rev);
			}
			
		}
		return this;

		}
		
		public DashboardToSalesInvoice costCenterSelection() 
		{
			WebElement cc =  locateElement("Xpath", "//div[@class='mat-form-field-infix']//input[@placeholder='CostCenter']");
			cc.click();
			String ccText = cc.getText();
			
			System.out.println("CurrentCostCenter is " + ccText);
			
			if(ccText!=""||ccText!=" ")
			{
				WebElement dept =  locateElement("Xpath", "//div[@class='mat-form-field-infix']//input[@placeholder='Department']");
				System.out.println(dept.getText());
				
				System.out.println("Seciton & department are tagged..!");
			}
			else
			{
			
				locateElement("Xpath", "//div[@class='mat-form-field-suffix ng-tns-c2-99 ng-star-inserted']//mat-icon[@role='img'])").click();
			//	List<WebElement> ccDropdown = driver.findElements(By.xpath("//span[@class='mat-option-text']"));
				locateElement("XPATH", "//span[@class='mat-option-text']").click();
				
			}
			return this;

		}
		
		public DashboardToSalesInvoice gstTaxRateType() 
		{
			String gstRateType = locateElement("Xpath", "//div[@class='mat-form-field-infix']//span[@class='ng-tns-c15-102 ng-star-inserted']").getText();
			
			if (gstRateType.equalsIgnoreCase("SAC")) 
			{
				System.out.println("GST rate type is :"+ gstRateType);
			}
			else
			{
				//throw new RuntimeException("For sales invoice GST rate type is :"+gstRateType );
				System.out.println("GST rate type is :"+ gstRateType);
			}
			
			return this;

		}
		
		 public DashboardToSalesInvoice	hsnsacField()
		{
			WebElement hsnfield = locateElement("Xpath", "//input[@placeholder='HSN/SAC']");
			
			hsnfield.click();
			
			Random random = new Random();
			
			int  randomNumber= 100 + random.nextInt(500);
			
			String value = String.valueOf(randomNumber);
			
			clearAndType(hsnfield, value);
			
			return this;
		}
		
}
