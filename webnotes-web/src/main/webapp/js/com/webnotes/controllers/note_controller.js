'use strict';

class NoteController {

    constructor(noteKey) {
        this.noteModel = new NoteContentModel();
        this.presenter = new NotePresenter(this.noteModel,
            (parentKey) => {
                this.toBack(parentKey);
            },
            (actionKey, complete) => {
                this.checkAction(actionKey, complete);
            },
            (name, text, actionTexts) => {
                this.changeNoteContent(name, text, actionTexts);
            });
        this.noteKey = noteKey;
    }

    loadNoteContent() {
        $.get("/loadNote",
            {key: this.noteKey},
            (data) => {
                this.noteModel.setNoteContent(data);
                this.presenter.drawNoteContent();
            });
    }

    toBack(parentKey) {
        location.href='show_list.jsp?key='+parentKey;
    }

    changeNoteContent(name, text, actionTexts) {
        $.get("/changeNote",
            {key: this.noteKey, name: name, text: text, actions: actionTexts},
            (data) => {
                this.noteModel.setNoteContent(data);
                this.presenter.drawNoteContent();
            }
        );
    }

    checkAction(actionKey, complete) {
        $.get("/checkAction",
            {key: this.noteKey, action: actionKey, complete: complete}
        );
    }





}