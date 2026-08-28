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

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var i = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %7.2f", i, p.nombre, p.cantidad,importe))
        i++
    }
    println("------------------------------------------")

 val masCaro = carrito.maxByOrNull { it.precio }
 if (masCaro != null){
     println("Producto mas caro: ${masCaro.nombre} " + String.format("(S/%.2f", masCaro.precio))
 val descuento = calcularDescuento(total)
 if (descuento > 0.0){
     println("Descuento aplicado: 5% por compra mayor a S/ 3000")
 val totalConDescuento = total - descuento
 println(String.format("TOTAL CON DESCUENTO   : S/ %7.2f", totalConDescuento))
 println("\nGracias por su compra, $nombreCliente")
 }
 }
}