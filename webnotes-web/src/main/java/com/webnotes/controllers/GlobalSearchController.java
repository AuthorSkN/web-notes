package com.webnotes.controllers;

import com.webnotes.business.GlobalSearchOperationImpl;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.ListDto;
import com.webnotes.dto.NoteHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class GlobalSearchController {

    private static final int NOT_GROUP = -1;
    private static final GroupDto[] EMPTY_GROUPS_DTO = new GroupDto[0];
    private static final NoteHeaderDto[] EMPTY_NOTES_DTO = new NoteHeaderDto[0];

    @Autowired
    private GlobalSearchOperationImpl globalSearchOperation;

    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public ListDto globalSearch(@RequestParam(value = "sstr") String searchString) {
        if (searchString.isEmpty()) {
            return new ListDto(EMPTY_NOTES_DTO, EMPTY_GROUPS_DTO);
        } else {
            Set<Note> selectedNotes = globalSearchOperation.globalSearch(searchString.toLowerCase());
            NoteHeaderDto[] noteHeaderDtos = new NoteHeaderDto[selectedNotes.size()];
            int idx = 0;
            for(Note note : selectedNotes) {
                int parentKey = (note.getGroup() == null)? NOT_GROUP : note.getGroup().getId();
                noteHeaderDtos[idx++] = new NoteHeaderDto(note.getId(), note.getName(), parentKey);
            }
            return new ListDto(noteHeaderDtos, EMPTY_GROUPS_DTO);
        }
    }

}