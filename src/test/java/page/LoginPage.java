package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void dataInput(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        dataInput(info);
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        dataInput(info);
        errorNotification.shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }
}