'use strict';

class SearchPresenter {

	constructor(notesModel) {
		this.notesModel = notesModel;
		this.notesContentSection = $("#notes-content");
		this.noteIdxToKey = {};
	}


	drawFoundNotes() {
		let notes = this.notesModel.notes;
		this.notesContentSection.empty();
        this.notesContentSection.append("<ul class='list-group list-group-flush notes-list'>");
        let notesSection = this.notesContentSection.find(".notes-list");
        let noteIdx = 0;
        for (let noteKey in notes) {
            this.noteIdxToKey[noteIdx++] = noteKey;
            notesSection.append("<li class='list-group-item list-group-item-action '>" +
                                          "<span class='list-name'>" + notes[noteKey].name + "</span>"+
                                          "<span class='list-act '></span></li>");
        }
        let liNameNoteSet = $(".notes-list li");
        liNameNoteSet.dblclick(() => alert("hello2"));
	}

}