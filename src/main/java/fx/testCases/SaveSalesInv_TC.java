package fx.testCases;

import org.testng.annotations.Test;

//import fx.baseMethods.SpecificMethods;
import fx.pages.SalesInvSavePage;

public class SaveSalesInv_TC  {
	
	@Test
	public void invoiceSave() 
	{
		new SalesInvSavePage()
		.saveValidation();

	}

}
