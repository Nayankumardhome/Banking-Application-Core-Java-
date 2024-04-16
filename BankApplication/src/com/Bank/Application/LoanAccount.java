package com.Bank.Application;

public class LoanAccount extends Account {

	public static double sactionLoan;
	static float intrestRate;
	
	static {
		intrestRate = 11f;
		sactionLoan = generateSactionLoanAmount(0);
	}
	
	public LoanAccount(long accountNumber, String name, double balance) {
		super(accountNumber,name,balance);
	}

	
	public static double getSactionLoan() {
		return sactionLoan;
	}


	private static void setSactionLoan(double sactionLoan) {
		LoanAccount.sactionLoan = sactionLoan;
	}


	public static float getIntrestRate() {
		return intrestRate;
	}


	public static void setIntrestRate(float intrestRate) {
		LoanAccount.intrestRate = intrestRate;
	}


	private static double generateSactionLoanAmount(double income) {
	    double sanctionedLoanAmount = 0;

	    if (income > 200000 && income <= 300000) {
	        sanctionedLoanAmount = 500000;
	    } else if (income > 300000 && income <= 600000) {
	        sanctionedLoanAmount = 1200000;
	    } else if (income > 600000 && income <= 900000) {
	        sanctionedLoanAmount = 2400000;
	    } else if (income > 900000) {
	        sanctionedLoanAmount = income * 1.5;
	    }
	    LoanAccount.setSactionLoan(sanctionedLoanAmount);
	    return sanctionedLoanAmount;
	}

	public boolean loanProcess(double income) {
		double sAmount = generateSactionLoanAmount(income);
		return (sAmount > 0);
	}
	
	@Override
	public void withdraw(double amount) {
		if(amount <= LoanAccount.sactionLoan) {
			Transaction withdrawTransaction = new Transaction("Loan Withdraw", amount);
			this.balance -= amount;
			LoanAccount.sactionLoan -= amount;
			transactions[this.transactionCount++] = withdrawTransaction;
		}
		else {
			try {
				throw new ZeroBalanceException("Insufficiant Sanction Loan to Withdraw..");
			}
			catch(ZeroBalanceException zbe) {
				System.out.println(zbe.getMessage());
				System.out.println("Amount : " + amount);
				System.out.println("Remaining Sanction Loan : " + LoanAccount.sactionLoan);
				System.out.println("Transaction is Fail..");
			}			
		}
	}

	@Override
	public double showCalculateRateOfInterest(int choice) {
		if(choice == 1){
		    return ((this.getBalance() + LoanAccount.intrestRate) / 12);
		}
		else if(choice == 2){
			return (this.getBalance() + LoanAccount.intrestRate);
		}
		return 0;
	}


	@Override
	public void updateRateOfInterest() {
		this.balance += (this.balance * (LoanAccount.intrestRate/365));
	}
	
	public double calculateEmiMonth()
	{
		int loanTermInMonths = 12;
		double monthlyInterestRate = (LoanAccount.getIntrestRate() / 12) / 100;
		double principalAmount = getBalance();
		
		double emi = principalAmount * (monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTermInMonths))
                / (Math.pow(1 + monthlyInterestRate, loanTermInMonths) - 1);

        return emi;
		
	}

}
