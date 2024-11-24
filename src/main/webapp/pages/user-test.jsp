<%--
  Created by IntelliJ IDEA.
  User: lizbr
  Date: 24/11/2024
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Usuário</title>
</head>
<body>
<h1>Cadastro de Usuário</h1>

<form action="${pageContext.request.contextPath}/UserRegisterServlet" method="POST">
    <div>
        <label for="username">Nome de Usuário</label>
        <input type="text" id="username" name="username">
    </div>

    <div>
        <label for="email">E-mail</label>
        <input type="email" id="email" name="email">
    </div>

    <div>
        <label for="pathProfilePicture">Foto de Perfil (URL)</label>
        <input type="text" id="pathProfilePicture" name="pathProfilePicture">
    </div>

    <div>
        <label for="biografy">Biografia</label>
        <textarea id="biografy" name="biografy"></textarea>
    </div>

    <div>
        <label for="password">Senha</label>
        <input type="password" id="password" name="password">
    </div>

    <div>
        <button type="submit">Cadastrar</button>
    </div>
</form>

<c:if test="${not empty result}">
    <p>
        <c:choose>
            <c:when test="${result == 'registered'}">
                Usuário registrado com sucesso!
            </c:when>
            <c:when test="${result == 'notRegistered'}">
                Erro ao registrar usuário. Tente novamente.
            </c:when>
        </c:choose>
    </p>
</c:if>
</body>
</html>

