package com.maza.lab02_dm

data class RegistroVehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
    val cliente: String,
    val esFrecuente: Boolean,
    var totalPagado: Double = 0.0
)

var limiteVehiculos: Int = 0
var listaVehiculos = mutableListOf<RegistroVehiculo>()
val historialVisitas = mutableMapOf<String, Int>()

const val IGV = 0.18

fun iniciarProcesamiento(cantidad: Int) {
    if (cantidad > 0) {
        limiteVehiculos = cantidad
        listaVehiculos.clear()
        historialVisitas.clear()
    }
}

fun hayEspacioDisponible(): Boolean {
    return listaVehiculos.size < limiteVehiculos
}

fun calcularSiEsFrecuente(placa: String): Boolean {
    val visitasAnteriores = historialVisitas.getOrDefault(placa, 0)
    val visitasActuales = visitasAnteriores + 1

    historialVisitas[placa] = visitasActuales

    return visitasActuales > 1
}

fun calcularPago(vehiculo: RegistroVehiculo): Double {

    val tarifaBase = when (vehiculo.tipo.lowercase()) {
        "moto" -> 2.0
        "auto" -> 4.0
        "camioneta" -> 10.0
        "trailer" -> 20.0
        else -> 0.0
    }

    var subtotal = 0.0
    val horas = vehiculo.horas

    // Tramo 1: Horas 1-2 -> GRATIS (no suma nada)

    // Tramo 2: Horas 3-5 -> 20%
    if (horas > 2) {
        val horasTramo2 = if (horas > 5) 3 else horas - 2
        subtotal += horasTramo2 * (tarifaBase * 0.20)
    }

    // Tramo 3: Horas 6-10 -> 40%
    if (horas > 5) {
        val horasTramo3 = if (horas > 10) 5 else horas - 5
        subtotal += horasTramo3 * (tarifaBase * 0.40)
    }

    // Tramo 4: Horas 11 a más -> 50%
    if (horas > 10) {
        val horasTramo4 = horas - 10
        subtotal += horasTramo4 * (tarifaBase * 0.50)
    }

    // Descuento para cliente frecuente
    if (vehiculo.esFrecuente) {
        subtotal *= 0.90
    }

    // Descuento adicional: si el total supera 500, 20% de descuento
    if (subtotal > 500) {
        subtotal *= 0.80
    }

    // Se agrega el IGV peruano (18%)
    val totalConIGV = subtotal * (1 + IGV)

    vehiculo.totalPagado = totalConIGV

    return totalConIGV
}

fun mostrarVehiculosRegistrados() {

    println()
    println("=== LISTA DE VEHÍCULOS REGISTRADOS ===")

    for ((index, vehiculo) in listaVehiculos.withIndex()) {

        val datos = """
            Vehículo ${index + 1}:
            - Placa: ${vehiculo.placa}
            - Cliente: ${vehiculo.cliente}
            - Tipo: ${vehiculo.tipo}
            - Horas: ${vehiculo.horas}
            - Frecuente: ${if (vehiculo.esFrecuente) "Sí (10% Dscto)" else "No"}
            - Total a Pagar (incl. IGV): S/ ${String.format("%.2f", vehiculo.totalPagado)}
            -----------------------------------
        """.trimIndent()

        println(datos)
    }
}

fun registrarVehiculo(
    placa: String,
    tipo: String,
    horasIngresadas: Int,
    cliente: String
) {

    if (!hayEspacioDisponible()) {
        println("Aforo completo. No hay espacio para registrar más vehículos.")
        return
    }

    val horasReales =
        if (horasIngresadas < 1) 1 else horasIngresadas

    val esFrecuenteCalculado =
        calcularSiEsFrecuente(placa)

    val nuevoRegistro = RegistroVehiculo(
        placa = placa,
        tipo = tipo,
        horas = horasReales,
        cliente = cliente,
        esFrecuente = esFrecuenteCalculado
    )

    calcularPago(nuevoRegistro)

    listaVehiculos.add(nuevoRegistro)

    println()
    println("Vehículo registrado correctamente.")

    if (listaVehiculos.size == limiteVehiculos) {
        mostrarVehiculosRegistrados()
    }
}