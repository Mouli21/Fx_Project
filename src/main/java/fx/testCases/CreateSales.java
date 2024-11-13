package fx.testCases;

import java.text.ParseException;

import org.testng.annotations.Test;

import fx.pages.DashboardToSalesInvoice;

public class CreateSales {
	
	@Test
	public void salesInvoiceCreation() throws InterruptedException, ParseException
	{
		new DashboardToSalesInvoice()
		.salesInvocieList().invoiceCreation().companySelection().invoiceDateCheck().currencySelection().placeOfSupply().termsAndDueDate();

	}

}
