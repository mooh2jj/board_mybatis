<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@include file="../includes/header.jsp"%>

<style>
    .uploadResult {
        width: 100%;
        background-color: gray;
    }

    .uploadResult ul {
        display: flex;
        flex-flow: row;
        justify-content: center;
        align-items: center;
    }

    .uploadResult ul li {
        list-style: none;
        padding: 10px;
    }

    .uploadResult ul li img {
        width: 100px;
    }

</style>

<div class="container">
    <h2>게시판 - 보기 </h2>
    <br>
    <div class="row">
        <div class="col-lg-12">
            <form id='form' action="/board/modify" method="get">
                <input type='hidden' id='id' name='id' value='<c:out value="${board.id}"/>'>
                <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
                <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
                <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
                <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

                <div class="form-group">
                    <label>작성자</label> ${board.writer }
                    <%--                <input class="form-control" name='writer' value='<c:out value="${board.writer }"/>' readonly="readonly">--%>
                </div>
                <div class="form-group">
                    <label>조회수</label> ${board.hit+1}
                </div>
                <label>등록일시</label>
                <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.createdAt}"/>
                <label>수정일시</label>
                <fmt:formatDate pattern="yyyy.MM.dd HH:mm:ss" value="${board.updatedAt}"/>
                <br>
                <div class="form-group">
                    <label>[${board.category}] ${board.title}</label>
                </div>
                <div class="form-group">
                    <table class="table table-striped">
                        <tr>
                            <td style="min-height: 200px; text-align: left;">
                                <label>
                                    <textarea class="form-control" rows="3" name='content' readonly><c:out
                                            value="${board.content}"/></textarea>
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><label>파일</label></div>
                            <!-- /.panel-heading -->
                            <div class="panel-body">
                                <div class='uploadResult'>
                                    <ul>
                                    </ul>
                                </div>
                            </div>
                            <!--  end panel-body -->
                        </div>
                        <!--  end panel-body -->
                    </div>
                    <!-- end panel -->
                </div>
                <!-- /.row -->
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading"><label>댓글</label></div>
                            <div class="panel-body">
                                <div id="listReply"></div>
                                <br>
                                <input type="text" id="replyText" placeholder="댓글을 입력해주세요"/>
                                <button type="button" id="btnReply">등록</button>
                            </div>
                        </div>
                    </div>
                </div>

                <br>
                <button id="list" data-oper='list' class="btn btn-info">목록</button>
                <sec:authentication property="principal" var="pinfo" />
                <sec:authorize access="isAuthenticated()">
                    <c:if test="${pinfo.username eq board.writer}">
                    <button id="modifyForm" data-oper='modifyForm' class="btn btn-default">수정</button>
                    <button id="remove" data-oper="remove" class="btn btn-danger">삭제</button>
                    </c:if>
                </sec:authorize>
            </form>
        </div>
    </div>


    <div class='bigPictureWrapper'>
        <div class='bigPicture'>
        </div>
    </div>

    <%-- 비밀번호 modal창 --%>
    <div class="modal fade" id="modal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">비밀번호 창</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label>비밀번호</label>
                        <input type="password" class="pw" placeholder="비밀번호" name="password" id="password" maxlength="50">
                        <span id="alert-success" style="display: none; color: #2b52f6; font-weight: bold;">비밀번호가 일치합니다.</span>
                        <span id="alert-danger" style="display: none; color: #d92742; font-weight: bold;">비밀번호가 일치하지 않습니다.</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id='modalCancel' type="button" class="btn btn-primary">취소</button>
                    <button id='modalCheck' type="button" class="btn btn-default">확인</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function () {
        // 댓글 보여주기
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
                    listReply();
                }
            });

        });

        function listReply() {
            var boardId = "${board.id}";
            console.log("listReply boardId: ", boardId)
            $.ajax({
                type: "get",
                url: "/reply/list/" + boardId,
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
        var modal = $("#modal");

        let boardId = '<c:out value="${board.id}"/>';
        let password = $("#password");

        $("#remove").on("click", function (e) {
            e.preventDefault();
            modal.modal("show");
        });

        $("#list").on("click", function (e) {
            e.preventDefault();

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
            formObj.submit();
        });

        $("#modifyForm").on("click", function () {
            formObj.submit();
        });

        // modal 비밀번호
        // modal창 닫기
        $("#modalCancel").on("click", function (e) {
            e.preventDefault();
            modal.modal('hide');
            // 취소후 비밀번호 입력창 초기화
            modal.find("input[name='password']").val("");
            $("#alert-danger").css('display', 'none');
            $("#alert-success").css('display', 'none');
        });

        // modal창 비밀번호 체크 후 삭제
        $("#modalCheck").on("click", function (e) {
            e.preventDefault();

            let passwordVal = password.val().trim();

            if (passwordVal === "" || password.val().length === 0) {
                alert("비밀번호을 입력하세요.");
                password.focus();
                return false;
            }

            if (confirm("정말 삭제하시겠습니까?")) {
                formObj.attr("action", "/board/remove").attr("method", "post");
                let password = $("input[name='password']").clone();

                formObj.append(password);
                formObj.submit();
            }
        });

        $('.pw').on("focusout", function () {

            let passwordVal = password.val().trim();

            let param = {
                "boardId": boardId,
                "passwordVal": passwordVal
            }

            $.ajax({
                type: "post",
                url: "/board/checkPassword",
                data: JSON.stringify(param),
                contentType: "application/json",
                success: function (result) {
                    console.log(result);    // boolean
                    searchPassword(result);
                },
            })
        });

        function searchPassword(result) {
            if (result) {
                $("#alert-success").css('display', 'inline-block');
                $("#alert-danger").css('display', 'none');
            } else {
                $("#alert-success").css('display', 'none');
                $("#alert-danger").css('display', 'inline-block');
                return false;
            }
        }
    });
</script>
<script>
    $(document).ready(function () {

        (function () {

            var boardId = '<c:out value="${board.id}"/>';

            $.getJSON("/board/getAttachList", {boardId: boardId}, function (arr) {

                console.log(arr);

                var str = "";

                $(arr).each(function (i, attach) {

                    //image type
                    if (attach.image) {
                        var fileCallPath = encodeURIComponent(attach.uploadPath + "/" + attach.uuid + "_" + attach.fileName);

                        str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.image + "' ><div>";
                        str += "<span> " + attach.fileName + "</span><br/>";
                        str += "<img src='/display?fileName=" + fileCallPath + "'>";
                        str += "</div>";
                        str += "</li>";
                    } else {
                        str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.image + "' ><div>";
                        str += "<span> " + attach.fileName + "</span><br/>";
                        str += "<img src='/resources/img/attach.png'></a>";
                        str += "</div>";
                        str += "</li>";
                    }
                });

                $(".uploadResult ul").html(str);


            });//end getjson


        })();//end function

        $(".uploadResult").on("click", "li", function (e) {

            console.log("view image");

            var liObj = $(this);

            var path = encodeURIComponent(liObj.data("path") + "/" + liObj.data("uuid") + "_" + liObj.data("filename"));

            // if(liObj.data("type")){
            //     showImage(path.replace(new RegExp(/\\/g),"/"));
            // }else {
            //     //download
            //     self.location ="/download?fileName="+path
            // }
            console.log("path: ", path)
            // 바로 다운로드
            self.location = "/download?fileName=" + path

        });

        function showImage(fileCallPath) {

            alert(fileCallPath);

            $(".bigPictureWrapper").css("display", "flex").show();

            $(".bigPicture")
                .html("<img src='/display?fileName=" + fileCallPath + "' >")
                .animate({width: '100%', height: '100%'}, 1000);

        }

        $(".bigPictureWrapper").on("click", function (e) {
            $(".bigPicture").animate({width: '0%', height: '0%'}, 1000);
            setTimeout(function () {
                $('.bigPictureWrapper').hide();
            }, 1000);
        });


    });

</script>
<%@include file="../includes/footer.jsp"%>
