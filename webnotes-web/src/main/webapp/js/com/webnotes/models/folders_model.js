'use strict';

class FoldersModel {

    constructor() {
        this.folders = [];

        for(let i = 0; i < 6; i++){
            this.folders.push({name: "Folder_"+i});
        }
    }
}