package com.app.proyectpolleria.Entidad

import java.math.BigDecimal

data class CarCompras(
    val id: String,
    val modelo: String,
    val precio: BigDecimal,
    var cantidad: Int,
    val products: Plato,
    val marca: String,
    val imagen: String
)
