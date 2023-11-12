package com.basiliskSB.rest;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/salesman")
public class SalesmanRestController {

    @Autowired
    private SalesmanService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/employeeNumber={employeeNumber}",
        "/name={name}",
        "/employeeLevel={employeeLevel}",
        "/superiorName={superiorName}",
        "/page={page}&employeeNumber={employeeNumber}",
        "/page={page}&name={name}",
        "/page={page}&employeeLevel={employeeLevel}",
        "/page={page}&superiorName={superiorName}",
        "/employeeNumber={employeeNumber}&name={name}",
        "/employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/name={name}&employeeLevel={employeeLevel}",
        "/name={name}&superiorName={superiorName}",
        "/employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/page={page}&employeeNumber={employeeNumber}&name={name}",
        "/page={page}&employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/page={page}&employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/page={page}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/page={page}&employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/page={page}&employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/page={page}&employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}"
    })
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) String name,
        @PathVariable(required = false) String employeeLevel,
        @PathVariable(required = false) String superiorName){
        page = (page == null) ? 1 : page;
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        name = (name == null) ? "" : name;
        employeeLevel = (employeeLevel == null) ? "" : employeeLevel;
        superiorName = (superiorName == null) ? "" : superiorName;
        try{
            List<SalesmanGridDTO> salesmen = service.getSalesmanGrid(page, employeeNumber, name, employeeLevel, superiorName);
            return ResponseEntity.status(HttpStatus.OK).body(salesmen);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages/{id}",
        "/pages/{id}/employeeNumber={employeeNumber}",
        "/pages/{id}/name={name}",
        "/pages/{id}/employeeLevel={employeeLevel}",
        "/pages/{id}/superiorName={superiorName}",
        "/pages/{id}/employeeNumber={employeeNumber}&name={name}",
        "/pages/{id}/employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/pages/{id}/employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/pages/{id}/name={name}&employeeLevel={employeeLevel}",
        "/pages/{id}/name={name}&superiorName={superiorName}",
        "/pages/{id}/employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/pages/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/pages/{id}/employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/pages/{id}/name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/pages/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}"
    })
    public ResponseEntity<Object> getTotalPage(
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) String name,
        @PathVariable(required = false) String employeeLevel,
        @PathVariable(required = false) String superiorName){
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        name = (name == null) ? "" : name;
        employeeLevel = (employeeLevel == null) ? "" : employeeLevel;
        superiorName = (superiorName == null) ? "" : superiorName;
        try{
            Long totalPages = service.getTotalPages(employeeNumber, name, employeeLevel, superiorName);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{employeeNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String employeeNumber){
        try{
            UpdateSalesmanDTO dto = service.getUpdateSalesman(employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                String respondId = service.insertSalesman(dto);
                dto.setEmployeeNumber(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.updateSalesman(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String employeeNumber){
        try{
            service.deleteSalesman(employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body(employeeNumber);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/detail/{employeeNumber}",
        "/detail/{employeeNumber}/page={page}",
        "/detail/{employeeNumber}/city={city}",
        "/detail/{employeeNumber}/page={page}&city={city}"
    })
    public ResponseEntity<Object> getDetail(
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String city){
        page = (page == null) ? 1 : page;
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        city = (city == null) ? "" : city;
        try{
            List<RegionGridDTO> salesmanDetails = service.getRegionGridBySalesman(employeeNumber, page, city);
            return ResponseEntity.status(HttpStatus.OK).body(salesmanDetails);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/detail/pages/{employeeNumber}",
        "/detail/pages/{employeeNumber}/city={city}",
    })
    public ResponseEntity<Object> getDetailTotalPages(
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) String city){
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        city = (city == null) ? "" : city;
        try{
            long totalPages = service.getDetailTotalPages(employeeNumber, city);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={"/detail/header/{employeeNumber}"})
    public ResponseEntity<Object> getDetailHeader(@PathVariable(required = true) String employeeNumber){
        try{
            SalesmanHeaderDTO header = service.getSalesmanHeader(employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body(header);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(value={"/detail"})
    public ResponseEntity<Object> postDetail(@Valid @RequestBody AssignRegionDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.assignRegion(dto);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/detail/regionId={regionId}/employeeNumber={employeeNumber}")
    public ResponseEntity<Object> deleteDetail(
        @PathVariable(required = true) Long regionId,
        @PathVariable(required = true) String employeeNumber){
        try{
            service.detachRegionSalesman(regionId, employeeNumber);
            return ResponseEntity.status(HttpStatus.OK).body("remove assignment success.");
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
