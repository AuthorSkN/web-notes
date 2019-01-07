<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./patterns/simple_header.html" %>
<script lang="javascript" src="./js/com/webnotes/controllers/search_controller.js"></script>
<script lang="javascript" src="./js/com/webnotes/models/notes_model.js"></script>
<script lang="javascript" src="./js/com/webnotes/presenters/search_presenter.js"></script>

<section class="container" id="main-section">
    <div class="row">
        <div class="input-group input-group-lg mb-3">
            <div class="input-group-prepend">
                <button class="btn btn-outline-success" type="button" id="global-search">Search</button>
            </div>
            <input type="text" class="form-control" placeholder="input search string" aria-label="input search"
                   aria-describedby="input search" id="input-search-string">
        </div>
        <section class="col-10" id="content-section">
            <div id="notes-content">
            </div>
        </section>
    </div>
</section>

<script lang="javascript">
    let searchController = new SearchController();
    $("#global-search").click(() => {
        let str = $("#input-search-string").val().trim();
        searchController.searchNotes(str);
    })

    let searchStringParamValue = "<c:out value="${param['sstr']}"/>".trim();
    $("#input-search-string").val(searchStringParamValue);
    if (searchStringParamValue !== "") {
        searchController.searchNotes(searchStringParamValue);
    }
</script>

<%@ include file="./patterns/footer.html" %>