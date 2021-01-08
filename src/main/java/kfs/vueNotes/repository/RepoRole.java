package kfs.vueNotes.repository;

import kfs.vueNotes.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRole extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
