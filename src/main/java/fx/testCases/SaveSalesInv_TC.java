package fx.testCases;

import org.testng.annotations.Test;

import fx.pages.SalesInvSavePage;
//import fx.baseMethods.SpecificMethods;

public class SaveSalesInv_TC  {
	
	@Test
	public void invoiceSave() 
	{
		new SalesInvSavePage()
		//.serviceRevenue();
		.saveValidation().viewAccountSummary().clickSave();

	}

}
