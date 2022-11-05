<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false"
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>게시판 목록</title>
    <style>
        ul {
            list-style: none;
            width : 30%;
            display: inline-block;
        }

        li {
            float: left;
            margin-left : 5px;
        }

        .clicked {
            color: crimson;
        }
    </style>
</head>
<body>
<h2>게시판 - 목록</h2>

<form align="left" id="searchForm" method="get" action="/board/list">
    <select name="type">
        <option value="" <c:out value="${pageMaker.cri.type == null?'selected':''}"/>>--</option>
        <option value="TWC" <c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}"/>>제목+작성자+내용</option>
        <option value="T" <c:out value="${pageMaker.cri.type eq 'T'?'selected':''}"/>>제목</option>
        <option value="W" <c:out value="${pageMaker.cri.type eq 'W'?'selected':''}"/>>작성자</option>
        <option value="C" <c:out value="${pageMaker.cri.type eq 'C'?'selected':''}"/>>내용</option>
    </select>
    <input type="text" name="keyword" style="width: 290px;" value='<c:out value="${pageMaker.cri.keyword}"/>'  placeholder="검색어를 입력해주세요. (제목+작성자+내용)">
    <input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>' />
    <input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>' />
    <button class='btn btn-default'>검색</button>
</form>

<br>
총 ${pageMaker.total}건
<br>
<div class="container">
    <div class="row">
        <table class="table table-striped"
               style="text-align: center; border: 1px solid #dddddd;">
            <tr>
                <th style="background-color: #eeeeee; text-align: center">카테고리</th>
<%--                <th style="background-color: #eeeeee; text-align: center">&nbsp;</th>--%>
                <th style="background-color: #eeeeee; text-align: center">제목</th>
                <th style="background-color: #eeeeee; text-align: center">작성자</th>
                <th style="background-color: #eeeeee; text-align: center">조회수</th>
                <th style="background-color: #eeeeee; text-align: center">등록 일시</th>
                <th style="background-color: #eeeeee; text-align: center">수정 일시</th>
            </tr>
            <c:forEach var="board" items="${list}">
                <tr>
                    <td>${board.category}</td>
<%--                    <c:choose>--%>
<%--                        <c:when test="${board.fileYn}">--%>
<%--                            <td><img src="/img/file_img.png"  alt=""></td>--%>
<%--                        </c:when>--%>
<%--                        <c:otherwise>--%>
<%--                            <td>&nbsp;</td>--%>
<%--                        </c:otherwise>--%>
<%--                    </c:choose>--%>
                    <td style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap; max-width: 500px"><a class="move" href="${board.id}"> ${board.title}</a></td>
                    <td>${board.writer}</td>
                    <td>${board.hit}</td>
                    <td><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.createdAt}"/></td>
                    <td>${board.createdAt}</td>
                    <c:choose>
                        <c:when test="${board.updatedAt ne null}">
                            <td><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.updatedAt}"/></td>
                            <td>${board.updatedAt}</td>
                        </c:when>
                        <c:otherwise>
                            <td style="text-align: center">-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
        </table>
        <div>
            <ul class="pagination">
                <c:if test="${pageMaker.prev}">
                    <li class="paginate_button prev">
                        <a href="<%=1%>">&lt;&lt;</a>
                        <a href="${pageMaker.startPage - 1}">&lt;</a>
                    </li>
                </c:if>
                <c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
                    <li class="paginate_button  ${pageMaker.cri.pageNum == num ? "active":""} ">
                        <a href="${num}">${num}</a>
                    </li>
                </c:forEach>

                <c:if test="${pageMaker.next}">
                    <li class="paginate_button next">
                        <a href="${pageMaker.endPage + 1}">&gt;</a>
                        <a href="${pageMaker.realEnd}">&gt;&gt;</a>
                    </li>
                </c:if>
            </ul>
        </div>
        <%-- 검색시, 페이징이동해도 검색유지 처리 해결 --%>
        <form id='actionForm' action="/board/list" method='get'>
            <input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
            <input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
            <input type='hidden' name='type' value='<c:out value="${pageMaker.cri.type}"/>'>
            <input type='hidden' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>'>
        </form>

        <%--            <a href="write.jsp?pageNum=${pageNum}" class="btn btn-primary pull-right">등록</a>--%>
        <button id="regBtn" type="button" class="btn btn-xs pull-right">등록</button>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {

        // 페이징 클릭 기능 메서드
        var actionForm = $("#actionForm");

        $(".paginate_button a").on("click", function(e) {
            e.preventDefault();
            console.log('click');
            $(this).toggleClass('clicked');     // TODO: submit으로 사라짐. 색깔 유지 방법은?

            actionForm.find("input[name='pageNum']").val($(this).attr("href"));
            actionForm.submit();
        });

        // 검색 선택 기능 메서드
        var searchForm = $("#searchForm");

        $("#searchForm button").on("click", function(e) {

            if (!searchForm.find("option:selected").val()) {
                alert("검색종류를 선택하세요");
                return false;
            }
            if (!searchForm.find("input[name='keyword']").val()) {
                alert("키워드를 입력하세요");
                return false;
            }
            searchForm.find("input[name='pageNum']").val("1");      // 검색버튼을 클릭하면 검색은 1페이지로 이동
            e.preventDefault();
            searchForm.submit();

        });

        $(".move").on("click", function(e) {
            e.preventDefault();
            actionForm.append("<input type='hidden' name='id' value='"
                + $(this).attr("href")
                + "'>");
            actionForm.attr("action", "/board/get");
            actionForm.submit();

        });

        $("#regBtn").on("click", function() {

            self.location = "/board/write";

        });

    });

</script>
</body>
</html>
