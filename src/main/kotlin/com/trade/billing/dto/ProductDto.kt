package com.trade.billing.dto

class ProductDto(
    var id:Long?,
    var descriptionBrand: String?=null
)
class LoginDto {
    var username:String?=null
    var password:String?=null
}
class TokenDto {
    var jwt:String?=null
}