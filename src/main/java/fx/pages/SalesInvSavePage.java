package fx.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import fx.base.BaseClass;


public class SalesInvSavePage extends DashboardToSalesInvoice {
	
	public String amountColumn,taxColumn,totalColumn;
	public double enteredAmount,enteredTax,enteredTotalAmt;
	public String finalAmt;
	public double sum,taxSum,totalSum;
	
	public double finalAmtValue;
	
	
	public SalesInvSavePage saveValidation() {
		
			
		List<WebElement> elements = driver.findElements(By.xpath("//mat-cell[@class='mat-cell cdk-column-Amount mat-column-Amount ng-star-inserted']"));
		

		//WebElement rateColumn = locateElement("class", "mat-cell cdk-column-Amount mat-column-Amount ng-star-inserted");
		
		List<Double> amountValue = new ArrayList<Double>(); // as we don't know how many revenues will occur so, list will take care.
		
		for (WebElement amount : elements)
		{
			 amountColumn = amount.getText();
			 
			 if (amountColumn.equals("")||amountColumn.equals(" "))
			 {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
				    String amt =  (String) js.executeScript(script,amount);
				    
				    try {
						
						enteredAmount = Double.parseDouble(amt.trim());
						
						amountValue.add(enteredAmount);
						
						sum += enteredAmount;
						
						}
						catch (NumberFormatException e)
						{
							System.out.println("Invalid input inside If Catch Block :" + amountColumn);
						}
		    }
			
	else {
			try {
			
			enteredAmount = Double.parseDouble(amountColumn.trim());
			
			amountValue.add(enteredAmount);
			
			sum += enteredAmount;
			
			}
			catch (NumberFormatException e)
			{
				System.out.println("Invalid input :" + amountColumn);
			}
			
		}
	}

			Actions act = new Actions(driver);
			WebElement totalINR = locateElement("xpath", "//h4[text()='Upload Document']");
			act.moveToElement(totalINR).build().perform();
			
			try {
				
			WebElement finalAmount = 	locateElement("XPATH", "(//td[@class='tdFontRight'])[1]//b");
			
			finalAmt = finalAmount.getText();
			
		    finalAmtValue =	Double.parseDouble(finalAmt);
		    
		   double transactionValue = Double.parseDouble(decimalValue);
		   
		   if (transactionValue==finalAmtValue) {
				   
				   List<WebElement> element = driver.findElements(By.xpath("//mat-cell[@class='mat-cell cdk-column-Tax mat-column-Tax ng-star-inserted']"));
				   
				   List<Double> taxValue = new ArrayList<Double>(); // as we don't know how many revenues will occur so, list will take care.
					
					for (WebElement taxAmount : element)
					{
						 taxColumn = taxAmount.getText();
						 
						 if (taxColumn.equals("")||taxColumn.equals(" "))
						 {
								JavascriptExecutor js = (JavascriptExecutor) driver;
								String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
							    String taxAmt =  (String) js.executeScript(script,taxAmount);
							    
							    try {
									
							    	enteredTax = Double.parseDouble(taxAmt.trim());
									
							    	taxValue.add(enteredTax);
									
									taxSum += enteredTax;
									
									}
									catch (NumberFormatException e)
									{
										System.out.println("Invalid input inside If Catch Block :" + e);
									}
					    }
						
				else {
						try {
						
							enteredTax = Double.parseDouble(taxColumn.trim());
						
						taxValue.add(enteredTax);
						
						taxSum += enteredTax;
						
						}
						catch (NumberFormatException e)
						{
							System.out.println("Invalid input :" + e);
						}
						
					}
				}
			
		if (taxSum==sumOfTaxes || taxSum==0.00) {
			
			 List<WebElement> elementss = driver.findElements(By.xpath("//mat-cell[@class='mat-cell cdk-column-Total mat-column-Total ng-star-inserted']"));
			   
			   List<Double> totalValue = new ArrayList<Double>(); // as we don't know how many revenues will occur so, list will take care.
				
				for (WebElement totalAmount : elementss)
				{
					 totalColumn = totalAmount.getText();
					 
					 if (totalColumn.equals("")||totalColumn.equals(" "))
					 {
							JavascriptExecutor js = (JavascriptExecutor) driver;
							String script = "return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
						    String totalAmt =  (String) js.executeScript(script,totalAmount);
						    
						    try {
								
						    	enteredTotalAmt = Double.parseDouble(totalAmt.trim());
								
						    	totalValue.add(enteredTotalAmt);
								
								totalSum += enteredTotalAmt;
								
								}
								catch (NumberFormatException e)
								{
									System.out.println("Invalid input inside If Catch Block :" + e);
								}
				    }
					
			else {
					try {
					
						enteredTotalAmt = Double.parseDouble(totalColumn.trim());
					
						totalValue.add(enteredTotalAmt);
					
					totalSum += enteredTotalAmt;
					
					}
					catch (NumberFormatException e)
					{
						System.out.println("Invalid input :" + e);
					}
					
				}
			}
				if (sum+taxSum==totalSum) {
					
			if (finalAmtValue==sum) 
			{
				System.out.println("Final Amount is equal with sum.of. Amount");
			}
		}	
				
	}
		   }
		   
			else {
				System.out.println("Final amount is not equals");
			}
			
				
			} catch (Exception e) {
				System.err.println(e);
			}
			
		return this;

	}
	
	public SalesInvSavePage viewAccountSummary() throws InterruptedException 
	{
		
		Thread.sleep(3000);
		locateElement("Xpath", "//span[text()='View Account Summary']").click();
		
		WebElement accSumm = locateElement("Xpath", "//span[text()='Account Summary']");
		
		
		wait= new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(accSumm));
		
		WebElement debitSummary = locateElement("xpath", "(//div[@fxflex='14%']//span)[1]");
		String drSummary = debitSummary.getText();
		WebElement creditSummary = locateElement("xpath", "(//div[@fxflex='14%']//span)[2]");
		String crSummary = creditSummary.getText();
		
		if (drSummary.equals(crSummary)) 
		{
			Assert.assertTrue(true);
		locateElement("xpath", "//div[@fxflex='5%']//span[text()='Back']").click();
			
		}
		else {
			Assert.assertFalse(false);
		}

		return this;
	}
	
	public DashboardToSalesInvoice clickSave()
	{
		locateElement("XPATH", "//span[text()='Save']").click();
		WebElement saveYes= locateElement("Xpath", "//button//span[text()='Yes']");
		saveYes.click();
		return  new InvoiceList();
	}
	

	
	public void clickSavePrint() {
		// NEED TO DEVELOP LOGIC WITH APPROVAL CONDITION

	}
	

}
