'use strict';

class ListPresenter {

    constructor(foldersModel, notesModel) {
        this.foldersModel = foldersModel;
        this.notesModel = notesModel;
        this.foldersContentSection = $("#content-section #folders-content");
        this.notesContentSection = $("#content-section #notes-content");
        this.selectedFolderIdx = null;
        this.selectedNoteIdx = null;
    }

    drawAll() {
        let folders = this.foldersModel.folders;
        this.foldersContentSection.empty();
        this.foldersContentSection.append("<ul class='list-group folders-list'>");
        let foldersSection = this.foldersContentSection.find(".folders-list");
        foldersSection.append("<li class='list-group-item active'><h3>Folders</h3></li>");
        for (let folder of folders) {
            foldersSection.append("<li class='list-group-item list-group-item-action'>" +
                "<span class='list-name '>" + folder.name + "</span>" +
                "<span class='list-act '></span>" +
                "</li>");
        }
        let liNameFolderSet = $(".folders-list li");
        liNameFolderSet.click(event => {
            this.addFolderActions(event.currentTarget);
        });
        liNameFolderSet.dblclick(()=> alert("hello"));

        this.drawNotes();
    }

    drawNotes() {
        let notes = this.notesModel.notes;
        this.notesContentSection.empty();
        this.notesContentSection.append("<h3>Notes</h3>");
        this.notesContentSection.append("<ul class='list-group list-group-flush notes-list'>");
        let notesSection = this.notesContentSection.find(".notes-list");
        for (let note of notes) {
            notesSection.append("<li class='list-group-item list-group-item-action '>" +
                                          "<span class='list-name'>" + note.name + "</span>"+
                                          "<span class='list-act '></span></li>");
        }
        let liNameNoteSet = $(".notes-list li");
        liNameNoteSet.click(event => {
            this.addNoteActions(event.currentTarget);
        });
        liNameNoteSet.dblclick(() => alert("hello2"));
    }

    addFolderActions(element) {
        let foldersList = $(".folders-list li");
        let idxInList = foldersList.index(element);
        if (idxInList === this.selectedFolderIdx) {
            return;
        }
        if (this.selectedFolderIdx !== null) {
            let pastSelectedEl =foldersList.eq(this.selectedFolderIdx);
            pastSelectedEl.find(".list-act").empty();
        }
        this.selectedFolderIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
    }

    addNoteActions(element) {
        let notesList = $(".notes-list li");
        let idxInList = notesList.index(element);
        if (idxInList === this.selectedNoteIdx) {
             return;
        }
        if (this.selectedNoteIdx !== null) {
             let pastSelectedEl = notesList.eq(this.selectedNoteIdx);
             pastSelectedEl.find(".list-act").empty();
        }
        this.selectedNoteIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
    }

    removeFolderActions() {
        if(event.target.parentElement == this) {
            return;
        }

        $(this).find(".list-act").empty();
    }

}