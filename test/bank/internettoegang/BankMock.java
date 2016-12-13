package bank.internettoegang;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.util.NumberDoesntExistException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oscar de Leeuw
 */
public class BankMock implements IBank {
    private String name;
    private int counter = 10000000;

    public BankMock(String name) {
        this.name = name;
    }

    @Override
    public int openRekening(String naam, String plaats) {
        return counter++;
    }

    @Override
    public boolean maakOver(int bron, int bestemming, Money bedrag) throws NumberDoesntExistException {
        return true;
    }

    @Override
    public IRekening getRekening(int nr) {
        return nr <= counter ? new RekeningMock() : null;
    }

    @Override
    public String getName() {
        return name;
    }
}
