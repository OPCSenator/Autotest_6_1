package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
public class DashboardPage {


    private final ElementsCollection cards = $$(".list__item div");
    private final ElementsCollection buttons = $$x("//span[@class = 'button__text' and (text()='Пополнить')]");
    private final SelenideElement heading = $x("//h2[contains(@class, 'heading')]");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getFirstCardBalance() {
        val text = cards.get(0).text();
        return extractBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.get(1).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage transferTo(int index) {
        buttons.get(index).click();
        return new MoneyTransferPage();
    }
}