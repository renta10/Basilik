package com.basiliskSB.rest;
import com.basiliskSB.dto.supplier.*;
import com.basiliskSB.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierRestController {

    @Autowired
    private SupplierService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/company={company}",
        "/contact={contact}",
        "/jobTitle={jobTitle}",
        "/page={page}&company={company}",
        "/page={page}&contact={contact}",
        "/page={page}&jobTitle={jobTitle}",
        "/company={company}&contact={contact}",
        "/company={company}&jobTitle={jobTitle}",
        "/contact={contact}&jobTitle={jobTitle}",
        "/page={page}&company={company}&contact={contact}",
        "/page={page}&company={company}&jobTitle={jobTitle}",
        "/company={company}&contact={contact}&jobTitle={jobTitle}",
        "/page={page}&company={company}&contact={contact}&jobTitle={jobTitle}"})
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String company,
        @PathVariable(required = false) String contact,
        @PathVariable(required = false) String jobTitle){
        page = (page == null) ? 1 : page;
        company = (company == null) ? "" : company;
        contact = (contact == null) ? "" : contact;
        jobTitle = (jobTitle == null) ? "" : jobTitle;
        try{
            List<SupplierGridDTO> suppliers = service.getSupplierGrid(page, company, contact, jobTitle);
            return ResponseEntity.status(HttpStatus.OK).body(suppliers);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages",
        "/pages/company={company}",
        "/pages/contact={contact}",
        "/pages/jobTitle={jobTitle}",
        "/pages/company={company}&contact={contact}",
        "/pages/company={company}&jobTitle={jobTitle}",
        "/pages/contact={contact}&jobTitle={jobTitle}",
        "/pages/company={company}&contact={contact}&jobTitle={jobTitle}"})
    public ResponseEntity<Object> getTotalPage(
        @PathVariable(required = false) String company,
        @PathVariable(required = false) String contact,
        @PathVariable(required = false) String jobTitle){
        company = (company == null) ? "" : company;
        contact = (contact == null) ? "" : contact;
        jobTitle = (jobTitle == null) ? "" : jobTitle;
        try{
            Long totalPages = service.getTotalPages(company, contact, jobTitle);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            UpsertSupplierDTO dto = service.getUpdateSupplier(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = service.saveSupplier(dto);
                dto.setId(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveSupplier(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try{
            service.deleteSupplier(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
