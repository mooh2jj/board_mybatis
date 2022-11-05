<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>게시글 수정</title>
</head>
<body>

<h2>게시판 - 수정</h2>
<br>
<h3>기본정보</h3>

<div class="container">
    <div class="row">
        <form role="form" action="/board/modify" method="post">
            <input type='hidden' id='id' name='id' value='<c:out value="${board.id}"/>'>
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
            <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

            <table class="table table-striped"
                   style="text-align: center; border: 1px solid #dddddd;">
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">카테고리</td>
                    <td colspan="2">${board.category}</td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">등록일시</td>
                    <td colspan="2"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.createdAt}"/></td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">수정일시</td>
                    <c:choose>
                        <c:when test="${board.updatedAt ne null}">
                            <td colspan="2"><fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.updatedAt}"/></td>
                        </c:when>
                        <c:otherwise>
                            <td colspan="2" style="text-align: center">-</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">조회수</td>
                    <td colspan="2">${board.hit}</td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">작성자</td>
                    <td colspan="2">
                        <input type="text" class="form-control"
                               placeholder="작성자" name="writer" maxlength="50"
                               value="${board.writer}">
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">비밀번호</td>
                    <td colspan="2">
                        <input type="text" class="form-control"
                               placeholder="비밀번호" name="password" maxlength="50">
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">제목</td>
                    <td colspan="2">
                        <input type="text" class="form-control"
                               placeholder="제목" name="title" maxlength="50"
                               value="${board.title}">
                    </td>
                </tr>

                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">내용</td>
                    <td colspan="2">
                        <textarea class="form-control" placeholder="내용"
                                  name="content" maxlength="2048" style="height: 350px;">
                                  ${board.content}</textarea>
                    </td>
                </tr>
<%--                <tr>--%>
<%--                    <td style="width: 20%; background-color: #eeeeee;">파일 첨부</td>--%>
<%--                    <c:forEach var="fileItem" items="<%=fileItemList%>">--%>
<%--                    <td colspan="2">--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${board.fileUUID ne null}">--%>
<%--                                <div class="uploadResult">--%>
<%--                                    <ul>--%>
<%--                                        <a href="downloadAction.jsp?fileUUIDName=${fileItem.fileUUIDName}">${fileItem.fileName}</a>--%>
<%--                                        <button type="button" id="delete_fileItem">삭제</button>--%>
<%--                                    </ul>--%>
<%--                                </div>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <span>&nbsp;</span><br>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                    </c:forEach>--%>
<%--                        <div><input type="file" name="upload" value="" class="file_modify_input" /></div>--%>
<%--                    </td>--%>
<%--                </tr>--%>
            </table>
            <button type="submit" data-oper='list' class="btn btn-info">취소</button>
            <button type="submit" data-oper='modify' class="btn btn-primary pull-left">수정</button>
<%--         TODO: modifyAction.jsp에 보내기 위해 hidden으로 file delete oper 정보 보내야돼! --%>
        </form>

    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {

        var formObj = $("form");

        $('button').on("click", function (e) {
            e.preventDefault();
            var operation = $(this).data("oper");
            console.log("operation: ", operation);

            if(operation === 'list'){
                //move to list
                formObj.attr("action", "list.jsp").attr("method","get");

                var pageNumTag = $("input[name='pageNum']").clone();
                var amountTag = $("input[name='amount']").clone();
                var keywordTag = $("input[name='keyword']").clone();
                var searchOption = $("input[name='searchOption']").clone();

                console.log("pageNumTag: ", pageNumTag);
                console.log("amountTag: ", amountTag);
                console.log("keywordTag: ", keywordTag);
                console.log("searchOption: ", searchOption);

                formObj.empty();        // pageNum, amount, keyword, searchOption 외에는 가져오지 않기 위해

                formObj.append(pageNumTag);
                formObj.append(amountTag);
                formObj.append(keywordTag);
                formObj.append(searchOption);
            }

            if (operation === 'modify') {
                console.log("modify clicked");

                var str = "";

                $(".uploadResult ul").each(function (i, obj) {
                    var jobj = $(obj);

                    console.dir(jobj);
                    // TODO: 파일 나온 부분을 가져와서 jquery 태그로 자겨와서 modifyAction.jsp로 보내기
                    // str += "<input type='hidden' name='fileItem["+i+"].fileName' value='"+jobj.data("filename")+"'>";
                });

            }

            formObj.submit();

        });
        // TODO : modify시 파일 change -> 파일이 나오게 처리
        $('.file_modify_input').change('')

        // 파일 a태그 삭제
        $('.uploadResult').on('click', 'button', function (e) {
            // e.preventDefault();
            console.log('delete_fileItem click');
            if (confirm("파일을 삭제하시겠습니까?")) {
                var targetLi = $(this).closest("div");
                targetLi.remove();
            }

        });
    });
</script>

</body>
</html>
