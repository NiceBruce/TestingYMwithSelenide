package utils;

import static utils.Properties.filterProperties;

/**
 *   Данный класс содержит дополнительные методы, необходимые для работы других классов.
 *   UPD. Некоторые методы пригодятся в будущем.
 *   @author Борис Демин
 */
public class Util {

    /**
     * Метод, проверяющий, есть ли в текстовой строке символы кириллицы.
     * UPD может быть актуально, так как в наименовании товаров в результатах поисковой выдачи - HP, LENOVO
     * присутствовали символы кириллицы. Из-за чего тесты могут не проходить, хотя наглядно LENOVO как LENOVO,
     * HP как HP, но есть символы кириллицы. Как внедрить пока не придумал, но метод накатал
     * @param modelName наименование модели
     * @return булевое значение - наличие кириллицы
     */
    public static boolean isContainsCyrillic(String modelName) {
        return modelName.matches("[а-яА-я]");
    }

    /**
     * Метод, проверяющий соответствие название модели - наименованию производителя.
     * @param modelName наименование модели
     * @return булевое значение соответствие название модели товара - наименованию необходимой модели производителя
     */
    public static boolean checkManufacturer(String modelName) {
        return modelName.toLowerCase().contains(filterProperties.filterModelName().toLowerCase());
    }

    /**
     * Метод, проверяющий соответствие цены модели - фильтрам по "цене ОТ" и "цене ДО".
     * @param price целочисленное значение цены модели
     * @return булевое значение соответствие цены модели - фильтрам по "цене ОТ" и "цене ДО"
     */
    public static boolean checkPriceValue(Integer price) {
        return (price >= filterProperties.filterPriceFrom()) & (price <= filterProperties.filterPriceTo());
    }
}
