package kfs.vueNotes.map;

import kfs.vueNotes.dto.Note;
import kfs.vueNotes.entity.KNote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapNote extends KfsMap <KNote, Note> {
}
