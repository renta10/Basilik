package com.basiliskSB.rest;
import com.basiliskSB.dto.product.*;
import com.basiliskSB.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/name={name}",
        "/categoryId={categoryId}",
        "/supplierId={supplierId}",
        "/page={page}&name={name}",
        "/page={page}&categoryId={categoryId}",
        "/page={page}&supplierId={supplierId}",
        "/categoryId={categoryId}&supplierId={supplierId}",
        "/page={page}&name={name}&categoryId={categoryId}",
        "/page={page}&name={name}&supplierId={supplierId}",
        "/name={name}&categoryId={categoryId}&supplierId={supplierId}",
        "/page={page}&name={name}&categoryId={categoryId}&supplierId={supplierId}",
    })
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String name,
        @PathVariable(required = false) Long categoryId,
        @PathVariable(required = false) Long supplierId){
        page = (page == null) ? 1 : page;
        name = (name == null) ? "" : name;
        try{
            List<ProductGridDTO> products = service.getProductGrid(page, name, categoryId, supplierId);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages",
        "/pages/name={name}",
        "/pages/categoryId={categoryId}",
        "/pages/supplierId={supplierId}",
        "/pages/&name={name}&categoryId={categoryId}",
        "/pages/&name={name}&supplierId={supplierId}",
        "/pages/&categoryId={categoryId}&supplierId={supplierId}",
        "/pages/&name={name}&categoryId={categoryId}&supplierId={supplierId}"
    })
    public ResponseEntity<Object> getTotalPage(
        @PathVariable(required = false) String name,
        @PathVariable(required = false) Long categoryId,
        @PathVariable(required = false) Long supplierId){
        name = (name == null) ? "" : name;
        try{
            Long totalPages = service.getTotalPages(name, categoryId, supplierId);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        try{
            UpsertProductDTO dto = service.getUpdateProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertProductDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = service.saveProduct(dto);
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
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertProductDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveProduct(dto);
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
            service.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
