'use strict';

class ListController {

    constructor(isJSON){
        window.isJSON = isJSON;
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

    clearing(hackXML) {
        const sp = hackXML.indexOf("undefined");
        const ep = sp + 9;
        return hackXML.substring(0, sp) + hackXML.substr(ep);
    }

    getHeader() {
        return window.isJSON
            ? {
                Accept: "application/json; charset=utf-8",
                "Content-Type": "application/json; charset=utf-8"
            }
            : {
                Accept: "application/xml; charset=utf-8",
                "Content-Type": "application/xml; charset=utf-8"
            };
    }


    loadFullList(key){
        $.ajax({
            url:"/loadAll",
            headers: this.getHeader(),
            success : (data) => {
                if (!window.isJSON) {
                    const json = xml2json(data);
                    const fixData = JSON.parse(this.clearing(json)).ListDto;
                    this.notesModel.setData(fixData, false);
                } else {
                    this.notesModel.setData(data, true);
                }
                if (typeof key === "undefined") {
                    this.presenter.drawAllContent();
                } else {
                    this.presenter.drawGroupContent(key);
                }
            }
        });

    }

    createNewGroup(name) {
        $.ajax({
            url:"/addGroup",
            headers: this.getHeader(),
            data: {name: name},
            success : (data) => {
                if (!window.isJSON) {
                    const json = xml2json(data);
                    const fixData = JSON.parse(this.clearing(json)).GroupDto;
                    this.notesModel.addGroup(fixData);
                } else {
                    this.notesModel.addGroup(data);
                }
                this.presenter.drawAllContent();
            }
        });
    }

    createNewNote(groupKey, name) {
        $.ajax({
            url:"/addNote",
            headers: this.getHeader(),
            data: {name: name, group: groupKey},
            success : (data) => {
                if (!window.isJSON) {
                    const json = xml2json(data);
                    const fixData = JSON.parse(this.clearing(json)).NoteHeaderDto;
                    this.notesModel.addNote(fixData);
                } else {
                    this.notesModel.addNote(data);
                }
                this.presenter.drawAllContent();
            }
        });
    }


    removeGroup(groupKey) {
        $.ajax({
            url:"/deleteGroup",
            headers: this.getHeader(),
            data: {key: groupKey},
            success : (data) => {
                if (!window.isJSON) {
                    const json = xml2json(data);
                    const fixData = JSON.parse(this.clearing(json)).Integer;
                    this.notesModel.removeGroup(fixData);
                } else {
                    this.notesModel.removeGroup(data);
                }
                this.presenter.drawAllContent();
            }
        });
    }

    removeNote(noteKey) {
        $.ajax({
            url:"/deleteNote",
            headers: this.getHeader(),
            data: {key: noteKey},
            success : (data) => {
                if (!window.isJSON) {
                    const json = xml2json(data);
                    const fixData = JSON.parse(this.clearing(json)).NoteHeaderDto;
                    fixData.parentKey = Number(fixData.parentKey);
                    this.notesModel.removeNote(fixData);
                } else {
                    this.notesModel.removeNote(data);
                }
                this.presenter.drawAllContent();
            }
        });
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