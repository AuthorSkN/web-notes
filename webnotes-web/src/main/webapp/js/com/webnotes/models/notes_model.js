'use strict';

class NotesModel {

    constructor(){
        this.notes = [];

        for(let i = 0; i < 10; i++){
            this.notes.push({name: "Note_"+i});
        }
    }
}