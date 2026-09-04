package com.maza.lab02_dm

-------------------------------------------------
// DATA CLASS
// Representa un vehículo registrado.
// ---------------------------------------------------------
data class RegistroVehiculo(
    val placa: String,
    val tipo: String,
    val horas: Int,
    val cliente: String,
    val esFrecuente: Boolean,
    var totalPagado: Double = 0.0
)


// ---------------------------------------------------------
// VARIABLES GLOBALES
// ---------------------------------------------------------
var limiteVehiculos: Int = 0

val listaVehiculos =
    mutableListOf<RegistroVehiculo>()

val historialVisitas =
    mutableMapOf<String, Int>()


// ---------------------------------------------------------
// INICIAR PROCESAMIENTO
// ---------------------------------------------------------
fun iniciarProcesamiento(cantidad: Int) {

    if (cantidad > 0) {

        limiteVehiculos = cantidad

        listaVehiculos.clear()

        historialVisitas.clear()

        println("Sistema configurado para $cantidad vehículos.")
    }
}


// ---------------------------------------------------------
// CALCULAR SI EL CLIENTE ES FRECUENTE
// ---------------------------------------------------------
fun calcularSiEsFrecuente(placa: String): Boolean {

    val visitasAnteriores =
        historialVisitas.getOrDefault(placa, 0)

    val visitasActuales =
        visitasAnteriores + 1

    historialVisitas[placa] =
        visitasActuales

    return visitasActuales > 1
}


// ---------------------------------------------------------
// CALCULAR PAGO
// ---------------------------------------------------------
fun calcularPago(vehiculo: RegistroVehiculo): Double {

    val tarifaBase = when (vehiculo.tipo.lowercase()) {

        "moto" -> 2.0

        "auto" -> 4.0

        "camioneta" -> 10.0

        else -> 0.0
    }


    var subtotal = 0.0

    val horas = vehiculo.horas


    // -----------------------------------------------------
    // TRAMO 1
    // Primeras 2 horas con tarifa normal
    // -----------------------------------------------------
    val horasNormales =
        if (horas > 2) 2 else horas

    subtotal +=
        horasNormales * tarifaBase


    // -----------------------------------------------------
    // TRAMO 2
    // De la tercera a la quinta hora
    // Recargo del 20%
    // -----------------------------------------------------
    if (horas > 2) {

        val horasCon20 =
            if (horas > 5) {
                3
            } else {
                horas - 2
            }

        subtotal +=
            horasCon20 * (tarifaBase * 1.20)
    }


    // -----------------------------------------------------
    // TRAMO 3
    // Más de 5 horas
    // Recargo del 50%
    // -----------------------------------------------------
    if (horas > 5) {

        val horasCon50 =
            horas - 5

        subtotal +=
            horasCon50 * (tarifaBase * 1.50)
    }


    // -----------------------------------------------------
    // DESCUENTO POR CLIENTE FRECUENTE
    // -----------------------------------------------------
    if (vehiculo.esFrecuente) {

        subtotal *= 0.90
    }


    // Guardamos el total
    vehiculo.totalPagado =
        subtotal

    return subtotal
}


// ---------------------------------------------------------
// MOSTRAR VEHÍCULOS REGISTRADOS
// ---------------------------------------------------------
fun mostrarVehiculosRegistrados() {

    println()
    println("======================================")
    println("      LISTA DE VEHÍCULOS REGISTRADOS")
    println("======================================")


    for ((index, vehiculo)
    in listaVehiculos.withIndex()) {

        println()
        println("Vehículo ${index + 1}")
        println("--------------------------------------")

        println(
            "Placa: ${vehiculo.placa}"
        )

        println(
            "Cliente: ${vehiculo.cliente}"
        )

        println(
            "Tipo: ${vehiculo.tipo}"
        )

        println(
            "Horas: ${vehiculo.horas}"
        )

        println(
            "Frecuente: ${
                if (vehiculo.esFrecuente)
                    "Sí (10% Dscto)"
                else
                    "No"
            }"
        )

        println(
            String.format(
                "Total a Pagar: S/ %.2f",
                vehiculo.totalPagado
            )
        )

        println("--------------------------------------")
    }
}


// ---------------------------------------------------------
// REGISTRAR VEHÍCULO
// ---------------------------------------------------------
fun registrarVehiculo(
    placa: String,
    tipo: String,
    horasIngresadas: Int,
    cliente: String
) {

    // Verificar si todavía hay espacio
    if (listaVehiculos.size < limiteVehiculos) {

        // Las horas mínimas son 1
        val horasReales =
            if (horasIngresadas < 1)
                1
            else
                horasIngresadas


        // Verificar si es cliente frecuente
        val esFrecuenteCalculado =
            calcularSiEsFrecuente(placa)


        // Crear registro
        val nuevoRegistro =
            RegistroVehiculo(
                placa = placa,
                tipo = tipo,
                horas = horasReales,
                cliente = cliente,
                esFrecuente = esFrecuenteCalculado
            )


        // Calcular pago
        calcularPago(nuevoRegistro)


        // Agregar a la lista
        listaVehiculos.add(nuevoRegistro)


        println()
        println(
            "Vehículo registrado: ${nuevoRegistro.placa}"
        )


        // Si se llegó al límite, mostrar todos
        if (listaVehiculos.size == limiteVehiculos) {

            mostrarVehiculosRegistrados()
        }

    } else {

        println(
            "No se pueden registrar más vehículos."
        )
    }
}


// =========================================================
// MAIN
// =========================================================
fun main() {

    println("======================================")
    println("     SISTEMA DE ESTACIONAMIENTO")
    println("======================================")

    println(
        "Iniciando sistema..."
    )


    // -----------------------------------------------------
    // 1. Configuramos el límite
    // -----------------------------------------------------
    iniciarProcesamiento(3)


    // -----------------------------------------------------
    // 2. Registramos un auto
    // Juan - 4 horas
    // -----------------------------------------------------
    registrarVehiculo(
        "ABC-123",
        "Auto",
        4,
        "Juan Perez"
    )


    // -----------------------------------------------------
    // 3. Registramos una moto
    // Maria - 6 horas
    // -----------------------------------------------------
    registrarVehiculo(
        "MOT-099",
        "Moto",
        6,
        "Maria Lopez"
    )


    // -----------------------------------------------------
    // 4. Registramos nuevamente a Juan
    // Como es la segunda visita, obtiene 10% de descuento.
    // -----------------------------------------------------
    registrarVehiculo(
        "ABC-123",
        "Auto",
        2,
        "Juan Perez"
    )


    println()
    println("======================================")
    println("       PROGRAMA FINALIZADO")
    println("======================================")
}