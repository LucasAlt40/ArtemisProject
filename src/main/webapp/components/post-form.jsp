<%--
  Created by IntelliJ IDEA.
  User: caiolopes
  Date: 29/11/24
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="isComment" value="${not empty param.threadId}" />

<form action="${pageContext.request.contextPath}/ControllerServlet" method="POST" enctype="multipart/form-data">
    <div class="card mb-2">
        <div class="card-body">
            <input type="hidden" name="threadId" value="${param.threadId}">
            <input class="form-control mb-1" id="content" name="content" placeholder="Escreva alguma coisa..." />
            <img width="100" id="fileImage"/>
        </div>
        <div class="card-footer d-flex flex-row justify-content-between">
            <label for="imagePost" class="form-label">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="lucide lucide-paperclip">
                    <path d="M13.234 20.252 21 12.3"/>
                    <path d="m16 6-8.414 8.586a2 2 0 0 0 0 2.828 2 2 0 0 0 2.828 0l8.414-8.586a4 4 0 0 0 0-5.656 4 4 0 0 0-5.656 0l-8.415 8.585a6 6 0 1 0 8.486 8.486"/>
                </svg>
            </label>
            <input class="form-control form-control-sm d-none" type="file" id="imagePost" name="imagePost">
            <button class="btn btn-primary btn-sm" name="action" value="addPost">
                <span>Enviar</span>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-send" viewBox="0 0 16 16">
                    <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576zm6.787-8.201L1.591 6.602l4.339 2.76z"/>
                </svg>
            </button>
        </div>
    </div>
</form>

<script>
    const fileImage = document.getElementById("fileImage")
    const input = document.getElementById("imagePost");
    const fileReader = reader = new FileReader();

    input.addEventListener("change", () => {
        if(input.files.length <= 0){
            return;
        }
        fileReader.onload = () => {
            fileImage.src = fileReader.result;
        }
        fileReader.readAsDataURL(input.files[0])
    })
</script>