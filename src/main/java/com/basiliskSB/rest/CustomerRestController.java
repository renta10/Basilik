package com.basiliskSB.rest;

import com.basiliskSB.dto.customer.*;
import com.basiliskSB.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerRestController {

    @Autowired
    private CustomerService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/company={company}",
        "/contact={contact}",
        "/page={page}&company={company}",
        "/page={page}&contact={contact}",
        "/company={company}&contact={contact}",
        "/page={page}&company={company}&contact={contact}"
    })
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String company,
        @PathVariable(required = false) String contact){
        page = (page == null) ? 1 : page;
        company = (company == null) ? "" : company;
        contact = (contact == null) ? "" : contact;
        try{
            List<CustomerGridDTO> customers = service.getCustomerGrid(page, company, contact);
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages",
        "/pages/company={company}",
        "/pages/contact={contact}",
        "/pages/company={company}&contact={contact}"
    })
    public ResponseEntity<Object> getTotalPage(
        @PathVariable(required = false) String company,
        @PathVariable(required = false) String contact){
        company = (company == null) ? "" : company;
        contact = (contact == null) ? "" : contact;
        try{
            Long totalPages = service.getTotalPages(company, contact);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            UpsertCustomerDTO dto = service.getUpdateCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCustomerDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = service.saveCustomer(dto);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCustomerDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveCustomer(dto);
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
            service.deleteCustomer(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

}
