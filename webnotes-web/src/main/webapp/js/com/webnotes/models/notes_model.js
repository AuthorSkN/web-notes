'use strict';

class NotesModel {


    constructor() {
        this.notes = {};
        this.groups = {};
	}

	setData(dto, isJSON) {
        const notesHandler = (notes) => {
        	if (notes.length) {
                for (let note of notes) {
                    this.notes[note.key] = new Note(note.key, note.name, null);
                }
            } else {
                this.notes[notes.key] = new Note(notes.key, notes.name, null);
            }
        };

		if (isJSON) {
            notesHandler(dto.notes);
			for (let group of dto.groups) {
				this.groups[group.key] = new Group(group.key, group.name);
				for (let note of group.notes) {
					this.groups[group.key].add(note);
				}
			}
        } else {
            notesHandler(dto.notes.notes);
            if (dto.groups.groups.length) {
                for (let group of dto.groups.groups) {
                    this.groups[group.key] = new Group(group.key, group.name);
                    if (!group.notes) continue;
                    if (group.notes.length) {
                        for (let note of group.notes) {
                            this.groups[group.key].add(note);
                        }
                    } else {
                        this.groups[group.key].add(group.notes);
                    }
                }
			} else {
            	const group = dto.groups.groups;
                this.groups[group.key] = new Group(group.key, group.name);
                if (group.notes.length) {
                    for (let note of group.notes) {
                        this.groups[group.key].add(note);
                    }
                } else {
                    this.groups[group.key].add(group.notes);
                }
			}
        }
    }


    addNote(noteDto) {
        if (noteDto.parentKey === -1 ) {
            this.notes[noteDto.key] = new Note(noteDto.key, noteDto.name, null);
        } else {
        	this.groups[Number(noteDto.parentKey)].add(new Note(noteDto.key, noteDto.name, noteDto.parentKey));
		}
	}

	removeNote(noteDto) {
    	if (noteDto.parentKey === -1) {
            delete this.notes[noteDto.key];
		} else {
    		let group = this.groups[noteDto.parentKey];
    		delete group.notes[noteDto.key];
		}

    }

    removeGroup(groupKey) {
        delete this.groups[groupKey];
    }

	addGroup(groupDto) {
    	this.groups[groupDto.key] = new Group(groupDto.key, groupDto.name);
	}

	changeGroupName(groupKey, newName) {
    	this.groups[groupKey].name = newName;
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