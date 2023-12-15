package com.trade.billing.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "brand")
    var brand: String? = null

    @Column(name = "price", precision = 10, scale = 2)
    var price: BigDecimal? = null

    @Column(name = "stock")
    var stock: Int = 0
}