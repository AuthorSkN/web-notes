
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заметки</title>
    <script lang="javascript" src="./js/thirdparty/jquery-3.3.1.min.js"></script>
    <script lang="javascript" src="./js/com/webnotes/controllers/list_controller.js"></script>
</head>
<body>
    <%@ include file = "./patterns/header.html" %>

    <section id="main-section">
        <div id="base-action-section">
            <button>Создать</button>
        </div>
        <section id="content-section">
            Контент
        </section>
    </section>

    <%@ include file = "./patterns/footer.html" %>

</body>
</html>
