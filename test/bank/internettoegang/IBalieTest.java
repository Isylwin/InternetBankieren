package bank.internettoegang;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import testutil.CommonTestUtil;

import java.rmi.RemoteException;

/**
 * @author Oscar de Leeuw
 */
public class IBalieTest {
    IBalie balie;

    @Before
    public void setUp() throws RemoteException {
        balie = BalieTestUtil.maakBalieDefault();
    }

    @Test
    public void openRekening_WithNullStrings_ReturnsNull() throws Exception {
            String result = balie.openRekening("", "", "");
            Assert.assertNull(result);
    }

    @Test
    public void openRekening_WithNullName_ReturnsNull() throws Exception {
        String result = balie.openRekening("", "Auschwitz", "1234");
        Assert.assertNull(result);
    }

    @Test
    public void openRekening_WithNullPlace_ReturnsNull() throws Exception {
        String result = balie.openRekening("Henk", "", "1234");
        Assert.assertNull(result);
    }

    @Test
    public void openRekening_WithNullPassword_ReturnsNull() throws Exception {
        String result = balie.openRekening("Henk", "Auschwitz", "");
        Assert.assertNull(result);
    }

    @Test
    public void openRekening_WithValidStrings_ReturnsString() throws Exception {
        String result = balie.openRekening("Henk", "Auschwitz", "1234");
        Assert.assertTrue(result.matches("^[a-z0-9]{8}$"));
    }

    @Test
    public void logIn_WithInvalidCredentials_ReturnsNull() throws Exception {
        IBankiersessie result = balie.logIn("", "");
        Assert.assertNull(result);
    }

    @Test
    public void logIn_WithValidCredentials_ReturnsBankiersSessie() throws Exception {
        String account = balie.openRekening("Henk", "Auschwitz", "1234");
        IBankiersessie result = balie.logIn(account, "1234");

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isGeldig());
    }
}
