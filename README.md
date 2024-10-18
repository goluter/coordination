
# 구현범위
- 브랜드 생성, 수정, 삭제, 조회 API 구현 (/brands)
- 상품 생성, 수정, 삭제, 조회 API 구현 (/products)
- 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API 구현(/lowest-prices/category)
- 단일 브랜드 최저가 API 구현(/lowest-prices)
- 단일 브랜드 최저가 API 구현(/lowest-prices)

# 미리보기


# 구조
- config: 라이브러리 설정
- controllers: API 컨트롤러
- services: 도메인 구현 서비스, 도메인 모델 별로 디렉토리 구성


# 사용 기술
- **Kotlin** 1.9.25
- **Spring Boot** 3.3.4
- **JPA** with Hibernate
- **QueryDSL** 5.0.0
- **Kotlin Coroutines** 1.8.0
- **H2** 데이터베이스

# 코드 빌드, 테스트, 실행 방법
### API 빌드, 실행
```
# docker-compose up -d 
$ docker compose up -d 
```

### 프론트 빌드, 실행
```
$ cd frontend
# yarn install 
$ yarn run dev

# yarn build
# yarn run start
```

# 기타 추가 정보
### DB 접속 정보
H2: http://${host}/h2-console/login.do?jsessionid=dc11542008d4db3032b19b41a02d43e3
username: codi
password: password