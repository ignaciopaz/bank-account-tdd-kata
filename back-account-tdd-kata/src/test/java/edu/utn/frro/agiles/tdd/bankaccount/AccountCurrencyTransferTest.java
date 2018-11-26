package edu.utn.frro.agiles.tdd.bankaccount;

import java.util.Currency;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
@RunWith(MockitoJUnitRunner.class)
public class AccountCurrencyTransferTest {
	private static Currency USD, EUR, GBP;
	@Mock private CurrencyConverter currencyConverter;
	
	@BeforeClass public static void beforeClass() {
		USD = Currency.getInstance("USD");
		GBP = Currency.getInstance("GBP");
		EUR = Currency.getInstance("EUR");
	}
	
	@Before public void before() {
		when(currencyConverter.convert(USD, EUR)).thenReturn(0.88);
		//when(currencyConverter.convert(USD, USD)).thenReturn(1.0);
	}
	
	@Test public void given_twoAccountWithSameCurrency_when_transfer_balanceIsIncreasedAndConverterNotCalled() {
		Account usd = new Account(USD, 20);
		Account usd2 = new Account(USD, 30);
		usd.transfer(10, usd2);
		usd.setCurrencyConverter(currencyConverter);
		assertThat(usd.getBalance(), equalTo(10.0));
		assertThat(usd2.getBalance(), equalTo(40.0));
		verify(currencyConverter, never()).convert(USD, USD);
	}
	
	@Test public void given_twoAccountWithDifferentCurrency_when_transfer_balanceIsIncreasedWithCurrencyExchange() {
		Account usd = new Account(USD, 20);
		Account eur = new Account(EUR, 30);		
		usd.setCurrencyConverter(currencyConverter);
		usd.transfer(10, eur);
		assertThat(usd.getBalance(), equalTo(10.0));
		assertThat(eur.getBalance(), equalTo(38.8));
	}
	
}
