package utils;

import io.qameta.allure.Step;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;

/**
 *   Данный класс необходим для переопределения Assertions org.junit.jupiter
 *   <br/> Каждый из методов представляет STEP - Allure
 */
public class Assertions {

    /**
     * Переопределение базового Assertions org.junit.jupiter - assertTrue,
     * используемого в проверках.
     */
    @Step("Тест пройден, отсутствует сообщение: {message}")
    public static void assertTrue(boolean condition, String message) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

    /**
     * Метод-обертка для "SOFT-ASSERTIONS"
     * Переопределение Assertions org.junit.jupiter - assertAll,
     * используемого в массовых проверках, содержащих вложенные Assertions.assertTrue.
     */
    @Step("Проверка группы тестов")
    public static void assertAll(Executable... executables) {
        org.junit.jupiter.api.Assertions.assertAll(executables);
    }

    /**
     * Метод-обертка для "SOFT-ASSERTIONS"
     * Переопределение Assertions org.junit.jupiter - assertAll,
     * используемого в массовых проверках, содержащих вложенные Assertions.assertTrue.
     * В данном случае перегруженный метод assertAll принимает коллекцию executables,
     * содержащих в себе вложенные проверки результатов проверки.
     */
    @Step("Проверка всех страниц с представленными результатами")
    public static void assertAll(Collection<Executable> executables) {
        org.junit.jupiter.api.Assertions.assertAll(executables);
    }
}
