package fx.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class InvoiceList extends SalesInvSavePage {

	public String invListdate;
	
	public InvoiceList savedInvVerify() throws ParseException {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-cell[@role='gridcell']")));
		try {
			WebElement invListDocDate = locateElement("Xpath", "(//mat-cell[@role='gridcell'])[3]");
		 invListdate =	invListDocDate.getText();
		if (invListdate.equals("") || invListdate.equals(" ")) 
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script ="return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
		invListDocumentDate = (String)	js.executeScript(script,invListdate);
		}
			
		} catch (Exception e) {
			System.err.print(e);
		}
		
		try {
		WebElement invListAmount = 	locateElement("Xpath", "(//mat-cell[@role='gridcell'])[8]");
		String invAmt = invListAmount.getText();
		
		if (invAmt.equals("")||invAmt.equals(" ")) 
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script ="return arguments[0].value || arguments[0].textContent || arguments[0].innerText;";
		invListAmt =(String)js.executeScript(script,invAmt);
		}
		
		} catch (Exception e) {
			System.err.println("Exception invListAmount Field "+ e);
		}
		
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		String newInvListDate = date.format(invListdate);
		String newinvListDocumentDate = date.format(invListDocumentDate);
		 
		
				
		try {
			
			if (transactionDate.equals(newinvListDocumentDate)) 
			{
				if (debitAmount.equals(newInvListDate)) 
				{
					Assert.assertTrue(false);
				}
			
			}
		} catch (Exception e) {
			System.out.println("Exception "+e);
			
			if (transactionDate.equals(newInvListDate)) 
			{
				if (debitAmount.equals(invListAmt)) 
				{
					System.out.println("Inside Catch Block");
					Assert.assertTrue(true);
				}
			}
		}
		return this;
	}
}
