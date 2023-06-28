# LicMon

Простой проект на Spring Boot для просмотра активированных на сервере лицензий 1С

## Подготовка

Для сборки проекта необходимо скачать и установить утилиту EnterpriseLicenseTools версии 0.15.0.2

## Сборка 

1. Клонировать репозиторий
```git clone https://github.com/marictheirin/licmon.git```

2. Изменить текущий каталог на корень проекта
```cd licmon```

3. Скопировать зависимости из установленной утилиты Ring 
```
cp "C:\Program Files\1C\1CE\components\1c-enterprise-license-tools-0.15.0+2-x86_64\com._1c.license.activator.crypt-0.15.0-2.jar" "ext_libs"  
cp "C:\Program Files\1C\1CE\components\1c-enterprise-license-tools-0.15.0+2-x86_64\com._1c.license.activator.data-0.15.0-2.jar" "ext_libs"  
cp "C:\Program Files\1C\1CE\components\1c-enterprise-license-tools-0.15.0+2-x86_64\com._1c.license.activator.hard-0.15.0-2.jar" "ext_libs"  
cp "C:\Program Files\1C\1CE\components\1c-enterprise-license-tools-0.15.0+2-x86_64\com._1c.v8.core.streams-0.4.0-3.jar" "ext_libs"  
```
4. Собрать проект при помощи Maven
```./mvn clean install ```

## Запуск

Запуск проекта осуществляется при помощи команды 
```mvn spring-boot:run```

Приложение будет запущено по стандартному адресу ```http://localhost:8080/licenses```

## Список эндпоинтов

### Получить перечень лицензий (GET-Запрос)
* http://localhost:8080/licenses

### Получить данные конкретной лицензии (GET-Запрос)
* http://localhost:8080/licenses/{id}  
где id - серийный номер лицензии
