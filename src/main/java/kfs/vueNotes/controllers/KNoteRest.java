package kfs.vueNotes.controllers;

import kfs.vueNotes.dto.Note;
import kfs.vueNotes.entity.KNote;
import kfs.vueNotes.map.MapNote;
import kfs.vueNotes.repository.RepoKNote;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/kApiNotes")
public class KNoteRest {

    private final RepoKNote repoKNote;
    private final MapNote mapNote;

    public KNoteRest(RepoKNote repoKNote, MapNote mapNote) {
        this.repoKNote = repoKNote;
        this.mapNote = mapNote;
    }

    long getUserId() {
        Object credence = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        if (credence instanceof Long) {
            return (Long)credence;
        }
        throw new UsernameNotFoundException(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/notes")
    public ResponseEntity<Map<String, Object>> getNotes(@RequestParam(required = false) String title,
                                                      @RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "8") int size) {
        try {

            long userId = getUserId();

            Page<KNote> ret;
            Pageable paging = PageRequest.of(page-1, size);
            if ((title == null) || "".equals(title)) {
                log.info("run findByOrderByPublishedDesc");
                ret = repoKNote.findByUserIdOrderByPublishedDesc(userId, paging);
            } else {
                log.info("run findByTitleContainingOrderByPublishedDesc");
                ret = repoKNote.findByUserIdAndTitleContainingOrderByPublishedDesc(userId, title, paging);
            }
            List<Note> noteList = mapNote.entityToDto(ret.getContent());
            log.info("Find list size: " + noteList.size());

            Map<String, Object> response = new HashMap<>();
            response.put("notes", noteList);
            response.put("currPage", ret.getNumber()+1);
            response.put("totalPages", ret.getTotalPages());
            response.put("totalCount", ret.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error in getNotes", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getById(@PathVariable("id") String ids) {
        try {
            long userId = getUserId();
            Long id = Long.parseLong(ids);
            KNote ret = repoKNote.findById(id).orElse(null);
            if (ret == null) {
                log.warn("Cannot find note id {}", ids);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (userId != ret.getUserId()) {
                log.warn("User id {} cannot access to note id {} (user id {})",
                        userId, ret.getId(), ret.getUserId());
                return new ResponseEntity<>(null, HttpStatus.LOCKED);
            }
            Note nRet = mapNote.entityToDto(ret);
            return new ResponseEntity<>(nRet, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error in getById", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/note")
    public ResponseEntity<Note> create(@RequestBody Note note) {
        try {
            long userId = getUserId();
            KNote knote = mapNote.dtoToEntity(note);
            knote.setPublished(ZonedDateTime.now());
            if ((note.getStatus() == null) || "".equals(note.getStatus())) {
                knote.setStatus("CREATED");
            }
            knote.setUserId(userId);
            KNote ret = repoKNote.save(knote);
            Note retNote = mapNote.entityToDto(ret);
            return new ResponseEntity<>(retNote, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error in create", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<Note> update(@PathVariable("id") String ids, @RequestBody Note note) {
        try {
            long userId = getUserId();
            Long id = Long.parseLong(ids);
            KNote ret = repoKNote.findById(id).orElse(null);
            if (ret == null) {
                log.warn("Cannot update note with id: " + id);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (ret.getUserId() != userId) {
                log.warn ("User {} cannot update note {} ( uid {})", userId, ret.getId(), ret.getUserId());
                return new ResponseEntity<>(null, HttpStatus.LOCKED);
            }
            ret.setTitle(note.getTitle());
            ret.setDescription(note.getDescription());
            ret.setStatus(note.getStatus()  );
            ret = repoKNote.save(ret);

            Note retNote = mapNote.entityToDto(ret);
            return new ResponseEntity<>(retNote, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error in update", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") String ids) {
        try {
            Long id = Long.parseLong(ids);
            long userId = getUserId();
            KNote ret = repoKNote.findById(id).orElse(null);
            if (ret == null) {
                log.warn("Cannot delete note with id: " + id);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (ret.getUserId() != userId) {
                log.warn ("User {} cannot update note {} ( uid {})", userId, ret.getId(), ret.getUserId());
                return new ResponseEntity<>(null, HttpStatus.LOCKED);
            }
            repoKNote.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error in delete", ex);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
