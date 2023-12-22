package com.trade.billing.service

import com.trade.billing.model.Detail
import com.trade.billing.repository.DetailRepository
import com.trade.billing.repository.InvoiceRepository
import com.trade.billing.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class DetailService {
    @Autowired
    lateinit var detailRepository: DetailRepository

    @Autowired
    lateinit var invoiceRepository: InvoiceRepository

    @Autowired
    lateinit var productRepository: ProductRepository

    fun list(): List<Detail> {
        return detailRepository.findAll()
    }

    fun listOne(id: Long): Optional<Detail> {
        return detailRepository.findById(id)
    }

    // PETICIONES POST
    fun save(modelo: Detail): Detail {
        val listDetail= detailRepository.findByInvoiceId(modelo.invoiceId)
        val invoiceseptup= invoiceRepository.findById(modelo.invoiceId)
        var sum = (0).toBigDecimal()
        listDetail.map {
            sum += (it.price?.times( it.quantity.toBigDecimal())!!)
        }
        invoiceseptup?.apply{
            total= sum
        }
        if (invoiceseptup != null) {
            invoiceRepository.save(invoiceseptup)
        }
        try {
            // Verifica si la factura asociada existe
            invoiceRepository.findById(modelo.invoiceId)
                    ?: throw Exception("ID de factura no encontrado")

            // Verifica si el producto asociado existe
            productRepository.findById(modelo.productId)
                    ?: throw Exception("ID de producto no encontrado")

            val product= productRepository.findById(modelo.productId)
            println(product)
            product?.apply {
                stock -=modelo.quantity
            }
            val response = detailRepository.save(modelo)
            return response

        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    // clase service -Petición put
    fun update(modelo: Detail): Detail {
        var productToUpdate=productRepository.findById(modelo.productId)
            ?:throw Exception("Id del Producto no existe")
        invoiceRepository.findById(modelo.invoiceId)
            ?:throw Exception("Id del Invoice no existe")
        var oldQuantity= detailRepository.findById(modelo.id)?.quantity;
        try {
            detailRepository.findById(modelo.id)
                    ?: throw Exception("ID no existe")

            val product = productRepository.findById(modelo.productId)
            product?.apply {
                stock += modelo.quantity
                }

            return detailRepository.save(modelo)
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    // clase service - Delete by id
    fun delete(id: Long?): Boolean? {

        try {
            val detail = detailRepository.findById(id)
                    ?: throw Exception("ID no existe")

            val product = productRepository.findById(detail.productId)
            product?.apply {
                stock += detail.quantity
            }
            detailRepository.deleteById(id!!)
            return true
        } catch (ex: Exception) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }
}
