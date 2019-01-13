package com.webnotes.controllers;

import com.webnotes.dto.GroupDto;
import com.webnotes.business.GlobalSearchBean;
import com.webnotes.dto.ListDto;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class GlobalSearchController {

    private static final String SEARCH_STRING_PARAMETER = "sstr";
    private static final int NOT_GROUP = -1;
    private static final GroupDto[] EMPTY_GROUPS_DTO = new GroupDto[0];


    private GlobalSearchBean globalSearchBean;

    @RequestMapping(value = "/globalSearch", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public ListDto globalSearch(@RequestParam(value = "sstr") String searchString) {
        return null;
    }


        /*String responseJSON;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        String searchString = request.getParameter(SEARCH_STRING_PARAMETER);
        if (searchString.isEmpty()) {
            responseJSON = gson.toJson(new ListDto(new NoteHeaderDto[0], EMPTY_GROUPS_DTO));
        } else {
            Set<Note> selectedNotes = globalSearchBean.globalSerach(searchString.toLowerCase());
            NoteHeaderDto[] noteHeaderDtos = new NoteHeaderDto[selectedNotes.size()];
            int idx = 0;
            for(Note note : selectedNotes) {
                int parentKey = (note.getGroup() == null)? NOT_GROUP : note.getGroup().getId();
                noteHeaderDtos[idx++] = new NoteHeaderDto(note.getId(), note.getName(), parentKey);
            }
            responseJSON = gson.toJson(new ListDto(noteHeaderDtos, EMPTY_GROUPS_DTO));
        }


        PrintWriter out = response.getWriter();
        out.print(responseJSON);*/
}