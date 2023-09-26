package ProyectoADD

import java.util.*


object PruebaTiendaLocal {
    @JvmStatic
    fun main(args: Array<String>) {
        val gestor = GestorConsultas()
        val scanner = Scanner(System.`in`)
        while (true) {
            println("\nMenu de opciones:")
            println("1. Salir del programa.")
            println("2. Listar los autores en el catálogo de la tienda.")
            println("3. Buscar un autor o grupo con un nombre dado y mostrar sus discos en la tienda.")
            println("4. Comprar un disco con un código dado y mostrar sus datos.")
            println("5. Revender a la tienda un ejemplar de un disco de su catálogo con un código dado y mostrar sus datos.")
            print("Seleccione una opción: ")
            val opcion = scanner.nextInt()
            scanner.nextLine()
            when (opcion) {
                1 -> {
                    gestor.cierraGestor()
                    scanner.close()
                    System.exit(0)
                }

                2 -> {
                    val autores = gestor.listaAutores()
                    println("\nAutores en el catálogo de la tienda:")
                    for (autor in autores) {
                        println(autor)
                    }
                }

                3 -> {
                    print("Introduce el nombre del autor o grupo a buscar: ")
                    val autorBuscado = scanner.nextLine()
                    val discosAutor = gestor.buscaAutor(autorBuscado)
                    if (discosAutor.size > 0) {
                        println("\nDiscos de $autorBuscado:")
                        for (disco in discosAutor) {
                            println(disco)
                        }
                    } else {
                        println("No se encontraron discos de $autorBuscado.")
                    }
                }

                4 -> {
                    print("Introduce el código del disco a comprar: ")
                    val codigoCompra = scanner.nextInt()
                    scanner.nextLine()
                    val compraResult = gestor.compraDisco(codigoCompra)
                    if (compraResult.contains("No puedes comprar un disco que no está en el catálogo.")) {
                        println(compraResult)
                    } else if (!compraResult.isEmpty()) {
                        println("\nDisco comprado:\n$compraResult")
                    } else {
                        println("El disco no está disponible en el catálogo.")
                    }
                }

                5 -> {
                    print("Introduce el código del disco a revender: ")
                    val codigoVenta = scanner.nextInt()
                    scanner.nextLine()
                    val ventaResult = gestor.revenderDisco(codigoVenta)
                    if (!ventaResult.contains("No puedes revender un disco que no está en el catálogo.")) {
                        println("\nDisco revendido:\n$ventaResult")
                    } else {
                        println(ventaResult)
                    }
                }

                else -> println("Opción no válida. Introduce un número del 1 al 5.")
            }
        }
    }
}

