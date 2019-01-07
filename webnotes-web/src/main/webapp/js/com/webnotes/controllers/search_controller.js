'use strict';

class SearchController {

	constructor() {
		this.notesModel = new NotesModel();
		this.searchPresenter = new SearchPresenter(this.notesModel);
	}

	searchNotes(searchString) {
		$.get("search-controller",
			{sstr: searchString},
            (data) => {
		        this.notesModel.setData(JSON.parse(data));
                this.searchPresenter.drawFoundNotes();
            });
	}

}