## 소셜 미디어 통합 Feed 서비스

> 💡 유저 계정의 해시태그(”#dani”) 를 기반으로 `인스타그램`, `스레드`, `페이스북`, `트위터` 등 복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 통합 Feed 어플리케이션 입니다.

<br/>

## 디렉토리 구조

```jsx
➜  pre_onboarding git:(develop) ✗ tree .

└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── wanted
    │   │           ├── common
    │   │           │   ├── config
    │   │           │   │   └── property
    │   │           │   ├── dto
    │   │           │   ├── exception
    │   │           │   ├── redis
    │   │           │   │   └── repository
    │   │           │   └── security
    │   │           │       ├── controller
    │   │           │       ├── dto
    │   │           │       ├── enums
    │   │           │       ├── exception
    │   │           │       ├── filter
    │   │           │       ├── handler
    │   │           │       ├── service
    │   │           │       ├── utils
    │   │           │       └── vo
    │   │           ├── content
    │   │           │   ├── controller
    │   │           │   ├── dto
    │   │           │   │   └── response
    │   │           │   ├── entity
    │   │           │   ├── enums
    │   │           │   ├── repository
    │   │           │   ├── service
    │   │           │   └── validation
    │   │           │       └── annotation
    │   │           ├── external
    │   │           │   ├── info
    │   │           │   │   └── impl
    │   │           │   └── service
    │   │           ├── hashtag
    │   │           │   ├── entity
    │   │           │   └── repository
    │   │           └── user
    │   │               ├── controller
    │   │               ├── dto
    │   │               │   ├── request
    │   │               │   └── response
    │   │               ├── entity
    │   │               ├── mapper
    │   │               ├── repository
    │   │               ├── service
    │   │               ├── utils
    │   │               └── validation
    │   │                   ├── annotation
    │   │                   ├── utils
    │   │                   └── validator
    │   └── resources

```

- 각 도메인별로 패키지를 나눈에 뒤 각 도메인에 controller, dto, entity, enums, repository, service 하위 패키지를 두고, 그 안에서 작업합니다.
- common 패키지 내에는 공통으로 사용하는 config, dto(ex. 공통 response), exception 등과 security 패키지를 두었음.

<br/>

## 역할 분배

<table>
  <tbody>
    <tr><th colspan="5">백엔드</th></tr>
    <tr>
      <td align="center"><a href="https://github.com/ffolabear"><img src="https://avatars.githubusercontent.com/u/65614734?v=4" width="130px;" alt=""/><br /><sub><b>김태현(팀장)</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/jkde7721"><img src="https://avatars.githubusercontent.com/u/65665065?v=4"
      width="130px;" alt=""/><br /><sub><b>김다은</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/kho903"><img src="https://avatars.githubusercontent.com/u/52434805?v=4" width="130px;" alt=""/><br /><sub><b>김지훈</b></sub></a><br /></td>
       <td align="center"><a href="https://github.com/Byeonghee-son"><img src="https://avatars.githubusercontent.com/u/96256807?v=4" width="130px;" alt=""/><br /><sub><b>손병희</b></sub></a><br /></td>     
      <td align="center"><a href="https://github.com/LEEGIHO94"><img src="https://avatars.githubusercontent.com/u/116015708?v=4" width="130px;" alt=""/><br /><sub><b>이기호</b></sub></a><br /></td>
    </tr>
    <tr>
       <td align="center"><sub><b>통계 API</b></sub></td>
       <td align="center"><sub><b>게시물 좋아요, 공유 API</b></sub></td>
       <td align="center"><sub><b>게시물 목록 조회, 상세 조회 API</b></sub></td>
       <td align="center"><sub><b>인증 이메일 API</b></sub></td>
       <td align="center"><sub><b>회원가입, 로그인 API</b></sub></td>
    </tr>
    <tr></tr>
  </tbody>
</table>
<br/>

## 프로젝트 실행 방법

1. 프로젝트 Clone

```shell
git clone https://github.com/wanted-pre-onboarding-7th-backend-T/social-media-feed-api-service.git
```

2. 프로젝트 파일 이동

```shell
cd social-media-feed-api-service
```

3. 프로젝트 실행

```shell
./gradlew build
java -jar build/libs/pre_onboarding-0.0.1-SNAPSHOT.jar
```

<br/>

## Skills

<div>
  <img src="https://img.shields.io/badge/Springboot.3.1.5-80EA62?style=flat-square&logo=Springboot&logoColor=black"/>
  <img src="https://img.shields.io/badge/Spring DATA JPA-80EA62?style=flat-square&logo=Spring&logoColor=black"/>
  <img src="https://img.shields.io/badge/SpringSecurity6.1.5-80EA62?style=flat-square&logo=Spring&logoColor=black"/>
</div>

<div>
  <img src="https://img.shields.io/badge/REDIS-dc382c?style=flat-square&logo=REDIS&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-764ABC?style=flat-square&logo=MySQL&logoColor=white">
  <img src="https://img.shields.io/badge/QueryDSL-764ABC?style=flat-square&logo=QueryDSL&logoColor=white">
</div>

<div>
  <img src="https://img.shields.io/badge/JWT-000000?style=flat-square&logo=JWT&logoColor=white"/>
  <img src="https://img.shields.io/badge/junit5-25A162?style=flat-square&logo=junit5&logoColor=white"/>
</div>

<br/>

## 요구사항 분석

요구사항 : [요구사항 링크](https://www.notion.so/938175f1165b470e90462d1f1d52fd78?pvs=21)

<br/>

### 로그인 시 사용할 ID email VS username

email 과 username 중 어떤 값을 로그인 시 ID로 사용하는 지 결정할 필요 존재

- 요구사항 정의서에는 해당 부분에 대한 내용의 모호성 존재
- 요구사항의 의도파악을 위한 관리자와의 대화를 통해 같은 email로 여러개의 계정 생성가능하며 username은 고유 값을 이용한다는 정보를 얻어 username을 ID로 사용

<br/>

### 가입 로직에서 가입 → 승인 로직 분석

- 요구사항을 보면 email, password 등의 정보를 먼저 입력한 뒤 가입 승인을 할 때 다시 email, password 등의 정보와 email에 전달된 token 값을 추가 전달하도록 되어 있음
- 요구사항 마지막단 계정, 비밀번호, 인증 코드 입력 시 가입 완료 된다는 내용을 바탕으로
  email 전송 → 인증 코드 전달 → 전달된 인증 코드 포함해 회원 가입 기능 구현

<br/>

### 공통 비밀번호 선정 기준

- 2023년 가장 자주 사용한 비밀번호 30개 중 다른 validation을 만족하는 공통 비밀번호들로 선정
    - 비밀번호 길이 10자리 이상
    - 2가지 이상의 서로 다른 종류의 문자
    - 3회 이하의 연속되는 문자 사용
- 참조 링크 : [공통 비밀번호 참조 링크 2023.06.23](https://www.beckershospitalreview.com/cybersecurity/30-most-common-passwords-of-2023.html)

<br/>

### WebFlux 사용 이유

- 스프링에서 외부 API를 호출하기 위해 RestTemplate 또는 WebClient 사용 가능
- 스프링 [공식문서](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)에 따르면 현재 RestTemplate은 유지 관리 모드로 WebClient 사용을 권장하고 있어 WebClient를 사용 `implementation 'org.springframework.boot:spring-boot-starter-webflux'`
<details>
  <summary>외부 API 호출을 테스트하기 위해 MockWebServer를 사용 <code>testImplementation 'com.squareup.okhttp3:mockwebserver'</code></summary><br/>

  ```java
    static MockWebServer mockWebServer;
    
    @BeforeAll
    static void initAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }
    
    @BeforeEach
    void init() {
        baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
    }
    
    @AfterAll
    static void tearDownAll() throws IOException {
        mockWebServer.shutdown();
    }
    
    @Test
    void mockWebServertest() {
    	mockWebServer.enqueue(new MockResponse()
                  .setBody("")
    	            .setHeader("Content-Type", "application/json"));
    }
  ```

- 테스트 라이프 사이클에 따라 `mockWebServer` 객체를 초기화
- `mockWebServer`의 내부 큐에 `MockResponse` 객체를 저장
- 요청마다 큐의 `MockResponse` 객체가 pop되어 반환

</details>

<br/>

### 요구 사항 통계의 동적 쿼리 구현을 위한 QueryDSL 구현

- 동적 쿼리의 손쉬운 사용을 위한 QueryDSL 구현

<br/>

### DB다이어그램 구현

- Like의 경우는 외부 SNS의 게시물을 호출 해서 Like를 증가 시키기 때문에 Like 테이블을 따로 두지 않고 content에 필드로 넣어 사용

<br/>

## ERD

ERD Link :  https://www.erdcloud.com/d/52GPYthzDmgicoPrS

![image](https://github.com/wanted-pre-onboarding-7th-backend-T/social-media-feed-api-service/assets/65665065/6543b41f-0432-42c1-b08e-f183188263d0)

<br/>

## API Reference

<details>
<summary><strong>사용자 등록 [POST] /api/users</strong></summary>
<div markdown="1">

- Valid
    - 비밀번호 최소 길이 10자 이상
    - 통상적으로 자주 사용되는 비밀번호 사용 불가
    - Password 종류 (통상적으로 사용하는 비밀번호 선정 기준)

      ```text
      abc123
      password1
      1q2w3e4r
      qwerty123
      zaq12wsx
      qaz2wsx
      p@ssw0rd!!
      ```

      - 숫자, 문자, 특수문자 중 2가지 이상 포함
      - 3회 이상 연속되는 문자 사용 불가능

- Stub
    ``` json
    Request
    POST /api/users
    {
      "username":"test001",
      "email":"test001@gmail.com",
      "password" :"qwer123!!" 
    }
    
    Response
    {
      "userId":1
    }
    ```
</div>
</details>

<details>
<summary><strong>게시물 목록 조회 [GET] /api/contents</strong></summary>
<div markdown="1">

- 요청의 쿼리 파라미터에 따라 feed 게시물 목록을 조회하는 API

  <details>
    <summary><strong>쿼리 파라미터 목록</strong></summary>
  
    | query | 속성 | default(미입력 시 값) | 설명 |
    | --- | --- | --- | --- |
    | hashtag | string | 본인계정 | 맛집, 성수동 등 1건의 해시태그 이며, 정확히 일치하는 값(Exact)만 검색합니다. |
    | type | string |  | 게시물의 type 필드 값 별로 조회가 됩니다. 미입력 시 모든 type 이 조회됩니다. |
    | order_by | string | created_at | 정렬순서이며, created_at,updated_at,like_count,share_count,view_count 가 사용 가능합니다.
    오름차순 , 내림차순 모두 가능하게 구현 |
    | search_by | string | title,content | 검색 기준이며, title , content, title,content 이 사용 가능합니다. 각각 제목, 내용, 제목 + 내용 에 해당합니다. |
    | search | string |  | search_by 에서 검색할 키워드 이며 유저가 입력합니다. 해당 문자가 포함된 게시물을 검색합니다. |
    | page_count | number | 10 | 페이지당 게시물 갯수를 지정합니다. |
    | page | number | 0 | 조회하려는 페이지를 지정합니다. |

  </details> 
  
- 검색 조건에 일치하는 게시물이 없으면 빈 리스트가 조회
- 조회되는 게시물 데이터는 `식별자`, `SNS 별 식별자`, `SNS 타입`, `제목`, `내용`, `해시태그 목록`, `조회수`, `좋아요수`, `공유하기수`, `작성일`, `수정일` 포함
- 페이징 처리로 일부 게시물 목록만 조회

- Stub
    ```json
    Reqeust
    GET /api/contents
    
    Response
    {
      "data": {
        "pagingUtil": {
          "totalElements": 16,
          "totalPages": 2,
          "pageNumber": 1,
          "pageSize": 10,
          "totalPageGroups": 1,
          "pageGroupSize": 5,
          "pageGroup": 1,
          "startPage": 1,
          "endPage": 2,
          "existPrePageGroup": false,
          "existNextPageGroup": false
        },
        "content": [{
          "content_id" : 1,
          "type": "facebook",
          "title": "게시글 제목입니다.",
          "content": "게시글 내용입니다.",
          "hashtags": [
            "해시태그1", "해시태그2", "해시태그3"
          ],
          "view_count": 1,
          "like_count": 2,
          "share_count": 3,
          "updated_at": "2023-11-02 13:15:11",
          "created_at": "2023-11-02 13:15:11"
        }]
      },
      "message":"OK",
      "code" : 200,
      "timeStamp":"2023-11-02 13:15:11"
    }
    ```
</div>
</details>

<details>
<summary><strong>게시물 상세 조회 [GET] /api/contents/{contentId}</strong></summary>
<div markdown="1">

- 게시물 식별자로 특정 게시물 조회하는 API
- 조회되는 게시물 데이터는 `식별자`, `SNS 별 식별자`, `SNS 타입`, `제목`, `내용`, `해시태그 목록`, `조회수`, `좋아요수`, `공유하기수`, `작성일`, `수정일` 포함
- API 호출 시 해당 게시물의 `조회수` 1 증가

- Stub
    ``` json
    Reqeust
    GET /api/contents/{contentsId}
    
    Response
    {
      "data" : {
        "content_id" : 1,
        "content_sns_id" : "sns 아이디",
        "type": "facebook",
        "title": "게시글 제목입니다.",
        "content": "게시글 내용입니다.",
        "hashtags": [
          "해시태그1", "해시태그2", "해시태그3"
        ],
        "view_count": 1,
        "like_count": 2,
        "share_count": 3,
        "updated_at": "2023-11-02 13:15:11",
        "created_at": "2023-11-02 13:15:11"
      },
      "message":"OK",
      "code" : 200,
      "timeStamp":"2023-11-02 13:15:11"
    }
    ```
</div>
</details>

<details>
<summary><strong>게시물 좋아요 [POST] /api/contents/{contentId}/likes</strong></summary>
<div markdown="1">

- 게시물 식별자로 특정 게시물을 좋아요 하는 API
- 좋아요 클릭 시 각 SNS 별 외부 API 동기 호출
- 만약 외부 API 호출에 실패하면 좋아요 개수가 증가되지 않고 기존 좋아요 개수 반환
  
  <details>
    <summary><strong>외부 API 목록</strong></summary><br/>

    | type | method | endpoint |
    | --- | --- | --- |
    | facebook | POST | https://www.facebook.com/likes/<content_id> |
    | twitter | POST | https://www.twitter.com/likes/<content_id> |
    | instagram | POST | https://www.instagram.com/likes/<content_id> |
    | threads | POST | https://www.threads.net/likes/<content_id> |

  </details>

- Stub
    ``` json
    Reqeust
    POST /api/contents/1/likes
    
    Response
    {
      "data": {
        "contentId": 1,
        "likeCount": 10
      },
      "message": "OK",
      "code": 200,
      "timeStamp":"2023-11-02 13:15:11"
    }
    ```
</div>
</details>

<details>
<summary><strong>게시물 공유 [POST] /api/contents/{contentId}/share</strong></summary>
<div markdown="1">

- 게시물 식별자로 특정 게시물을 공유하는 API
- 공유하기 클릭 시 각 SNS 별 외부 API 동기 호출
- 만약 외부 API 호출에 실패하면 공유하기 개수가 증가되지 않고 기존 공유하기 개수 반환

  <details>
    <summary><strong>외부 API 목록</strong></summary><br/>

    | type | method | endpoint |
    | --- | --- | --- |
    | facebook | POST | https://www.facebook.com/share/<content_id> |
    | twitter | POST | https://www.twitter.com/share/<content_id> |
    | instagram | POST | https://www.instagram.com/share/<content_id> |
    | threads | POST | https://www.threads.net/share/<content_id> |

  </details>

- Stub
    ``` json
    Reqeust
    POST /api/contents/1/likes
    
    Response
    {
      "data": {
        "contentId": 1,
        "shareCount": 10
      },
      "message": "OK",
      "code": 200,
      "timeStamp":"2023-11-02 13:15:11"
    }
    ```
</div>
</details>

<details>
<summary><strong>게시물 통계 [GET] /api/status</strong></summary>
<div markdown="1">

- 요청의 쿼리 파라미터를 통해 게시물의 통계를 조회하는 API

  <details>
    <summary><strong>쿼리 파라미터 목록</strong></summary><br/>
  
    | query | 속성 | default(미입력 시 값) | 설명 |
    | --- | --- | --- | --- |
    | hashtag | string | 본인계정 |  |
    | type | string (열거형) | 필수 값 | date, hour |
    | start | date | 오늘로 부터 7일전 | 2023-10-01 과 같이 데이트 형식이며 조회 기준 시작일을 의미합니다. |
    | end | date | 오늘 | 2023-10-25 과 같이 데이트 형식이며 조회 기준 시작일을 의미합니다. |
    | value | string | count | count , view_count, like_count, share_count 가 사용 가능합니다. |
  
  </details>

  <details>
    <summary><strong><code>value</code> 쿼리 파라미터</strong></summary><br/>

    | count | 게시물 개수 |
    | --- | --- |
    | viewCount | 조회된 게시물의 조회수 총합 |
    | likeCount | 조회된 게시물의 좋아요수 총합 |
    | shareCount | 조회된 게시물의 공유하기수 총합 |

  </details>

  <details>
    <summary><strong><code>type</code> 쿼리 파라미터</strong></summary><br/>

    | date | start ~ end 기간 내(시작일, 종료일 포함) 해당 hashtag가 포함된 게시물을 일자별로 조회<br/>❗최대 한달(30일) 조회 가능 |
    | --- | --- |
    | hour | start ~ end 기간 내(시작일, 종료일 포함) 해당 hashtag가 포함된 게시물을 시간별로 조회<br/>❗최대 일주일(7일) 조회 가능 |

  </details>

- Stub
    ``` json
    Request
    GET /api/status
    
    Response
    {
      "timeStamp": "2023-07-17 09:55:01",
      "code": 200,
      "message": "정상 처리",
      "data": {[
        {
          "date": "2023-10-01",
          "count": 5
        },
        {
          "date": "2023-10-02",
          "count": 6
        },
        {
          "date": "2023-10-03",
          "count": 7
        },
      ]}
    }
    ```
</div>
</details>

<br/>

### References

https://swagger.io/specification/

https://github.com/jwtk/jjwt

https://www.baeldung.com/spring-5-webclient

https://howtodoinjava.com/java/library/mockwebserver-junit-webclient/
