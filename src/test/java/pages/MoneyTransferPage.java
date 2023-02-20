package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import pages.DashboardPage;

import static com.codeborne.selenide.Selenide.$x;

public class MoneyTransferPage {
    private final SelenideElement amountField = $x("//div[@data-test-id = 'amount']//input");
    private final SelenideElement fromField = $x("//span[@data-test-id = 'from']//input");
    private final SelenideElement buttonTrans = $x("//button[@data-test-id='action-transfer']");

    private final SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");

        public DashboardPage transfer(int amount, String cardNumber) {
        amountField.setValue(Integer.toString(amount));
        fromField.setValue(cardNumber);
        buttonTrans.click();
        return new DashboardPage();
    }

    public void getNotification() {

        errorNotification.shouldBe(Condition.visible);
        errorNotification.shouldHave(Condition.exactText("Ошибка\n"+"Ошибка! Произошла ошибка"));


    }
}
