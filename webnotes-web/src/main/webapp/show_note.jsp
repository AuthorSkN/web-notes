<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./patterns/header.html" %>
<script lang="javascript" src="./js/com/webnotes/controllers/note_controller.js"></script>
<script lang="javascript" src="./js/com/webnotes/models/note_content_model.js"></script>
<script lang="javascript" src="./js/com/webnotes/presenters/note_presenter.js"></script>

<section class="container" id="main-section">
    <div class="row">
        <section class="col-2" id="base-action-section">

        </section>
        <section class="col-10" id="content-section">

        </section>
    </div>
</section>
<%@ include file="./patterns/note_show_wins.html" %>

<script>
    let noteKey = Number(<c:out value="${param['key']}"/>);
    let noteController = new NoteController(noteKey);
    noteController.loadNoteContent();
</script>

<%@ include file="./patterns/footer.html" %>