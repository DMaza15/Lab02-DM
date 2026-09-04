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

fun iniciarProcesamiento(cantidad: Int) {
    if (cantidad > 0) {
        limiteVehiculos = cantidad
        listaVehiculos.clear()
        historialVisitas.clear()
    }
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

        // NUEVO: Trailer
        "trailer" -> 20.0

        else -> 0.0
    }

    var subtotal = 0.0
    val horas = vehiculo.horas

    // Tramo 1: 0 a 2 horas
    val horasNormales =
        if (horas > 2) 2 else horas

    subtotal += horasNormales * tarifaBase

    // Tramo 2: De la 3ra a la 5ta hora con 20%
    if (horas > 2) {

        val horasCon20 =
            if (horas > 5) 3 else horas - 2

        subtotal += horasCon20 * (tarifaBase * 1.20)
    }

    // Tramo 3: Más de 5 horas con 50%
    if (horas > 5) {

        val horasCon50 = horas - 5

        subtotal += horasCon50 * (tarifaBase * 1.50)
    }

    // Descuento para cliente frecuente
    if (vehiculo.esFrecuente) {
        subtotal *= 0.90
    }

    vehiculo.totalPagado = subtotal

    return subtotal
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
            - Total a Pagar: S/ ${String.format("%.2f", vehiculo.totalPagado)}
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

    if (listaVehiculos.size < limiteVehiculos) {

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

    } else {
        println("No hay espacio para registrar más vehículos.")
    }
}


// =========================================================
// MAIN
// Los datos son ingresados por el usuario.
// =========================================================

fun main() {

    println("======================================")
    println("     SISTEMA DE ESTACIONAMIENTO")
    println("======================================")

    // Pedir cantidad de vehículos
    print("Ingrese la cantidad de vehículos a registrar: ")

    val cantidad =
        readLine()?.toIntOrNull() ?: 0

    if (cantidad <= 0) {
        println("Cantidad inválida.")
        return
    }

    iniciarProcesamiento(cantidad)

    println()

    // Registrar vehículos
    for (i in 1..cantidad) {

        println("--------------------------------------")
        println("          VEHÍCULO $i")
        println("--------------------------------------")

        // Placa
        print("Ingrese la placa: ")
        val placa =
            readLine()?.trim() ?: ""

        // Tipo
        print("Ingrese el tipo (Moto/Auto/Camioneta/Trailer): ")
        val tipo =
            readLine()?.trim() ?: ""

        // Horas
        print("Ingrese las horas: ")
        val horas =
            readLine()?.toIntOrNull() ?: 1

        // Cliente
        print("Ingrese el nombre del cliente: ")
        val cliente =
            readLine()?.trim() ?: ""

        // Registrar
        registrarVehiculo(
            placa = placa,
            tipo = tipo,
            horasIngresadas = horas,
            cliente = cliente
        )

        println()
    }

    println("======================================")
    println("       REGISTRO FINALIZADO")
    println("======================================")
}

