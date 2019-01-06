'use strict';

const OPERATION_LOAD = 0;
const OPERATION_CHECK_ACTION = 1;
const OPERATION_CHANGE_NOTE = 2;

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
        $.get("note-controller",
            {oper: OPERATION_LOAD, key: this.noteKey},
            (data) => {
                this.noteModel.setNoteContent(JSON.parse(data));
                this.presenter.drawNoteContent();
            });
    }

    toBack(parentKey) {
        location.href='show_list.jsp?key='+parentKey;
    }

    changeNoteContent(name, text, actionTexts) {
        $.get("note-controller",
            {oper: OPERATION_CHANGE_NOTE, key: this.noteKey, name: name, text: text, actions: actionTexts},
            (data) => {
                this.noteModel.setNoteContent(JSON.parse(data));
                this.presenter.drawNoteContent();
            }
        );
    }

    checkAction(actionKey, complete) {
        $.get("note-controller",
            {oper: OPERATION_CHECK_ACTION, key: this.noteKey, action: actionKey, complete: complete}
        );
    }





}