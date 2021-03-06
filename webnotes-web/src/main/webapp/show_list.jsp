<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./patterns/header.html" %>
<script lang="javascript" src="./js/com/webnotes/controllers/list_controller.js"></script>
<script lang="javascript" src="./js/com/webnotes/models/notes_model.js"></script>
<script lang="javascript" src="./js/com/webnotes/presenters/list_presenter.js"></script>

<section class="container" id="main-section">
    <div class="row">
        <section class="col-2" id="base-action-section">

        </section>
        <section class="col-10" id="content-section">
            <div id="groups-content">
            </div>
            <div id="notes-content">
            </div>
        </section>
    </div>
</section>

<%@ include file="./patterns/list_modal_wins.html" %>

<script>
    let key = "<c:out value="${param['key']}"/>";
    let listController = new ListController();
    if ((key === "") || (Number(key) === -1)) {
        listController.loadFullList();
    } else {
        listController.loadFullList(Number(key));
    }
</script>

<%@ include file="./patterns/footer.html" %>

