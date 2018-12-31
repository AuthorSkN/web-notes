'use strict';

class ListPresenter {

    constructor(notesModel, callbackCreateFunction, callbackRemoveFunction) {
        this.notesModel = notesModel;
        this.groupsContentSection = $("#content-section #groups-content");
        this.notesContentSection = $("#content-section #notes-content");
        this.selectedGroupIdx = null;
        this.selectedNoteIdx = null;
        this.isGroupSelected = false;
        this.inGroup = null;
        this.callbackCreateFunction = callbackCreateFunction;
        this.callbackRemoveFunction = callbackRemoveFunction;
        this.noteIdxToKey = {};
        this.groupIdxToKey = {};

        $("#createNewItem").click(() => {
            this.createNewGroupOrNote();
        });

        $("#createNewNote").click(() => {
            this.createNewNoteInGroup();
        });
    }


    drawGroupContent(groupKey) {
        let group = this.notesModel.groups[groupKey];
        this.isGroupSelected = true;
        this.groupsContentSection.empty();
        this.notesContentSection.empty();
        this.inGroup = group.key;

        this.drawNotes(group.name, group.notes);

        $("#base-action-section").empty();
        this.addBackButton(() => {
            this.drawAllContent();
        });
        this.addCreateButton(() => {
            $('#createModalOnlyNote').modal();
        });
        this.addEditButton("Edit name", () => {
            $('#editGroupNameModal').modal(); 
        });
    }

    drawAllContent() {
        this.isGroupSelected = false;
        this.inGroup = null;
        let groups = this.notesModel.groups;
        this.groupsContentSection.empty();
        this.groupsContentSection.append("<ul class='list-group groups-list'>");
        let groupsSection = this.groupsContentSection.find(".groups-list");
        groupsSection.append("<li class='list-group-item active'><h3>Groups</h3></li>");
        let groupIdx = 1;
        for (let groupKey in groups) {
            this.groupIdxToKey[groupIdx++] = groupKey;
            groupsSection.append("<li class='list-group-item list-group-item-action'>" +
                "<span class='list-name '>" + this.notesModel.groups[groupKey].name + "</span>" +
                "<span class='list-act '></span>" +
                "</li>");
        }
        let liNameGroupSet = $(".groups-list li");
        liNameGroupSet.click(event => {
            this.addGroupActions(event.currentTarget);
        });
        liNameGroupSet.dblclick(event => {
            let idx = groupsSection.find("li").index(event.currentTarget);
            this.drawGroupContent(this.groupIdxToKey[idx]);
        });

        this.drawNotes("Notes", this.notesModel.notes);

        $("#base-action-section").empty();
        this.addCreateButton(() => {
            $('#createModal').modal();
        });
    }

    drawNotes(headers, notes) {
        this.notesContentSection.empty();
        this.notesContentSection.append("<h3>"+headers+"</h3>");
        this.notesContentSection.append("<ul class='list-group list-group-flush notes-list'>");
        let notesSection = this.notesContentSection.find(".notes-list");
        let noteIdx = 0;
        for (let noteKey in notes) {
            this.noteIdxToKey[noteIdx++] = noteKey;
            notesSection.append("<li class='list-group-item list-group-item-action '>" +
                                          "<span class='list-name'>" + notes[noteKey].name + "</span>"+
                                          "<span class='list-act '></span></li>");
        }
        let liNameNoteSet = $(".notes-list li");
        liNameNoteSet.click(event => {
            this.addNoteActions(event.currentTarget);
        });
        liNameNoteSet.dblclick(() => alert("hello2"));
    }

    addGroupActions(element) {
        let foldersList = $(".groups-list li");
        let idxInList = foldersList.index(element);
        if (idxInList === this.selectedGroupIdx) {
            return;
        }
        this.isGroupSelected = true;
        this.removeActions();
        this.selectedGroupIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
        $(acts).find(".btn-outline-danger").click(() => {
            if (confirm("Do you want remove this group?")) {
                let groupId = this.groupIdxToKey[this.selectedGroupIdx];
                this.callbackRemoveFunction("group", groupId, this.inGroup);
            }
        });
    }

    addNoteActions(element) {
        let notesList = $(".notes-list li");
        let idxInList = notesList.index(element);
        if (idxInList === this.selectedNoteIdx) {
             return;
        }
        this.isGroupSelected = false;
        this.removeActions();
        this.selectedNoteIdx = idxInList;
        let acts = $(element).find(".list-act");
        acts.append("<button class='btn btn-outline-danger'>Remove</button>");
        $(acts).find(".btn-outline-danger").click(() => {
             if (confirm("Do you want remove this note?")) {
                let noteId = this.noteIdxToKey[this.selectedNoteIdx];
                this.callbackRemoveFunction("note", noteId, this.inGroup);
            }
        });
    }

    removeActions() {
        $("#content-section .list-act").empty();
    }

    addCreateButton(eventFunction) {
        $("#base-action-section").append(" <button class='btn btn-outline-success'>Create</button>");
        $("#base-action-section .btn-outline-success").click(eventFunction);
    }

    addEditButton(buttonName, eventFunction) {
        $("#base-action-section").append(" <button class='btn btn-outline-primary'>"+buttonName+"</button>");
        $("#base-action-section .btn-outline-primary").click(eventFunction);
    }

    addBackButton(eventFunction) {
        $("#base-action-section").append("<button class='btn btn-outline-warning'><- Back</button>");
        $("#base-action-section .btn-outline-warning").click(eventFunction);
    }

    createNewGroupOrNote() {
        let inputName = $('#inputName').val().trim();
        if (inputName === "") {
            alert("Name can't be empty.");
        } else {
            $('#createModal').modal('hide');
            let typeItem = $('#createModal input[name="options"]:checked').val();
            this.callbackCreateFunction(typeItem, inputName, this.inGroup);
        }
        
    }

    createNewNoteInGroup() {
        let inputName = $('#inputNoteName').val().trim();
        if (inputName === "") {
            alert("Name can't be empty.");
        } else {
            $('#createModalOnlyNote').modal('hide');
            let typeItem = "note";
            this.callbackCreateFunction(typeItem, inputName, this.inGroup);
        }
    }

}