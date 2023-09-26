package ProyectoADD

import java.io.EOFException
import java.io.IOException
import java.io.RandomAccessFile
import java.util.*


class Disco {
    var codigo = 0
    var titulo: String? = null
    var autor: String? = null
    var precio = 0.0
    var cantidad = 0

    constructor()
    constructor(codigo: Int, titulo: String?, autor: String?, precio: Double, cantidad: Int) {
        this.codigo = codigo
        this.titulo = titulo
        this.autor = autor
        this.precio = precio
        this.cantidad = cantidad
    }

    override fun toString(): String {
        return "Código: $codigo\nTítulo: $titulo\nAutor: $autor\nPrecio: $precio\nCantidad: $cantidad"
    }

    fun leeDeTeclado(teclado: Scanner) {
        print("Introduce el código del disco: ")
        codigo = teclado.nextInt()
        teclado.nextLine()
        print("Introduce el título del disco: ")
        titulo = teclado.nextLine()
        print("Introduce el autor del disco: ")
        autor = teclado.nextLine()
        print("Introduce el precio del disco: ")
        precio = teclado.nextDouble()
        print("Introduce la cantidad de ejemplares disponibles: ")
        cantidad = teclado.nextInt()
    }

    @Throws(IOException::class)
    fun escribeEnFichero(stream: RandomAccessFile) {
        stream.writeInt(codigo)
        stream.writeUTF(titulo)
        stream.writeUTF(autor)
        stream.writeDouble(precio)
        stream.writeInt(cantidad)
    }

    @Throws(EOFException::class, IOException::class)
    fun leeDeFichero(stream: RandomAccessFile) {
        codigo = stream.readInt()
        titulo = stream.readUTF()
        autor = stream.readUTF()
        precio = stream.readDouble()
        cantidad = stream.readInt()
    }
}


