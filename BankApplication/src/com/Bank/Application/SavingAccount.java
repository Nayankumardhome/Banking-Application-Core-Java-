package com.Bank.Application;

public class SavingAccount extends Account {
	
	static int minimumBalance;
	static float intrestRate;
	
	static {
		minimumBalance = 10000;
		intrestRate = 2.9f;
	}

	public SavingAccount(long accountNumber, String name, double balance) {
			super(accountNumber,name,balance);
	}

	@Override
	public void withdraw(double amount) {
		
		if(this.balance - minimumBalance >= amount) {
			Transaction withdrawTransaction = new Transaction("Withdraw", amount);
			this.balance -= amount;
			transactions[this.transactionCount++] = withdrawTransaction;
		}
		else {
			try {
				throw new ZeroBalanceException("Insufficent Balance");
			}
			catch(ZeroBalanceException zbe) {
				System.out.println(zbe.getMessage());
				System.out.println("Transaction is Fail..");
			}
		}     

	}

	@Override
	public double showCalculateRateOfInterest(int choice) {
		if(choice == 1){
		    return ((this.getBalance() + SavingAccount.intrestRate) / 12);
		}
		else if(choice == 2){
			return (this.getBalance() + SavingAccount.intrestRate);
		}
		return 0;
	}

	@Override
	public void updateRateOfInterest() {
		this.balance += (this.balance * (LoanAccount.intrestRate/365));
	}

}
