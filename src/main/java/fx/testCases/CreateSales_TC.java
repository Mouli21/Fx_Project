package fx.testCases;

import java.text.ParseException;

import org.testng.annotations.Test;

import fx.pages.DashboardToSalesInvoice;

public class CreateSales_TC {
	
	@Test
	public void salesInvoiceCreation() throws InterruptedException, ParseException
	{
		new DashboardToSalesInvoice()
		.salesInvocieList().invoiceCreation().companySelection().invoiceDateCheck().currencySelection().placeOfSupply().termsField()
		.revenueSelection().costCenterSelection().gstTaxRateType().hsnsacField().findUOM().enterRate().supplyTypeSelection("None").ledgerSelectionCredit()
		.validateAmount()
		//.invoiceAddButtonYes() //UnComment if need to add more revenue
		.invoiceAddButtonNo();

	}

}
