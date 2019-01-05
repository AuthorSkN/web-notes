'use strict';

class NoteController {

    constructor(noteKey) {
        this.noteModel = new NoteContentModel();
        this.presenter = new NotePresenter(this.noteModel);
        this.noteKey = noteKey;
    }

    loadNoteContent() {
    	this.presenter.drawNoteContent();
    }

    changeNoteContent() {

    }





}