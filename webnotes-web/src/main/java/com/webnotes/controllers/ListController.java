package com.webnotes.controllers;

import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.GroupDAOImpl;
import com.webnotes.data.dao.NoteDAOImpl;
import com.webnotes.data.entity.Group;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.ListDto;
import com.webnotes.dto.NoteHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ListController {

    private static final String EMPTY_TEXT = "";
    private static final long NOT_GROUP = -1;

    @Autowired
    private NoteDAOImpl noteDataAccessor;
    @Autowired
    private GroupDAOImpl groupDataAccessor;


    @RequestMapping(value = "/loadAll", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
        method = RequestMethod.GET)
    @ResponseBody
    public ListDto loadAllOperation() {
        List<Note> notesData = noteDataAccessor.getAll();
        List<Group> groupsData = groupDataAccessor.getAll();

        List<NoteHeaderDto> noteHeaderDtoList = new ArrayList<>();
        List<GroupDto> groupDtoList = new ArrayList<>();

        for (Note note : notesData) {
            Group group = note.getGroup();
            if (group == null) {
                noteHeaderDtoList.add(new NoteHeaderDto(note.getId(), note.getName(), NOT_GROUP));
            }
        }

        for (Group group : groupsData) {
            Set<Note> notesOfGroup = group.getNotes();
            NoteHeaderDto[] noteHeaderDtosForGroup = new NoteHeaderDto[notesOfGroup.size()];
            int idx = 0;
            for (Note noteOfGroup : notesOfGroup) {
                noteHeaderDtosForGroup[idx++] = new NoteHeaderDto(noteOfGroup.getId(), noteOfGroup.getName(), group.getId());
            }
            groupDtoList.add(new GroupDto(group.getId(), group.getName(), noteHeaderDtosForGroup));
        }

        NoteHeaderDto[] noteHeaderDtos = noteHeaderDtoList.toArray(new NoteHeaderDto[noteHeaderDtoList.size()]);
        GroupDto[] groupDtos = groupDtoList.toArray(new GroupDto[groupDtoList.size()]);

        return new ListDto(noteHeaderDtos, groupDtos);
    }

    @RequestMapping(value = "/addNote", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public NoteHeaderDto addNoteOperation(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "group") int groupKey) {
        Note note = new Note(name, EMPTY_TEXT, new Date());
        if (groupKey != -1) {
            Group parentGroup = groupDataAccessor.getById(groupKey);
            note.setGroup(parentGroup);
            parentGroup.getNotes().add(note);
        }
        noteDataAccessor.add(note);

        return new NoteHeaderDto(note.getId(),
                note.getName(),
                (note.getGroup() == null) ? NOT_GROUP : note.getGroup().getId());
    }

    @RequestMapping(value = "/addGroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public GroupDto addGroupOperation(@RequestParam(value = "name") String name) {
        Group group = new Group(name, new Date());
        groupDataAccessor.add(group);

        return new GroupDto(group.getId(), group.getName(), new NoteHeaderDto[]{});
    }

    @RequestMapping(value = "/deleteNote", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public NoteHeaderDto deleteNoteOperation(@RequestParam(value = "key") int noteKey) {

        Note note = noteDataAccessor.getById(noteKey);
        Group parentGroup = note.getGroup();
        noteDataAccessor.delete(note);
        if (parentGroup != null) {
            parentGroup.getNotes().remove(note);
        }

        return new NoteHeaderDto(noteKey, note.getName(),
                (parentGroup == null) ? NOT_GROUP : parentGroup.getId());
    }

    @RequestMapping(value = "/deleteGroup", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public Integer removeGroupOperation(@RequestParam(value = "key") int groupKey) {
        Group group = groupDataAccessor.getById(groupKey);
        groupDataAccessor.delete(group);

        return groupKey;
    }

    @RequestMapping(value = "/editGroupName", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            method = RequestMethod.GET)
    @ResponseBody
    public Integer editGroupNameOperation(@RequestParam(value = "key") int groupKey,
                                           @RequestParam(value = "name") String newGroupName) {
        Group group = groupDataAccessor.getById(groupKey);
        group.setName(newGroupName);
        groupDataAccessor.update(group);

        return groupKey;
    }
}
