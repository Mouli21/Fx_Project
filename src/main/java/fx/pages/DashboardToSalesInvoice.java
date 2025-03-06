package fx.pages;

//import java.sql.Date;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
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
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import fx.base.BaseClass;

public class DashboardToSalesInvoice extends BaseClass {
	
	private String TTtext;
	
	public static String transactionDate;
	
	private  String invoiceDate;
	
	private  String hotelState;
	
	public static String decimalValue;
	
	public  String revenueCode;
	
	public WebElement creditLedger,debitLedger;
	
	protected String transactionAmount,debitAmount,creditAmount;
	
	public String taxAmount,taxes;
	
	public double enteredTax,sumOfTaxes;
	
	public String invListDocumentDate,invListAmt;
	
	
//	public DashboardToSalesInvoice(String revenueCode, String creditAmount, double sumOfTaxes )
//	{
//	    this.revenueCode = revenueCode;
//		this.creditAmount = creditAmount;
//		this.sumOfTaxes = sumOfTaxes;
//		
//	}
	
	
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
		
		Random num = new Random();
		int nextInt = num.nextInt(10);
		
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
			
			Random num = new Random();
			int revSelectionNumber = num.nextInt(10);
			
		locateElement("Xpath", "(//mat-icon[@role='img'][@style='color:#ff8e00'][text()='search'])[2]").click();
			
		List<WebElement> revenue=	driver.findElements(By.xpath("//mat-option[@role='option']//span"));
		
		revenueCode = dbConnection("Select top "+ revSelectionNumber+" * from FXFAS_InterfaceLink where PmsCustcode="+pmscustcode+" and IsDeleted=0 and Module='FrontOffice' and DebitAccount<>'' and CreditAccount<>'' and CategoryType='Revenue' ", "RevenueName");// interfacelink tbl
		
		for(WebElement rev : revenue)
		{
			
			String listRevenue = rev.getText();
		//	System.out.println(listRevenue);
			//Thread.sleep(2000)
			if (listRevenue.startsWith(revenueCode)) 
			{
				click(rev);
				break;
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
			
			if(ccText==""||ccText==" ")
			{
				
				locateElement("Xpath", "(//mat-icon[@class='mat-icon material-icons mat-icon-no-color ng-star-inserted'])[4]").click();
				locateElement("XPATH", "//span[@class='mat-option-text']").click();				
				
			}
			else
			{
				WebElement dept =  locateElement("Xpath", "//div[@class='mat-form-field-infix']//input[@placeholder='Department']");
				System.out.println(dept.getText());
				
				System.out.println("Seciton & department are tagged..!");
				
			}
			return this;

		}
		
     public DashboardToSalesInvoice gstTaxRateType() throws InterruptedException 
		{
			Thread.sleep(3000);
			String gstRateType = locateElement("Xpath", "//mat-select[@aria-label='GST Rate Type']").getText();
			
			if (gstRateType.equalsIgnoreCase("SAC")) 
			{
				System.out.println("GST rate type is :"+ gstRateType);
			}
			else
			{
				//throw new RuntimeException("For sales invoice GST rate type is :"+gstRateType );
				System.out.println("GST rate type in else block is :"+ gstRateType);
				locateElement("xpath", "(//div[@class='mat-select-value'])[2])").click();
				locateElement("Xpath", "(//div[@class='mat-form-field-infix']//span[@ng-reflect-ng-switch='false']//span)[2]").click();
			}
			
			return this;

		}
		
	public DashboardToSalesInvoice	hsnsacField()
		{
			WebElement hsnfield = locateElement("Xpath", "//input[@placeholder='HSN/SAC']");
			
			hsnfield.click();
			
			String sacValue = hsnfield.getText();
			
			System.out.println("sacValue is : "+ sacValue);
			
			if(sacValue=="" ||sacValue==" ")
			{
				Random random = new Random();
				
				int  randomNumber= 100 + random.nextInt(500);
				
				String value = String.valueOf(randomNumber);
				
				clearAndType(hsnfield, value);
			}
			

			
			return this;
		}
		 
	public DashboardToSalesInvoice findUOM() {
			 
			 WebElement UOM = locateElement("Xpath", "//div[@class='mat-form-field-infix']//input[@placeholder='UOM']");
			 
			 UOM.click();
			 
		//	System.out.println( UOM.getText());
			 
		//	if( UOM.getText()=="" ||UOM.getText()==" " )
			//{
			
		      List<WebElement> uomList = driver.findElements(By.xpath("(//div[@class='cdk-overlay-pane mat-autocomplete-panel-above'])//mat-option//span")); //(//div[@class='cdk-overlay-pane'])[2]//mat-option//span[text()=' NOS - NUMBERS ']
		      
		      for (WebElement list : uomList) 
		      {
		    	String listText =   list.getText();
		    	
		   // 	System.out.println("UOM list are : "+ listText);
		    	
		    	if (listText.contains(" NOS") || listText.contains("NOS")) 
		    	{
					list.click();
					break;
				}
				
		    	}
		       
			//} 
			 
			return this;

		}
		 
	public DashboardToSalesInvoice enterRate() 
	{
		WebElement rate = locateElement("Xpath", "//input[@placeholder='Rate']");
		
		rate.click();
		
		Random number = new Random();
		
	 double decimal =number.nextDouble(2000);
	 
	double decimalRoundOffValue = Math.round(decimal * 100.0) /100.0;
	 
	  decimalValue = String.valueOf(decimalRoundOffValue);
	
	 rate.sendKeys(decimalValue);
	 
	 WebElement scrollEle = locateElement("xpath", "//h5[text()='Debit ']");
	 
		Actions action = new Actions(driver);
		action.moveToElement(scrollEle).build().perform();

		return this;
	}
	
	public DashboardToSalesInvoice supplyTypeSelection(String gstSelection)
	{
		WebElement supplyType = locateElement("XPATH", "//span[text()='Supply Type']");
		
		supplyType.click();
		
	List<WebElement> supplyTypeDropDown= driver.findElements(By.xpath("//div[contains(@class,'ng-trigger ng-trigger-transformPanel')]//mat-option//span"));
	
	for(WebElement supply :supplyTypeDropDown )
	{
		
	String supplyValue =supply.getText();
	
	if (supplyValue.contains(gstSelection))
	{
		supply.click();
		break;
	}
		
	}
	
	if (gstSelection.equals("None"))
	{
		enterDescription();
	}
	else if (gstSelection.equalsIgnoreCase("Exempted")) 
	{
		enterDescription();
	}
	else if (gstSelection.equalsIgnoreCase("Nil Rated")) 
	{
		enterDescription();
	}
	else if (gstSelection.equalsIgnoreCase("Regular GST") || gstSelection.equalsIgnoreCase("Non GST") || gstSelection.equalsIgnoreCase("Zero Rated")) 
	{
		taxSelection();
	}
	
		
		return this;

	}
	
	public DashboardToSalesInvoice taxSelection() 
	{
		
		
		locateElement("Xpath", "//input[@placeholder='Tax']").click();
		
		List<WebElement> taxType = driver.findElements(By.xpath("//div[@role='listbox']//mat-option//span//span"));
		
		for (WebElement taxSelection : taxType) 
		{
			String taxStructures = taxSelection.getText().trim();
			
			System.out.println(taxStructures.length());
			
			if (taxStructures.toLowerCase().contains("gst 18")) 
			{
				taxSelection.click();
				break;
			}
			else if (taxStructures.contains("igst ")) 
			{
				taxSelection.click();
			}
		}
		enterDescription();
	
		return this;
	}
	
	public DashboardToSalesInvoice enterDescription() 
	{
	   WebElement desc =locateElement("Xpath", "//textarea");
	   
	   desc.click();
	   
	   desc.sendKeys("Amount of Rs: "+decimalValue+ " was passed against the revenue "+ revenueCode);
	   
	   
	   WebElement debitSide = locateElement("Xpath", "//label[text()='Debit Account']");
	   
		Dimension dimen =  debitSide.getSize();
		
	//	System.out.println(dimen.getHeight());
		//System.out.println(dimen.getWidth());
		  		
		//Location
		Point p = debitSide.getLocation();
		//System.out.println(p.getX());
		//System.out.println(p.getY());
	   
//	   Actions act = new Actions(driver);
//	   act.moveToElement(debitSide,0,420).build().perform();
		
		return this;

	}
	
	public  DashboardToSalesInvoice ledgerSelectionCredit() 
	{
		 creditLedger = locateElement("Xpath", "(//div[contains(@class,'mat-form-field-suffix ng-tns')]//mat-icon)[11]");
		
		String revenueLedger = creditLedger.getText();
		
		System.out.println(revenueLedger);
		
		if (revenueLedger.equalsIgnoreCase("close"))
		{
			
			debitLedger = locateElement("Xpath", "(//div[contains(@class,'mat-form-field-suffix ng-tns')]//mat-icon)[10]");
			
		    String debtorsLedger =debitLedger.getText();
		 
		  if (debtorsLedger.equalsIgnoreCase("close"))
	    	{
			  WebElement amount=  locateElement("xpath", "//input[@placeholder='Amount']");
			 String fieldValue = amount.getText();
			  System.out.println(fieldValue);
			  
			  if (fieldValue.equals("")|| fieldValue.equals(" ")) 
			  {
				  JavascriptExecutor js = (JavascriptExecutor) driver;
				  String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
				  transactionAmount = (String) js.executeScript(script, amount);
				  System.out.println(transactionAmount);
				
		    	}
			
	    	}
		  else {
			System.out.println("Not able to fetch the amount");
		}
		
			
//			List<WebElement> ledgerDropDown = driver.findElements(By.xpath("//div[contains(@class,'mat-autocomplete-panel')]//mat-option//span"));
//			
//			for (WebElement selectLedger : ledgerDropDown) 
//			{
//				selectLedger.click();
//				break;
//			}
		}
		  else {
				System.out.println("Not went inside if block itself");
			}
//		else
//		{
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			 String jsvalue= (String) js.executeScript("return arguments[0].innertext", revenueLedger);
//			 
//			 System.out.println(jsvalue);
//		}
		
		return this;	

	}
	
	public DashboardToSalesInvoice taxAmountFields()
	{
		WebElement taxField = locateElement("Xpath", "//input[@placeholder=' Tax Account']");
		
		if(taxField.isEnabled())
		{
			List<WebElement> tax = driver.findElements(By.xpath("//input[contains(@placeholder,'Tax Amount')]"));
			
			List<Double> taxDouble = new ArrayList<Double>();
			
			for(WebElement taxVal :tax)
			{
				taxAmount = taxVal.getText();
				
				if (taxAmount.equals("")||taxAmount.equals(" "))
				{
					JavascriptExecutor js = (JavascriptExecutor)driver;
					String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
					 taxes = (String) js.executeScript(script, taxVal);
					 
					 try {
						 
						 enteredTax = Double.parseDouble(taxes.trim());
						 
						 taxDouble.add(enteredTax);
						 
						 sumOfTaxes += enteredTax;
						
					} catch (Exception e) {
						System.out.println("taxAmountFields catch block :"+e);
					}
				}
					
			}
		}
		else {
			System.out.println("Tax fields are not enabled");
			//validateAmount();
		}
		
		
		
		return this;
		
	}
	
	public  DashboardToSalesInvoice validateAmount() 
	{
		
		WebElement debAmt = locateElement("xpath", "(//input[@placeholder='Amount'])[2]");
		WebElement credAmt = locateElement("xpath", "(//input[@placeholder='Amount'])[3]");
		
		try {
			
			String debit = debAmt.getText();
			
			if (debit.equals("")|| debit.equals(" "))
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
				debitAmount =  (String) js.executeScript(script,debAmt);
			}
			else
			{
				throw new Exception("Not able to fetch Debit Amount");
			}
			
		} catch (Exception e) 
		{
			System.err.println();
		}
		
		
		try {
			
			String credit = credAmt.getText();
			
			if (credit.equals("")|| credit.equals(" "))
			{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
				creditAmount =  (String) js.executeScript(script,credAmt);
			}
			else
			{
				throw new Exception("Not able to fetch Credit Amount");
			}
			
		} catch (Exception e) 
		{
			System.err.println();
		}
		
		
		if (debitAmount.equals(creditAmount)) 
		{
		 System.out.println("Both amount fields are equal");
		}
       else if(!debitAmount.equals(creditAmount))
       {
    	   System.out.println("Inside else if block");
    	   taxAmountFields();
    	   
    	  String overallCredit =  sumOfTaxes+creditAmount;
    	  
    	  Double creditAmt = Double.parseDouble(creditAmount);
    	  
    	 Double overallCreditValue =  sumOfTaxes + creditAmt;
    	  
    	 Double debitAmountValue = Double.parseDouble(debitAmount);
    	  
    	  if (debitAmountValue.equals(overallCreditValue)) 
    	  {
    		 System.out.println("Overall credit value is  equals with your debit Amount");
			
	    	}
    	  else {
			System.out.println("Overall Credit value is not equal with your debit Amount... Check the script");
		}
    	  	
	    }
		
		return this;

	}
	
	public DashboardToSalesInvoice invoiceAddButtonYes() throws InterruptedException 
	{
		WebElement invAddButon = locateElement("id", "InvAddBtn");
		
		if (invAddButon.isEnabled()) 
		{
			invAddButon.click();
			WebElement  addRevYesButtn = locateElement("xpath", "//span[text()='Yes']");
			addRevYesButtn.click();
			revenueSelection().costCenterSelection().gstTaxRateType().hsnsacField().findUOM().enterRate().supplyTypeSelection("Regular GST")
			.enterDescription().ledgerSelectionCredit().validateAmount();
		}
		else {
			System.out.println("Invoice Add Button is not enabled");
			Assert.assertFalse(false);
		}

		return this;
	}
	
	public SalesInvSavePage invoiceAddButtonNo() 
	{
		WebElement invAddButon = locateElement("id", "InvAddBtn");
		
		if (invAddButon.isEnabled()) 
		{
			invAddButon.click();
			WebElement  addRevNoButtn = locateElement("xpath", "//span[text()='No']");
			addRevNoButtn.click();
		}
		else {
			System.out.println("Invoice Add Button is not enabled");
			Assert.assertFalse(false);
		}
			 
		 return new SalesInvSavePage();	

	}
	
	
	}

