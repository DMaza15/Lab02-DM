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

    val nombreCliente = "Juan Leon"
    println("Cliente: $nombreCliente")
    println()

    val carrito = Carrito()
    carrito.agregarProducto(ProductoElectronico("Laptop HP", 2500.0, 1, 12))
    carrito.agregarProducto(ProductoElectronico("Mouse Logitech", 45.5, 2, 6))
    carrito.agregarProducto(ProductoAlimento("Chocolate Sublime", 3.5, 5, "2026-12-01"))
    carrito.agregarProducto(ProductoRopa("Polo Deportivo", 79.9, 3, "M"))

    println()
    println("Cantidad de productos: ${carrito.cantidadProductos()}")
    println()

    carrito.mostrarDetalle()

    val subtotal = carrito.calcularSubtotalGeneral()
    val igv = carrito.calcularImpuestoGeneral()
    val total = carrito.calcularTotal()
    val descuento = carrito.calcularDescuento()
    val totalConDescuento = total - descuento

    println(String.format("Subtotal:             S/ %8.2f", subtotal))
    println(String.format("IGV:                  S/ %8.2f", igv))
    println(String.format("TOTAL A PAGAR:        S/ %8.2f", total))

    if (descuento > 0) {
        println(String.format("Descuento aplicado:  -S/ %8.2f", descuento))
    } else {
        println("No se aplico descuento (total <= S/ 3000)")
    }
    println(String.format("TOTAL CON DESCUENTO:  S/ %8.2f", totalConDescuento))
    println()

    val masCaro = carrito.productoMasCaro()
    if (masCaro != null) {
        println("Producto mas caro: ${masCaro.nombre} " +
                String.format("(S/ %.2f)", masCaro.getPrecio()))
    }

    println()
    val buscado = carrito.buscarProducto("Mouse Logitech")
    if (buscado != null) {
        println("Producto encontrado: ${buscado.nombre}")
    }

    carrito.eliminarProducto("Mouse Logitech")
    println("Despues de eliminar 'Mouse Logitech':")
    carrito.mostrarDetalle()
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

class ProductoElectronico(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val garantiaMeses: Int
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = calcularSubtotal() * 0.18

    // POLIMORFISMO: personaliza mostrarInfo() reutilizando
    // la versión del padre con super.mostrarInfo()
    override fun mostrarInfo(): String {
        return super.mostrarInfo() + "  [Garantia: $garantiaMeses meses]"
    }
}

class ProductoAlimento(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val fechaVencimiento: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = 0.0

    override fun mostrarInfo(): String {
        return super.mostrarInfo() + "  [Vence: $fechaVencimiento]"
    }
}

class ProductoRopa(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val talla: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double = calcularSubtotal() * 0.18

    override fun mostrarInfo(): String {
        return super.mostrarInfo() + "  [Talla: $talla]"
    }
}
// -------------------------------------------------------
// Clase Carrito: administra la colección de productos.
// También aplica ENCAPSULAMIENTO (la lista es privada).
// -------------------------------------------------------
class Carrito {

    private val productos = mutableListOf<Producto>()

    fun agregarProducto(p: Producto) {
        productos.add(p)
        println("Producto agregado: ${p.nombre}")
    }

    fun cantidadProductos(): Int = productos.size

    fun calcularSubtotalGeneral(): Double =
        productos.sumOf { it.calcularSubtotal() }

    // POLIMORFISMO en acción: cada producto de la lista
    // ejecuta SU PROPIA versión de calcularImpuesto().
    fun calcularImpuestoGeneral(): Double =
        productos.sumOf { it.calcularImpuesto() }

    fun calcularTotal(): Double =
        calcularSubtotalGeneral() + calcularImpuestoGeneral()

    fun mostrarDetalle() {
        println("--------- DETALLE DEL CARRITO ---------")
        productos.forEachIndexed { index, p ->
            println("${index + 1}. ${p.mostrarInfo()}")
        }
        println("---------------------------------------")
    }
}
fun calcularDescuento(): Double {
    val total = calcularTotal()
    return when {
        total > 5000 -> total * 0.10
        total > 3000 -> total * 0.05
        else -> 0.0
    }
}

fun productoMasCaro(): Producto? = productos.maxByOrNull { it.getPrecio() }

fun buscarProducto(nombre: String): Producto? {
    return productos.find { it.nombre.equals(nombre, ignoreCase = true) }
}

fun eliminarProducto(nombre: String): Boolean {
    return productos.removeIf { it.nombre.equals(nombre, ignoreCase = true) }
}
