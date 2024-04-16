package com.Bank.Application;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Transaction {
    private static int transactionCounter;

    private long transactionID;
    private String transactionType;
    private double amount;
    private Date timestamp;
    private LocalDate transactionDate;

    static {
    	transactionCounter = 1;
    }
    public Transaction(String transactionType, double amount) {
        this.transactionID = generateUniqueTransactionID();
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = new Date();
        this.transactionDate = LocalDate.now();
    }

    private long generateUniqueTransactionID() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formattedTimestamp;

        synchronized (this) {
            formattedTimestamp = dateFormat.format(new Date());
            transactionCounter++;
        }

        return Long.parseLong(formattedTimestamp + (transactionCounter - 1));
    }

    public long getTransactionID() {
        return transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

	public static int getTransactionCounter() {
		return transactionCounter;
	}

	public static void setTransactionCounter(int transactionCounter) {
		Transaction.transactionCounter = transactionCounter;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
