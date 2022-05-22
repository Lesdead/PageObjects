package page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class RefillPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement refillButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    private void clearField() {
        amountField.sendKeys(Keys.CONTROL + "A");
        amountField.sendKeys(Keys.BACK_SPACE);
        fromField.sendKeys(Keys.CONTROL + "A");
        fromField.sendKeys(Keys.BACK_SPACE);
    }

    public void inputData(String amount, String from) {
        clearField();
        amountField.setValue(amount);
        fromField.setValue(from);
        refillButton.click();
    }

    public DashboardPage refillCard(String amount, String from) {
        inputData(amount, from);
        return new DashboardPage();
    }

    public void wrongRefillCard(String amount, String from) {
        inputData(amount, from);
        errorNotification.shouldHave(text("Ошибка!"));
    }

    public void checkErrorBalance() {
        errorNotification.shouldHave(text("Не достаточно баланса на карте"));
    }

}