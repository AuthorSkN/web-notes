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
            (type, item, parentGroup) => {
                if (type === "note"){
                    this.removeNote(item);
                } else {
                    this.removeGroup(parentGroup, item);
                }
            });
    }


    loadFullList(){
        $.get("hello",
            {name: "max"},
            (data) => alert(JSON.parse(data).notes[0].name)
        );
        this.presenter.drawAllContent();
    }

    loadNotes() {

    }

    createNewGroup(name) {
        alert("getRequest?name="+name);

        this.presenter.drawAllContent();
    }

    createNewNote(folder, name) {
        alert("getRequest?name="+name+"&group="+folder);
        this.notesModel.notes.push({'name':name});
        this.presenter.drawAllContent();
    }


    removeGroup(folder) {
        alert("getRequest?group="+folder);
    }

    removeNote(folder, note) {
        alert("getRequest?group="+folder+"&note="+note);
    }

}