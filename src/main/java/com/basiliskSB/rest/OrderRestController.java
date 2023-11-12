package com.basiliskSB.rest;
import com.basiliskSB.dto.order.*;
import com.basiliskSB.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {

    @Autowired
    private OrderService service;

    @GetMapping(value={
        "",
        "/page={page}",
        "/invoiceNumber={invoiceNumber}",
        "/customerId={customerId}",
        "/employeeNumber={employeeNumber}",
        "/deliveryId={deliveryId}",
        "/orderDate={orderDate}",
        "/page={page}&invoiceNumber={invoiceNumber}",
        "/page={page}&customerId={customerId}",
        "/page={page}&employeeNumber={employeeNumber}",
        "/page={page}&deliveryId={deliveryId}",
        "/page={page}&orderDate={orderDate}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}",
        "/invoiceNumber={invoiceNumber}&employeeNumber={employeeNumber}",
        "/invoiceNumber={invoiceNumber}&deliveryId={deliveryId}",
        "/invoiceNumber={invoiceNumber}&orderDate={orderDate}",
        "/employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/deliveryId={deliveryId}&orderDate={orderDate}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}",
        "/page={page}&invoiceNumber={invoiceNumber}&employeeNumber={employeeNumber}",
        "/page={page}&invoiceNumber={invoiceNumber}&deliveryId={deliveryId}",
        "/page={page}&invoiceNumber={invoiceNumber}&orderDate={orderDate}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&deliveryId={deliveryId}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&orderDate={orderDate}",
        "/customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/customerId={customerId}&employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&deliveryId={deliveryId}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&orderDate={orderDate}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}",
        "/page={page}&invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}"
    })
    public ResponseEntity<Object> get(
        @PathVariable(required = false) Integer page,
        @PathVariable(required = false) String invoiceNumber,
        @PathVariable(required = false) Long customerId,
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) Long deliveryId,
        @PathVariable(required = false) String orderDate){
        page = (page == null) ? 1 : page;
        invoiceNumber = (invoiceNumber == null) ? "" : invoiceNumber;
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        LocalDate formattedDate = null;
        if(orderDate != null && orderDate != "") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = LocalDate.parse(orderDate, formatter);
        }
        try{
            List<OrderGridDTO> orders = service.getOrderGrid(page, invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/pages",
        "/pages/invoiceNumber={invoiceNumber}",
        "/pages/customerId={customerId}",
        "/pages/employeeNumber={employeeNumber}",
        "/pages/deliveryId={deliveryId}",
        "/pages/orderDate={orderDate}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}",
        "/pages/invoiceNumber={invoiceNumber}&employeeNumber={employeeNumber}",
        "/pages/invoiceNumber={invoiceNumber}&deliveryId={deliveryId}",
        "/pages/invoiceNumber={invoiceNumber}&orderDate={orderDate}",
        "/pages/employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/pages/employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/pages/deliveryId={deliveryId}&orderDate={orderDate}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&deliveryId={deliveryId}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&orderDate={orderDate}",
        "/pages/customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/pages/customerId={customerId}&employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/pages/employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&orderDate={orderDate}",
        "/pages/customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}",
        "/pages/invoiceNumber={invoiceNumber}&customerId={customerId}&employeeNumber={employeeNumber}&deliveryId={deliveryId}&orderDate={orderDate}"
    })
    public ResponseEntity<Object> getTotalPage(
        @PathVariable(required = false) String invoiceNumber,
        @PathVariable(required = false) Long customerId,
        @PathVariable(required = false) String employeeNumber,
        @PathVariable(required = false) Long deliveryId,
        @PathVariable(required = false) String orderDate){
        invoiceNumber = (invoiceNumber == null) ? "" : invoiceNumber;
        employeeNumber = (employeeNumber == null) ? "" : employeeNumber;
        LocalDate formattedDate = null;
        if(orderDate != null && orderDate != "") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = LocalDate.parse(orderDate, formatter);
        }
        try{
            Long totalPages = service.getTotalPages(invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String invoiceNumber){
        try{
            UpdateOrderDTO dto = service.getUpdateOrder(invoiceNumber);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody InsertOrderDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                String respondId = service.insertOrder(dto);
                dto.setInvoiceNumber(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpdateOrderDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.updateOrder(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String invoiceNumber){
        try{
            service.deleteOrder(invoiceNumber);
            return ResponseEntity.status(HttpStatus.OK).body(invoiceNumber);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={
        "/detail/{invoiceNumber}",
        "/detail/{invoiceNumber}/page={page}"
    })
    public ResponseEntity<Object> getDetail(
        @PathVariable(required = true) String invoiceNumber,
        @PathVariable(required = false) Integer page){
        page = (page == null) ? 1 : page;
        try{
            List<OrderDetailGridDTO> orderDetails = service.getOrderDetailGrid(invoiceNumber, page);
            return ResponseEntity.status(HttpStatus.OK).body(orderDetails);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={"/detail/header/{invoiceNumber}"})
    public ResponseEntity<Object> getDetailHeader(@PathVariable(required = true) String invoiceNumber){
        try{
            OrderHeaderDTO header = service.getOrderHeader(invoiceNumber);
            return ResponseEntity.status(HttpStatus.OK).body(header);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping(value={"/detail/pages/{invoiceNumber}"})
    public ResponseEntity<Object> getDetailTotalPage(@PathVariable(required = true) String invoiceNumber){
        try{
            Long totalPages = service.getDetailTotalPages(invoiceNumber);
            return ResponseEntity.status(HttpStatus.OK).body(totalPages);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> getUpdateDetail(@PathVariable(required = true) Long id){
        try{
            UpsertOrderDetailDTO dto = service.getUpdateOrderDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PostMapping(value={"/detail/{invoiceNumber}"})
    public ResponseEntity<Object> postDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                dto.setId(0l);
                Long respondId = service.saveOrderDetail(dto);
                dto.setId(respondId);
                return ResponseEntity.status(HttpStatus.CREATED).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @PutMapping(value={"/detail"})
    public ResponseEntity<Object> putDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveOrderDetail(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/detail/{id}")
    public ResponseEntity<Object> deleteDetail(@PathVariable(required = true) Long id){
        try{
            service.deleteOrderDetail(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
