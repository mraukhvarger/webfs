# webfs
Сервис для доступа к файловой системе через web-интерфейс. Проект выполнен в качестве задания для OTUS.

## Подробнее

Сервис представляет собой файловый менеджер, доступный из браузера. Поддерживается загрузка и выгрузка, открытие, 
редактирование и сохренение файлов. Расширяемость реализуется с помощью кастомной аннотации @FileViewer(extension = "txt"),
которая автоматически определяет тип файлов и позволяет открыть их в отдельной вкладке.

## Сборка

Для запуска в режиме отладки надо выполнить команду **mvn install**. Во время сборки, появится сообщение об отсутствии 
node.js (он используется как менеджер js-пакетов). В логе появится mvn-команда такого вида: 
**mvn com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm -DnodeVersion="v10.16.0"**, которую надо 
будет выполнить. После этого проект запускается из Идеи как обычно.

Для сборки в production-режиме необходимо выставить плагин **production**.

**mvn dockerfile:build** создаст докер-образ.

## Параметры окружающей среды

**START_DIR** - Корневой каталог, который будет доступен из браузера.

## Планы на будущее

- Подключение MongoDB
- Создание админки с пользователями и ролями (с read/write доступом и корневой директорией)
- Добавление расширения для просмотра логов
- Добавление расширений просмотрщика файлов для видео, аудио и фото-контента
- Список избранных файлов, привязанных к пользователю
- Закладки, для запоминания позиции открытого файла (любого типа)
- Комментарии для файлов
