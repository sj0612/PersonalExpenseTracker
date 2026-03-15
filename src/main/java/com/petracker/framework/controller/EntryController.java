package com.petracker.framework.controller;

import com.petracker.framework.dto.EntryGetDTO;
import com.petracker.framework.dto.EntryPostDTO;
import com.petracker.framework.response.APIResponse;
import com.petracker.framework.service.EntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/entry")
@Tag(name = "Entry", description = "Expense / income entry management APIs")
@SecurityRequirement(name = "bearerAuth")
public class EntryController {
    @Autowired
    public EntryService entryService;

    @Operation(
            summary = "Get all entries (paginated)",
            description = "Retrieve a paginated and sortable list of all expense/income entries for the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entries retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Page.class)))
    })
    @GetMapping("/get")
    public Page<EntryGetDTO> getAllEntries(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field to sort by", example = "added_time")
            @RequestParam(defaultValue = "added_Time") String sortBy,
            @Parameter(description = "Sort direction: asc or desc", example = "desc")
            @RequestParam(defaultValue = "desc") String sortDirection
    ){
        return entryService.getAllEntries(page, size, sortBy, sortDirection);
    }

    @Operation(
            summary = "Add a new entry",
            description = "Create a new expense or income entry for the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entry added successfully",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "500", description = "Entry creation failed",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
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

    @Operation(
            summary = "Update an existing entry",
            description = "Update the details of an existing entry. The `entryId` in the body identifies the record to update."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entry updated successfully",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "500", description = "Entry update failed",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
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

    @Operation(
            summary = "Delete an entry",
            description = "Permanently delete an entry by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Entry deleted successfully",
                    content = @Content(schema = @Schema(implementation = APIResponse.class))),
            @ApiResponse(responseCode = "500", description = "Entry deletion failed",
                    content = @Content(schema = @Schema(implementation = APIResponse.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEntry(
            @Parameter(description = "ID of the entry to delete", required = true, example = "1")
            @RequestParam Long id){
        try{
            entryService.deleteEntry(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse("Entry deleted Successfully", id));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse("Entry deletion failed..." , e.getMessage()));
        }
    }
}
