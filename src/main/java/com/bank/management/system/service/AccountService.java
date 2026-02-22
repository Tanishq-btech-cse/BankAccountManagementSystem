package com.bank.management.system.service;

import com.bank.management.system.config.HibernateUtility;
import com.bank.management.system.entity.Account;
import com.bank.management.system.entity.History;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class responsible for handling all business logic
 * related to {@link Account} operations.
 *
 * <p>This class acts as the service layer between the UI and
 * the persistence layer (Hibernate).</p>
 *
 * <p>It provides functionalities such as:</p>
 * <ul>
 *     <li>Opening new accounts</li>
 *     <li>User authentication</li>
 *     <li>Deposit & Withdraw operations</li>
 *     <li>Money transfer between accounts</li>
 *     <li>Checking account balance</li>
 *     <li>Retrieving transaction history</li>
 * </ul>
 *
 * @author Tanishq
 * @since 1.0
 */
public class AccountService implements AccountServiceInterface {

    /**
     * Opens a new bank account.
     *
     * @param name           Account holder name
     * @param initialBalance Initial deposit amount (must not be negative)
     * @param username       Login username
     * @param password       Login password
     * @param mPin           Security MPIN for transactions
     */
    @Override
    public void openAccount(String name, double initialBalance,
                            String username, String password, int mPin) {

        if (initialBalance < 0) {
            System.out.println("Initial balance cannot be negative");
            return;
        }

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Account account = new Account();
            History history = new History();

            account.setName(name);
            account.setBalance(initialBalance);
            account.setUsername(username);
            account.setPassword(password);
            account.setMPin(mPin);

            history.setType("New Account");
            history.setAmount(initialBalance);
            history.setDateTime(LocalDateTime.now());

            account.addHistory(history);

            session.persist(account);
            transaction.commit();

            System.out.println("Account created successfully ❤️");
            System.out.println("Your Account Number: " + account.getAccountNumber());
        }
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param username User's login username
     * @param password User's login password
     * @return {@link Account} object if credentials are valid,
     *         otherwise null
     */
    @Override
    public Account login(String username, String password) {

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {

            String hql = "FROM Account WHERE username = :u AND password = :p";
            Query<Account> query = session.createQuery(hql, Account.class);
            query.setParameter("u", username);
            query.setParameter("p", password);

            return query.uniqueResult();
        }
    }

    /**
     * Deposits a specified amount into an account.
     *
     * @param account Account in which money will be deposited
     * @param amount  Amount to deposit (must be greater than 0)
     */
    @Override
    public String deposit(Account account, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return "Enter valid amount";
        }

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Account managedAccount =
                    session.find(Account.class, account.getAccountNumber());
            History history = new History();
            managedAccount.setBalance(managedAccount.getBalance() + amount);
            history.setType("Deposit");
            history.setAmount(amount);
            history.setDateTime(LocalDateTime.now());
            managedAccount.addHistory(history);
            transaction.commit();
            System.out.println("Deposit successful 👍");
            return "Deposit successful 👍";
        }
    }

    /**
     * Withdraws a specified amount from an account.
     *
     * @param account Account from which money will be withdrawn
     * @param amount  Amount to withdraw (must be greater than 0
     *                and less than available balance)
     */
    @Override
    public String withdraw(Account account, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return "Enter valid amount";
        }
        try (Session session = HibernateUtility.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Account managedAccount =
                    session.find(Account.class, account.getAccountNumber());
            if (managedAccount.getBalance() < amount) {
                System.out.println("Insufficient balance ❌");
                return "Insufficient balance ❌";
            }
            History history = new History();
            managedAccount.setBalance(managedAccount.getBalance() - amount);
            history.setType("Withdraw");
            history.setAmount(amount);
            history.setDateTime(LocalDateTime.now());
            managedAccount.addHistory(history);
            transaction.commit();
            System.out.println("Withdrawal successful 👍");
            System.out.println("New Balance: " + managedAccount.getBalance());
            return "Withdrawal successful 👍";
        }
    }

    /**
     * Displays the current balance of the account.
     *
     * @param account Account whose balance will be checked
     */
    @Override
    public void checkBalance(Account account) {

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {

            Account managedAccount =
                    session.find(Account.class, account.getAccountNumber());

            System.out.println("Current Balance: " + managedAccount.getBalance());
        }
    }

    /**
     * Transfers money from one account to another.
     *
     * @param sender        Account sending the money
     * @param receiverAccNo Receiver's account number
     * @param amount        Amount to transfer (must be valid and
     *                      less than sender balance)
     */
    @Override
    public String transfer(Account sender, long receiverAccNo, double amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount");
            return "Invalid amount";
        }

        try (Session session = HibernateUtility.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            Account managedSender =
                    session.find(Account.class, sender.getAccountNumber());

            Account receiver = session.find(Account.class, receiverAccNo);

            if (receiver == null) {
                System.out.println("Receiver account not found ❌");
                return "Receiver account not found ❌";
            }

            if (managedSender.getBalance() < amount) {
                System.out.println("Insufficient balance ❌");
                return "Insufficient balance ❌";
            }

            History sendHistory = new History();
            History receiveHistory = new History();

            managedSender.setBalance(managedSender.getBalance() - amount);
            receiver.setBalance(receiver.getBalance() + amount);

            sendHistory.setType("Transfer");
            sendHistory.setAmount(amount);
            sendHistory.setDateTime(LocalDateTime.now());

            receiveHistory.setType("Received");
            receiveHistory.setAmount(amount);
            receiveHistory.setDateTime(LocalDateTime.now());

            managedSender.addHistory(sendHistory);
            receiver.addHistory(receiveHistory);

            transaction.commit();

            System.out.println("Transfer successful 👍");
            return "Transfer successful 👍";
        }
    }

    /**
     * Retrieves the latest version of an account from the database.
     *
     * @param accountNumber Account number
     * @return Updated {@link Account} entity
     */
    @Override
    public Account getUpdatedAccount(long accountNumber) {

        try(Session session = HibernateUtility
                .getSessionFactory()
                .openSession()) {

            return session.find(Account.class, accountNumber);
        }
    }

    /**
     * Fetches transaction history of a specific account.
     *
     * @param accountNumber Account number
     * @return List of {@link History} records ordered by
     *         date in descending order
     */
    @Override
    public List<History> getTransactionHistory(long accountNumber) {

        try (Session session = HibernateUtility
                .getSessionFactory()
                .openSession()) {

            Query<History> historyQuery = session.createQuery(
                    "SELECT h FROM History h " +
                            "WHERE h.account.accountNumber = :acc " +
                            "ORDER BY h.dateTime DESC",
                    History.class
            );

            historyQuery.setParameter("acc", accountNumber);
            return historyQuery.getResultList();
        }
    }
}