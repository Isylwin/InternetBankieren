package bank.internettoegang;

import bank.bankieren.IKlant;
import bank.bankieren.IRekening;
import bank.bankieren.Money;

/**
 * @author Oscar de Leeuw
 */
public class RekeningMock implements IRekening {
    @Override
    public int getNr() {
        return 0;
    }

    @Override
    public Money getSaldo() {
        return null;
    }

    @Override
    public IKlant getEigenaar() {
        return null;
    }

    @Override
    public int getKredietLimietInCenten() {
        return 0;
    }
}
