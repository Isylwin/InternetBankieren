package bank.bankieren;

import fontys.util.NumberDoesntExistException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testutil.CommonTestUtil;

/**
 * @author Oscar de Leeuw
 */
public class IBankTest {
    private IBank bank;
    private String naam1 = "Henk";
    private String naam2 = "Piet";
    private String plaats1 = "Aardappel";
    private String plaats2 = "Frikandel";

    private int openRekeningSameKlant(int amount) {
        int ret = 0;

        for(int i = 0; i < amount; i++) {
            ret = bank.openRekening(naam1, plaats1);
        }

        return ret;
    }

    private int openRekeningDifferentKlant(long amount) {
        int ret = 0;

        for (long i = 0; i < amount; i++) {
            ret = i % 2 == 0 ? bank.openRekening(naam1, plaats1) : bank.openRekening(naam2, plaats2);
        }

        return ret;
    }

    @Before
    public void setUp() throws Exception {
        bank = BankTestUtil.maakBankDefault();
    }

    @Test
    public void openRekening_WithEmptyStrings_ReturnsMinusOne() throws Exception {
        int actual = bank.openRekening("", "");
        int expected = -1;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getRekeningNummer_WithOpenRekening_Returns100000000() throws Exception {
        int actual = openRekeningSameKlant(1);
        int expected = 100000000;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getRekeningNummer_OpenTwoRekeningenSameKlant_Returns100000001() throws Exception {
        int actual = openRekeningSameKlant(2);

        Assert.assertEquals(100000001, actual);
    }

    @Test
    public void getRekeningNummer_OpenTwoRekeningenDifferentKlant_Returns100000001() throws Exception {
        int actual = openRekeningDifferentKlant(2);

        Assert.assertEquals(100000001, actual);
    }

    @Test
    public void getRekening_WithOpenRekening_ReturnsRekening() throws Exception {
        int nummer  = openRekeningSameKlant(1);

        IRekening rekening = bank.getRekening(nummer);

        Assert.assertEquals(100000000, rekening.getNr());
        Assert.assertEquals("Henk", rekening.getEigenaar().getNaam());
        Assert.assertEquals("Aardappel", rekening.getEigenaar().getPlaats());
    }

    @Test
    public void maakOver_WithDifferentAccounts_ReturnsTrue() throws Exception {
        openRekeningDifferentKlant(2);

        boolean result = bank.maakOver(100000000, 100000001, CommonTestUtil.maakMoneyDefault());

        Assert.assertTrue(result);
    }

    @Test(expected = NumberDoesntExistException.class)
    public void maakOver_WithWrongDestAccountNumber_ExpectsException() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(100000000, 1214, CommonTestUtil.maakMoneyDefault());
    }

    @Test(expected = NumberDoesntExistException.class)
    public void maakOver_WithWrongSourceAccountNumber_ExpectsException() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(124, 100000000, CommonTestUtil.maakMoneyDefault());
    }

    @Test(expected = RuntimeException.class)
    public void maakOver_WithOwnAccountNumber_ExpectsException() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(1, 1, CommonTestUtil.maakMoneyDefault());
    }

    @Test(expected = RuntimeException.class)
    public void maakOver_WithNegativeMoney_ExpectsException() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(100000000, 100000001, CommonTestUtil.maakMoneyWithAmount(-500));
    }

    @Test
    public void maakOver_WithOverdrawing_ReturnsFalse() throws Exception {
        openRekeningDifferentKlant(2);

        boolean result = bank.maakOver(100000000, 100000001, CommonTestUtil.maakMoneyWithAmount(99999));

        Assert.assertFalse(result);
    }

    @Test
    public void maakOver_WithOverdrawingSourceAccount_ReturnsZero() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(100000000, 100000001, CommonTestUtil.maakMoneyWithAmount(99999));

        IRekening rekening = bank.getRekening(100000000);
        long actual = rekening.getSaldo().getCents();

        Assert.assertEquals(0, actual);
    }

    @Test
    public void maakOver_WithOverdrawingDestAccount_ReturnsZero() throws Exception {
        openRekeningDifferentKlant(2);

        bank.maakOver(100000000, 100000001, CommonTestUtil.maakMoneyWithAmount(99999));

        IRekening rekening = bank.getRekening(100000001);
        long actual = rekening.getSaldo().getCents();

        Assert.assertEquals(0, actual);
    }

    @Test
    public void getName_WithString_ReturnsString() throws Exception {
        String actual = bank.getName();
        String expected = "TOPKEK";

        Assert.assertEquals(expected, actual);
    }

}