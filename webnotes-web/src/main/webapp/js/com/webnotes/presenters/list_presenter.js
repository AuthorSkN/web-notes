'use strict';

class ListPresenter {

    constructor(foldersModel, notesModel, callbackCreateFunction, callbackRemoveFunction) {
        this.foldersModel = foldersModel;
        this.notesModel = notesModel;
        this.foldersContentSection = $("#content-section #folders-content");
        this.notesContentSection = $("#content-section #notes-content");
        this.selectedFolderIdx = null;
        this.selectedNoteIdx = null;
        this.isFolderSelected = false;
        this.inFolder = null;
        this.callbackCreateFunction = callbackCreateFunction;
        this.callbackRemoveFunction = callbackRemoveFunction;

        $("#createNewItem").click(() => {
            this.createNewFolderOrNote();
        });
    }

    drawFolderContent() {

    }

    drawAllContent() {
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

        this.drawNotes("Notes", this.notesModel.notes);

        this.addCreateButton(() => {
            $('#createModal').modal();
        });
    }

    drawNotes(headers, notes) {
        this.notesContentSection.empty();
        this.notesContentSection.append("<h3>"+headers+"</h3>");
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
        this.isFolderSelected = true;
        this.removeActions();
        this.selectedFolderIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
        $(acts).find(".btn-outline-danger").click(() => {
            if (confirm("Do you want remove this folder?")) {
                let folderId = this.selectedFolderIdx;
                this.callbackRemoveFunction("folder", folderId, this.inFolder);
            }
        });
    }

    addNoteActions(element) {
        let notesList = $(".notes-list li");
        let idxInList = notesList.index(element);
        if (idxInList === this.selectedNoteIdx) {
             return;
        }
        this.isFolderSelected = false;
        this.removeActions();
        this.selectedNoteIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
        $(acts).find(".btn-outline-danger").click(() => {
             if (confirm("Do you want remove this note?")) {
                let noteId = this.selectedNoteIdx;
                this.callbackRemoveFunction("note", noteId, this.inFolder);
            }
        });
    }

    removeActions() {
        $("#content-section .list-act").empty();
    }

    addCreateButton(eventFunction) {
        $("#base-action-section").empty().append(" <button class='btn btn-outline-success'>Create</button>");
        $("#base-action-section .btn-outline-success").click(eventFunction);
    }

    createNewFolderOrNote() {
        let inputName = $('#inputName').val().trim();
        if (inputName === "") {
            alert("Name can't be empty.");
        } else {
            $('#createModal').modal('hide');
            let typeItem = $('#createModal input[name="options"]:checked').val();
            this.callbackCreateFunction(typeItem, inputName, this.inFolder);
        }
        
    }

}