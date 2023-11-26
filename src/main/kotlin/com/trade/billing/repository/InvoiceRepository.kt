package com.trade.billing.repository

import com.trade.billing.model.Detail
import com.trade.billing.model.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long?>{
    fun findById (id: Long?): Invoice?

}