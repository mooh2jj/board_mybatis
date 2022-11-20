# board_mybatis

* 언어 : Java11
* 프레임워크 : Springboot2.7
* 빌드도구 : Gradle
* Front Template : JSP
* DB : MySQL8.0
* SqlMapper : Mybatis3.0
* 디자인 UI : Bootstrap4
  * issue
    1) mybatis sql 로그 보는 법 - jdbc log4j2 라이브러리 연동 사용, 참고 - https://frozenpond.tistory.com/86
    2) `[unread]` 오류 -> 각 자바 클래스 필드와 DB테이블 컬럼 매칭이 안돼서 -> 해결) `<resultMap>` 태그 사용, 그리고 속성 javaType, jdbcType 맞추면됨
    3) TimeStamp -> LocalDateTime 변환시, log4j2 드라이버에서 오류, jsp에서 LocalDateTime 변환시, 2번을 parsing 해주어야 함. TimeStamp로 계속 감.
<br>

### 구현 사항

- 게시판 - 목록 
- 게시판 - 보기
- 게시판 - 등록
- 게시판 - 수정
- 게사판 - 삭제

<페이징>
* 검색, 페이징 파라미터 -> Criteria, PagingDTO 파라미터 이용
* form 태그로 이동시 항시 줄려면 -> hidden 태그 사용
* 다른 페이지이동 후 다시 페이징되는 목록페이지로 이동시, 그전 페이징, 검색까지 가져와야 -> UriComponentsBuilder 객체 사용

<비밀번호>
* Modal 창 사용해야 -> div태그로 만들 어진 것
* 등록시, 비밀번호 입력 
* 수정시, 삭제시 `비밀번호 입력시 파라미터로 필수`로 넣어주어야함. -> Bryto encdoer 라이브러리 사용(SpringFramework전용, 현재 암호화 코더중 가장 좋다고 함.)
* 프론트단에서 비밀번호 확인, 서버단에서 비밀번호 확인 둘다 필요(Validation만 얘기하는 게 아님!)

<파일첨부>
* MutipartRequest 객체(springFramework전용)사용, form 태그 enctype="multipart/form-data" 지정, input 태그 multi 넣우줌(다중파일일시)
* 업로드/다운로드 api 컨트롤러 만들어주어야 함
* 파일경로 @Value 어노테이션으로 application.yml에서 바인딩해줌
* 자세 내용 : https://velog.io/@mooh2jj/Springboot-MVC-단일다중-File-upload-구현하기

<배치-파일첨부삭제>
* Quartz 라이브러리 사용
* 일정시간에 DB에 없는 내용 찾아서 -> 첨부파일 삭제처리
* 일정시간을 지정해줄 업로드 폴더 생성해야
* Quartz 라이브러리 + @Scheduled(cron = "15 * * * * ?") cronTap 사용법 익혀둬야

<br>

### 컨벤션

1) 주석처리
- 왠만하면 code네이밍을 통해서 주석을 최대한 적지않게
- TODO 처리 ex. `// TODO`
- `/** **/`  JavaDoc 을 대비한 주석처리 

2) 메서드 이름
- service : get
- DAO : find

3) xml 설정 파일
- 케밥 케이스

<br>

### 유용 플러그인
- MybatisX: 자바 클래스 메서드와 xml 메서드 연결시킴


### 프론트(Bootstrap4)
* 목록 
![image](https://user-images.githubusercontent.com/62453668/202919924-ac25c349-4a7a-499c-893e-3e4252f5d283.png)
* 등록
![image](https://user-images.githubusercontent.com/62453668/202919974-5c7fc39b-7751-497e-902e-9b1bad4dc46e.png)
* 상세보기
![image](https://user-images.githubusercontent.com/62453668/202920192-9e1fc1e3-793e-46cc-8893-12b40f34636a.png)
* 수정
![image](https://user-images.githubusercontent.com/62453668/202920236-33d9eaed-2a9b-4332-ad70-1deae1d27b7d.png)
* 비밀번호창(삭제시)
![image](https://user-images.githubusercontent.com/62453668/202920474-961b3ffd-4820-4e26-83dc-100688927274.png)

