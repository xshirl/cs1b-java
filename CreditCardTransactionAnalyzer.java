//---------------------------------------------------
// @version1.0 10-24-2020
// @author Shirley Xu
// File Name: CreditCardTransactionAnalyzer.java
// Program purpose: To write a program that analyzes customers' credit card
// monthly purchase transactions.
// Revision history:
// Date Description
//10/24/20 - Finished up to subclasses of transaction
// 10/25/20 - Finished up to customer class
// 10/26/20 - Finished and ran

package com.company;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class CreditCardTransactionAnalyzer
{
   public static void main(String[] args) {
      Customer newCustomer = new Customer("Shirley", "1111222233334444",
              2000.00, 1000.0);
      newCustomer.readTransactions();
      newCustomer.reportTransactions();
      newCustomer.reportBankingCharges();
      newCustomer.reportRewardSummary();
   }
}

class Date implements Cloneable {
   private int month;
   private int day;
   private int year;

   public Date() {
      month = 1;
      day = 1;
      year = 1970;
   }

   public Date(int month, int day, int year) {
      if(month >= 1 && month <= 12) {
         this.month = month;
      }
      if(day >= 1 && day <= 31) {
         this.day = day;
      }
      if(year >= 1970) {
         this.year = year;
      }
   }

   public Date(Date date) {
      month = date.month;
      day = date.day;
      year = date.year;
   }

   public int getMonth()
   {
      return month;
   }

   public void setMonth(int month)
   {
      this.month = month;
   }

   public int getDay()
   {
      return day;
   }

   public void setDay(int day)
   {
      this.day = day;
   }

   public int getYear()
   {
      return year;
   }

   public void setYear(int year)
   {
      this.year = year;
   }

   public String toString() {
      String month = "";
      String day = "";
      String year = "";
      if (this.getMonth() < 10) {
         month += "0" + this.getMonth();
      } else {
         month += this.getMonth();
      }
      if(this.getDay() < 10) {
         day += "0" + this.getDay();
      } else {
         day += this.getDay();
      }
      year += this.getYear();
      return month + "/" + day + "/" + year;
   }

   public boolean equals(Date date) {
      if(date.month == this.month && date.day == this.day && date.year == this.year) {
         return true;
      }
      return false;
   }

   public Date clone() throws CloneNotSupportedException {
      Date cloned = (Date) super.clone();
      return cloned;
   }

   public int compareTo(Date date2) {
      int res = 0;
      if(this.equals(date2)) {
         res = 0;
      } else {
         if (this.year < date2.year) {
            res = 1;
         } else if (this.year >= date2.year){
            res = -1;
         }
         if(this.month < date2.month) {
            res = 1;
         } else if (this.month >= date2.month) {
            res = -1;
         }
         if(this.day < date2.day) {
            res = 1;
         } else if (this.day >= date2.day) {
            res = -1;
         }
      }
      return res;
   }
}

abstract class Transaction implements Rewardable {
   protected int id;
   protected Date date;
   protected double amount;

   public Transaction() {
      id = 0;
      date = new Date();
      amount = 0.0;
   }

   public Transaction(int id, int month, int day, int year, double amount) {
      this.id = id;
      this.date = new Date(month, day, year);
      this.amount = amount;
   }


   public abstract void list();
   public String toString() {
      String result = "";
      result += date.toString() + "~" + this.getId() + "~" + this.getAmount();
      return result;
   }

   public int getId()
   {
      return id;
   }

   public void setId(int id)
   {
      this.id = id;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public double getAmount()
   {
      return amount;
   }

   public void setAmount(double amount)
   {
      this.amount = amount;
   }

   public boolean equals(Transaction one, Transaction two) {
      if(one.getId() == two.getId()) {
         return true;
      }
      return false;
   }



}

class DepartmentStoreTransaction extends Transaction implements Rewardable{
   private String departmentName;
   private int returnPolicy;
   public DepartmentStoreTransaction() {
      super();
      departmentName = "Macys";
      returnPolicy = 30;

   }
   public DepartmentStoreTransaction(int id, int month, int day, int year,
                                     double amount, String departmentName,
                                     int returnPolicy) {
      super(id, month, day, year, amount);
      this.departmentName = departmentName;
      this.returnPolicy = returnPolicy;
   }

   public String getDepartmentName()
   {
      return departmentName;
   }

   public void setDepartmentName(String departmentName)
   {
      this.departmentName = departmentName;
   }

   public int getReturnPolicy()
   {
      return returnPolicy;
   }

   public void setReturnPolicy(int returnPolicy)
   {
      this.returnPolicy = returnPolicy;
   }

   public String toString() {
      String result = "";
      result += "DS" + "~" + super.toString() + "~" + this.getDepartmentName() +
              "~" + this.getReturnPolicy();
      return result;
   }

   public void list() {
      System.out.println(date.toString() + "\t" + "Department Store" + "\t" +
              this.getDepartmentName() + "\t" + "$"+
              this.getAmount() + "(return in " + this.getReturnPolicy() +
              " days)");
   }
   public int earnPoints() {
      return (int) this.amount * 3;
   }
}

class BankingTransaction extends Transaction implements Rewardable {
   private String type;
   private double charge;
   public BankingTransaction() {
      super();
      type = "CASH withdraw";
      charge = 0.00;
   }

   public BankingTransaction(int id, int month, int day, int year,
                             double amount, String type, double charge) {
      super(id, month, day, year, amount);
      this.type = type;
      this.charge = charge;
   }

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }

   public double getCharge()
   {
      return charge;
   }

   public void setCharge(double charge)
   {
      this.charge = charge;
   }

   public String toString() {
      String result = "";
      result += "BK" + "~" + super.toString() + "~" + this.getType() + "~" + this.getCharge();
      return result;
   }

   public void list() {
      System.out.println(date.toString() + "\t" + "Banking" + "\t" +
              this.getType() +" withdraw " + "\t" + "$" + (this.getAmount() + this.getCharge()));
   }

   public int earnPoints() {
      return 0;
   }
}

class GroceryTransaction extends Transaction implements Rewardable {
   private String storeName;

   public GroceryTransaction() {
      super();
      storeName = "Walmart";
   }
   public GroceryTransaction(int id, int month, int day, int year,
                             double amount, String storeName) {
      super(id, month, day, year, amount);
      this.storeName = storeName;
   }

   public String getStoreName()
   {
      return storeName;
   }

   public void setStoreName(String storeName)
   {
      this.storeName = storeName;
   }

   public String toString(){
      String result = "";
      result += "GR" + "~" + super.toString() + "~" + this.getStoreName();
      return result;
   }
   public void list() {
      System.out.println(date.toString() + "\t" + "Grocery" + "\t" + this.getStoreName() +
       "\t" + "$" + this.getAmount());
   }
   public int earnPoints() {
      return (int) this.amount * 5;
   }

}

interface Rewardable {
  int earnPoints();
}

class Customer {
   private static final int SIZE  = 32;
   private String customerName;
   private String creditCardNumber;
   private double balance;
   private double points;
   private Transaction[] transactions;
   private Rewardable[] rewardables;

   public Customer() {
      customerName = "John Doe";
      creditCardNumber = "1111222233334444";
      balance = 0.0;
      points = 1000.0;
      transactions = new Transaction[SIZE];
      rewardables = new Rewardable[SIZE];
   }

   public Customer(String customerName, String creditCardNumber,
                   double balance, double points) {
      this.customerName = customerName;
      this.creditCardNumber = creditCardNumber;
      this.balance = balance;
      this.points = points;
      transactions = new Transaction[SIZE];
      rewardables = new Rewardable[SIZE];
   }

   public void readTransactions() {
      final String INPUT_FILE_LOCATION =
              "/Users/shirleyxu/IdeaProjects/CS1B Assignment " +
                      "2/src/com/company/transactions.txt";
      Path inputFilePath = Paths.get(INPUT_FILE_LOCATION);
      BufferedReader reader = null;
      String line = null;
      int index = 0;
      int index2 = 0;
      try {
         reader = Files.newBufferedReader(inputFilePath,
                 StandardCharsets.US_ASCII);
         while((line = reader.readLine()) != null && index < 8) {
            addTransaction(line, transactions, index++);
         }
         reader.close();
      } catch(IOException e) {
         e.printStackTrace();
      }

      for(Transaction transaction: transactions) {
         if(transaction instanceof Rewardable) {
            rewardables[index2] = transaction;
            index2++;
         }
      }

   }

   public static void addTransaction(String line, Transaction[] transactions,
                                     int index) {
      String type;
      int month;
      int day;
      int year;
      int id;
      double amount;
      String departmentName;
      int returnPolicy;
      String bankingType;
      double charge;
      String storeName;
      String[] data = line.split("~");
      type = data[0];
      String[] date = data[1].split("/");
      month = Integer.parseInt(date[0]);
      day = Integer.parseInt(date[1]);
      year = Integer.parseInt(date[2]);
      id = Integer.parseInt(data[2]);
      amount = Double.parseDouble(data[3]);
      if(type.equals("DS")) {
         departmentName = data[4];
         returnPolicy = Integer.parseInt(data[5]);
         transactions[index] = new DepartmentStoreTransaction(id, month, day,
                 year, amount, departmentName, returnPolicy);
      } else if (type.equals("BK")) {
         bankingType = data[4];
         charge = Double.parseDouble(data[5]);
         transactions[index] = new BankingTransaction(id, month, day, year,
                 amount, bankingType, charge);
      } else if (type.equals("GR")) {
         storeName = data[4];
         transactions[index] = new GroceryTransaction(id, month, day, year,
                 amount, storeName);
      }


   }

   public void reportTransactions() {
      System.out.println("TRANSACTION LISTING REPORT");
      for(Transaction transaction: transactions)
      {
         if (transaction != null)
         {
            transaction.list();
         }
      }
   }

   public void reportBankingCharges() {
      double totalCharge = 0.0;
      for(Transaction transaction: transactions) {
         if(transaction instanceof BankingTransaction) {
            totalCharge += ((BankingTransaction) transaction).getCharge();
         }
      }
      System.out.println("Total charges: $" + totalCharge);
   }

   public void reportRewardSummary() {
      double departmentPoints = 0;
      double groceryPoints = 0;
      for(Transaction transaction: transactions) {
         if(transaction instanceof GroceryTransaction) {
            groceryPoints += transaction.earnPoints();
         }
         else if(transaction instanceof DepartmentStoreTransaction) {
            departmentPoints += transaction.earnPoints();
         }
      }
      double totalPoints = points;
      totalPoints += departmentPoints + groceryPoints;
      System.out.println("Rewards Summary for " + customerName + " "+ creditCardNumber.substring(creditCardNumber.length()-4));
      System.out.println("Previous points balance" + "\t" + points);
      System.out.println("+ Points earned on Department store purchases: " + departmentPoints);
      System.out.println("+ Points earned on Grocery Stores purchases: " + groceryPoints);
      System.out.println(
              "----------------------------------------------------------------");
      System.out.println("= Total points available for redemption: " + totalPoints);
   }
}



