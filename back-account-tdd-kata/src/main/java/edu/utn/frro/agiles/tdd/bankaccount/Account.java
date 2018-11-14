package edu.utn.frro.agiles.tdd.bankaccount;

import java.util.Currency;

public class Account {

	private Double balance;
	private final Currency currency;
	private CurrencyConverter currencyConverter;

	public Account(double balance) {
		this(Currency.getInstance("USD"), balance);
	}

	public Account() {
		this(Currency.getInstance("USD"), 0);
	}

	public Account(Currency currency) {
		this(Currency.getInstance("USD"), 0);
	}

	public Account(Currency currency, double balance) {
		if (balance < 0)
			throw new IllegalArgumentException("Balance cannot be less than 0");
		if (currency == null)
			throw new IllegalArgumentException("Currency cannot be null");
		this.currency = currency;
		this.balance = balance;
	}

	public Double getBalance() {
		return balance;
	}

	public void deposit(double amount) {
		validatePositiveAmount(amount);
		balance += amount;
	}

	public void withdraw(double amount) {
		validatePositiveAmount(amount);
		if (amount > balance)
			throw new AccountException("funds are not enough");
		balance -= amount;
	}

	private void validatePositiveAmount(double amount) {
		if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than 0");
	}

	public void transfer(double amount, Account accountTo) {
		if (currencyConverter == null && currency != accountTo.getCurrency())
			throw new AccountException("Currency cannot be different");
		double depositAmount=amount;
		if (currencyConverter != null) {
			depositAmount = amount * currencyConverter.convert(currency, accountTo.getCurrency());
		}
		withdraw(amount);
		accountTo.deposit(depositAmount);
	}

	public Currency getCurrency() {
		return currency;
	}

	public boolean isSameCurrency(Account other) {
		return currency.equals(other.getCurrency());
	}

	public void setCurrencyConverter(CurrencyConverter currencyConverter) {
		this.currencyConverter = currencyConverter;
	}

}
