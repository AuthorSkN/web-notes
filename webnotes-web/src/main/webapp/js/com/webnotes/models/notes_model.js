'use strict';

class NotesModel {


    constructor() {
        this.notes = {};
        this.groups = {};
	}

	setData(dto) {
        for (let note of dto.notes) {
            this.notes[note.key] = new Note(note.key, note.name, null);
        }
        for (let group of dto.groups) {
            this.groups[group.key] = new Group(group.key, group.name);
            for (let note of group.notes) {
                this.groups[group.key].add(note.key, note.name, group.key);
            }
        }
    }


    addNote(noteDto) {
        let parent = (noteDto.parentKey === -1)? null : noteDto.parentKey;
    	this.notes[noteDto.key] = new Note(noteDto.key, noteDto.name, parent);
	}

	removeNote(noteKey) {
        delete this.notes[noteKey];
    }

    removeGroup(groupKey) {
        delete this.groups[groupKey];
    }

	addGroup(groupDto) {
    	this.groups[groupDto.key] = new Group(groupDto.key, groupDto.name);
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