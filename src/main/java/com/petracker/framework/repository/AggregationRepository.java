package com.petracker.framework.repository;

import com.petracker.framework.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregationRepository extends JpaRepository<Entry,Integer> {
    @Query(value="SELECT c.name, SUM(e.amount) " +
            "FROM categories c " +
            "JOIN entries e ON c.category_id = e.category_id " +
            "WHERE e.user_id = :userId " +
            "GROUP BY c.name " +
            "ORDER BY c.name",
            nativeQuery = true)
    List<Object[]> findCategorySums(@Param("userId") Long userId);

    @Query(
            value = "SELECT " +
                    "TO_CHAR(TO_TIMESTAMP(added_time / 1000), 'Mon-YYYY') AS month_year, " +
                    "SUM(CASE WHEN entries.type='DEBIT' THEN amount END) AS DebitAmount, " +
                    "SUM(CASE WHEN entries.type='CREDIT' THEN amount END) AS CreditAmount " +
                    "FROM entries " +
                    "WHERE user_id = :userId " +
                    "GROUP BY month_year " +
                    "ORDER BY MIN(TO_TIMESTAMP(added_time / 1000)) DESC",
            nativeQuery = true
    )
    List<Object[]> findMonthlySummary(@Param("userId") Long userId);
}
