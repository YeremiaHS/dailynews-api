# Daily-news API
REST API Daily-news

## Run API
```.\mvnw spring-boot:run```

## Service methods
- Level Login:
    - **Login User**: POST - http://localhost:8080/user/login
    - **Login Admin**: POST - http://localhost:8080/admin/login
    - **Login Creator**: POST - http://localhost:8080/creator/login

- Level Register:
    - **Register User**: POST - http://localhost:8080/user/register
    - **Register Admin**: POST - http://localhost:8080/admin/register
    - **Register Creator**: POST - http://localhost:8080/creator/register

- News:
    - **Create News**: POST - http://localhost:8080/news/add
    - **Show All News**: GET - http://localhost:8080/news/all
    - **Read News**: GET - http://localhost:8080/news/read/{id}
    - **Show Recent News**: GET - http://localhost:8080/news/recent

- Comment:
    - **Add Comment**: POST - http://localhost:8080/comment/add
    - **Show Comment**: POST - http://localhost:8080/news/coment/{id}

- Image:
    - **Store Image**: POST - http://localhost:8080/files/add
    - **Load Image**: POST - http://localhost:8080/files/news/{id}


## Documentation Online
**https://documenter.getpostman.com/view/29586181/2s9YBz2umz**