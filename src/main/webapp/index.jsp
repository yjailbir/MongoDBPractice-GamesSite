<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link
            rel="stylesheet"
            href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
            integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
            crossorigin="anonymous"
    />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Log in</title>
</head>
<body>
<br/>
<form action="main" method="post">
    <h3>Login</h3>
    <input type="text" name="login" class="form-control"><br>
    <h3>Password</h3>
    <input type="text" name="password" class="form-control">
    <hr>
    <input type="hidden" name="operation" value="1">
    <input type="submit" value="enter" name="button" class="btn btn-dark">
    <input type="submit" value="register" name="button" class="btn btn-dark">
</form>
</body>
</html>