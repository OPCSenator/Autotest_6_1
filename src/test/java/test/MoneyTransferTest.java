package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.DashboardPage;
import static com.codeborne.selenide.Selenide.open;

public class MoneyTransferTest {
    @BeforeEach
    public void testLogin() {

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }
    @Test
    public void from1CardTo2Card() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        var firstBalance = dashboardPage.getFirstCardBalance();
        var secondBalance = dashboardPage.getSecondCardBalance();
        int cardIndex = 1;
        int amount = 2000;
        dashboardPage.transferTo(cardIndex).transfer(amount, DataHelper.getFirstCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstBalance - amount, currentBalanceFirst);
        Assertions.assertEquals(secondBalance + amount, currentBalanceSecond);
    }
    @Test
    public void from2CardTo1Card() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        var firstBalance = dashboardPage.getFirstCardBalance();
        var secondBalance = dashboardPage.getSecondCardBalance();
        int cardIndex = 0;
        int amount = 3000;
        dashboardPage.transferTo(cardIndex).transfer(amount, DataHelper.getSecondCardNumber().getNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstBalance + amount, currentBalanceFirst);
        Assertions.assertEquals(secondBalance - amount, currentBalanceSecond);
    }
    @Test
    public void invalidCardNumber() {
        Configuration.holdBrowserOpen = true;
        var dashboardPage = new DashboardPage();
        int cardIndex = 0;
        int amount = 1000;
        var page = dashboardPage.transferTo(cardIndex);
        page.transfer(amount, DataHelper.getInvalidCardNumber().getNumber());
        page.getNotification();

    }
}