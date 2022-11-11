# board_mybatis

* 언어 : Java11
* 프레임워크 : Springboot2.7
* 빌드도구 : Gradle
* Front Template : JSP
* DB : MySQL8
* SqlMapper : Mybatis
  * issue
    1) mybatis sql 로그 보는 법 - jdbc log4j2 라이브러리 연동 사용, 참고 - https://frozenpond.tistory.com/86
    2) `[unread]` 오류 -> 각 자바 클래스 필드와 DB테이블 컬럼 매칭이 안돼서 -> 해결) `<resultMap>` 태그 사용, 그리고 속성 javaType, jdbcType 맞추면됨
    
<br>

### 구현 사항

- 게시판 - 목록 
- 게시판 - 보기
- 게시판 - 등록
- 게시판 - 수정
- 게사판 - 삭제

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
