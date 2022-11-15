package com.management.account.util;

import java.util.concurrent.atomic.AtomicLong;

public class AccountUtil {

    private AccountUtil(){}

    private static final AtomicLong counter = new AtomicLong(1);
    private static final AtomicLong accountNumber = new AtomicLong(1000000001);

    public static long generateId(){
        return counter.incrementAndGet();
    }

    public static String generateAccountNumber(){
        return accountNumber.getAndIncrement() + "";
    }
}
