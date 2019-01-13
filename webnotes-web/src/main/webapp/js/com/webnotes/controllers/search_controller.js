'use strict';

class SearchController {

	constructor() {
		this.notesModel = new NotesModel();
		this.searchPresenter = new SearchPresenter(this.notesModel);
	}

	searchNotes(searchString) {
		$.get("/globalSearch",
			{sstr: searchString},
            (data) => {
		        this.notesModel.setData(data);
                this.searchPresenter.drawFoundNotes();
            });
	}

}