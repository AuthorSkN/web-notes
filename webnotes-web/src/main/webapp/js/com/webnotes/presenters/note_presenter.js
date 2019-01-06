'use strict';


class NotePresenter {

	constructor(noteContentModel, callbackBackFunction, callbackCheckActionFunction, callbackChangeNoteFunction) {
		this.noteModel = noteContentModel;
		this.actionSection = $("#base-action-section");
		this.noteContentSection = $("#content-section");
		this.callbackBackFunction = callbackBackFunction;
		this.callbackCheckActionFunction = callbackCheckActionFunction;
		this.callbackChangeNoteFunction = callbackChangeNoteFunction;
		this.selectedActionIdx = -1;
		this.actionIdxToKey = {};

		$("#addNewAction").click(() => {
            this.addAction();
        });
	}

	drawNoteContent() {
		this.noteContentSection.empty();
		this.noteContentSection.append("<h3>"+this.noteModel.name+"</h3>");
		this.noteContentSection.append("<div class='note-text'>"+this.noteModel.text+"</div>");
		this.noteContentSection.append("<ul class='list-group list-group-flush actions-list'>");
		let actionList = $(".actions-list");
		let actionIdx = 0;
		for (let actionKey in this.noteModel.actions) {
			this.actionIdxToKey[actionIdx++] = actionKey;
			let action = this.noteModel.actions[actionKey];
			let actionCompleteStyle;
			if (action.complete) {
				actionCompleteStyle = "act-complete";
			} else {
				actionCompleteStyle = "act-not-complete";
			}
			actionList.append("<li class='list-group-item list-group-item-action'>"
				+"<span class='act-circle "+actionCompleteStyle+"'></span>"
				+"<span class='list-note-action'>"+action.text+"</span>"
				+"</li>"
			);
		}

		$(".act-circle").click(event => this.checkNoteAction(event.target));

		this.actionSection.empty();
		this.addBackButton(() => this.callbackBackFunction(this.noteModel.parentKey));
		this.addEditButton("Edit", () => this.drawEditableNote());
	}

	drawEditableNote() {
		this.noteContentSection.empty();
		this.noteContentSection.append("<input id='note-name-input' class='form-control form-control-lg edit-input' type='text' placeholder='name' value='"
			+this.noteModel.name+"'>");
		this.noteContentSection.append("<textarea class='form-control edit-input' id='note-text-input' rows='2'>"
			+this.noteModel.text+"</textarea>");
		this.noteContentSection.append("<ul class='list-group list-group-flush actions-list'>");
		let actionList = $(".actions-list");
		let actionIdx = 0;
		for (let actionKey in this.noteModel.actions) {
			this.actionIdxToKey[actionIdx++] = actionKey;
			let action = this.noteModel.actions[actionKey];
			actionList.append("<li class='list-group-item list-group-item-action'>"
				+"<span class='act-circle act-edit-complete'></span>"
				+"<span class='list-note-action'>"+action.text+"</span>"
				+"<span class='list-act'></span>"
				+"</li>"
			);
		}
		let liNameNoteSet = $(".actions-list li");
		liNameNoteSet.click(event => {
			this.addRemoveButtonForAction(event.currentTarget);
		});

		this.actionSection.empty();
		this.addCancelButton(() => this.drawNoteContent());
		this.addAddActionButton("Add action", () => {
            $('#addActionModal').modal(); 
        });
		this.addSaveButton(() => this.saveChangeNoteData());
	}

	checkNoteAction(element) {
		let liElement = $(element).parents().eq(0);
		let liActionsElements = $("li");
		let actionKey = this.actionIdxToKey[liActionsElements.index(liElement)];
		let actionStatus = this.noteModel.checkAction(actionKey);
		if (actionStatus) {
			$(element).removeClass("act-not-complete");
			$(element).addClass("act-complete");
		} else {
			$(element).removeClass("act-complete");
			$(element).addClass("act-not-complete");
		}
        this.callbackCheckActionFunction(actionKey, actionStatus);
	}

	removeActions() {
        $("#content-section .list-act").empty();
    }

    removeAllEvent(elements) {
    	elements.unbind();
    }

	addRemoveButtonForAction(element) {
		let actRemoveSect = $(element).find(".list-act");
		let liActionsElements = $("li");
		let idx = liActionsElements.index(element);
		if (idx === this.selectedActionIdx) {
             return;
        }
        this.selectedActionIdx = idx;
        this.removeActions();
		actRemoveSect.append("<button class='btn btn-outline-danger'>Remove</button>");
		actRemoveSect.find(".btn-outline-danger").click(() => this.removeAction(element));
	}

	removeAction(action) {
		this.selectedActionIdx = -1;
		$(action).remove();
	}

	addAction() {
		let actionText = $('#inputActionText').val().trim();
        if (actionText === "") {
            alert("Text can't be empty.");
        } else {
            $('#addActionModal').modal('hide');
            let actionList = $(".actions-list");
			actionList.append("<li class='list-group-item list-group-item-action'>"
				+"<span class='act-circle act-edit-complete'></span>"
				+"<span class='list-note-action'>"+actionText+"</span>"
				+"<span class='list-act'></span>"
				+"</li>"
			);
            let liNameNoteSet = actionList.find("li");
            this.removeAllEvent(liNameNoteSet);
            liNameNoteSet.click(event => {
				this.addRemoveButtonForAction(event.currentTarget);
			});
        }
	}

	addEditButton(buttonName, eventFunction) {
        this.actionSection.append(" <button class='btn btn-outline-primary'>"+buttonName+"</button>");
        $("#base-action-section .btn-outline-primary").click(eventFunction);
    }

    addAddActionButton(buttonName, eventFunction) {
        this.actionSection.append(" <button class='btn btn-outline-primary'>"+buttonName+"</button>");
        $("#base-action-section .btn-outline-primary").click(eventFunction);
    }

    addBackButton(eventFunction) {
        this.actionSection.append("<button class='btn btn-outline-warning'><- Back</button>");
        $("#base-action-section .btn-outline-warning").click(eventFunction);
    }

    addSaveButton(eventFunction) {
        this.actionSection.append("<button class='btn btn-outline-success'>Save</button>");
        $("#base-action-section .btn-outline-success").click(eventFunction);
    }

    addCancelButton(eventFunction) {
        this.actionSection.append("<button class='btn btn-outline-danger'>Cancel</button>");
        $("#base-action-section .btn-outline-danger").click(eventFunction);
    }


    saveChangeNoteData() {
		let newName = $("#note-name-input").val();
        if (newName === "") {
            alert("Name can't be empty.");
        } else {
        	let newText = $("#note-text-input").val();
        	let actions = "";
        	$(".list-note-action").each((idx, element) => actions += element.innerText+";");
            this.callbackChangeNoteFunction(newName, newText, actions);
        }
	}

}