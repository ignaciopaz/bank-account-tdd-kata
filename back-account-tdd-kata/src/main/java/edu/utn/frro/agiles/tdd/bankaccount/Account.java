package edu.utn.frro.agiles.tdd.bankaccount;

public class Account {

	private Double balance;

	public Account(double balance) {
		if (balance < 0)
			throw new IllegalArgumentException("Balance cannot be less than 0");
		this.balance = balance;
	}

	public Account() {
		this(0);
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

	public void transfer(double amount, Account account2) {
		withdraw(amount);
		account2.deposit(amount);
	}

}
