'use strict';

class NoteContentModel {

    constructor() {
        this.noteKey = 0;
        this.name = "";
        this.text = "";
        this.actions = {};
    }

    setNoteContent(data) {

    }

    checkAction(actionKey) {
        this.actions[actionKey].complete = !this.actions[actionKey].complete;
    }

}