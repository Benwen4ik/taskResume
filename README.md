## Resume
 Rest сервис с фронт-частью, который принимает резюме от пользователей
## Установка
  1. Клонирование репозитория 
  
  ``` git clone https://github.com/Benwen4ik/taskResume.git ```
  
  2. Изменение конфигурационных данных в application.properties
  
  ``` По умолчанию port = 8081 ```
  ``` username=postgres ```
  ``` password=postgres ```
  
  3. Создание бд в PostgreSQL
  
  ```   По умолчанию название бд в application.propertes "resumeTaskDB" ```
  
  4. Запуск команд в init.sql для создания и инициализации таблиц бд
## Документация
Форма для создания резюме находится по адресу http://localhost:8081/view/form 

Таблица с резюме находится по адресу http://localhost:8081/view/resumes

REST контроллер принимает запросы по URL http://localhost:8081/api/v1/resume/
1. /api/v1/resume/add - POST запрос для сохранения резюме
   
```
Например {
    "fullName" : "Иванов Иван Иванавич" ,
    "prevCompany": "TechIT",
    "level": "Junior",
    "position": "Java разработчик",
    "salary": 1500,
    "description": "Умею все",
    "skills": [11, 14,7]
}
```

2.  /api/v1/resume/{Category}  GET-запрос для получения списка резюме по указанной категории
3.  /api/v1/resume?id={id}  GET-запрос для получения резюме по его идентификатору
4.  /api/v1/resume/all GET-запрос для получения списка всех резюме
5.  /api/v1/resume/decide POST-запрос для изменения статуса решения резюме
```
Например {
   "id" : 2,
   "decide" : "SendTestTask"
}
```
