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
    <title>게시판 등록</title>
</head>
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
<body>
<h2>게시판 - 등록</h2>
<br>
<br>
<div class="container">
    <div class="row">
        <form id="form" action="/board/register" method="post" enctype="multipart/form-data">
            <table class="table table-striped"
                   style="text-align: center; border: 1px solid #dddddd;">
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">카테고리</td>
                    <td colspan="2">
                        <select id="category" name="category" size="1">
                            <option value="" selected></option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">작성자</td>
                    <td colspan="2"><input type="text" id="writer" class="check" placeholder="작성자" name="writer" maxlength="50"></td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">비밀번호</td>
                    <td colspan="2">
                        <input type="text" class="check" placeholder="비밀번호" id="password" name="password" maxlength="50">
                        <input type="password" class="check" placeholder="비밀번호 확인" id="passwordCheck" name="passwordCheck" maxlength="50">
                        <span id="alert-success" style="display: none; color: #2b52f6; font-weight: bold;">비밀번호가 일치합니다.</span>
                        <span id="alert-danger" style="display: none; color: #d92742; font-weight: bold;">비밀번호가 일치하지 않습니다.</span>
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">제목</td>
                    <td colspan="2"><input type="text" id="title" class="check" placeholder="제목" name="title" size="67" maxlength="300"></td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">내용</td>
                    <td colspan="2"><textarea id="content" class="check" placeholder="글 내용" name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
                </tr>
<%--                <tr>--%>
<%--                    <td style="width: 20%; background-color: #eeeeee;">파일 첨부</td>--%>
<%--                    <td><input type="file" name="file1" value="" class="board_view_input" /></td>--%>
<%--                    <td><input type="file" name="file2" value="" class="board_view_input" /></td>--%>
<%--                    <td><input type="file" name="file3" value="" class="board_view_input" /></td>--%>
<%--                </tr>--%>
            </table>
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
            <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

            <button data-oper='list' class="btn btn-info">목록</button>
            <button data-oper='register' class="btn btn-default">등록</button>
        </form>
    </div>
</div>
<%--파일 첨부 부분--%>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">파일 첨부</div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div class="form-group uploadDiv">
                    <%--   multiple: 파일 다중 업로드   --%>
                    <input type="file" name='uploadFile' multiple>
                </div>
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
<script src="https://code.jquery.com/jquery-3.6.1.min.js" integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script>
    $(document).ready(function() {
        var formObj = $("#form");
        // TODO: 엔터키 입력시 목록창으로 이동 enter키 막기
        $('button').on("click", function(e) {
            e.preventDefault();
            var operation = $(this).data("oper");
            console.log("operation: ", operation);

            if (operation === 'list') {
                //move to list
                formObj.attr("action", "/board/list").attr("method","get");
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
            } else if(operation === 'register') {

                var str = "";
                $(".uploadResult ul li").each(function (i, obj) {
                    var jobj = $(obj);

                    console.dir(jobj);
                    console.log("-------------------------");
                    console.log(jobj.data("filename"));

                    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].image' value='" + jobj.data("type") + "'>";

                });
                console.log("str: ", str);
                formObj.append(str).submit();
            }

        });

        // 파일업로드 따로 처리
        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
        var maxSize = 5242880; //5MB

        function checkExtension(fileName, fileSize){

            if(fileSize >= maxSize){
                alert("파일 사이즈 초과");
                return false;
            }

            if(regex.test(fileName)){
                alert("해당 종류의 파일은 업로드할 수 없습니다.");
                return false;
            }
            return true;
        }


        $("input[type='file']").change(function(e){

            console.log("file change!")

            var formData = new FormData();

            var inputFile = $("input[name='uploadFile']");

            var files = inputFile[0].files;
            console.log("files: ", files)
            for(var i = 0; i < files.length; i++){

                if(!checkExtension(files[i].name, files[i].size) ){
                    return false;
                }
                formData.append("uploadFile", files[i]);
                console.log("for files: ", files[i])

            }
            console.log("formData: ", formData)

            // 파일 업로드 ajax 처리
            $.ajax({
                url: '/uploadAjaxAction',
                processData: false,
                contentType: false,
                data:formData,
                // beforeSend: function(xhr) {
                //     xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                // },
                type: 'POST',
                success: function(result){
                    console.log("result:", result);
                    showUploadResult(result); //업로드 결과 처리 함수

                }
            }); //$.ajax

        });

        function showUploadResult(uploadResultArr){

            if(!uploadResultArr || uploadResultArr.length == 0){ return; }

            var uploadUL = $(".uploadResult ul");

            var str ="";

            $(uploadResultArr).each(function(i, obj){

                //image type
                console.log("obj.image: ", obj.image)
                if(obj.image){
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/"+obj.uuid +"_"+obj.fileName);
                    str += "<li data-path='"+obj.uploadPath+"'";
                    str += " data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>";
                    str += "<div>";
                    str += "<span> "+ obj.fileName+"</span>";
                    str += "<button type='button' data-file=\'"+fileCallPath+"\' "
                    str += "data-type='image' class='btn btn-warning btn-circle'>X</button><br>";
                    str += "<img src='/display?fileName="+fileCallPath+"'>";
                    str += "</div>";
                    str += "</li>";
                }else{
                    var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                    var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                    str += "<li "
                    str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'>";
                    str += "<div>";
                    str += "<span> "+ obj.fileName+"</span>";
                    str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' "
                    str += "class='btn btn-warning btn-circle'>X</button><br>";
                    str += "<img src='/resources/img/attach.png'></a>";
                    str += "</div>";
                    str += "</li>";
                }

            });

            uploadUL.append(str);
        }

        $(".uploadResult").on("click", "button", function(e){

            console.log("delete file");

            var targetFile = $(this).data("file");
            var type = $(this).data("type");

            var targetLi = $(this).closest("li");

            $.ajax({
                url: '/deleteFile',
                data: {fileName: targetFile, type:type},
                // beforeSend: function(xhr) {
                //     xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                // },

                dataType:'text',
                type: 'POST',
                success: function(result){
                    alert(result);

                    targetLi.remove();
                }
            }); //$.ajax
        });

    });
</script>
<script>
    $(function () {
        // 등록시 값 유효성 검사
        // TODO: 먼저 alert창이 뜨는 현상 방지
        $('.check').on('change', function (e) {
            e.preventDefault();
            let category = $("#category option:selected");

            let writer = $('#writer');
            let title = $('#title');
            let content = $('#content');

            let password = $("#password");
            let passwordCheck = $("#passwordCheck");

            let categoryVal = category.val().trim();
            let writerVal = writer.val().trim();
            let titleVal = title.val().trim();
            let contentVal = content.val().trim();

            let passwordVal = password.val().trim();
            let passwordCheckVal = passwordCheck.val().trim();

            if (categoryVal === "") {
                alert("카테고리를 입력하세요.");
                category.focus();
                return false;
            }

            if (writerVal === "") {
                alert("작성자명을 입력하세요.");
                writer.focus();
                return false;
            }

            if(writerVal.length < 3 || writerVal.length >= 5){
                alert("작성자명은 3글자 이상 5글자 미만이어야 합니다.");
                writer.val("");
                writer.focus();
                return false;
            }

            if (passwordVal === "" || password.val().length === 0) {
                alert("비밀번호을 입력하세요.");
                password.focus();
                return false;
            }

            let check_pw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$/;
            if (!check_pw.test(passwordVal) ) {
                alert("비밀번호는 4글자 이상, 16글자 미만 그리고 영문/숫자/특수문자 포함이어야 합니다.");
                password.val("");
                password.focus();
                return false;
            }

            if (passwordCheckVal === "" || passwordCheckVal.length === 0) {
                alert("비밀번호 확인을 위하여 다시한번 입력해주세요");
                passwordCheck.focus();
                return false;
            }

            if (passwordVal !== '' && passwordCheck.val() !== '') {
                if (passwordVal === passwordCheckVal) {
                    $("#alert-success").css('display', 'inline-block');
                    $("#alert-danger").css('display', 'none');
                } else {
                    $("#alert-success").css('display', 'none');
                    $("#alert-danger").css('display', 'inline-block');
                    passwordCheck.val("");
                    passwordCheck.focus();
                    return false;
                }
            }

            if(titleVal === ""){
                alert("제목을 입력해주세요.");
                title.val("");
                title.focus();
                return false;
            }

            if (titleVal.length < 4 || titleVal.length >= 100) {
                alert("제목은 4글자 이상 100글자 미만이어야 합니다.");
                title.val("");
                title.focus();
                return false;
            }

            if(contentVal === ""){
                alert("내용을 입력해주세요.");
                content.val("");
                content.focus();
                return false;
            }

            if (contentVal.length < 4 || contentVal.length >= 2000) {
                alert("내용은 4글자 이상 2000글자 미만이어야 합니다.");
                content.val("");
                content.focus();
                return false;
            }

        });
    });
</script>
</body>
</html>
