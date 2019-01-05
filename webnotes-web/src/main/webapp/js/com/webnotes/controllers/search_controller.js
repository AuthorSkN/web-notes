'use strict';

class SearchController {

	constructor() {
		this.notesModel = new NotesModel();
		this.searchPresenter = new SearchPresenter(this.notesModel);
	}

	searchNotes(searchString) {
		this.searchPresenter.drawFoundNotes();
	}

}