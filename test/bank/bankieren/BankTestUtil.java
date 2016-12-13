package bank.bankieren;

/**
 * @author Oscar de Leeuw
 */
public class BankTestUtil {
    public static IBank maakBankDefault() {
        return new Bank("TOPKEK");
    }

    public static IBank maakBankWithName(String naam) {
        return new Bank(naam);
    }
}
