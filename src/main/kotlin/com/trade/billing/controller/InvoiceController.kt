package com.trade.billing.controller

import com.trade.billing.model.Invoice
import com.trade.billing.service.InvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE])
@RestController
@RequestMapping("/invoice")
class InvoiceController {
    @Autowired
    lateinit var invoiceService: InvoiceService
    @GetMapping
    fun list ():List <Invoice>{
        return invoiceService.list()
    }

    @GetMapping("/{id}")
    fun listOne (@PathVariable id: Long) : Optional<Invoice> {
        return invoiceService.listOne(id)
    }
    @GetMapping("/filter-total/{value}")
    fun listTotals (@PathVariable("value") value: Double ):ResponseEntity<*>{
        return ResponseEntity(invoiceService.listByTotal(value), HttpStatus.OK)
    }

    //Peticiones post - Clase controller
    @PostMapping
    fun save (@RequestBody modelo: Invoice): ResponseEntity<Invoice> {
        return ResponseEntity(invoiceService.save(modelo), HttpStatus.OK)
    }

    //clase controller - Petición Put
    @PutMapping
    fun update (@RequestBody modelo: Invoice): ResponseEntity<Invoice> {
        return ResponseEntity(invoiceService.update(modelo), HttpStatus.OK)
    }

    //clase  controller-Petiicon Patch
    @PatchMapping
    fun updateName (@RequestBody modelo: Invoice): ResponseEntity<Invoice> {
        return ResponseEntity(invoiceService.update(modelo), HttpStatus.OK)
    }

    //clase  controller - Petición Delete
    @DeleteMapping("/delete/{id}")
    fun delete (@PathVariable("id") id: Long):Boolean?{
        return invoiceService.delete(id)
    }
}