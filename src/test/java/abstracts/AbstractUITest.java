package test.java.abstracts;

import org.json.JSONException;
import test.java.Fragment.DepositPage;
import test.java.Fragment.LoginFragment;
import test.java.Fragment.NewAccountPage;
import test.java.Fragment.NewCustomerPage;

public abstract class AbstractUITest extends AbstractTest {

    protected static final String PAGE_NEW_CUSTOMER = "/manager/addcustomerpage.php";
    protected static final String PAGE_NEW_ACCOUNT = "/manager/addAccount.php";
    protected static final String PAGE_DEPOSIT = "/manager/DepositInput.php";

    protected void signIn(String userID, String password) throws JSONException {
        openUrl(getRootUrl());
        LoginFragment.getInstance(browser).login(userID, password);
    }

    public NewCustomerPage initNewCustomerPage() {
        openUrl(PAGE_NEW_CUSTOMER);
        return NewCustomerPage.getInstance(browser);
    }

    public NewAccountPage initNewAccountPage() {
        openUrl(PAGE_NEW_ACCOUNT);
        return NewAccountPage.getInstance(browser);
    }

    public DepositPage initDepositPage() {
        openUrl(PAGE_DEPOSIT);
        return DepositPage.getInstance(browser);
    }
}
