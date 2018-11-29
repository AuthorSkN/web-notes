'use strict';

class ListController {

    constructor(){
        this.notesModel = new NotesModel();
        this.folderModel = new FoldersModel();
        this.presenter = new ListPresenter(this.folderModel, this.notesModel);
    }


    loadFullList(){
        this.presenter.drawAll();
    }

    loadNotes() {

    }


}