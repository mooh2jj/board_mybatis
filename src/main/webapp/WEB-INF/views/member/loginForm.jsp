<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false"
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ include file="../includes/header.jsp" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form>
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="email"
                                       id="email" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password"
                                       id="password" type="password" value="">
                            </div>
                            <div class="checkbox">
                                <label> <input name="remember-me" type="checkbox">Remember
                                    Me
                                </label>
                            </div>
                            <!-- Change this to a button or input when using this as a form -->
                        </fieldset>
<%--                        <input type="hidden" name="${_csrf.parameterName}"--%>
<%--                               value="${_csrf.token}" />--%>
                    </form>
                    <button id ="btn-login" class="btn btn-primary">로그인</button>

                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#btn-login").on("click", function (e) {

            let data = {
                email: $("#email").val(),
                password: $("#password").val(),
            }
            console.log("data: ",data);

            $.ajax({
                type: "post",
                url: "/member/login",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌!
                success: function (res) {
                    //alert(resp);
                    console.log("res: ", res);
                    alert("로그인 되었습니다!!");
                    location.replace("/board/list");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR);  //응답 메시지
                    console.log(textStatus); //"error"로 고정인듯함
                    console.log(errorThrown);
                    alert("로그인이 실패하였습니다!");
                },
            });

        });
    });
</script>

<%@include file="../includes/footer.jsp"%>
