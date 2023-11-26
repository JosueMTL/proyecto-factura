package com.trade.billing.service

import com.trade.billing.model.Product
import com.trade.billing.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
@Service
class ProductService {
    @Autowired
    lateinit var productRepository: ProductRepository

    fun list ():List<Product>{
        return productRepository.findAll()
    }

    fun listOne (id: Long): Optional<Product> {
        return productRepository.findById(id)
    }

    //PETICIONES POST
    //clase service
    fun save(modelo: Product): Product{
        try{
            return productRepository.save(modelo)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
    //clase service -Petición put
    fun update(modelo: Product): Product{
        try {
            productRepository.findById(modelo.id)
                    ?: throw Exception("ID no existe")

            return productRepository.save(modelo)
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }

    //clase service - Delete by id
    fun delete (id: Long?):Boolean?{
        try{
            val response = productRepository.findById(id)
                    ?: throw Exception("ID no existe")
            productRepository.deleteById(id!!)
            return true
        }
        catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND,ex.message)
        }
    }
}