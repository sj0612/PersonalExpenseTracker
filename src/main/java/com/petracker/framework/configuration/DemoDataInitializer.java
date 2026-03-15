package com.petracker.framework.configuration;

import com.petracker.framework.models.Category;
import com.petracker.framework.models.Entry;
import com.petracker.framework.models.PaymentMode;
import com.petracker.framework.models.TransactionType;
import com.petracker.framework.models.User;
import com.petracker.framework.repository.CategoryRepository;
import com.petracker.framework.repository.EntryRepository;
import com.petracker.framework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@ConditionalOnProperty(name = "app.demo.seed.enabled", havingValue = "true")
@Transactional
public class DemoDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DemoDataInitializer.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EntryRepository entryRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.demo.user.name:Demo User}")
    private String demoUserName;

    @Value("${app.demo.user.email:demo@petracker.com}")
    private String demoUserEmail;

    @Value("${app.demo.user.mobile:9876543210}")
    private String demoUserMobile;

    @Value("${app.demo.user.password:Demo@123}")
    private String demoUserPassword;

    public DemoDataInitializer(UserRepository userRepository,
                               CategoryRepository categoryRepository,
                               EntryRepository entryRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.entryRepository = entryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Category> categories = seedCategories();
        User demoUser = seedDemoUser();
        int createdEntries = seedDemoEntries(demoUser, categories);

        log.info("Demo data ready. Demo login email: {}, new demo entries inserted: {}", demoUserEmail, createdEntries);
    }

    private Map<String, Category> seedCategories() {
        List<String> categoryNames = List.of("Salary", "Food", "Transport", "Shopping", "Entertainment", "Bills");
        Map<String, Category> categories = new LinkedHashMap<>();

        for (String categoryName : categoryNames) {
            Category existingCategory = categoryRepository.findByName(categoryName);
            if (existingCategory != null) {
                categories.put(categoryName, existingCategory);
                continue;
            }

            Category category = new Category();
            category.setName(categoryName);
            categories.put(categoryName, categoryRepository.save(category));
        }

        return categories;
    }

    private User seedDemoUser() {
        Optional<User> existingUserBox = userRepository.findByMailId(demoUserEmail);
        if (existingUserBox.isPresent()) {
            User existingUser = existingUserBox.get();
            boolean updated = false;

            if (!passwordEncoder.matches(demoUserPassword, existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(demoUserPassword));
                updated = true;
            }
            if (existingUser.getName() == null || existingUser.getName().isBlank()) {
                existingUser.setName(demoUserName);
                updated = true;
            }

            if (updated) {
                return userRepository.save(existingUser);
            }
            return existingUser;
        }

        User demoUser = new User();
        demoUser.setName(demoUserName);
        demoUser.setMailId(demoUserEmail);
        demoUser.setMobileNo(resolveDemoMobileNumber());
        demoUser.setPassword(passwordEncoder.encode(demoUserPassword));
        return userRepository.save(demoUser);
    }

    private String resolveDemoMobileNumber() {
        if (userRepository.findByMobileNo(demoUserMobile).isEmpty()) {
            return demoUserMobile;
        }

        for (int suffix = 1; suffix <= 9; suffix++) {
            String candidate = demoUserMobile.substring(0, demoUserMobile.length() - 1) + suffix;
            if (userRepository.findByMobileNo(candidate).isEmpty()) {
                return candidate;
            }
        }

        return String.valueOf(System.currentTimeMillis()).substring(3, 13);
    }

    private int seedDemoEntries(User demoUser, Map<String, Category> categories) {
        List<DemoEntrySpec> demoEntries = buildDemoEntries();
        int createdEntries = 0;

        for (DemoEntrySpec spec : demoEntries) {
            if (entryRepository.existsByUserAndRemarks(demoUser, spec.remarks())) {
                continue;
            }

            Entry entry = new Entry();
            long eventTime = toEpochMillis(spec.monthsAgo(), spec.dayOfMonth(), spec.hourOfDay());
            entry.setAmount(spec.amount());
            entry.setRemarks(spec.remarks());
            entry.setModeOfPayment(spec.paymentMode());
            entry.setType(spec.transactionType());
            entry.setAdded_time(eventTime);
            entry.setUpdated_time(eventTime);
            entry.setUser(demoUser);
            entry.setCategory(categories.get(spec.categoryName()));
            entryRepository.save(entry);
            createdEntries++;
        }

        return createdEntries;
    }

    private List<DemoEntrySpec> buildDemoEntries() {
        return List.of(
                new DemoEntrySpec(85000.0f, "[DEMO][2026-03] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 0, 2, 9),
                new DemoEntrySpec(650.0f, "[DEMO][2026-03] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 0, 4, 18),
                new DemoEntrySpec(240.0f, "[DEMO][2026-03] Metro recharge", PaymentMode.CASH, TransactionType.DEBIT, "Transport", 0, 6, 8),
                new DemoEntrySpec(1750.0f, "[DEMO][2026-03] Electricity bill", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 0, 9, 20),
                new DemoEntrySpec(1300.0f, "[DEMO][2026-03] Weekend movie", PaymentMode.CARD, TransactionType.DEBIT, "Entertainment", 0, 12, 19),
                new DemoEntrySpec(3200.0f, "[DEMO][2026-03] Shoes purchase", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 0, 14, 16),

                new DemoEntrySpec(85000.0f, "[DEMO][2026-02] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 1, 1, 9),
                new DemoEntrySpec(590.0f, "[DEMO][2026-02] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 1, 5, 18),
                new DemoEntrySpec(210.0f, "[DEMO][2026-02] Auto fare", PaymentMode.CASH, TransactionType.DEBIT, "Transport", 1, 8, 8),
                new DemoEntrySpec(1800.0f, "[DEMO][2026-02] Internet and electricity bill", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 1, 10, 20),
                new DemoEntrySpec(950.0f, "[DEMO][2026-02] Dinner outing", PaymentMode.CARD, TransactionType.DEBIT, "Food", 1, 14, 21),
                new DemoEntrySpec(2200.0f, "[DEMO][2026-02] Shopping mall purchase", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 1, 18, 17),

                new DemoEntrySpec(84000.0f, "[DEMO][2026-01] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 2, 2, 9),
                new DemoEntrySpec(520.0f, "[DEMO][2026-01] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 2, 6, 18),
                new DemoEntrySpec(260.0f, "[DEMO][2026-01] Cab expense", PaymentMode.CARD, TransactionType.DEBIT, "Transport", 2, 9, 9),
                new DemoEntrySpec(1450.0f, "[DEMO][2026-01] OTT and entertainment", PaymentMode.CARD, TransactionType.DEBIT, "Entertainment", 2, 12, 20),
                new DemoEntrySpec(1700.0f, "[DEMO][2026-01] Electricity bill", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 2, 16, 20),
                new DemoEntrySpec(2800.0f, "[DEMO][2026-01] Winter shopping", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 2, 22, 15),

                new DemoEntrySpec(83000.0f, "[DEMO][2025-12] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 3, 1, 9),
                new DemoEntrySpec(610.0f, "[DEMO][2025-12] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 3, 4, 18),
                new DemoEntrySpec(180.0f, "[DEMO][2025-12] Bus pass", PaymentMode.CASH, TransactionType.DEBIT, "Transport", 3, 7, 8),
                new DemoEntrySpec(4200.0f, "[DEMO][2025-12] Festival shopping", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 3, 12, 16),
                new DemoEntrySpec(2100.0f, "[DEMO][2025-12] Utility bills", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 3, 18, 20),
                new DemoEntrySpec(1600.0f, "[DEMO][2025-12] Family dinner", PaymentMode.CARD, TransactionType.DEBIT, "Entertainment", 3, 24, 21),

                new DemoEntrySpec(82000.0f, "[DEMO][2025-11] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 4, 1, 9),
                new DemoEntrySpec(560.0f, "[DEMO][2025-11] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 4, 5, 18),
                new DemoEntrySpec(230.0f, "[DEMO][2025-11] Fuel expense", PaymentMode.CARD, TransactionType.DEBIT, "Transport", 4, 9, 8),
                new DemoEntrySpec(1750.0f, "[DEMO][2025-11] Electricity bill", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 4, 11, 20),
                new DemoEntrySpec(950.0f, "[DEMO][2025-11] Concert ticket", PaymentMode.CARD, TransactionType.DEBIT, "Entertainment", 4, 16, 19),
                new DemoEntrySpec(2400.0f, "[DEMO][2025-11] Clothing purchase", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 4, 23, 17),

                new DemoEntrySpec(81000.0f, "[DEMO][2025-10] Monthly salary credit", PaymentMode.ONLINE_TRANSFER, TransactionType.CREDIT, "Salary", 5, 2, 9),
                new DemoEntrySpec(580.0f, "[DEMO][2025-10] Grocery shopping", PaymentMode.CARD, TransactionType.DEBIT, "Food", 5, 6, 18),
                new DemoEntrySpec(200.0f, "[DEMO][2025-10] Train fare", PaymentMode.CASH, TransactionType.DEBIT, "Transport", 5, 8, 8),
                new DemoEntrySpec(1650.0f, "[DEMO][2025-10] Electricity bill", PaymentMode.ONLINE_TRANSFER, TransactionType.DEBIT, "Bills", 5, 13, 20),
                new DemoEntrySpec(1100.0f, "[DEMO][2025-10] Streaming and movie", PaymentMode.CARD, TransactionType.DEBIT, "Entertainment", 5, 18, 20),
                new DemoEntrySpec(2100.0f, "[DEMO][2025-10] Household shopping", PaymentMode.CARD, TransactionType.DEBIT, "Shopping", 5, 25, 16)
        );
    }

    private long toEpochMillis(int monthsAgo, int dayOfMonth, int hourOfDay) {
        YearMonth targetMonth = YearMonth.now().minusMonths(monthsAgo);
        int safeDayOfMonth = Math.min(dayOfMonth, targetMonth.lengthOfMonth());

        return LocalDate.of(targetMonth.getYear(), targetMonth.getMonth(), safeDayOfMonth)
                .atTime(hourOfDay, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    private record DemoEntrySpec(Float amount,
                                 String remarks,
                                 PaymentMode paymentMode,
                                 TransactionType transactionType,
                                 String categoryName,
                                 int monthsAgo,
                                 int dayOfMonth,
                                 int hourOfDay) {
    }
}

