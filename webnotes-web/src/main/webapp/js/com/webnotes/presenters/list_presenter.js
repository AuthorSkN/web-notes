'use strict';

class ListPresenter {

    constructor(foldersModel, notesModel) {
        this.foldersModel = foldersModel;
        this.notesModel = notesModel;
        this.foldersContentSection = $("#content-section #folders-content");
        this.notesContentSection = $("#content-section #notes-content");
    }

    drawAll() {
        let folders = this.foldersModel.folders;
        this.foldersContentSection.empty();
        this.foldersContentSection.append("<ul class='list-group folders-list'>");
        let foldersSection = this.foldersContentSection.find(".folders-list");
        foldersSection.append("<li class='list-group-item active row'><h3>Folders</h3></li>");
        for (let folder of folders) {
            foldersSection.append("<li class='list-group-item list-group-item-action row'>" +
                "<span class='list-name col-10'>" + folder.name + "</span>" +
                "<span class='list-act col-2'></span>" +
                "</li>");
        }
        let liNameFolderSet = $(".folders-list li");
        liNameFolderSet.mouseover(this.addFolderActions);
        liNameFolderSet.mouseout(this.removeFolderActions);

        this.drawNotes();
    }

    drawNotes() {
        let notes = this.notesModel.notes;
        this.notesContentSection.empty();
        this.notesContentSection.append("<h3>Notes</h3>");
        this.notesContentSection.append("<ul class='list-group list-group-flush notes-list'>");
        let notesSection = this.notesContentSection.find(".notes-list");
        for (let note of notes) {
            notesSection.append("<li class='list-group-item list-group-item-action '>" + note.name + "</li>");
        }
    }

    addFolderActions(event) {
        if(event.target.parentElement == this) {
            return;
        }
        let element = $(this).find(".list-act");
        element.append("<span>Edit</span>");
        element.append("<spam>Remove</spam>");
    }

    removeFolderActions(event) {
        if(event.target.parentElement == this) {
            return;
        }

        $(this).find(".list-act").empty();
    }

    addNoteActions() {

    }

    removeNoteActions() {

    }
}