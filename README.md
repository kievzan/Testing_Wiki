# Авто тесты веб и мобильного приложений

## Проект
Проект содержит автоматизированные тесты для:
1. Веб-сайта ru.wikipedia.org
2. Мобильного приложения Wikipedia

Проект использует технологии:
- Java
- Maven
- Selenium WebDriver
- Appium
- TestNG

## Требования
1. Java + Maven
2. к выполнению мобильных тестов:
    - Appium: `npm install -g appium`
    - Android Studio
    - эмулятор Android
    - приложение Wikipedia на эмуляторе
3. Chrome браузер

## Запуск тестов
```bash
mvn clean test
```
1. Веб-тесты (сайт ru.wikipedia.org)
```bash
mvn test -Dtest=WikipediaTests
```
2. Мобильные тесты (Wikipedia)

Приложение скачать [app-alpha-universal-release.apk](https://github.com/wikimedia/apps-android-wikipedia/releases/tag/latest)
- Запуск Android эмулятора
    ```bash
    emulator -avd Pixel_5
    ```
 
- Загрузка мобильного приложения на эмулятор с помощью Android Debug Bridge (входит в состав Android SDK Platform Tools)
```bash
adb -s emulator-5554 install app-alpha-universal-release.apk
```
- Запуск Appium сервера на порту 4723
    ```bash
    appium -p 4723
    ```
- Запуск тестов
```bash
mvn test -Dtest=WikipediaMobileTests
```



