'use strict';

class NotesModel {

    constructor(){
        this.notes = {};
        this.groups = {};

        for(let i = 0; i < 10; i++){
            this.notes[i.toString()] = new Note(i, "note "+i.toString(), null);
            this.groups[i.toString()] = new Group(i, "group "+i.toString());
        }

        for(let groupKey in this.groups) {
        	this.groups[groupKey].add(new Note(1,"note 1"));
            this.groups[groupKey].add(new Note(2, "note 1"));
            this.groups[groupKey].add(new Note(3, "note 1"));
            this.groups[groupKey].add(new Note(4, "note 1"));
            this.groups[groupKey].add(new Note(5, "note 1"));
        }
    }


    addNote(key, name, parent) {
    	this.notes[key] = new Note(key, name, parent);
	}

	addGroup(key, name) {
    	this.groups[key] = new Group(key, name);
	}

	getNodeFromGroup(groupKey, noteKey) {
    	return this.groups[groupKey].notes[noteKey];
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
		this.notes = {};
	}

	add(note) {
		note.parent = this.key;
		this.notes[note.key] = note;
	}
}