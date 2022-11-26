package ru.job4j.thread.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        accounts.put(account.id(), account);
        return accounts.get(account.id()) != null;
    }

    public synchronized boolean update(Account account) {
        int id = account.id();
        return accounts.replace(id, accounts.get(id), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> accountFrom = getById(fromId);
        Optional<Account> accountTo = getById(toId);
        boolean rsl = false;

        if (!accountFrom.equals(accountTo) && accountFrom.isPresent() && accountTo.isPresent()) {
            Account accFr = accountFrom.get();
            Account accTo = accountTo.get();
            if (accFr.amount() >= amount) {
                update(new Account(accFr.id(), accFr.amount() - amount));
                update(new Account(accTo.id(), accTo.amount() + amount));
                rsl = true;
            } else {
                throw new IllegalArgumentException("Not Enough Money To Transfer");
            }
        }
        return rsl;
    }
}
