'use strict';

class NoteContentModel {

    constructor() {
        this.noteKey = 0;
        this.name = "name";
        this.text = "text";
        this.actions = {};
        this.actions["0"] = new Action("0", false, "action text");
        this.actions["1"] = new Action("1", true, "action text 2");
        this.actions["2"] = new Action("2", false, "action text3");
    }

    setNoteContent(data) {

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