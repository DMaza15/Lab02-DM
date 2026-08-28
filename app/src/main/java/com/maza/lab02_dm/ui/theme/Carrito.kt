package com.maza.lab02_dm.ui.theme

data class Producto (
    val nombre: String,
    val precio: Double,
    var cantidad: Int
    )

fun main () {
    println("==========================================")
    println("  CARRITO DE COMPRAS - TIENDA TECSUP  ")
    println("==========================================")

    val nombreCliente = "Dario Maza"
    val carrito = mutableListOf<Producto>()

    println("Cliente: $nombreCliente\n")

    carrito.add(Producto("Laptop HP", 2500.0, 1))
    carrito.add(Producto("Mouse Logitech", 45.5, 2))
}

fun calcularSubtotal(productos: List<Producto>): Double{
    var subtotal = 0.0
    for (p in productos){
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}

fun calcularIGV(subtotal: Double): Double{
   return subtotal * 0.18
}

fun calcularTotal(subtotal: Double, igv: Double): Double{
    return subtotal + igv
}