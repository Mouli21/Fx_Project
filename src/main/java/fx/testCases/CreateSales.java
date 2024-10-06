package fx.testCases;

import org.testng.annotations.Test;

import fx.pages.DashboardToSalesInvoice;

public class CreateSales {
	
	@Test
	public void salesInvoiceCreation() throws InterruptedException
	{
		new DashboardToSalesInvoice()
		.salesInvocieList().invoiceCreation();//.checkDefaultTT().checkSliderStatus();

	}

}
