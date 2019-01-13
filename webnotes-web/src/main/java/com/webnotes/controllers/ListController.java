package com.webnotes.controllers;


import com.webnotes.data.dao.DAO;
import com.webnotes.data.dao.DAOFactory;
import com.webnotes.data.entity.Group;
import com.webnotes.data.entity.Note;
import com.webnotes.dto.GroupDto;
import com.webnotes.dto.ListDto;
import com.webnotes.dto.NoteHeaderDto;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
public class ListController {

    private static final String EMPTY_TEXT = "";
    private static final long NOT_GROUP = -1;


    private DAOFactory dataFactory = new DAOFactory(DAOFactory.HIBERNATE_ADAPTER);


    @RequestMapping(value = "/loadAll", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public ListDto loadAllOperation() {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();
        DAO<Group> groupsDataAccessor = dataFactory.createGroupDAO();

        List<Note> notesData = noteDataAccessor.getAll();
        List<Group> groupsData = groupsDataAccessor.getAll();

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

    @RequestMapping(value = "/addNote", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public NoteHeaderDto addNoteOperation(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "group") int groupKey) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = new Note(name, EMPTY_TEXT, new Date());
        if (groupKey != -1) {
            DAO<Group> groupDataAccessor = dataFactory.createGroupDAO();
            Group parentGroup = groupDataAccessor.getById(groupKey);
            note.setGroup(parentGroup);
            parentGroup.getNotes().add(note);
        }
        noteDataAccessor.add(note);

        return new NoteHeaderDto(note.getId(),
                note.getName(),
                (note.getGroup() == null) ? NOT_GROUP : note.getGroup().getId());
    }

    @RequestMapping(value = "/addGroup", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public GroupDto addGroupOperation(@RequestParam(value = "name") String name) {
        DAO<Group> folderDataAccessor = dataFactory.createGroupDAO();

        Group group = new Group(name, new Date());
        folderDataAccessor.add(group);

        return new GroupDto(group.getId(), group.getName(), new NoteHeaderDto[]{});
    }

    @RequestMapping(value = "/deleteNote", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public NoteHeaderDto deleteNoteOperation(@RequestParam(value = "key") int noteKey) {
        DAO<Note> noteDataAccessor = dataFactory.createNoteDAO();

        Note note = noteDataAccessor.getById(noteKey);
        Group parentGroup = note.getGroup();
        noteDataAccessor.delete(note);
        if (parentGroup != null) {
            parentGroup.getNotes().remove(note);
        }

        return new NoteHeaderDto(noteKey, note.getName(),
                (parentGroup == null) ? NOT_GROUP : parentGroup.getId());
    }

    @RequestMapping(value = "/deleteGroup", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public Integer removeGroupOperation(@RequestParam(value = "key") int groupKey) {
        DAO<Group> groupDataAccessor = dataFactory.createGroupDAO();

        Group group = groupDataAccessor.getById(groupKey);
        groupDataAccessor.delete(group);

        return groupKey;
    }

    @RequestMapping(value = "/editGroupName", headers = "Accept=application/json", method = RequestMethod.GET)
    @ResponseBody
    public Integer editGroupNameOperation(@RequestParam(value = "key") int groupKey,
                                           @RequestParam(value = "name") String newGroupName) {
        DAO<Group> groupDataAccessor = dataFactory.createGroupDAO();

        Group group = groupDataAccessor.getById(groupKey);
        group.setName(newGroupName);
        groupDataAccessor.update(group);

        return groupKey;
    }
}
