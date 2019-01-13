'use strict';

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
            },
            (groupKey, newGroupName) => {
                this.editGroupName(groupKey, newGroupName);
            },
            (noteKey) => {
                this.toNotePage(noteKey);
            });

    }


    loadFullList(key){
        $.get("/loadAll",
            (data) => {
                this.notesModel.setData(data);
                if (typeof key === "undefined") {
                    this.presenter.drawAllContent();
                } else {
                    this.presenter.drawGroupContent(key);
                }
            }
        );
    }

    createNewGroup(name) {
        $.get("/addGroup",
            {name: name},
            (data) => {
                this.notesModel.addGroup(data);
                this.presenter.drawAllContent();
            }
        );
    }

    createNewNote(groupKey, name) {
        alert("getRequest?name="+name+"&group="+groupKey);
        $.get("/addNote",
            {name: name, group: groupKey},
            (data) => {
                this.notesModel.addNote(data);
                this.presenter.drawAllContent();
            }
        );
    }


    removeGroup(groupKey) {
        $.get("/deleteGroup",
            {key: groupKey},
            (data) => {
                this.notesModel.removeGroup(data);
                this.presenter.drawAllContent();
            }
        );
    }

    removeNote(noteKey) {
        $.get("/deleteNote",
            {key: noteKey},
            (data) => {
                this.notesModel.removeNote(data);
                this.presenter.drawAllContent();
            }
        );
    }

    editGroupName(groupKey, newGroupName) {
        $.get("/editGroupName",
            {key: groupKey, name: newGroupName },
            () => {
                this.notesModel.changeGroupName(groupKey, newGroupName);
                this.presenter.drawGroupContent(groupKey);
            }
        );
    }

    toNotePage(noteKey) {
        location.href='show_note.jsp?key='+noteKey;
    }

}