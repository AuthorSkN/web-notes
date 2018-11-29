<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./patterns/header.html" %>
<script lang="javascript" src="./js/com/webnotes/controllers/list_controller.js"></script>
<script lang="javascript" src="./js/com/webnotes/models/folders_model.js"></script>
<script lang="javascript" src="./js/com/webnotes/models/notes_model.js"></script>
<script lang="javascript" src="./js/com/webnotes/presenters/list_presenter.js"></script>

<section class="container" id="main-section">
    <div class="row">
        <section class="col-2" id="base-action-section">
            <button class="btn btn-outline-success">Создать</button>
        </section>
        <section class="col-10" id="content-section">
            <div id="folders-content">
                <ul class="list-group folders-list">
                    <li class="list-group-item active"><h3>Folders</h3></li>
                    <li class="list-group-item list-group-item-action">Dapibus ac facilisis in</li>
                    <li class="list-group-item list-group-item-action">Morbi leo risus</li>
                    <li class="list-group-item list-group-item-action">Porta ac consectetur ac</li>
                    <li class="list-group-item list-group-item-action">Vestibulum at eros</li>
                </ul>
            </div>

            <hr>

            <div id="notes-content">
                <h3>Notes</h3>
                <ul class="list-group list-group-flush notes-list">
                    <li class="list-group-item list-group-item-action ">Cras justo odio</li>
                    <li class="list-group-item list-group-item-action ">Dapibus ac facilisis in</li>
                    <li class="list-group-item list-group-item-action ">Morbi leo risus</li>
                    <li class="list-group-item list-group-item-action ">Porta ac consectetur ac</li>
                    <li class="list-group-item list-group-item-action ">Vestibulum at eros</li>
                </ul>
            </div>

        </section>
    </div>
</section>

<script>
    let listController = new ListController();
    listController.loadFullList();
</script>

<%@ include file="./patterns/footer.html" %>

