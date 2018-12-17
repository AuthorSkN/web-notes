'use strict';

class NotesModel {

    constructor(){
        this.notes = {};
        this.groups = {};

        for(let i = 0; i < 10; i++){
            this.notes[i.toString()] = new Note(i, name, null);
            this.group[i.toString()] = new Group(i, name);
        }

        for(let group in this.groups) {
        	group.notes = [
        		new Note("10", )
        	];
        }
    }


}

class Note {

	constructor(key,name, parent) {
		this.key = key;
		this.name = name;
		this.parent = parent;
	}
}

class Group {

	constructor(key, name) {
		this.key = key;
		this.name = name;
		this.notes = [];
	}
}