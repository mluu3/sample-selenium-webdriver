package test.java.Test;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.java.Fragment.AccountCreateMessagePage;
import test.java.Fragment.ConfirmationCustomerPage;
import test.java.Fragment.ConfirmationDepositPage;
import test.java.Fragment.DepositPage;
import test.java.Fragment.NewAccountPage;
import test.java.Fragment.NewAccountPage.AccountType;
import test.java.Fragment.NewCustomerPage;
import test.java.Fragment.NewCustomerPage.Gender;
import test.java.abstracts.AbstractProjectTest;

import java.util.Random;

public class UserTest extends AbstractProjectTest {

    private static final int RANDOM_NUMBER = new Random().nextInt((400 - 300) + 1) + 300;
    private static final String NAME = "SuperMan";
    private static final String dateOfBirth = "11081991";
    private static final String ADDRESS = "86 York Century";
    private static final String CITY = "America";
    private static final String STATE = "Texas";
    private static final int PIN = 111111;
    private static final int PHONE = 1934888222;
    private static final int DEPOSIT = 501;
    private static final String EMAIL = format("superman%s@gmail.com", RANDOM_NUMBER);
    private static final String PASSWORD = "111";

    private int customerID;
    private int accountID;

    @BeforeClass(alwaysRun = true)
    public void loadProperties() {
        System.out.print("Minh");
    }

    @Test(dependsOnGroups = "createProject")
    public void createNewCustomer() {
        NewCustomerPage newCustomerPage = initNewCustomerPage();
        newCustomerPage
                .setName(NAME).setGender(Gender.FEMALE).setDateOfBirth(dateOfBirth)
                .setAddress(ADDRESS).setCity(CITY).setState(STATE).setPin(PIN)
                .setPhone(PHONE).setEmail(EMAIL).setPassword(PASSWORD).submit();
        ConfirmationCustomerPage confirmationCustomerPage = newCustomerPage.getComfirmationCustomerPage();

        assertEquals(confirmationCustomerPage.getStatus(), "Customer Registered Successfully!!!");
        assertEquals(confirmationCustomerPage.getCustomerName(), "Customer Name " + NAME);
        assertEquals(confirmationCustomerPage.getGender(), "Gender " + Gender.FEMALE.toString().toLowerCase());
        assertEquals(confirmationCustomerPage.getAddress(), "Address " + ADDRESS);
        assertEquals(confirmationCustomerPage.getCity(), "City " + CITY);
        assertEquals(confirmationCustomerPage.getState(), "State " + STATE);
        assertEquals(confirmationCustomerPage.getPin(), "Pin " + PIN);
        assertEquals(confirmationCustomerPage.getPhoneNumber(), "Mobile No. " + PHONE);
        assertEquals(confirmationCustomerPage.getEmail(), "Email " + EMAIL);
        assertEquals(confirmationCustomerPage.getBirthday(), "Birthdate 1991-11-08");
        customerID = confirmationCustomerPage.getCustomerID();
        System.out.print("Customer ID: " + customerID);
    }

    @Test(dependsOnMethods = "createNewCustomer")
    public void createNewAccount() {
        NewAccountPage newAccountPage = initNewAccountPage();
        newAccountPage.setCustomerID(customerID).setAccountType(AccountType.CURRENT)
                .setDeposit(DEPOSIT).submit();
        AccountCreateMessagePage accountCreateMessagePage = newAccountPage.getAccountCreateMessagePage();
        assertEquals(accountCreateMessagePage.getStatus(), "Account Generated Successfully!!!");
        assertEquals(accountCreateMessagePage.getCustomerID(), customerID);
        assertEquals(accountCreateMessagePage.getCustomerName(), "Customer Name " + NAME);
        assertEquals(accountCreateMessagePage.getEmail(), "Email " + EMAIL);
        assertEquals(accountCreateMessagePage.getAccountType(), AccountType.CURRENT);
        //Cannot detect timezone of page
        System.out.print(accountCreateMessagePage.getDateOfOpening());
        assertEquals(accountCreateMessagePage.getCurrentAmount(), "Current Amount " + DEPOSIT);
        accountID = accountCreateMessagePage.getAccountID();
        System.out.print("Account ID: " + accountID);
    }

    @Test(dependsOnMethods = "createNewAccount")
    public void createDeposit() {
        String description = "text";
        DepositPage depositPage = initDepositPage();
        depositPage.setAccountID(accountID).setAmount(DEPOSIT).setDescription(description).submit();
        ConfirmationDepositPage confirmationDepositPage = depositPage.getConfirmationDepositPage();
        assertEquals(confirmationDepositPage.getStatus(), "Transaction details of Deposit " +
                "for Account " + accountID);
        System.out.print("Transaction ID: " + confirmationDepositPage.getTransactionID());
        assertEquals(confirmationDepositPage.getAccountID(), accountID);
        assertEquals(confirmationDepositPage.getAmountCredited(), DEPOSIT);
        assertEquals(confirmationDepositPage.getTypeOfTransaction(), "Type of Transaction Deposit");
        assertEquals(confirmationDepositPage.getDescription(), "Description " + description);
        System.out.print(confirmationDepositPage.getCurrentBalance());
    }
}
