'use strict';

const OPERATION_LOAD = 0;
const OPERATION_ADD_NOTE = 1;
const OPERATION_ADD_GROUP = 2;
const OPERATION_REMOVE_NOTE = 3;
const OPERATION_REMOVE_GROUP = 4;



class ListController {

    constructor(){
        this.notesModel = new NotesModel();
        this.presenter = new ListPresenter(this.notesModel,
            (type, name, inGroup) => {
                if (type === "note") {
                    this.createNewNote(inGroup, name);
                } else {
                    this.createNewGroup(name);
                }
            },
            (type, itemKey, parentGroup) => {
                if (type === "note"){
                    this.removeNote(itemKey);
                } else {
                    this.removeGroup(itemKey);
                }
            });
    }


    loadFullList(){
        $.get("list-controller",
            {oper: OPERATION_LOAD},
            (data) => {
                this.notesModel.setData(JSON.parse(data));
                this.presenter.drawAllContent();
            }
        );
    }

    loadNotes() {

    }

    createNewGroup(name) {
        $.get("list-controller",
            {oper: OPERATION_ADD_GROUP, name: name},
            (data) => {
                this.notesModel.addGroup(JSON.parse(data));
                this.presenter.drawAllContent();
            }
        );
    }

    createNewNote(groupKey, name) {
        alert("getRequest?name="+name+"&group="+groupKey);
        $.get("list-controller",
            {oper: OPERATION_ADD_NOTE, name: name, group: groupKey},
            (data) => {
                this.notesModel.addNote(JSON.parse(data));
                this.presenter.drawAllContent();
            }
        );
    }


    removeGroup(groupKey) {
        $.get("list-controller",
            {oper: OPERATION_REMOVE_GROUP, key: groupKey},
            (data) => {
                this.notesModel.removeGroup(JSON.parse(data));
                this.presenter.drawAllContent();
            }
        );
    }

    removeNote(noteKey) {
        $.get("list-controller",
            {oper: OPERATION_REMOVE_NOTE, key: noteKey},
            (data) => {
                this.notesModel.removeNote(JSON.parse(data));
                this.presenter.drawAllContent();
            }
        );
    }

}