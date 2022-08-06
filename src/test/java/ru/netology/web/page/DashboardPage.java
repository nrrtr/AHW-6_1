package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private String balanceStart = "баланс: ";
    private String balanceFinish = " р.";
    private SelenideElement topUpButton1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");
    private SelenideElement topUpButton2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String cardNumber) {
        return extractBalance(cards.find(text(cardNumber.substring(15, 19))).getText());
    }

    private int extractBalance(String text) {
        int start = text.indexOf(balanceStart);
        int finish = text.indexOf(balanceFinish);
        String value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage topUpFirstCard() {
        topUpButton1.click();
        return new TransferPage();
    }

    public TransferPage topUpSecondCard() {
        topUpButton2.click();
        return new TransferPage();
    }
}