# board_mybatis

* 프레임워크 : Springboot
* 빌드도구 : Gradle
* Front Template : JSP
* DB : Mysql
* DB연결기술 : Mybatis
  * issue
    1) [unread] -> <resultMap> 구현 각 자바 클래스 필르와 DB테이블 컬럼 매칭이 안돼서 -> 해결) resultMap javaType, jdbcType 맞추면됨
    2) mybatis sql 로그 보는 법 - jdbc log4j2 라이브러리 연동 사용, 참고 - https://frozenpond.tistory.com/86


### 구현 사항

- 게시판 - 목록 
- 게시판 - 보기
- 게시판 - 등록
- 게시판 - 수정
- 게사판 - 삭제

### 컨멘션
1) 메서드 이름
- service : get
- DAO : find

2) xml 설정 파일
- 케밥 케이스

### 유용 플러그인
- MybatisX: 자바 클래스 메서드와 xml 메서드 연결시킴
