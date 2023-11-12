package com.basiliskSB.rest;
import com.basiliskSB.dto.region.*;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/region")
public class RegionRestController {

    @Autowired
    private RegionService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/city={city}",
        "/page={page}&city={city}"
    })
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String city){
        page = (page == null) ? 1 : page;
        city = (city == null) ? "" : city;
        try{
            List<RegionGridDTO> regions = service.getRegionGrid(page, city);
            return ResponseEntity.status(HttpStatus.OK).body(regions);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages",
        "/pages/city={city}"
    })
    public ResponseEntity<Object> getTotalPage(@PathVariable(required = false) String city){
        city = (city == null) ? "" : city;
        try{
            Long totalPages = service.getTotalPages(city);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            UpsertRegionDTO dto = service.getUpdateRegion(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertRegionDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = service.saveRegion(dto);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertRegionDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveRegion(dto);
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
            service.deleteRegion(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/detail/{id}",
        "/detail/{id}/page={page}",
        "/detail/{id}/employeeNumber={employeeNumber}",
        "/detail/{id}/name={name}",
        "/detail/{id}/employeeLevel={employeeLevel}",
        "/detail/{id}/superiorName={superiorName}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}",
        "/detail/{id}/page={page}&name={name}",
        "/detail/{id}/page={page}&employeeLevel={employeeLevel}",
        "/detail/{id}/page={page}&superiorName={superiorName}",
        "/detail/{id}/employeeNumber={employeeNumber}&name={name}",
        "/detail/{id}/employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/detail/{id}/employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/detail/{id}/name={name}&employeeLevel={employeeLevel}",
        "/detail/{id}/name={name}&superiorName={superiorName}",
        "/detail/{id}/employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&name={name}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/detail/{id}/page={page}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/detail/{id}/employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/detail/{id}/name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/detail/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/{id}/page={page}&employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}"
    })
    public ResponseEntity<Object> getDetail(
        @PathVariable(required = true) Long id,
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
            List<SalesmanGridDTO> regionDetails = service.getSalesmanGridByRegion(id, page, employeeNumber, name, employeeLevel, superiorName);
            return ResponseEntity.status(HttpStatus.OK).body(regionDetails);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/detail/pages/{id}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}",
        "/detail/pages/{id}/name={name}",
        "/detail/pages/{id}/employeeLevel={employeeLevel}",
        "/detail/pages/{id}/superiorName={superiorName}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&name={name}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&employeeLevel={employeeLevel}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&superiorName={superiorName}",
        "/detail/pages/{id}/name={name}&employeeLevel={employeeLevel}",
        "/detail/pages/{id}/name={name}&superiorName={superiorName}",
        "/detail/pages/{id}/employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&name={name}&superiorName={superiorName}",
        "/detail/pages/{id}/name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}",
        "/detail/pages/{id}/employeeNumber={employeeNumber}&name={name}&employeeLevel={employeeLevel}&superiorName={superiorName}"
    })
    public ResponseEntity<Object> getDetailTotalPages(
        @PathVariable(required = true) Long id,
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) String name,
        @PathVariable(required = false) String employeeLevel,
        @PathVariable(required = false) String superiorName){
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        name = (name == null) ? "" : name;
        employeeLevel = (employeeLevel == null) ? "" : employeeLevel;
        superiorName = (superiorName == null) ? "" : superiorName;
        try{
            long totalPages = service.getDetailTotalPages(id, employeeNumber, name, employeeLevel, superiorName);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={"/detail/header/{id}"})
    public ResponseEntity<Object> getDetailHeader(@PathVariable(required = true) Long id){
        try{
            RegionHeaderDTO header = service.getRegionHeader(id);
            return ResponseEntity.status(HttpStatus.OK).body(header);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(value={"/detail"})
    public ResponseEntity<Object> postDetail(@Valid @RequestBody AssignSalesmanDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.assignSalesman(dto);
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
