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
    <title>게시판 수정</title>
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

<h2>게시판 - 수정</h2>
<br>
<h3>기본정보</h3>

<div class="container">
    <div class="row">
        <form id="form" action="/board/modify" method="post" enctype="multipart/form-data">
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
            <input type='hidden' id='id' name='id' value='<c:out value="${board.id}"/>'>
            <input type='hidden' name='pageNum' value='<c:out value="${cri.pageNum}"/>'>
            <input type='hidden' name='amount' value='<c:out value="${cri.amount}"/>'>
            <input type='hidden' name='keyword' value='<c:out value="${cri.keyword}"/>'>
            <input type='hidden' name='type' value='<c:out value="${cri.type}"/>'>

            <button data-oper='cancel' class="btn btn-info">취소</button>
            <button data-oper='modify' class="btn btn-primary pull-left">수정</button>
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

    $('button').on("click", function (e) {
        e.preventDefault();
        var operation = $(this).data("oper");
        console.log("operation: ", operation);

        if (operation === 'cancel') {
            //move to list
            formObj.attr("action", "/board/get").attr("method", "get");
            var boardIdTag = $("input[name='id']").clone();
            var pageNumTag = $("input[name='pageNum']").clone();
            var amountTag = $("input[name='amount']").clone();
            var keywordTag = $("input[name='keyword']").clone();
            var typeTag = $("input[name='type']").clone();

            formObj.empty();

            formObj.append(boardIdTag);
            formObj.append(pageNumTag);
            formObj.append(amountTag);
            formObj.append(keywordTag);
            formObj.append(typeTag);

            formObj.submit();
        } else if (operation === 'modify') {
            console.log("modify clicked");

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

        // formObj.submit();

    });

});
</script>
<script>
$(document).ready(function() {
    (function(){

        var boardId = '<c:out value="${board.id}"/>';
        console.log("boardId: ", boardId)

        $.getJSON("/board/getAttachList", {boardId: boardId}, function(arr){

            console.log(arr);

            var str = "";


            $(arr).each(function(i, attach){

                //image type
                if(attach.image){
                    var fileCallPath =  encodeURIComponent( attach.uploadPath+ "/"+attach.uuid +"_"+attach.fileName);

                    str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
                    str +=" data-filename='"+attach.fileName+"' data-type='"+attach.image+"'>";
                    str += "<div>";
                    str += "<span> "+ attach.fileName+"</span>";
                    str += "<button data-oper='download' data-file=\'"+fileCallPath+"\' data-type='image' "
                    str += " class='btn btn-warning btn-circle'>Download</button>";
                    str += "<button data-oper='delete' data-file=\'"+fileCallPath+"\' data-type='image' ";
                    str += "class='btn btn-warning btn-circle'>X</button><br>";
                    str += "<img src='/display?fileName="+fileCallPath+"'>";
                    str += "</div>";
                    str +"</li>";
                }else{

                    str += "<li data-path='"+attach.uploadPath+"' data-uuid='"+attach.uuid+"' "
                    str += "data-filename='"+attach.fileName+"' data-type='"+attach.image+"'>";
                    str += "<div>";
                    str += "<span> "+ attach.fileName+"</span>";
                    str += "<button data-oper='download' data-file=\'"+fileCallPath+"\' data-type='file' "
                    str += " class='btn btn-warning btn-circle'>Download</button>";
                    str += "<button data-oper='delete' data-file=\'"+fileCallPath+"\' data-type='file' ";
                    str += "class='btn btn-warning btn-circle'>X</button><br>";
                    str += "<img src='/resources/img/attach.png'></a>";
                    str += "</div>";
                    str +"</li>";
                }
            });

            $(".uploadResult ul").html(str);

        });//end getjson
    })();//end function

// 수정시 화면상 파일 삭제, 업로드 폴더 삭제는 하지 않음. -> 수정버튼 누를시, 업로드 폴더 삭제케 서버에서 처리
    $(".uploadResult").on("click", "button", function(e){

        var operation = $(this).data("oper");
        console.log("operation: ", operation);
        // 파일 '화면상' 삭제
        if (operation === 'delete') {
            console.log("delete file");
            if(confirm("파일을 삭제하시겠습니까?? ")){

                var targetLi = $(this).closest("li");
                targetLi.remove();
            }
        }
        // 파일 다운로드
        if (operation === 'download') {
            console.log("download file");
            var liObj = $(this).closest("li");

            var path = encodeURIComponent(liObj.data("path")+"/" + liObj.data("uuid")+"_" + liObj.data("filename"));
            // if(liObj.data("type")){
            //     showImage(path.replace(new RegExp(/\\/g),"/"));
            // }else {
            //     //download
            //     self.location ="/download?fileName="+path
            // }
            console.log("path: ", path)
            // 바로 다운로드
            self.location ="/download?fileName="+path
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
                str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'";
                str +=" ><div>";
                str += "<span> "+ obj.fileName+"</span>";
                str += "<button type='button' data-file=\'"+fileCallPath+"\' ";
                str += "data-type='image' class='btn btn-warning btn-circle'>X</button><br>";
                str += "<img src='/display?fileName="+fileCallPath+"'/>";
                str += "</div>";
                str +"</li>";
            }else{
                var fileCallPath =  encodeURIComponent( obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName);
                var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
                str += "<li data-path='"+obj.uploadPath+"'";
                str +=" data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.image+"'";
                str +=" ><div>";
                str += "<span> "+ obj.fileName+"</span>";
                str += "<button type='button' data-file=\'"+fileCallPath+"\' data-type='file' ";
                str += "class='btn btn-warning btn-circle'>X</button><br>";
                str += "<img src='/resources/img/attach.png'/>";
                str += "</div>";
                str +"</li>";
            }

        });

        uploadUL.append(str);
    }

});
</script>

</body>
</html>
