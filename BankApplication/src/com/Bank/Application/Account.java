package com.Bank.Application;

public abstract class Account {
	
	protected long accountNumber;
	protected String name;
	protected double balance;
	protected String status;
	protected Transaction[] transactions;
	protected int transactionCount;
		
	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Account(long accountNumber,String name, double balance) {
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
		this.status = "Active";
		transactions = new Transaction[1000];
		this.transactionCount = 0;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTransactionCount() {
		return transactionCount;
	}

	public void setTransactionCount(int transactionCount) {
		this.transactionCount = transactionCount;
	}

	public void setTransactions(Transaction[] transactions) {
		this.transactions = transactions;
	}

	public void display() {
		System.out.println("Account Number : " + this.accountNumber);
		System.out.println("Account Holder Name : " + this.name);
		System.out.println("Account Balance : " + this.balance);
		System.out.println("Account Status : " + this.status);
	}
		
	public Transaction[] getTransactions() {
        return transactions;
    }
	
	public final void deposit(double amount) {
		
        Transaction depositTransaction = new Transaction("Deposit", amount);

		this.balance += amount;
		
        transactions[this.transactionCount++] = depositTransaction;

	}
	
	
	public abstract void withdraw(double amount);
	
	public abstract double showCalculateRateOfInterest(int choice);

	public abstract void updateRateOfInterest();
}
