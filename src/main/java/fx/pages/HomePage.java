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
		
		FluentWait<WebDriver> wait = new FluentWait<>(driver);
		wait.withTimeout(Duration.ofSeconds(60));
		wait.pollingEvery(Duration.ofMillis(5000));
		
		String title = driver.getTitle();
		
		if (title.equalsIgnoreCase("login")) {
			FluentWait<WebDriver> wait1 = new FluentWait<>(driver);
			wait1.withTimeout(Duration.ofSeconds(60));
			wait1.pollingEvery(Duration.ofMillis(5000));

			Thread.sleep(40000);
			
			if (title=="Error") {
				locateElement("xpath", "//button[text()='Back']").click();
				Thread.sleep(4000);
				
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
			
		
		
		String newTitle = driver.getTitle();
		wait = new WebDriverWait(driver, Duration.ofSeconds(600));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i")));
		
		if (newTitle.equalsIgnoreCase("Home")) {
			
			//wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			
			 WebElement locateElement = locateElement("Xpath", "//div[@title='Financial Accounting System (FAS)']");
			
			if (locateElement.isEnabled()) {
				
				locateElement.click();
			}
			else {
				throw new RuntimeErrorException(null, "FAS-Card not available for this user");
			}
			
		}else {

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
			locateElement("xpath", "//button[text()='Back']").click();
			
			String mainPage = driver.getTitle();
			
			if (mainPage.equalsIgnoreCase("Login")) {
				LoginPage lp = new LoginPage();
				lp.userName().password().clickLogin().webPageTitle();
			
			}
			else {
				driver.close();
			}
			
		}
		return new DashboardToSalesInvoice() ;
		

	}
	

}
