package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public void transferBalance(int amount, DataHelper.CardInfo cardInfo) {
        $("[data-test-id='amount'] .input__control").setValue(String.valueOf(amount));
        $("[data-test-id='from'] .input__control").setValue(cardInfo.getCardNumber());
        $("[data-test-id='action-transfer']").click();
    }
}
