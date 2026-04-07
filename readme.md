# Task Manager API

REST API для управления задачами, построенный на Spring Boot 3 + PostgreSQL.

## Стек

- **Java 21**
- **Spring Boot** (Web MVC, Data JPA, Validation)
- **PostgreSQL**
- **Maven**

## Запуск

### Требования

- Java 21+
- PostgreSQL (запущенный инстанс)
- Maven или использовать встроенный `./mvnw`

### Конфигурация

Создайте файл `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Сборка и запуск

```bash
./mvnw spring-boot:run
```

Приложение запустится на `http://localhost:8080`.

---

## API

### Задачи

| Метод | Эндпоинт | Описание |
|-------|----------|----------|
| `GET` | `/task` | Получить все задачи |
| `GET` | `/task/{id}` | Получить задачу по ID |
| `POST` | `/task` | Создать задачу |
| `PUT` | `/task/{id}` | Обновить задачу |
| `DELETE` | `/task/{id}` | Удалить задачу |
| `POST` | `/task/{id}/start` | Перевести задачу в статус `IN_PROGRESS` |
| `POST` | `/task/{id}/complite` | Перевести задачу в статус `DONE` |

### Модель задачи

```json
{
  "creatorId": 1,
  "assignedUserId": 2,
  "deadLineTime": "2025-12-31",
  "priority": "HIGH"
}
```

| Поле | Тип | Обязательное | Описание |
|------|-----|:---:|----------|
| `creatorId` | `Long` | ✅ | ID создателя |
| `assignedUserId` | `Long` | ❌ | ID исполнителя |
| `deadLineTime` | `LocalDate` | ✅ | Дедлайн (сегодня или позже) |
| `priority` | `enum` | ✅ | `LOW`, `MEDIUM`, `HIGH` |

### Статусы задачи

```
CREATED → IN_PROGRESS → DONE
```

- Задача создаётся со статусом `CREATED`
- Перевести в `IN_PROGRESS` можно только если назначен исполнитель и у него менее 5 активных задач
- Удалить можно только задачу в статусе `CREATED`

### Приоритеты

| Значение | Описание |
|----------|----------|
| `LOW` | Низкий |
| `MEDIUM` | Средний |
| `HIGH` | Высокий |

---

## Обработка ошибок

Все ошибки возвращаются в едином формате:

```json
{
  "message": "Not found entity",
  "detailedMessage": "No found task by id=42",
  "errorTime": "2025-04-07T12:00:00"
}
```

| HTTP-статус | Причина |
|-------------|---------|
| `400` | Невалидные данные или нарушение бизнес-логики |
| `404` | Задача не найдена |
| `500` | Внутренняя ошибка сервера |