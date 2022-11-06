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
                        <select name="category" size="1">
                            <option value="" selected></option>
                            <c:forEach var="category" items="${categories}">
                                <option value="${category}">${category}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">작성자</td>
                    <td colspan="2"><input type="text" class="form-control" placeholder="작성자" name="writer" maxlength="50"></td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">비밀번호</td>
                    <td colspan="2">
                        <input type="text" class="pw" placeholder="비밀번호" id="password" name="password" maxlength="50">
                        <input type="text" class="pw" placeholder="비밀번호 확인" id="passwordCheck" name="passwordCheck" maxlength="50">
                        <span id="alert-success" style="display: none; color: #2b52f6; font-weight: bold;">비밀번호가 일치합니다.</span>
                        <span id="alert-danger" style="display: none; color: #d92742; font-weight: bold;">비밀번호가 일치하지 않습니다.</span>
                    </td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">제목</td>
                    <td colspan="2"><input type="text" class="form-control" placeholder="제목" name="title" size="67" maxlength="300"></td>
                </tr>
                <tr>
                    <td style="width: 20%; background-color: #eeeeee;">내용</td>
                    <td colspan="2"><textarea class="form-control" placeholder="글 내용" name="content" rows="10" cols="65" maxlength="4000"></textarea></td>
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

            <%--            <input type="button" class="btn btn-primary" onclick="location.href='list.jsp?pageNum=<%=pageNum%>&amount=<%=amount%>'" value="취소">--%>
            <button data-oper='list' class="btn btn-info">목록</button>
            <%--            <input type="submit" class="btn btn-primary pull-right" value="저장">--%>
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

        $('button').on("click", function(e) {
            e.preventDefault();
            console.log("submit clicked");

            var operation = $(this).data("oper");
            console.log(operation);

            if (operation === 'list') {
                formObj.attr("action", "/board/list").attr("method","get");
                var pageNumTag = $("input[name='pageNum']").clone();
                var amountTag = $("input[name='amount']").clone();
                var keywordTag = $("input[name='keyword']").clone();
                var searchOption = $("input[name='searchOption']").clone();

                formObj.empty();

                formObj.append(pageNumTag);
                formObj.append(amountTag);
                formObj.append(keywordTag);
                formObj.append(searchOption);

                formObj.submit();
            } else if(operation == 'register') {
                // 나머지는 그냥 submit -> register

                var str = "";

                $(".uploadResult ul li").each(function (i, obj) {

                    var jobj = $(obj);

                    console.dir(jobj);
                    console.log("-------------------------");
                    console.log(jobj.data("filename"));

                    str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
                    str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";

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
                    // var output = "";
                    // for(var i in result) {
                    //     output += "<li>" + "<input type='hidden' name='attachList[" + i + "].fileName' value='" + result[i].fileName + "'>" + "</li>";
                    //     output += "<li>" + "<input type='hidden' name='attachList[" + i + "].uuid' value='" + result[i].uuid + "'>" + "</li>";
                    //     output += "<li>" + "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + result[i].uploadPath + "'>" + "</li>";
                    //     output += "<li>" + "<input type='hidden' name='attachList[" + i + "].fileType' value='" + result[i].fileType + "'>" + "</li>";
                    // }
                    // console.log("uploadAjaxAction output: ", output);
                    // $(".uploadResult ul").html(output);
                }
            }); //$.ajax

            function showUploadResult(uploadResultArr){

                if(!uploadResultArr || uploadResultArr.length == 0){ return; }

                var uploadUL = $(".uploadResult ul");

                var str ="";

                $(uploadResultArr).each(function(i, obj){

                     //image type
/*                    if(obj.image){
                      var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
                      str += "<li><div>";
                      str += "<span> "+ obj.fileName+"</span>";
                      str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                      str += "<img src='/display?fileName="+fileCallPath+"'>";
                      str += "</div>";
                      str +"</li>";
                    }else{
                      var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                        var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                      str += "<li><div>";
                      str += "<span> "+ obj.fileName+"</span>";
                      str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                      str += "<img src='/resources/img/attach.png'></a>";
                      str += "</div>";
                      str +"</li>";
                    }*/
                    //image type

                    if(obj.image){
                        var fileCallPath =  encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid +"_"+obj.fileName);
                        str += "<li data-path='"+obj.uploadPath+"'";
                        str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'"
                        str +" ><div>";
                        str += "<span> "+ obj.fileName+"</span>";
                        str += "<button type='button' data-file=\'"+fileCallPath+"\' "
                        str += "data-type='image' class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                        str += "<img src='/display?fileName="+fileCallPath+"'>";
                        str += "</div>";
                        str +"</li>";
                    }else{
                        var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                        var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");

                        str += "<li "
                        str += "data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"' ><div>";
                        str += "<span> "+ obj.fileName+"</span>";
                        str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' "
                        str += "class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
                        str += "<img src='/resources/img/attach.png'></a>";
                        str += "</div>";
                        str +"</li>";
                    }

                });

                uploadUL.append(str);
            }

        });
        // 비밀번호 확인
        $('.pw').focusout (function () {
            let password = $("#password").val();
            let passwordCheck = $("#passwordCheck").val();
            console.log("password:", password);
            console.log("passwordCheck:", passwordCheck);

            if (password !== '' && passwordCheck === '') {
                null;
            } else if (password !== '' || passwordCheck !== '') {
                if (password === passwordCheck) {
                    $("#alert-success").css('display', 'inline-block');
                    $("#alert-danger").css('display', 'none');
                } else {
                    alert("비밀번호가 일치하지 않습니다. 비밀번호를 재확인해주세요.");
                    $("#alert-success").css('display', 'none');
                    $("#alert-danger").css('display', 'inline-block');
                }
            }
        });
    });
</script>
</body>
</html>
