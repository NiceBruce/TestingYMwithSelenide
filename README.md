# **_ТЕСТИРОВАНИЕ YM используя Selenide_** 🚲
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![JUNIT5](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Selenium](https://img.shields.io/badge/-selenium-%43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![Google Chrome](https://img.shields.io/badge/Google%20Chrome-4285F4?style=for-the-badge&logo=GoogleChrome&logoColor=white)
![Firefox](https://img.shields.io/badge/Firefox-FF7139?style=for-the-badge&logo=Firefox-Browser&logoColor=white)



---
    Суть задания заключается в написании автотеста по следующему тест-кейсу:

1. Открыть браузер и развернуть на весь экран. Перейти на https://market.yandex.ru/
2. Перейти в Каталог
3. Навести курсор на раздел "Электроника"
4. Перейти в раздел "Смартфоны"
5. Убедится что вы перешли в раздел “Смартфоны”
6. Задать параметр «Производитель» Apple.
7. Дождаться результатов поиска.
8. Убедится что в выборку попали только iPhone. Если страниц несколько – проверить все.
9. Тест должен работать для любого производителя.



##  Запуск тестов и отчетов allure
- Необходимо выполнить следующие команды: `mvn test`
  `mvn allure:serve`
