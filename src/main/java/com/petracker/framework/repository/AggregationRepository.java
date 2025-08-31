package com.petracker.framework.repository;

import com.petracker.framework.dto.CategoryAmountDTO;
import com.petracker.framework.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregationRepository extends JpaRepository<Entry,Integer> {
    @Query(value="SELECT c.name, SUM(e.amount) " +
            "FROM Category c JOIN Entry e ON c.categoryId = e.category.categoryId " +
            "GROUP BY c.name",
            nativeQuery = true)
    List<Object[]> findCategorySums();

    @Query(
            value = "SELECT " +
                    "TO_CHAR(TO_TIMESTAMP(added_time / 1000), 'Mon-YYYY') AS month_year, " +
                    "SUM(CASE WHEN entries.type='DEBIT' THEN amount END) AS DebitAmount, " +
                    "SUM(CASE WHEN entries.type='CREDIT' THEN amount END) AS CreditAmount " +
                    "FROM entries " +
                    "GROUP BY month_year " +
                    "ORDER BY month_year DESC",
            nativeQuery = true
    )
    List<Object[]> findMonthlySummary();
}
