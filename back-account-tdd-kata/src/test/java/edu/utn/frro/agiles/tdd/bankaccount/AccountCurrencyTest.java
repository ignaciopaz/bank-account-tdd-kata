package edu.utn.frro.agiles.tdd.bankaccount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Currency;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountCurrencyTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private static Currency USD, EUR;
	private Account usdEmptyAccount;
	
	@BeforeClass public static void beforeClass() {
		USD = Currency.getInstance("USD");
		EUR = Currency.getInstance("EUR");
	}
	
	@Before public void before() {
		usdEmptyAccount = new Account(USD);
	}
	
	@Test public void given_newAccountWithCurrency_When_isEmpty_then_balanceIs0AndCurrencyIsSet() {
		assertThat(usdEmptyAccount.getBalance(), is(Double.valueOf(0)));
		assertThat(usdEmptyAccount.getCurrency(), is(USD));
	}
	
	@Test public void given_newAccountWithCurrency_When_initializedWithBalance_then_balanceIsSet() {
		Account account = new Account(USD, 11);
		assertThat(account.getBalance(), is(Double.valueOf(11)));
		assertThat(account.getCurrency(), is(USD));
	}
	
	@Test public void given_newAccount_When_created_then_defaultCurrencyIsUSD() {
		Account account = new Account();
		assertThat(account.getBalance(), is(Double.valueOf(0)));
		assertThat(account.getCurrency(), is(USD));
	}
	
	@Test public void given_newAccount_When_initialBalance_then_defaultCurrencyIsUSD() {
		Account account = new Account(11);
		assertThat(account.getBalance(), is(Double.valueOf(11)));
		assertThat(account.getCurrency(), is(USD));
	}
	
	@Test public void given_newAccount_When_initialCurrencyAndNegativeBalance_then_throwsException() {
		expectIllegalArgumentException("Balance cannot be less than 0");
		Account account = new Account(USD, -11);
	}
	
	@Test public void given_newAccount_When_initialCurrencyIsNull_then_throwsException() {
		expectIllegalArgumentException("Currency cannot be null");
		Account account = new Account(null, 11);
	}
	
	@Test public void given_twoAccountsWithDifferentCurrency_When_transfer_then_throwsException() {
		Account account1 = new Account(USD, 10);
		Account account2 = new Account(EUR, 10);
		expectException(AccountException.class, "Currency cannot be different");
		account1.transfer(2, account2);
	}
	
	private void expectAmountMustBePositiveException() {
		expectIllegalArgumentException("Amount must be greater than 0");
	}
	
	private void expectIllegalArgumentException(String message) {
		expectException(IllegalArgumentException.class, message);
	}
	
	private void expectException(Class<? extends Throwable> type, String message) {
		expectedException.expect(type);
		expectedException.expectMessage(message);
	}
	
}
