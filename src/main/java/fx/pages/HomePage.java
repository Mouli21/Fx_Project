package fx.pages;

import java.time.Duration;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.By;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import fx.base.BaseClass;

public class HomePage extends BaseClass {
	
	public DashboardToSalesInvoice webPageTitle() throws InterruptedException {

		String title = driver.getTitle();
		
		if (title.equalsIgnoreCase("login")) {
			
			try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			WebElement element = driver.findElement(By.xpath("//img"));
			wait.until(ExpectedConditions.visibilityOf(element));
			locateElement("Xpath", "//div[@title='Financial Accounting System (FAS)']").click();
			}catch(Exception e1) {
			String title1 = driver.getTitle();
			if (title1=="Error") {
				locateElement("xpath", "//button[text()='Back']").click();
				String mainPage = driver.getTitle();
				try {
				
				if (mainPage.equalsIgnoreCase("Login")) {
					LoginPage lp = new LoginPage();
					lp.userName().password().clickLogin().webPageTitle();
				
				}
				
				}catch (Exception e) 
				{
					System.err.print(e);
				}
			}
			
		}
			
	}	
		
//		String newTitle = driver.getTitle();
//		
//		if (newTitle.equalsIgnoreCase("Home")) {
//			
//			//wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//			
//			 WebElement locateElement = locateElement("Xpath", "//div[@title='Financial Accounting System (FAS)']");
//			
//			if (locateElement.isDisplayed()) {
//				
//				locateElement.click();
//			}
//			else {
//				throw new RuntimeErrorException(null, "FAS-Card not available for this user");
//			}
//			
//		}else {
//
//			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
//			locateElement("xpath", "//button[text()='Back']").click();
//			
//			String mainPage = driver.getTitle();
//			
//			if (mainPage.equalsIgnoreCase("Login")) {
//				LoginPage lp = new LoginPage();
//				lp.userName().password().clickLogin().webPageTitle();
//			
//			}
//			else {
//				driver.close();
//			}
//			
//		}
		return new DashboardToSalesInvoice() ;
		

	}
	

}
