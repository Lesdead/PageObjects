package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static data.DataHelper.getCard;
import static data.DataHelper.getWrongCard;

public class MoneyTransferTest {

    private DataHelper.AuthInfo authInfo = DataHelper.getAuthInfo();
    private DataHelper.AuthInfo authInfoInvalid = DataHelper.getWrongAuthInfo(authInfo);
    private DataHelper.VerificationCode verificationCode = DataHelper.getVerificationCodeFor(authInfo);

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldAuthIfUserExist() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldAuthIfUserNotExist() {
        var loginPage = new LoginPage();
        loginPage.invalidLogin(authInfoInvalid);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsFromSecondToFirst() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashBoard = new DashboardPage();
        int amount = 5000;
        int primaryBalanceOne = dashBoard.getCardBalance(1);
        int primaryBalanceTwo = dashBoard.getCardBalance(2);
        var refillPage = dashBoard.increaseBalance(1);
        refillPage.refillCard(Integer.toString(amount), getCard(2).getCardNumber());
        int balanceActualOne = dashBoard.getCardBalance(1);
        int balanceActualTwo = dashBoard.getCardBalance(2);
        assertEquals((primaryBalanceOne + amount), balanceActualOne);
        assertEquals((primaryBalanceTwo - amount), balanceActualTwo);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsFromFirstToSecond() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashBoard = new DashboardPage();
        int amount = 5000;
        int primaryBalanceOne = dashBoard.getCardBalance(1);
        int primaryBalanceTwo = dashBoard.getCardBalance(2);
        var refillPage = dashBoard.increaseBalance(2);
        refillPage.refillCard(Integer.toString(amount), getCard(1).getCardNumber());
        int balanceActualOne = dashBoard.getCardBalance(1);
        int balanceActualTwo = dashBoard.getCardBalance(2);
        assertEquals((primaryBalanceOne - amount), balanceActualOne);
        assertEquals((primaryBalanceTwo + amount), balanceActualTwo);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsWhenNumberCardWrong() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashBoard = new DashboardPage();
        int amount = 5000;
        var refillPage = dashBoard.increaseBalance(2);
        refillPage.refillCard(Integer.toString(amount), getWrongCard().getCardNumber());
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsIfNotEnoughBalanceAtFirstCard() {
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage dashBoard = new DashboardPage();
        int amount = 15000;
        var refillPage = dashBoard.increaseBalance(1);
        refillPage.wrongRefillCard(Integer.toString(amount), getCard(2).getCardNumber());
        refillPage.checkErrorBalance();
    }
}