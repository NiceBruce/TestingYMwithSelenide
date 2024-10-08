package utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static utils.Properties.driversProperties;
import static utils.Properties.filterProperties;


/**
 *   Данный класс предоставляет набор данных для тестов маркетплейса market.yandex.ru
 *   @author Борис Демин
 */
public class DataProvider {

    /**
     * Метод, цель которого предоставить готовый набор данных для параметризированного-теста.
     * @return стрим, содержащий набор тестовых данных
     */
    public static Stream<Arguments> providerProductSelection(){
        return Stream.of(
                Arguments.of(
                        driversProperties.marketYandexUrl(),
                        "Электроника",
                        "Смартфоны",
                        filterProperties.filterManufacturer()
                        )
        );
    }
}
