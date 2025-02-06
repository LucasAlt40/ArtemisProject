<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 24/11/24
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:root>
    <jsp:body>
        <div class="container">
            <div class="row justify-content-center align-items-center vh-100">
                <form action="${pageContext.request.contextPath}/ControllerServlet" method="POST" class="col-3 needs-validation" novalidate>
                    <h3 class="text-center">Artemis</h3>
                    <div class="mb-3">
                        <input type="email" class="form-control" id="email" name="email" placeholder="email" required>
                        <div class="invalid-feedback">
                            Preencha o campo.
                        </div>
                    </div>
                    <div class="mb-3">
                        <input type="password" class="form-control" id="password" name="password" placeholder="senha" required>
                        <div class="invalid-feedback">
                            Preencha o campo.
                        </div>
                    </div>
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-primary" name="action" value="signIn">Acessar</button>
                        <a href="${pageContext.request.contextPath}/signup.jsp" class="link-underline text-center">Cadastre-se aqui</a>
                    </div>
                </form>
            </div>
        </div>
    </jsp:body>
</t:root>