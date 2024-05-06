# AA BE (SpringBoot) Base Template


### 0. pre-requisite
* docker 설치
* docker-compose 설치
***

### 1. 애플리케이션 구성 ( _TBD_ )
* Framework : Spring Boot 3.11
* openjdk : 17
* Build Tool : gradle
* DB : mysql ( local - docker container)
* Code Formatting : Spotless
* logging : Slf4j
* API Docs : Swagger
* 기타 lib : lombok
* ...
***

### 2. local DB 구성
DB password는 프로젝트 root 디렉토리 하위 `docker-compose.yaml 파일` 참고
```bash
# mysql docker container 실행
docker-compose up -d

# mysql docker container 중지
docker-compose down
```
***

### 3. 프로젝트 빌드 및 실행
```bash
# 코드 포맷팅
./gradlew spotlessApply

# 프로젝트 빌드 (코드 포맷팅 포함)
./gradlew clean build

# 프로젝트 실행
./gradlew bootRun
```
***

### 4. API 명세 (Swagger)
[Swagger Link] (http://localhost:8080/swagger-ui.html)
