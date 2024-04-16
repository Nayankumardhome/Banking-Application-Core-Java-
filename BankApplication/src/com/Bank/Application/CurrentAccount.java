package com.Bank.Application;

import java.time.Duration;
import java.util.Date;

public class CurrentAccount extends Account {

	static int minimumBalance;
	static float intrestRate;
	double overdraftLimit;
	
	static {
		minimumBalance = 10000;
		intrestRate = 1.2f;
	}
	
	public CurrentAccount(long accountNumber, String name, double balance) {
		super(accountNumber,name,balance);
		this.overdraftLimit = createOverdraftLimit();
	}

	@Override
	public void withdraw(double amount) {
		if(this.balance + overdraftLimit >= amount)
		{
			balance = balance - amount;
			System.out.println(amount + " withdrawn from your account. New balance : " + balance);
		}
		else
		{
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
        if (getBalance() > 0) {
        	if(choice == 1){
    		    return ((this.getBalance() + CurrentAccount.intrestRate) / 12);
    		}
    		else if(choice == 2){
    			return (this.getBalance() + CurrentAccount.intrestRate);
    		}
        }
        return 0;
    }
	
	private double calculateAverageDailyTransactionAmount() {
        if (transactionCount == 0) {
            return 0;
        }

        double totalAmount = 0;
        int depositCount = 0;
        int withdrawalCount = 0;

        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactions[i];
            if (transaction.getTransactionType().equalsIgnoreCase("Deposit")) {
                totalAmount += transaction.getAmount();
                depositCount++;
            } else if (transaction.getTransactionType().equalsIgnoreCase("Withdrawal")) {
                totalAmount -= transaction.getAmount();
                withdrawalCount++;
            }
        }

        int days = calculateDaysBetweenDates(transactions[0].getTimestamp(), new Date());
        int totalTransactionCount = depositCount + withdrawalCount;
        
        if (totalTransactionCount > 0) {
            return totalAmount / days;
        } else {
            return 0;
        }
    }

    public double createOverdraftLimit() {
        double averageDailyTransactionAmount = calculateAverageDailyTransactionAmount();

        if (averageDailyTransactionAmount < 100) {
            return 1000;
        } else if (averageDailyTransactionAmount < 500) {
            return 5000;
        } else {
            return 10000;
        }
    }

    private int calculateDaysBetweenDates(Date startDate, Date endDate) {
        long days = Duration.between(startDate.toInstant(), endDate.toInstant()).toDays();
        return Math.toIntExact(days);
    }

	@Override
	public void updateRateOfInterest() {
		this.balance += (this.balance * (LoanAccount.intrestRate/365));
	}

}
