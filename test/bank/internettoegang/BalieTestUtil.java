package bank.internettoegang;

import bank.bankieren.IBank;
import testutil.CommonTestUtil;

import java.rmi.RemoteException;

/**
 * @author Oscar de Leeuw
 */
public class BalieTestUtil {

    public static IBank maakBankDefault() {
        return new BankMock("TOPKEK");
    }

    public static IBalie maakBalieDefault() throws RemoteException {
        return new Balie(maakBankDefault());
    }

    public static IBankiersessie maakBankierSessieDefault() throws RemoteException
    {
        IBalie balie;
        String accountNaam;

        String naam = "Henk";
        String plaats = "Eindhoven";
        String wachtwoord = "1234";

        balie = maakBalieDefault();
        accountNaam = balie.openRekening(naam, plaats, wachtwoord);
        balie.openRekening("Gert", "Veldhoven", "12345");
        return balie.logIn(accountNaam, wachtwoord);
    }
}
