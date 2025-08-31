package com.petracker.framework.controller;

import com.petracker.framework.dto.EntryGetDTO;
import com.petracker.framework.dto.EntryPostDTO;
import com.petracker.framework.dto.UserPostDTO;
import com.petracker.framework.response.APIResponse;
import com.petracker.framework.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/entry")
public class EntryController {
    @Autowired
    public EntryService entryService;

    @GetMapping("/get")
    public Page<EntryGetDTO> getAllEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "added_Time") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        return entryService.getAllEntries(page, size, sortBy, sortDirection);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEntry(@RequestBody EntryPostDTO entryPostDTO){
        try {
            entryService.addEntry(entryPostDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse("Entry added Successfully", entryPostDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Entry creation failed..." , e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEntry(@RequestBody EntryGetDTO entryGetDTO){
        try {
            entryService.updateEntry(entryGetDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse("Entry updated Successfully", entryGetDTO));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Entry updation failed..." , e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEntry(@RequestParam Long id){
        try{
            entryService.deleteEntry(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse("Entry updated Successfully",id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Entry deletion failed..." , e.getMessage()));
        }
    }
}
