<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
    <!--BOOTSTRAP-->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <%--link ke css local--%>
    <link rel="stylesheet" href="../../resources/css/main.css"/>
    <link href="https://fonts.googleapis.com/css?family=Rouge+Script" rel="stylesheet">
</head>
<body style="background-color: white" onload="document.loginForm.username.focus()">
<div id="login-box">

    <div class="container containerLogin">
        <form class="loginForm" action="/login" name='loginForm' method='POST'>

            <div>
                <h1 class="titleMantapos">mantapos</h1>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <c:if test="${not empty logout}">
                    <div class="alert alert-success">${logout}</div>
                </c:if>
            </div>
            <label><b>Username</b></label>
            <input class="inputLogin" type="text" placeholder="Enter Username" name="username" required>
            <label><b>Password</b></label>
            <input class="inputLogin" type="password" placeholder="Enter Password" name="password" required>
            <button class="buttonSubmit" type="submit" name="submit">LOGIN</button>

            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}" />

        </form>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="../../resources/js/order.js"></script>
</body>
</html>