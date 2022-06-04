# Exchange-rate-gif
Тестовый проект, который обращается к сервису курсов валют, и отображает gif


Task description
-------------------------

Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
- если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
- если ниже - отсюда https://giphy.com/search/broke

Ссылки
- REST API курсов валют - https://docs.openexchangerates.org/
- REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide

Must Have
- Сервис на Spring Boot 2 + Java / Kotlin
- Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
- Для взаимодействия с внешними сервисами используется Feign
- Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
- На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
- Для сборки должен использоваться Gradle
- Результатом выполнения должен быть репо на GitHub с инструкцией по запуску

Nice to Have
- Сборка и запуск Docker контейнера с этим сервисом

Endpoint
-------------------------

### Request
#### Get gif by rate
`GET /api/rate/{code}`

    curl http://localhost:8080/api/rate/EUR

### Response

    HTTP/1.1 200 
    Connection: keep-alive
    Content-type: application/json
    Date: Sat, 04 Jun 2022 19:43:28 GMT
    Keep-alive: timeout=60
    Transfer-encoding: chunked

    {
        "data": {
            "type": "gif",
            "id": "hvY9Jpqu5w3GfyBUjd",
            "url": "https://giphy.com/gifs/AdVentureCapitalistHH-mansion-adcap-adventure-capitalist-hvY9Jpqu5w3GfyBUjd",
            "slug": "AdVentureCapitalistHH-mansion-adcap-adventure-capitalist-hvY9Jpqu5w3GfyBUjd",
            "bitly_gif_url": "https://gph.is/g/4oNKJ7Y",
            "bitly_url": "https://gph.is/g/4oNKJ7Y",
            "embed_url": "https://giphy.com/embed/hvY9Jpqu5w3GfyBUjd",
            "username": "AdVentureCapitalistHH",
            ...........
        }
    }


Run Application
-------------------------

```bash
./gradlew bootRun
```

Docker
-------------------------

Build executable jar file:
```bash
./gradlew build
```

Build Docker Image:
```bash
docker build -t exchange-rate-gif .
```

Run Docker container using the image built:
```bash
docker run exchange-rate-gif
```