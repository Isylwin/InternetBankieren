package testutil;

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.Money;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;

import java.rmi.RemoteException;

/**
 * @author Oscar de Leeuw
 */
public class CommonTestUtil {

    public static Money maakMoneyDefault() {
        return new Money(100, "€");
    }

    public static Money maakMoneyWithAmount(long amount) {
        return new Money(amount, "€");
    }
}
