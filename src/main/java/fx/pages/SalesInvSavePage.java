package fx.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import fx.base.BaseClass;


public class SalesInvSavePage extends BaseClass {
	
	public String amountColumn;
	public double enteredAmount;
	public String finalAmt;
	public double sum=0;
	
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
				    String amt =  (String) js.executeScript(script,amountColumn);
				    
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
			
			if (finalAmtValue==sum) 
			{
				System.out.println("Final Amount is equal with sum.of. Amount");
			}
			else {
				System.out.println("Final amount is not equals");
			}
			
				
			} catch (Exception e) {
				System.err.println(e);
			}
			
		

		
		return this;

	}
	

}
