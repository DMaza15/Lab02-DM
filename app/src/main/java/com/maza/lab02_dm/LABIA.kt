package com.maza.lab02_dm

// =========================================================
// CARRITO DE COMPRAS - TIENDA TECSUP
// Versión POO para ejecutar en consola
//
// Conceptos aplicados:
// - Abstracción
// - Encapsulamiento
// - Herencia
// - Polimorfismo
// =========================================================


// ---------------------------------------------------------
// ABSTRACCIÓN
// Clase abstracta que representa un producto.
// ---------------------------------------------------------
abstract class Producto(
    val nombre: String,
    precioInicial: Double,
    var cantidad: Int
) {

    // -----------------------------------------------------
    // ENCAPSULAMIENTO
    // El precio solamente puede modificarse mediante
    // getPrecio() y setPrecio().
    // -----------------------------------------------------
    private var precio: Double = precioInicial

    fun getPrecio(): Double {
        return precio
    }

    fun setPrecio(nuevoPrecio: Double) {
        if (nuevoPrecio > 0) {
            precio = nuevoPrecio
        } else {
            println("Precio invalido para $nombre, no se actualizo.")
        }
    }

    fun calcularSubtotal(): Double {
        return precio * cantidad
    }

    // -----------------------------------------------------
    // POLIMORFISMO
    // Cada tipo de producto implementará su propio impuesto.
    // -----------------------------------------------------
    abstract fun calcularImpuesto(): Double

    open fun mostrarInfo(): String {
        return String.format(
            "%-20s x%d  S/ %8.2f",
            nombre,
            cantidad,
            calcularSubtotal()
        )
    }
}


// ---------------------------------------------------------
// HERENCIA
// ProductoElectronico hereda de Producto.
// ---------------------------------------------------------
class ProductoElectronico(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val garantiaMeses: Int
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double {
        return calcularSubtotal() * 0.18
    }

    // POLIMORFISMO
    override fun mostrarInfo(): String {
        return super.mostrarInfo() +
                "  [Garantia: $garantiaMeses meses]"
    }
}


// ---------------------------------------------------------
// HERENCIA
// ProductoAlimento hereda de Producto.
// ---------------------------------------------------------
class ProductoAlimento(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val fechaVencimiento: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double {
        return 0.0
    }

    // POLIMORFISMO
    override fun mostrarInfo(): String {
        return super.mostrarInfo() +
                "  [Vence: $fechaVencimiento]"
    }
}


// ---------------------------------------------------------
// HERENCIA
// ProductoRopa hereda de Producto.
// ---------------------------------------------------------
class ProductoRopa(
    nombre: String,
    precio: Double,
    cantidad: Int,
    val talla: String
) : Producto(nombre, precio, cantidad) {

    override fun calcularImpuesto(): Double {
        return calcularSubtotal() * 0.18
    }

    // POLIMORFISMO
    override fun mostrarInfo(): String {
        return super.mostrarInfo() +
                "  [Talla: $talla]"
    }
}


// ---------------------------------------------------------
// CLASE CARRITO
// Administra todos los productos.
//
// ENCAPSULAMIENTO:
// La lista de productos es privada.
// ---------------------------------------------------------
class Carrito {

    private val productos = mutableListOf<Producto>()


    // -----------------------------------------------------
    // Agregar producto
    // -----------------------------------------------------
    fun agregarProducto(producto: Producto) {

        productos.add(producto)

        println("Producto agregado: ${producto.nombre}")
    }


    // -----------------------------------------------------
    // Cantidad de productos
    // -----------------------------------------------------
    fun cantidadProductos(): Int {
        return productos.size
    }


    // -----------------------------------------------------
    // Calcular subtotal general
    // -----------------------------------------------------
    fun calcularSubtotalGeneral(): Double {

        return productos.sumOf {
            it.calcularSubtotal()
        }
    }


    // -----------------------------------------------------
    // Calcular impuesto general
    //
    // POLIMORFISMO:
    // Cada producto ejecuta su propia versión de
    // calcularImpuesto().
    // -----------------------------------------------------
    fun calcularImpuestoGeneral(): Double {

        return productos.sumOf {
            it.calcularImpuesto()
        }
    }


    // -----------------------------------------------------
    // Calcular total
    // -----------------------------------------------------
    fun calcularTotal(): Double {

        return calcularSubtotalGeneral() +
                calcularImpuestoGeneral()
    }


    // -----------------------------------------------------
    // Calcular descuento
    // -----------------------------------------------------
    fun calcularDescuento(): Double {

        val total = calcularTotal()

        return when {

            total > 5000 -> total * 0.10

            total > 3000 -> total * 0.05

            else -> 0.0
        }
    }


    // -----------------------------------------------------
    // Mostrar detalle del carrito
    // -----------------------------------------------------
    fun mostrarDetalle() {

        println("--------- DETALLE DEL CARRITO ---------")

        productos.forEachIndexed { index, producto ->

            println(
                "${index + 1}. ${producto.mostrarInfo()}"
            )
        }

        println("---------------------------------------")
    }


    // -----------------------------------------------------
    // Buscar producto
    // -----------------------------------------------------
    fun buscarProducto(nombre: String): Producto? {

        return productos.find {

            it.nombre.equals(
                nombre,
                ignoreCase = true
            )
        }
    }


    // -----------------------------------------------------
    // Eliminar producto
    // -----------------------------------------------------
    fun eliminarProducto(nombre: String): Boolean {

        return productos.removeIf {

            it.nombre.equals(
                nombre,
                ignoreCase = true
            )
        }
    }


    // -----------------------------------------------------
    // Encontrar producto más caro
    // -----------------------------------------------------
    fun productoMasCaro(): Producto? {

        return productos.maxByOrNull {

            it.getPrecio()
        }
    }
}


// =========================================================
// FUNCIÓN PRINCIPAL
// =========================================================
fun main() {

    println("=========================================")
    println(" CARRITO DE COMPRAS - TIENDA TECSUP")
    println("              VERSION POO")
    println("=========================================")

    // -----------------------------------------------------
    // Datos del cliente
    // -----------------------------------------------------
    val nombreCliente = "Dario Maza"

    println("Cliente: $nombreCliente")
    println()


    // -----------------------------------------------------
    // Crear carrito
    // -----------------------------------------------------
    val carrito = Carrito()


    // -----------------------------------------------------
    // Agregar productos
    // -----------------------------------------------------
    carrito.agregarProducto(
        ProductoElectronico(
            "Laptop HP",
            2500.0,
            1,
            12
        )
    )

    carrito.agregarProducto(
        ProductoElectronico(
            "Mouse Logitech",
            45.5,
            2,
            6
        )
    )

    carrito.agregarProducto(
        ProductoAlimento(
            "Chocolate Sublime",
            3.5,
            5,
            "2026-12-01"
        )
    )

    carrito.agregarProducto(
        ProductoRopa(
            "Polo Deportivo",
            79.9,
            3,
            "M"
        )
    )


    // -----------------------------------------------------
    // Mostrar cantidad
    // -----------------------------------------------------
    println()

    println(
        "Cantidad de productos: " +
                carrito.cantidadProductos()
    )

    println()


    // -----------------------------------------------------
    // Mostrar detalle
    // -----------------------------------------------------
    carrito.mostrarDetalle()


    // -----------------------------------------------------
    // Calcular valores
    // -----------------------------------------------------
    val subtotal =
        carrito.calcularSubtotalGeneral()

    val igv =
        carrito.calcularImpuestoGeneral()

    val total =
        carrito.calcularTotal()

    val descuento =
        carrito.calcularDescuento()

    val totalConDescuento =
        total - descuento


    // -----------------------------------------------------
    // Mostrar resultados
    // -----------------------------------------------------
    println()

    println(
        String.format(
            "Subtotal:             S/ %8.2f",
            subtotal
        )
    )

    println(
        String.format(
            "IGV:                  S/ %8.2f",
            igv
        )
    )

    println(
        String.format(
            "TOTAL A PAGAR:        S/ %8.2f",
            total
        )
    )


    // -----------------------------------------------------
    // Descuento
    // -----------------------------------------------------
    if (descuento > 0) {

        println(
            String.format(
                "Descuento aplicado:  -S/ %8.2f",
                descuento
            )
        )

    } else {

        println(
            "No se aplico descuento (total <= S/ 3000)"
        )
    }


    println(
        String.format(
            "TOTAL CON DESCUENTO:  S/ %8.2f",
            totalConDescuento
        )
    )

    println()


    // -----------------------------------------------------
    // Producto más caro
    // -----------------------------------------------------
    val masCaro =
        carrito.productoMasCaro()

    if (masCaro != null) {

        println(
            "Producto mas caro: ${masCaro.nombre} " +
                    String.format(
                        "(S/ %.2f)",
                        masCaro.getPrecio()
                    )
        )
    }


    // -----------------------------------------------------
    // Buscar producto
    // -----------------------------------------------------
    println()

    val buscado =
        carrito.buscarProducto(
            "Mouse Logitech"
        )

    if (buscado != null) {

        println(
            "Producto encontrado: ${buscado.nombre}"
        )

    } else {

        println("Producto no encontrado.")
    }


    // -----------------------------------------------------
    // Eliminar producto
    // -----------------------------------------------------
    println()

    val eliminado =
        carrito.eliminarProducto(
            "Mouse Logitech"
        )

    if (eliminado) {

        println(
            "Producto 'Mouse Logitech' eliminado correctamente."
        )

    } else {

        println(
            "No se encontro el producto para eliminar."
        )
    }


    // -----------------------------------------------------
    // Mostrar carrito después de eliminar
    // -----------------------------------------------------
    println()

    println(
        "Despues de eliminar 'Mouse Logitech':"
    )

    carrito.mostrarDetalle()


    // -----------------------------------------------------
    // Fin
    // -----------------------------------------------------
    println()
    println("=========================================")
    println("        PROGRAMA FINALIZADO")
    println("=========================================")
}

