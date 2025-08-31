package com.petracker.framework.repository;

import com.petracker.framework.models.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface EntryRepository extends JpaRepository<Entry,Integer> {
    @Query(value = "SELECT * FROM ENTRIES WHERE USER_ID = :userId", nativeQuery = true)
    Page<Entry> findByUserId(@Param("userId") Long userId, Pageable pageable);

    Entry findById(Long entryId);

    void deleteByID(Long entryID);
}
