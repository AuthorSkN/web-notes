'use strict';

class ListController {

    constructor(){
        this.notesModel = new NotesModel();
        this.foldersModel = new FoldersModel();
        this.presenter = new ListPresenter(this.foldersModel, this.notesModel, 
            (type, name, inFolder) => {
                if (type === "note") {
                    this.createNewNote(inFolder, name);
                } else {
                    this.createNewFolder(name);
                }
            },
            (type, item, parentFolder) => {
                if (type === "note"){
                    this.removeNote(item);
                } else {
                    this.removeFolder(parentFolder, item);
                }
            });
    }


    loadFullList(){
        this.presenter.drawAllContent();
    }

    loadNotes() {

    }

    createNewFolder(name) {
        alert("getRequest?name="+name);
        this.foldersModel.folders.push({'name':name});
        this.presenter.drawAllContent();
    }

    createNewNote(folder, name) {
        alert("getRequest?name="+name+"&folder="+folder);
        this.notesModel.notes.push({'name':name});
        this.presenter.drawAllContent();
    }


    removeFolder(folder) {
        alert("getRequest?folder="+folder);
    }

    removeNote(folder, note) {
        alert("getRequest?folder="+folder+"&note="+note);
    }

}