package ru.job4j.thread.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public synchronized boolean add(Account account) {
        int i = id.incrementAndGet();
        accounts.put(i, account);
        return accounts.get(i) != null;
    }

    public boolean update(Account account) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public Optional<Account> getById(int id) {
        return Optional.empty();
    }

    public boolean transfer(int fromId, int toId, int amount) {
        return false;
    }
}
