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


fun iniciarProcesamiento(cantidad: Int) {
    if (cantidad > 0) {
        limiteVehiculos = cantidad
        listaVehiculos.clear()
    }
}

fun registrarVehiculo(placa: String, tipo: String, horasIngresadas: Int, cliente: String, esFrecuente: Boolean ) {
    if (listaVehiculos.size < limiteVehiculos){

        val horasReales = if (horasIngresadas < 1) 1 else horasIngresadas

        val nuevoRegistro = RegistroVehiculo(
            placa = placa,
            tipo = tipo,
            horas= horasReales,
            cliente = cliente,
            esFrecuente = esFrecuente
        )

        listaVehiculos.add(nuevoRegistro)
    }
}