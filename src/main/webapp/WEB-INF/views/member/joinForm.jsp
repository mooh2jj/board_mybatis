<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         isELIgnored="false"
%>

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
                        <!-- <form action="/user/join" method="post"> 옛날 방식 -> json으로 바꿈 -->
                        <div class="form-group">
                            <label for="name">name</label>
                            <input type="text" class="form-control" placeholder="Enter name" id="name">
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" placeholder="Enter password" id="password">
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" placeholder="Enter email" id="email">
                        </div>
                    </form>
                    <button id ="btn-save" class="btn btn-primary">회원가입</button>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script type="text/javascript">
    $(document).ready(function () {

        $("#btn-save").on("click", () => {	// function(){}, ()=> 하는 이유 : this를 바인딩하기 위해서

            alert("user의 save함수 호출됨");
            let data = {
                name: $("#name").val(),
                email: $("#email").val(),
                password: $("#password").val(),

            }

            $.ajax({
                type: "post",
                url: "/member/join",
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                //dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌!
                success: function (res) {
                    //alert(resp);
                    console.log(res);
                    alert("회원가입이 완료되었습니다!!");
                    location.href = "/board/list";
                }
            });

        });

    });

</script>

<%@include file="../includes/footer.jsp"%>
