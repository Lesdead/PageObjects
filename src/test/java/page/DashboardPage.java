package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static data.DataHelper.getCard;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item [data-test-id]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(int number) {
        val text = cards.findBy(attribute("data-test-id", getCard(number).getCardId())).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public RefillPage increaseBalance(int number) {
        cards.findBy(attribute("data-test-id", getCard(number).getCardId()))
                .find("[data-test-id=action-deposit]").click();
        return new RefillPage();
    }
}