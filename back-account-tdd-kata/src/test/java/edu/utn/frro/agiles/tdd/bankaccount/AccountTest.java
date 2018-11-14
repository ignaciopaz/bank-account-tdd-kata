package edu.utn.frro.agiles.tdd.bankaccount;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

import org.junit.Rule;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AccountTest.class)
public class AccountTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test public void given_newAccount_When_isEmpty_then_balanceIs0() {
		Account account = new Account();
		assertThat(account.getBalance(), is(Double.valueOf(0)));
	}
	
	@Test public void given_newAccount_When_isInitialized_then_balanceIsNotEmpty() {
		Account account = new Account(10);
		assertThat(account.getBalance(), is(Double.valueOf(10)));
	}
	
	@Test public void given_newAccount_When_isInitializedWithDecimals_then_balanceHasDecimals() {
		Account account = new Account(1.45);
		assertThat(account.getBalance(), is(1.45));
	}
	
	@Test
	public void given_newAccount_When_balanceIsNegative_then_throwsException() {
		expectIllegalArgumentException("Balance cannot be less than 0");
		Account account = new Account(-10);
	}
	
	@Test public void given_account_When_deposit_balanceIsIncreased() {
		Account account = new Account(10);
		account.deposit(2.5);
		assertThat(account.getBalance(), is(Double.valueOf(12.5)));
	}
	
	@Test public void given_account_When_depositNegativeValue_throwsException() {
		Account account = new Account(10);
		expectAmountMustBePositiveException();
		account.deposit(-2.5);
	}
	
	@Test public void given_account_When_deposit0_throwsException() {
		Account account = new Account(10);
		expectAmountMustBePositiveException();
		account.deposit(0);
	}
	
	@Test public void given_account_When_withdraw_balanceIsDeducted() {
		Account account = new Account(10);
		account.withdraw(2.5);
		assertThat(account.getBalance(), is(Double.valueOf(7.5)));
	}
	
	@Test public void given_account_When_withdrawMoreThanBalance_balanceIsNotDeductedAndThrowsException() throws Exception {
		Account account = new Account(11);

		expectException(AccountException.class, "funds are not enough");
		try {
			account.withdraw(14);
			fail();
		} catch (Exception e) {
			assertThat(account.getBalance(), is(11.0));
			throw e;
		}
	}
	
	@Test public void given_account_When_withdrawNegativeValue_throwsException() {
		Account account = new Account(10);
		expectAmountMustBePositiveException();
		account.withdraw(-2.5);
	}
	
	@Test public void given_account_When_withdraw0_throwsException() {
		Account account = new Account(10);
		expectAmountMustBePositiveException();
		account.withdraw(0);
	}	
	
	@Test public void given_twoAccounts_When_transfer_balance1IsDeductedAndBalance2IsIncreased() {
		Account account1 = new Account(10);
		Account account2 = new Account(3.3);
		account1.transfer(2.3, account2);
		assertThat(account1.getBalance(), is(Double.valueOf(7.7)));
		assertThat(account2.getBalance(), is(Double.valueOf(5.6)));
	}
	
	@Test public void given_twoAccounts_When_transferNegativeValue_balancesAreNotChangedAndThrowsException() throws Exception {
		Account account1 = new Account(11);
		Account account2 = new Account(1.3);
		expectAmountMustBePositiveException();
		try {
			account1.transfer(-2.3, account2);
			fail();
		} catch (Exception e) {
			assertThat(account1.getBalance(), is(11.0));
			assertThat(account2.getBalance(), is(1.3));
			throw e;
		}		
	}
	
	@Test public void given_twoAccounts_When_transfer0_balancesAreNotChangedAndThrowsException() throws Exception {
		Account account1 = new Account(11);
		Account account2 = new Account(1.3);
		expectAmountMustBePositiveException();
		try {
			account1.transfer(0, account2);
			fail();
		} catch (Exception e) {
			assertThat(account1.getBalance(), is(11.0));
			assertThat(account2.getBalance(), is(1.3));
			throw e;
		}		
	}
	
	@Test public void given_twoAccounts_When_transferWithNotEnoughFunds_balancesAreNotChangedAndThrowsException() throws Exception {
		Account account1 = new Account(11);
		Account account2 = new Account(1.3);
		expectException(AccountException.class, "funds are not enough");
		try {
			account1.transfer(12, account2);
			fail();
		} catch (Exception e) {
			assertThat(account1.getBalance(), is(11.0));
			assertThat(account2.getBalance(), is(1.3));
			throw e;
		}		
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
