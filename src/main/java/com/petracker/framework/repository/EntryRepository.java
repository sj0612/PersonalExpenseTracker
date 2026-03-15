package com.petracker.framework.repository;

import com.petracker.framework.models.Entry;
import com.petracker.framework.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EntryRepository extends JpaRepository<Entry,Long> {
    @Query(value = "SELECT * FROM ENTRIES WHERE USER_ID = :userId", nativeQuery = true)
    Page<Entry> findByUserId(@Param("userId") Long userId, Pageable pageable);

    Optional<Entry> findById(Long entryId);

    boolean existsByUserAndRemarks(User user, String remarks);

    void deleteById(Long entryID);
}
