package fx.baseMethods;

import org.testng.annotations.BeforeMethod;

import fx.base.BaseClass;

public class SpecificMethods extends BaseClass {

	
	@BeforeMethod
	public void beforeMethod() {
		driver = launchBrowser("chrome", "https://fx1staging.idsnext.live/#/login");
			
	}
}
