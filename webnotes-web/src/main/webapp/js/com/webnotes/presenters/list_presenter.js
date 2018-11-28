'use strict';

class ListPresenter {

    constructor(foldersModel, notesModel) {
        this.foldersModel = foldersModel;
        this.notesModel = notesModel;
        this.contentSection = $("#content-section");
    }

    drawAll() {
        let folders = foldersModel.folders;
        this.contentSection.empty();
        this.contentSection.append("<ul class='list-group folders-list'>");
        for (let folder of folders) {

        }
    }
}