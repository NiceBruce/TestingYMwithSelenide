package pages.results;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.junit.jupiter.api.function.Executable;
import pages.base.BasePage;
import utils.Assertions;

import java.time.Duration;
import java.util.*;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static utils.Assertions.assertAll;
import static utils.Properties.driversProperties;
import static utils.Properties.filterProperties;
import static utils.Util.*;

/**
 *   Данный класс описывает базовую общую структуру "результатов поисковой выдачи товаров по предустановленным фильтрам" веб-страницы маркетплейса.
 *   Класс содержит поля, описывающие XPATH-локаторы элементов "результатов", а
 *   так же методы, необходимые для поиска/обработки/навигации/проверки результатов выдачи поиска товаров по выбранным фильтрам.
 */
public class MarketYandexPageResult extends BasePage {

    /**
     * Константа, характеризующая диапазон смещения вниз/скроллинг по элементу "поисковой выдачи результатов".
     * Подставляется в метод slowlyMoveToResultSearch() в качестве значения для "random.nextInt" - генерации диапазона и
     * далее полученное значение записывается в переменную "shift"
     */
    private static final int PAGE_RESULT_OFFSET = 500;

    /**
     * Константа, характеризующая диапазон времени смещения вниз/скроллинга по элементу "поисковой выдачи результатов".
     * Подставляется в метод slowlyMoveToResultSearch() в качестве значения для "random.nextInt" - генерации диапазона времени и
     * далее полученное значение участвует в создании "паузы" после смещения/скроллинга по элементу "поисковой выдачи результатов"
     */
    private static final int SCROLLING_TIME_DELAY = 500;

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания блока элементов,
     * предназначенных для "навигации по результатам выдачи товаров"
     */
    private String searchPagerLocator = "//div[div[@data-auto='pagination-page']]";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания элемента,
     * предназначенного для "навигации по результатам выдачи товаров ВПЕРЕД"
     */
    private String paginationForwardLocator = "//div[@data-auto='pagination-next']";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для описания элемента,
     * предназначенного для "навигации по результатам выдачи товаров НАЗАД"
     */
    private String paginationBackwardLocator = "//div[@data-auto='pagination-prev']";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, описывающий имя модели единицы товара в поисковой выдаче
     */
    private String modelNameLocator = ".//h3[@data-zone-name='title']//span";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, описывающий цену единицы товара в поисковой выдаче
     */
    private String priceLocator = ".//div[@data-zone-name='price']//h3";

    /**
     * Текстовая переменная, представляющая XPATH-локатор, предназначенный для хранения списка результатов
     * поиска товаров по предустановленным фильтрам на странице
     */
    private String rawSearchResultsLocator = "//main[@id='searchResults']//article";

    /**
     * Коллекция, хранящая результаты выдачи товаров по предустановленным фильтрам ВСЕХ имеющихся страниц
     */
    private List<List<Map<String, Integer>>> allPagesResult = new ArrayList<>();

    /**
     * Коллекция, хранящая список элементов типа - Executable, в данном случае элементов,
     * представляющие проверки для каждого элемента-товара на странице
     */
    private List<Executable> assertionsOnPage = new ArrayList<>();

    /**
     * Метод, цель которого получить "наименование товара",
     * @param element веб-элемент типа SelenideElement, из которого необходимо извлечь и обработать "наименование товара".
     * @return текстовое значение "наименование товара"
     */
    public String getName(SelenideElement element) {
        return element.$x(modelNameLocator).getText();
    }

    /**
     * Метод, цель которого получить целочисленное значение "цены товара",
     * @param element веб-элемент типа SelenideElement, из которого необходимо извлечь и обработать значение цены товара.
     * @return целочисленное значение "цены товара"
     */
    public Integer getPriceValue(SelenideElement element) {
        return Integer.parseInt(element.$x(priceLocator)
                .getText().trim().replaceAll("\\D", ""));
    }

    /**
     * Метод, выполняющий подготовительные действия, необходимые для корректного получения данных
     * результатов поисковой выдачи товара по предустановленным фильтрам
     */
    public void waitForResultSearch() {
        actions.pause(Duration.ofSeconds(1)).perform();
        $x(preloaderResults).shouldNot(visible);

        $$x(rawSearchResultsLocator).get(0)
                .shouldBe(visible, Duration.ofMillis(driversProperties.driverExplicitlyWait()))
                .scrollTo();

        $x(searchPagerLocator).shouldBe(visible);
    }

    /**
     * Метод, осуществляющий обработку текущей СТРАНИЦЫ с результатами поисковой выдачи товаров по предустановленным фильтрам.
     * В процессе выполнения метод:
     * <br/> - ожидает исчезновения веб-элемента идентификатора загрузки/обновления результатов выдачи,
     * <br/> - ждет появления веб-элемента, предназначенного для хранения списка результатов поиска товаров по предустановленным фильтрам на странице
     * <br/> - создает коллекцию "notebooksOfPage", хранящую список<словарей товаров <наименование, цена>>
     * <br/> - далее происходит поэлементная проверка товаров текущей СТРАНИЦЫ, добавление их в коллекцию "notebooksOfPage" и
     * добавление ее в результирующую коллекцию с проверками ВСЕХ страниц.
     * @param page номер текущей страницы, необходимый для отображения в allure - отчете
     * @return коллекция, хранящая результаты проверок всех страниц выдачи товаров по предустановленным фильтрам
     */
    @Step("Шаг 9. Запись результатов поиска товаров на странице: {page}")
    public List<List<Map<String, Integer>>> getResultsFromPage(int page) {
        List<Map<String, Integer>> productsOfPage = new ArrayList<>();
        $x(preloaderResults).shouldBe(Condition.not(visible));
        ElementsCollection rawRes = $$x(rawSearchResultsLocator);

        rawRes.forEach(element -> {
            element.shouldBe(visible);
            productsOfPage.add(Map.of(getName(element), getPriceValue(element)));
        });
        allPagesResult.add(productsOfPage);
        return allPagesResult;
    }

    /**
     * Метод, осуществляющий обход/перемещение вниз/скроллинг текущей СТРАНИЦЫ с результатами поисковой выдачи товаров по предустановленным фильтрам.
     * Так как блок div с результатами поиска товаров по предустановленным фильтрам на странице изначально хранит в себе 8-элементов,
     * необходимо его плавно промотать/проскролить до конца наполнив результатами.
     * В процессе выполнения метод:
     * <br/> - ожидает исчезновения веб-элемента идентификатора загрузки/обновления результатов выдачи,
     * <br/> - ждет появления веб-элемента, предназначенного для хранения списка результатов поиска товаров по предустановленным фильтрам на странице
     * <br/> - ждет появления веб-элемента, предназначенного для "навигации по результатам выдачи товаров"
     * <br/> - далее происходит плавное перемещение вниз/скроллинг называйте как хотите по резутатам выдачи на станице.
     * Это необходимо для того, чтобы элемент "rawSearchResults" - представляющий собой блок div со списком товаров НЕ СХЛОПНУЛСЯ и не пропал
     * со страницы. При довольно длительном наблюдении было замечено, что быстрое перемещение по этому элементу приводит к такому эффекту. Для того,
     * что бы обойти данное ограничение было реализовано перемещение по странице по пикселям в РАНДОМНОМ диапазоне - константе "PAGE_RESULT_OFFSET", так же перемещаться нужно
     * не особо быстро, соответственно было введено РАНДОМНОЕ время перемещения, диапазон хранится в константе "SCROLLING_TIME_DELAY".
     * В общем логика метода такая, создается две переменные:
     * <br/> - "startPos" - начальное значение стартовой позиции, координата по Y первого элемента в списке результатов
     * <br/> - "endPos" - конечное значение координата по Y - элемента навигации по результатам выдачи товаров "searchPagerLocator",
     * которого нужно достичь/доскролить до него, т.к. этот элемент находится сразу за блоком результатов.
     * Далее в цикле сравниваем достигло ли начальное значение конечной точки, в теле цикла вычисляем смещение и прибавляем к начальной точке.
     * При данных манипуляциях можно проматывать страницу, не боясь, что элемент результатов схлопнется и исчезнет, если так происходит - нужно:
     * <br/> - уменьшить значение константы "PAGE_RESULT_OFFSET"
     * <br/> - увеличить значение константы "SCROLLING_TIME_DELAY"
     * @param page номер текущей страницы, необходимый для отображения в allure - отчете
     */
    @Step("Шаг 8. Перемещение по результатам поиска товаров для накопления элементов (товаров) на странице: {page}")
    public void slowlyMoveToResultSearch(int page) {
        $x(preloaderResults).shouldNot(visible);
        $x(searchPagerLocator).shouldBe(visible);

        Random random = new Random();
        int startPos = $$x(rawSearchResultsLocator).get(0).shouldBe(visible).getLocation().getY();
        int endPos = $x(searchPagerLocator).getLocation().getY()
                    - WebDriverRunner.getWebDriver().manage().window().getSize().getHeight();
        int shift;

        while(startPos <= endPos) {
            shift = random.nextInt(PAGE_RESULT_OFFSET);
            startPos += shift;
            actions.scrollByAmount(0, shift)
                    .pause(Duration.ofMillis(random.nextInt(SCROLLING_TIME_DELAY)))
                    .build().perform();
        }
    }

    /**
     * Метод, осуществляющий рекурсивное перемещение по страницам ВПЕРЕД и НАЗАД.
     * @param directionForward булевое значение, ИСТИНА - перемещаемся вперед и собираем данные,
     * если ЛОЖЬ - перемещаемся назад до первой страницы. На основе этой булевой переменной вычисляем локатор для
     * элемента навигации/пагинации "ВПРЕД" ИЛИ "НАЗАД"
     * <br/> выход из рекурсии - отсутствие элемента навигации/пагинации "ВПРЕД" и "НАЗАД"
     * @return возвращаем текущий объект, для последующего вызова необходимых методов
     * */
    public MarketYandexPageResult moveInDirection(boolean directionForward) {
        ElementsCollection pagerElements= $$x(directionForward ? paginationForwardLocator : paginationBackwardLocator);

        if (directionForward) {
            waitForResultSearch();
            slowlyMoveToResultSearch(allPagesResult.size() + 1);
            getResultsFromPage(allPagesResult.size() + 1);
        }

        if (!pagerElements.isEmpty()) {
            pagerElements.get(0).shouldBe(visible).scrollTo().click();
            $x(preloaderResults).shouldNot(visible);

            moveInDirection(directionForward);
        }
        
        return this;
    }

    /**
     * Метод, осуществляющий проверку полученных результатов после прохождения по всем страницам
     * с результатами поисковой выдачи товаров по предустановленным фильтрам.
     * Проходимся по коллекции хранящей результаты всех страниц И на каждой странице запускаем проверки каждого элемента
     * При этом, после обхода каждой страницы, чтобы тест НЕ УПАЛ ложим проверки в коллекцию List<Executable> и
     * затем в конце добавляем коллекцию в переопределенный метод assertAll для "SOFT ASSERT", тем самым ALLURE-отчет
     * покажет результаты всех проверок всех страниц, а не свалится на первом же попавшемся элементе n-ой страницы.
     * @return возвращаем текущий объект, для последующего вызова необходимых методов
     */
    @Step("Шаг 10. Проверка всех страниц на соответствие предустановленному фильтру")
    public MarketYandexPageResult checkResultFromAllPages() {
        for (int i = 0; i < allPagesResult.size(); i++) {
            List<Map<String, Integer>> page = allPagesResult.get(i);
            IntStream.range(0, page.size()).boxed()
                    .flatMap(notebook -> page.get(notebook).entrySet().stream())
                    .forEach(entry -> {
                        assertionsOnPage.add(() -> checkProduct(allPagesResult.indexOf(page) + 1,
                                entry.getKey(), entry.getValue()));
                    });
        }
        assertAll(assertionsOnPage.toArray(new Executable[0]));
        return this;
    }

    /**
     * Метод, осуществляющий проверку каждого элемента "результата поисковой выдачи товаров по предустановленным фильтрам"
     * Метод представляет собой ШАГ Allure-отчета, принимающий параметры:
     * @param pageNumber номер страницы - для красивого и понятного отображения в отчете
     * @param modelName наименование товара
     * @param price цена товара (опционально для данного ТК)
     * Тело метода представляет собой проверку на соответствие фильтрам по производителю и модели
     */
    @Step("Проверка товара на странице {pageNumber}: наименование: {modelName}, цена : {price}")
    public void checkProduct(int pageNumber, String modelName, Integer price) {
        Assertions.assertTrue(checkManufacturer(modelName),
                        String.format("Товар %s не соответствует фильтру по производителю: %s и модели %s",
                                modelName,
                                filterProperties.filterManufacturer(),
                                filterProperties.filterModelName()));
    }
}
