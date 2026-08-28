package com.maza.lab02_dm

// =========================================================
// CARRITO DE COMPRAS - TIENDA TECSUP (Versión POO)
// Autor: DARIO MAZA
// Conceptos aplicados: Abstracción, Encapsulamiento,
//                      Herencia, Polimorfismo
// =========================================================

// -------------------------------------------------------
// ABSTRACCIÓN: clase abstracta que define el "molde" común
// de todo producto. No se puede instanciar directamente.
// -------------------------------------------------------
abstract class Producto(
    val nombre: String,
    precioInicial: Double,
    var cantidad: Int
) {
    // ENCAPSULAMIENTO: el precio es privado, solo se accede
    // o modifica a través de las funciones controladas de abajo.
    private var precio: Double = precioInicial

    fun getPrecio(): Double = precio

    fun setPrecio(nuevoPrecio: Double) {
        if (nuevoPrecio > 0) {
            precio = nuevoPrecio
        } else {
            println("Precio invalido para $nombre, no se actualizo.")
        }
    }

    fun calcularSubtotal(): Double = precio * cantidad

    // Método abstracto: cada tipo de producto decide CÓMO
    // calcula su impuesto (esto habilita el polimorfismo).
    abstract fun calcularImpuesto(): Double

    open fun mostrarInfo(): String {
        return String.format(
            "%-20s x%d  S/ %8.2f",
            nombre, cantidad, calcularSubtotal()
        )
    }
}

fun main() {
    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP (POO) ")
    println("=========================================")
    println("Proyecto inicializado correctamente.")
}

// -------------------------------------------------------
// HERENCIA: cada clase extiende de Producto y hereda
// nombre, cantidad, precio y la lógica de subtotal.
// -------------------------------------------------------

class ProductoElectronico(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val garantiaMeses: Int
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = calcularSubtotal() * 0.18
}

class ProductoAlimento(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val fechaVencimiento: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = 0.0
}

class ProductoRopa(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val talla: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = calcularSubtotal() * 0.18
}