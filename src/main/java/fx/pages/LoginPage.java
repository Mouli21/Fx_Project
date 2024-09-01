package fx.pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import fx.base.BaseClass;

public class LoginPage extends BaseClass {
	
	//Creating framework with POM
	
	
	public LoginPage userName() {
		
		try {	
			clearAndType(locateElement("xpath", "//input[@type='text']"),"tsc7747@idsnext.com");//By using locate element method we located the element using xath & the value for ID
		
		}catch (NoSuchElementException e) {
			System.err.print(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return this;
	}

public LoginPage password() {
		
		try {
			
			clearAndType(locateElement("xpath", "//input[@type='password']"),"Ids@1001");
			
		} catch (NoSuchElementException e) {
			System.err.print(e);
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return this;
	}

public HomePage clickLogin() {
	
	try {
		click(locateElement("xpath", "//button[@type='button']"));
	}
	catch (Exception e) {
		System.err.print(e);
	}
	return new  HomePage();

}
}
