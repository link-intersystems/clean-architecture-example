package com.link_intersystems.jdbc.tx;

public class TransactionHolder {

    private static final ThreadLocal<Transaction> TRANSACTION_THREAD_LOCAL = new ThreadLocal<>();

    public static Transaction getCurrentTransaction() {
        return TRANSACTION_THREAD_LOCAL.get();
    }

    static void setCurrentTransaction(Transaction transaction) {
        if (transaction == null) {
            TRANSACTION_THREAD_LOCAL.remove();
        } else {
            TRANSACTION_THREAD_LOCAL.set(transaction);
        }
    }
}
