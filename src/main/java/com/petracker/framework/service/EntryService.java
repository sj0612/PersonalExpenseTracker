package com.petracker.framework.service;

import com.petracker.framework.dto.EntryGetDTO;
import com.petracker.framework.dto.EntryPostDTO;
import com.petracker.framework.models.Entry;
import com.petracker.framework.models.User;
import com.petracker.framework.repository.CategoryRepository;
import com.petracker.framework.repository.EntryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EntryService {
    @Autowired
    public EntryRepository entryRepository;
    @Autowired
    public UserService userService;
    @Autowired
    public CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Page<EntryGetDTO> getAllEntries(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Long userId = userService.getCurrentUser().getUserId();
        System.out.println("User id : "+userId);

        return convertToDtoPage(entryRepository.findByUserId(userId , pageable));
    }

    public Page<EntryGetDTO> convertToDtoPage(Page<Entry> entriesPage) {
        return entriesPage.map(this::convertToDto);
    }

    public EntryGetDTO convertToDto(Entry entry) {
        return modelMapper.map(entry, EntryGetDTO.class);
    }

    public void addEntry(EntryPostDTO entryPostDTO) throws Exception {
        Entry entry = new Entry();

        entry.setAdded_time(System.currentTimeMillis());
        entry.setUpdated_time(System.currentTimeMillis());
        entry.setAmount(entryPostDTO.getAmount());


        System.out.println("Category name : "+entryPostDTO.getCategory());
        entry.setCategory(categoryRepository.findByName(entryPostDTO.getCategory()));


        entry.setType(entryPostDTO.getType());
        entry.setRemarks(entryPostDTO.getRemarks());
        entry.setModeOfPayment(entryPostDTO.getModeOfPayment());


        User currUser = userService.getCurrentUser();
        entry.setUser(currUser);
        entryRepository.save(entry);
    }

    public void updateEntry(EntryGetDTO entryGetDTO) {
        Optional<Entry> entryBox = entryRepository.findById(entryGetDTO.getEntryId());
        if(entryBox.isPresent()) {
            Entry entry = entryBox.get();
            entry.setAmount(entryGetDTO.getAmount());
            entry.setRemarks(entryGetDTO.getRemarks());
            entry.setModeOfPayment(entryGetDTO.getModeOfPayment());
            entry.setType(entryGetDTO.getType());
            entry.setUpdated_time(System.currentTimeMillis());
            entryRepository.save(entry);
        }
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }
}
