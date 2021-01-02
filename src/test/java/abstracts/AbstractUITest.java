package test.java.abstracts;

import org.json.JSONException;
import test.java.fragment.DepositPage;
import test.java.fragment.LoginFragment;
import test.java.fragment.NewAccountPage;
import test.java.fragment.NewCustomerPage;

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
