<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>게시글 상세보기</title>
</head>
<body>

<h2>게시판 - 보기 </h2>

<div class="container">
    <div class="row">
        <form id='form' action="/board/modifyForm" method="get">
            <input type='hidden' id='id' name='id' value='<c:out value="${board.id}"/>'>
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
            <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

            작성자
            ${board.writer}
            조회수: ${board.hit+1}
            등록일시
            <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value = "${board.createdAt}" />
            수정일시
            <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value = "${board.updatedAt}" />
            <br>
            [${board.category}] ${board.title}
            <hr>
            <table class="table table-striped">
                <tr>
                    <td style="min-height: 200px; text-align: left;">
                        <label>
                            <textarea class="form-control" rows="3" name='content' readonly><c:out
                                    value="${board.content}"/></textarea>
                        </label>
                    </td>
                </tr>
                <%--                <c:forEach var="fileItem" items="<%=fileItemList%>">--%>
                <%--                    <tr>--%>
                <%--                        <c:choose>--%>
                <%--                            <c:when test="${fileItem.fileName ne null}">--%>
                <%--                                <td><a href="downloadAction.jsp?fileUUIDName=${fileItem.fileUUIDName}">${fileItem.fileName}</a></td>--%>
                <%--                            </c:when>--%>
                <%--                            <c:otherwise>--%>
                <%--                                <span>&nbsp;</span>--%>
                <%--                            </c:otherwise>--%>
                <%--                        </c:choose>--%>
                <%--                    </tr>--%>
                <%--                </c:forEach>--%>
                <%--                <br>--%>
                <%--                <c:forEach var="reply" items="${replyList}">--%>
                <%--                    <tr>--%>
                <%--                        <td>--%>
                <%--                            <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${reply.repliedAt}"/>--%>
                <%--                            <br>--%>
                <%--                                ${reply.replyText}--%>
                <%--                        </td>--%>
                <%--                    </tr>--%>
                <%--                </c:forEach>--%>


                <%--                <tr>--%>
                <%--                    <td>--%>

                <%--                    <td>--%>
                <%--                </tr>--%>
            </table>
            <br>
            <button data-oper='list' class="btn btn-info">목록</button>
            <button data-oper='modifyForm' class="btn btn-default">수정</button>
            <button data-oper="remove" class="btn btn-danger">삭제</button>
        </form>
    </div>
</div>
<br>
<div>
    <div id="listReply"></div>
    <input type="text" id="replyText" placeholder="댓글을 입력해주세요"/>
    <button type="button" id="btnReply">등록</button>
</div>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {

        listReply();

        $('#btnReply').on('click', function (e) {
            e.preventDefault();
            console.log('btnReply click');

            var replyText = $('#replyText').val();
            var boardId = "${board.id}";
            console.log("replyText: ", replyText);
            console.log("boardId: ", boardId);

            var param = {
                "replyText": replyText,
                "boardId": boardId
            };


            $.ajax({
                type: "post",
                url: "/reply/insert",
                data: JSON.stringify(param),
                contentType: "application/json; charset=utf-8",
                success: function (result) {
                    alert("댓글이 입력되었습니다.");
                    console.log("result: ", result)
                    // listReply();
                    // TODO : 게시글 등록 후 list페이지로 돌아가지 않게 하기
                }
            });

        });

        function listReply() {
            var boardId = "${board.id}";
            console.log("listReply boardId: ", boardId)
            $.ajax({
                type: "get",
                url: "/reply/list/" + boardId,
                // contentType: "application/json; charset=utf-8", // ==> 생략가능(RestController이기때문에 가능)
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    var output = "<table>";
                    for (var i in result) {
                        output += "<tr>";
                        /* output += "<td>"+result[i].userName; */
                        // output += "<td>"+result[i].replyer;
                        output += "<td>" + changeDate(result[i].repliedAt) + "</td>";
                        output += "</tr>";
                        output += "<tr>";
                        output += "<td>" + result[i].replyText + "</td>";
                        output += "</tr>";
                    }
                    output += "</table>";
                    $("#listReply").html(output);
                }
            });
        }

        // **날짜 변환함수 작성
        function changeDate(date) {
            // date = new Date(date);
            date = new Date(date);
            let year = date.getFullYear();
            let month = date.getMonth() + 1;
            month = month >= 10 ? month : '0' + month
            let day = date.getDate();
            day = day >= 10 ? day : '0' + day
            let hour = date.getHours();
            hour = hour >= 10 ? hour : '0' + hour
            let min = date.getMinutes();
            min = min >= 10 ? min : '0' + min
            let sec = date.getSeconds();
            sec = sec >= 10 ? sec : '0' + sec

            return year + "." + month + "." + day + " " + hour + ":" + min + ":" + sec;
        }

    });
</script>

<script>
    $(document).ready(function () {
        var formObj = $("#form");
        $('button').on("click", function (e) {
            e.preventDefault();

            var operation = $(this).data("oper");
            console.log(operation);

            if (operation === 'remove') {
                alert('정말로 삭제하시겠습니까?');
                formObj.attr("action", "/board/remove").attr("method", "post");
            } else if (operation === 'list') {
                formObj.attr("action", "/board/list").attr("method", "get");
                var pageNumTag = $("input[name='pageNum']").clone();
                var amountTag = $("input[name='amount']").clone();
                var keywordTag = $("input[name='keyword']").clone();
                var typeTag = $("input[name='type']").clone();

                formObj.empty();

                formObj.append(pageNumTag);
                formObj.append(amountTag);
                formObj.append(keywordTag);
                formObj.append(typeTag);
            }


            // $(".uploadResult ul li").each(function(i, obj){
            //
            //     var jobj = $(obj);
            //
            //     console.dir(jobj);
            //
            //     str += "<input type='hidden' name='attachList["+i+"].fileName' value='"+jobj.data("filename")+"'>";
            //     str += "<input type='hidden' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
            //     str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='"+jobj.data("path")+"'>";
            //     str += "<input type='hidden' name='attachList["+i+"].fileType' value='"+ jobj.data("type")+"'>";
            //
            // });
            // formObj.append(str).submit();
            formObj.submit();   // 나머지 modifyForm 페이지 이동
        });
    });
</script>
</body>
</html>
