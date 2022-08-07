package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import java.util.Random;

import static com.codeborne.selenide.Selenide.open;

public class CardMoneyTransferTest {
    Random random = new Random();
    String url = "http://localhost:9999";

    @BeforeEach
    void setUp() {
        var loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        var diffBetweenBalances = (firstCardBalance - secondCardBalance) / 2;
        if (diffBetweenBalances > 0) {
            var transferPage = dashboardPage.topUpSecondCard();
            transferPage.transferBalance(diffBetweenBalances, DataHelper.getFirstCardNumber());
        } else {
            var transferPage = dashboardPage.topUpFirstCard();
            transferPage.transferBalance(diffBetweenBalances, DataHelper.getSecondCardNumber());
        }
    }

    @Test
    @DisplayName("Should transfer money between own cards to first")
    void shouldTransferMoneyToFirstCard() {
        LoginPage loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.topUpFirstCard();
        int amountToTransfer = random.nextInt(secondCardBalance);
        transferPage.transferBalance(amountToTransfer, DataHelper.getSecondCardNumber());
        var expectedFirstRemainder = firstCardBalance + amountToTransfer;
        var expectedSecondRemainder = secondCardBalance - amountToTransfer;
        var actualFirstRemainder = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var actualSecondRemainder = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        Assertions.assertEquals(expectedFirstRemainder, actualFirstRemainder);
        Assertions.assertEquals(expectedSecondRemainder, actualSecondRemainder);
    }

    @Test
    @DisplayName("Should not transfer money between own cards more then balance")
    void shouldNotTransferMoneyToFirstCard() {
        LoginPage loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.topUpFirstCard();
        int amountToTransfer = secondCardBalance * 2;
        transferPage.transferBalance(amountToTransfer, DataHelper.getSecondCardNumber());
        transferPage.transferBalanceError();
    }

    @Test
    @DisplayName("Should transfer money between own cards to Second")
    void shouldTransferMoneyToSecondCard() {
        LoginPage loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.topUpSecondCard();
        int amountToTransfer = random.nextInt(firstCardBalance);
        transferPage.transferBalance(amountToTransfer, DataHelper.getFirstCardNumber());
        var expectedFirstRemainder = firstCardBalance - amountToTransfer;
        var expectedSecondRemainder = secondCardBalance + amountToTransfer;
        var actualFirstRemainder = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var actualSecondRemainder = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        Assertions.assertEquals(expectedFirstRemainder, actualFirstRemainder);
        Assertions.assertEquals(expectedSecondRemainder, actualSecondRemainder);
    }

    @Test
    @DisplayName("Should not transfer money between own cards more then balance")
    void shouldNotTransferMoneyToSecondCard() {
        LoginPage loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirstCardNumber().getCardNumber());
        var secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCardNumber().getCardNumber());
        var transferPage = dashboardPage.topUpSecondCard();
        int amountToTransfer = firstCardBalance * 2;
        transferPage.transferBalance(amountToTransfer, DataHelper.getFirstCardNumber());
        transferPage.transferBalanceError();
    }

    @Test
    @DisplayName("Should login with other account")
    void shouldLoginWithOtherAccount() {
        LoginPage loginPage = open(url, LoginPage.class);
        var authInfo = DataHelper.getOtherAuthInfo(DataHelper.getAuthInfo());
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCode(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }
}
