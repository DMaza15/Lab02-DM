package com.maza.lab02_dm

import org.junit.Test

class TareaTest {

    @Test
    fun ejecutar() {

        println("======================================")
        println("     SISTEMA DE ESTACIONAMIENTO")
        println("======================================")

        print("Ingrese la cantidad de vehículos a registrar: ")

        val cantidad =
            readLine()?.toIntOrNull() ?: 0

        if (cantidad <= 0) {
            println("Cantidad inválida.")
            return
        }

        iniciarProcesamiento(cantidad)

        println()

        for (i in 1..cantidad) {

            println("--------------------------------------")
            println("          VEHÍCULO $i")
            println("--------------------------------------")

            print("Ingrese la placa: ")
            val placa =
                readLine()?.trim() ?: ""

            print("Ingrese el tipo (Moto/Auto/Camioneta/Trailer): ")
            val tipo =
                readLine()?.trim() ?: ""

            print("Ingrese las horas: ")
            val horas =
                readLine()?.toIntOrNull() ?: 1

            print("Ingrese el nombre del cliente: ")
            val cliente =
                readLine()?.trim() ?: ""

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
}