package bank.internettoegang;

import bank.bankieren.IRekening;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testutil.CommonTestUtil;

/**
 * @author Oscar de Leeuw
 */
public class IBankierSessieTest {
    IBankiersessie sessie;

    @Before
    public void setUp() throws Exception {
        sessie = BalieTestUtil.maakBankierSessieDefault();
    }

    @Test
    public void isGeldig_WithTimeout_ShouldBeFalse() throws Exception {
        Thread.sleep(100);
        Assert.assertFalse(sessie.isGeldig());
    }

    @Test
    public void maakOver_WithDefaultMoney_ShouldBeTrue() throws Exception {
        boolean result = sessie.maakOver(100000001, CommonTestUtil.maakMoneyDefault());
        Assert.assertTrue(result);
    }

    @Test
    public void getRekening_WithoutArgument_ReturnsRekening() throws Exception {
        IRekening rekening = sessie.getRekening();
        Assert.assertNotNull(rekening);
    }
}
