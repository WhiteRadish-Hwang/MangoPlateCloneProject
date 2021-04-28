# MangoPlate APP Clone Project

## :melon: MangoPlate Clone project에 사용된 백엔드 코드 저장소 입니다.

---
## 🏠  팀원
* [(Radi) 황상연](https://github.com/WhiteRadish-Hwang) : 프론트엔드 개발
* [(Lake) 황성준](https://github.com/sungjun1116) : 백엔드 개발
* [(Andrew)이유상](https://github.com/liyusang1) : 백엔드 개발
---
## 🛠 개발 환경
>AWS EC2에서 Node를 통해 서버를 구동하고 AWS RDS를 사용.

<img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fvelog.io%2F%40dogakday%2FKotlin-%25EC%25BD%2594%25ED%258B%2580%25EB%25A6%25B0%25EC%259D%2580-%25EB%25AC%25B4%25EC%2597%2587%25EC%259D%25B8%25EA%25B0%2580&psig=AOvVaw1yhpTvpSAJmZHtulH-9bfx&ust=1619679413985000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKD0pZOuoPACFQAAAAAdAAAAABAD" width="250" height="100"><img src="https://www.google.com/url?sa=i&url=https%3A%2F%2Fmedium.com%2Fandroid-news%2Fandroid-mvp-for-beginners-25889c500443&psig=AOvVaw3rpbblmOE48NHnni31OqHw&ust=1619679460677000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCKC0n6quoPACFQAAAAAdAAAAABAD" width="200" height="100">


---

```text
소프트스퀘어드 노드 템플릿은 Node, Express Framwork, MVC (Route, Controller)으로 구성되어있고,
데이터베이스 모듈(= 라이브러리)는 mysql2 을 사용하여 DB와 통신하고있다. 설정파일은 /config/database.js 에 있다.

그리고 winston 이라는 모듈와 winston-daily-rotate-file 이라는 모듈 사용하여 Logger (=/config/winston.js) 를 구축해놓았다.
Firebase나 토큰이나 누군가에게 공개해선 안되는 키값들은 /config/secret.js 라는 곳에 모아놓고있다.

jwt 는 /config/jwtMiddleware.js 에서 검증을 jwtMiddleware 라는 자체모듈로 만들어서 사용하고있다. 이거는 route 파일에서 체이닝 방식으로 사용하고있다. (예제는 /app/routes/* 에 있는 파일을 참고하면 된다.)

express 는 /config/express.js 에 설정 값들이 모여있다. 
기본 설정들은 해놓았는데 필요한 설정이 있다면 이 파일로 가서 추가/수정/삭제를 하면 된다.
```

---
## :calendar: 프로젝트 기간

```
2021-02-03 ~ 2021-02-17
```


## :movie_camera: 시연 영상
> https://www.youtube.com/watch?v=DVkmQm3k7Lw
---
# :iphone: 실행 화면


### 로그인

<img src=image/5.jpg width="260" height="500">  

---

### 음식점 찾기

<img src=image/6.jpg width="260" height="500"> <img src=image/7.jpg width="260" height="500">

---

### 음식점 상세조회


<img src=image/n3.jpg width="260" height="500"> <img src=image/n2.jpg width="260" height="500"> <img src=image/n1.jpg width="260" height="500"> <img src=image/17.jpg width="260" height="500">

---
### 소식

<img src=image/1.jpg width="260" height="500"> <img src=image/3.jpg width="260" height="500">

---

### EAT DEAL

<img src=image/18.jpg width="260" height="500"> <img src=image/19.jpg width="260" height="500"> <img src=image/t1.jpg width="260" height="500">

---

### 식당 등록

<img src=image/14.jpg width="260" height="500"> <img src=image/15.jpg width="260" height="500">

---

### TOP List

<img src=image/16.jpg width="260" height="500">

---

### 내 정보

<img src=image/2.jpg width="260" height="500"> <img src=image/img.png width="260" height="500">

---

## :warning: License

본 템플릿은 소프트스퀘어드에 소유권이 있으며 본 자료에 대한 상업적 이용 및 무단 복제,배포 및 변경을 원칙적으로 금지하며 이를 위반할 때에는 형사처벌을 받을 수 있습니다.
