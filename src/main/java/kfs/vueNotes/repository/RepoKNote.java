package kfs.vueNotes.repository;


import kfs.vueNotes.entity.KNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoKNote extends JpaRepository<KNote, Long> {

    Page<KNote> findByUserIdAndTitleContainingOrderByPublishedDesc(long userId, String title, Pageable pageable);
    Page<KNote> findByUserIdOrderByPublishedDesc(long userId, Pageable pageable);
    //Page<KNote> findByUserIdAndStatus(long userId, String status, Pageable pageable);
}
