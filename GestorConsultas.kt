package ProyectoADD

import java.io.*


class GestorConsultas {
    var stream: RandomAccessFile? = null

    init {
        creaFichero("discosDAML.dat")
    }

    private fun creaFichero(nombreFichero: String) {
        try {
            val exists = File(nombreFichero).exists()
            stream = RandomAccessFile(nombreFichero, "rw")
            if (!exists) {
                creaDiscosPorDefecto()
            }
        } catch (e: FileNotFoundException) {
            println("Error al abrir el fichero: $nombreFichero")
            System.exit(0)
        }
    }

    private fun creaDiscosPorDefecto() {
        val disco1 = Disco(1, "Que voy a hacer", "Los Planetas", 20.0, 5)
        disco1.escribeEnFichero(stream!!)
        val disco2 = Disco(2, "La voz del presidente", "Viva Suecia", 35.0, 1)
        disco2.escribeEnFichero(stream!!)
        val disco3 = Disco(3, "La revolución sexual", "La casa azul", 20.0, 10)
        disco3.escribeEnFichero(stream!!)
        val disco4 = Disco(4, "Finisterre", "Vetusta Morla", 40.0, 5)
        disco4.escribeEnFichero(stream!!)
        val disco5 = Disco(5, "Paradise", "Coldplay", 35.0, 2)
        disco5.escribeEnFichero(stream!!)
    }

    fun cierraGestor() {
        try {
            stream!!.close()
        } catch (e: IOException) {
            println("No se ha podido cerrar el fichero")
        }
    }

    private fun buscaCodigo(codigoBuscado: Int): Long {
        val disco = Disco()
        try {
            stream!!.seek(0)
            while (true) {
                val posicion = stream!!.filePointer
                disco.leeDeFichero(stream!!)
                if (disco.codigo == codigoBuscado) {
                    return posicion
                }
            }
        } catch (e: EOFException) {
            return -1
        } catch (e: IOException) {
            println("Error al buscar el disco por código")
            System.exit(0)
        }
        return -1
    }

    fun listaAutores(): Array<String?> {
        val autoresSet = HashSet<String?>()
        val disco = Disco()
        try {
            stream!!.seek(0)
            while (true) {
                disco.leeDeFichero(stream!!)
                autoresSet.add(disco.autor)
            }
        } catch (e: EOFException) {
            val autoresArray = arrayOfNulls<String>(autoresSet.size)
            autoresSet.toArray(autoresArray)
            return autoresArray
        } catch (e: IOException) {
            println("Error al listar los autores")
            System.exit(0)
        }
        return arrayOfNulls(0)
    }

    fun buscaAutor(autorBuscado: String?): Array<String?> {
        val disco = Disco()
        val discosAutor = StringBuilder()
        try {
            stream!!.seek(0)
            while (true) {
                disco.leeDeFichero(stream!!)
                if (disco.autor.equals(autorBuscado, ignoreCase = true)) {
                    discosAutor.append(disco.toString()).append("\n\n")
                }
            }
        } catch (e: EOFException) {
            return discosAutor.toString().split("\n\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray<String?>()
        } catch (e: IOException) {
            println("Error al buscar los discos por autor")
            System.exit(0)
        }
        return arrayOfNulls(0)
    }

    fun compraDisco(codigoBuscado: Int): String {
        val posicion = buscaCodigo(codigoBuscado)
        if (posicion == -1L) {
            return "No puedes comprar un disco que no está en el catálogo."
        }
        try {
            stream!!.seek(posicion)
            val disco = Disco()
            disco.leeDeFichero(stream!!)
            val cantidadActual = disco.cantidad
            if (cantidadActual > 0) {
                val nuevaCantidad = cantidadActual - 1
                stream!!.seek(posicion + 32)
                stream!!.writeInt(nuevaCantidad)
                disco.cantidad = nuevaCantidad
                return disco.toString()
            } else {
                return "No hay ejemplares disponibles de este disco en el catálogo."
            }
        } catch (e: IOException) {
            println("Error al comprar el disco")
            System.exit(0)
        }
        return ""
    }

    fun revenderDisco(codigoBuscado: Int): String {
        val posicion = buscaCodigo(codigoBuscado)
        if (posicion == -1L) {
            return "No puedes revender un disco que no está en el catálogo."
        }
        try {
            stream!!.seek(posicion)
            val disco = Disco()
            disco.leeDeFichero(stream!!)
            val nuevaCantidad = disco.cantidad + 1
            stream!!.seek(posicion + 32)
            stream!!.writeInt(nuevaCantidad)
            disco.cantidad = nuevaCantidad
            return disco.toString()
        } catch (e: IOException) {
            println("Error al revender el disco")
            System.exit(0)
        }
        return ""
    }

}


