package com.maza.lab02_dm

data class RegistroVehiculo (
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

fun registrarVehiculo(placa: String, tipo: String, horasIngresadas: Int, cliente: String, esFrecuente: Boolean ) {
    if (listaVehiculos.size < limiteVehiculos){

        val horasReales = if (horasIngresadas < 1) 1 else horasIngresadas

        val esFrecuenteCalculado = calculcularSiEsFrecuente(placa)

        val nuevoRegistro = RegistroVehiculo(
            placa = placa,
            tipo = tipo,
            horas= horasReales,
            cliente = cliente,
            esFrecuente = esFrecuente
        )

        calcularPago(nuevoRegistro)
        listaVehiculos.add(nuevoRegistro)

        if (listaVehiculos.size == listaVehiculos){
            mostrarVehiculosRegistrados()
        }
    }
}


fun calcularSiEsFrecuente(placa: String): Boolean{
    val visistasAnteriores = historialVisitas.getOrDefault(placa, 0)

    val visitasActuales = visistasAnteriores + 1
    historialVisitas[placa] = visitasActuales

    return visitasActuales > 1
}

fun calcularPago(vehiculo: RegistroVehiculo): Double {

    val tarifaBase = when (vehiculo.tipo.lowercase()){
        "moto" -> 2.0
        "auto" -> 4.0
        "camioneta" -> 10.0
        else -> 0.0
    }

    var subtotal = 0.0
    val horas = vehiculo.horas

    val horasNormales = if (horas > 2) 2 else horas
    subtotal += horasNormales * tarifaBase

    if (horas > 2) {
        val horasCon20 = if (horas > 5) 3 else (horas - 2)
        subtotal += horasCon20 * (tarifaBase * 1.20)
    }

    if (horas > 5) {
        val horascon50 = horas - 5
        subtotal += horascon50 * (tarifaBase * 1.50)
    }

    if (vehiculo.esFrecuente) {
        subtotal = subtotal * 0.90
    }

    vehiculo.totalPagado = subtotal

    return subtotal
}

fun mostrarVehiculosRegistrados(){
    println("=== LISTA DE VEHÍCULOS REGISTRADOS ===")
    for ((index, vehiculo) in listaVehiculos.withIndex()) {
        val datos = """
            Vehículo ${index + 1}:
           -Placa: ${vehiculo.placa}
           -Cliente: ${vehiculo.cliente}
           -Tipo: ${vehiculo.tipo}
           -Horas: ${vehiculo.horas}
           -Frecuente: ${if (vehiculo.esFrecuente) "Sí (10% Dscto) else "No""}
           -Total a Pagar: S/ ${String.format("%.2f", vehiculo.totalPagado)}
           ----------------------------------
       """.trimIndent()

       println(datos)
    }
}