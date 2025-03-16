package fx.testCases;

import java.text.ParseException;

import org.testng.annotations.Test;

import fx.pages.InvoiceList;

public class SalesInvoiceList_TC {

	@Test
	public void invoiceListScreen() throws ParseException  {
		new InvoiceList()
		.savedInvVerify();

	}
}
