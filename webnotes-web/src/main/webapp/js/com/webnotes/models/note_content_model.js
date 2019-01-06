'use strict';

class NoteContentModel {

    constructor() {
        this.noteKey = 0;
        this.name = "name";
        this.text = "text";
        this.parentKey = -1;
        this.actions = {};

    }

    setNoteContent(noteDto) {
        this.noteKey = noteDto.key;
        this.name = noteDto.name;
        this.text = noteDto.text;
        this.parentKey = noteDto.parentKey;
        this.actions = {};
        for(let action of noteDto.actions) {
            this.actions[action.key] = new Action(action.key, action.complete, action.text);
        }
    }

    checkAction(actionKey) {
        this.actions[actionKey].complete = !this.actions[actionKey].complete;
        return this.actions[actionKey].complete;
    }

}

class Action {

    constructor(key, complete, text) {
        this.key = key;
        this.complete = complete; 
        this.text = text;
    }

}