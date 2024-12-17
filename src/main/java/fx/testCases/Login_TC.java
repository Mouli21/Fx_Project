package fx.testCases;

import org.testng.annotations.Test;

import fx.baseMethods.SpecificMethods;
import fx.pages.LoginPage;

public class Login_TC extends SpecificMethods {
	
	@Test
	public void loginFx() throws InterruptedException {
		
		 new LoginPage()
		 .userName()
		 .password()
		 .clickLogin()
		 .webPageTitle();
	   

	}

}
