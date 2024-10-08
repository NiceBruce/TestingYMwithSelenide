package pages.base;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import pages.filters.MarketYandexFilterPage;
import utils.Assertions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 *   Реализация паттерна "Page Object".
 *   Данный класс описывает базовую общую структуру веб-страницы маркетплейса и общие методы для работы с ней,
 *   а именно навигации для перехода в необходимые разделы товаров.
 *   @author Борис Демин
 */
public class MarketYandexBasePage extends BasePage{


    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания секции "Электороника",
     * которая отображается ПОСЛЕ нажатия кнопки "каталог" на главной странице Яндекс-маркета.
     * Может быть передан любой другой раздел секции.
     */
    private String mainSectionLocator = "//li[@data-zone-name and .//span[contains(., '%s')]]";


    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания ссылки на страницу с товарами: "Смартфоны"
     * Может быть передан любой другой параметр для подстановки в ссылку.
     */
    private String subCategoryLinkLocator = "//a[contains(@href, '/catalog/') and contains(., '%s')]";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания каталога "Смартфоны" после перехода по ссылке "smartPhoneLink"
     * Может быть передан любой другой параметр для подстановки в локатор, характеризующий искомый раздел
     */
    private String subCategorySectionLocator = "//li//span[@itemprop='name' and text()='%s']";


    /**
     * Метод, ожидающий видимость элемента "catalogButton" и осуществляющий клик по нему.
     * После чего ожидаем увидеть элементы каталога товаров.
     * @param mainCatalogName - необходимый ГЛАВНЫЙ каталог - в данном случае "Электроника",
     * который при наведении открывает меню -> со ссылкой на подкаталог "Смартфоны"
     * @return - возвращаем текущий объект, для последующего вызова необходимых методов
     */
    @Step("Шаг 2. Переходим в каталог: {mainCatalogName}")
    public MarketYandexBasePage goToCatalog(String mainCatalogName) {
        $x(String.format(catalogButtonLocator, mainCatalogName))
                .shouldBe(Condition.visible).click();

        Assertions.assertTrue($x(String.format(mainSectionLocator, mainCatalogName))
                        .shouldBe(Condition.visible).exists(),
                String.format("Каталог %s не найден на странице!", mainCatalogName));

        return this;
    }

    /**
     * Метод, ожидающий видимость элемента каталого "Электроника" и
     * осуществляющий перемещение к нему (его выделение).
     * @param mainCatalogName - необходимый ГЛАВНЫЙ каталог - в данном случае "Электроника",
     * который при наведении открывает меню -> со ссылкой на подкаталог "Смартфоны"
     * @return - возвращаем текущий объект, для последующего вызова необходимых методов
     */
    @Step("Шаг 3. Наводим курсор на раздел каталога: {mainCatalogName}")
    public MarketYandexBasePage selectMainCatalogSection(String mainCatalogName) {
        $x(String.format(mainSectionLocator, mainCatalogName)).shouldBe(Condition.visible).hover();

        Assertions.assertTrue($x(String.format(mainSectionLocator, mainCatalogName))
                        .attr("aria-selected").equals("true"),
                String.format("На каталог %s не наведен курсор", mainCatalogName));

        return this;
    }

    /**
     * Метод, ожидающий видимость элемента-ссылки подкаталога "Смартфоны" и
     * клик с последующим переходом в раздел "Смартфоны".
     * Сначала происходит проверка, что элемент отображен на странице, затем клик по нему.
     * @param subCategory - текстовая переменная, характеризующая необходимую подкатегорию (Смартфоны)
     * @return - возвращаем текущий объект, для последующего вызова необходимых методов
     */
    @Step("Шаг 4. Переходим в раздел: {subCategory}")
    public MarketYandexBasePage moveToSubCategory(String subCategory) {
        Assertions.assertTrue($x(String.format(subCategoryLinkLocator, subCategory)).exists(),
                String.format("Каталог %s не найден на странице!", subCategory));

        $x(String.format(subCategoryLinkLocator, subCategory))
                .shouldBe(Condition.visible).click();

        return this;
    }

    /**
     * Цель метода заключается в утверждении,
     * что произведен переход в раздел "Смартфоны"
     * @param subCategory - текстовая переменная, характеризующая необходимую подкатегорию (Смартфоны)
     * @return возвращаемое значение класс MarketYandexFilterPage, предназначенный для установки фильтров на странице маркетплейса.
     */
    @Step("Шаг 5. Проверка, что осуществлен переход в раздел: {subCategory} перед установкой фильтров по товарам")
    public MarketYandexFilterPage checkIsSectionDisplayed(String subCategory) {
        $x(String.format(subCategorySectionLocator, subCategory))
                .shouldBe(Condition.visible);
        Assertions.assertTrue($x(String.format(subCategorySectionLocator, subCategory)).exists(),
                String.format("Каталог %s не найден на странице!", subCategory));
        return page(MarketYandexFilterPage.class);
    }
}
