open class Plato(
    var nombre: String,
    var precio: Double,
    var tiempoPreparacion: Int,
    val ingredientes: MutableList<String>
) {

    init {
        require(nombre.isNotBlank()) { "El nombre del plato no puede estar vacío" }
        require(precio > 0) { "El precio del plato debe ser mayor que 0" }
        require(tiempoPreparacion > 1) { "El tiempo de preparación debe ser mayor a 1 minuto" }
    }

    fun agregarIngrediente(ingrediente: String) {
        require(ingrediente.isNotBlank()) { "El ingrediente no puede estar vacío" }
        ingredientes.add(ingrediente)
    }

    override fun toString(): String {
        val ingredientesSeparados = ingredientes.joinToString(", ")
        return "$nombre (${tiempoPreparacion} min.) -> ${"%.2f".format(precio)}€ ($ingredientesSeparados)"
    }
}

open class Pedido(val numero: Int, var platos: MutableList<Plato>, var estado: String = "pendiente") {
    var contPedidos: Int = 0

    init {
        contPedidos++
    }

    fun agregarPlato(plato: Plato) {
        platos.add(plato)
    }

    fun eliminarPlato(nombrePlato: String) {
        platos.removeAll { it.nombre == nombrePlato }
    }

    fun calcularPrecio(): Double {
        return platos.sumByDouble { it.precio }
    }

    fun calcularTiempo(): Int {
        return platos.sumBy { it.tiempoPreparacion }
    }

    override fun toString(): String {
        val platosSeparados = platos.joinToString("\n")
        return "Pedido: $numero ($estado):$platosSeparados"
    }
}

open class Mesa(
    val numero: Int,
    val capacidad: Int,
    var estado: String = "libre",
    val pedidos: MutableList<Pedido> = mutableListOf()
) {

    init {
        require(capacidad in 1..6) { "La capacidad de la mesa debe estar entre 1 y 6" }
    }

    fun ocuparMesa() {
        if (estado == "libre") estado = "ocupada"
    }

    fun ocuparReserva() {
        if (estado == "reservada") estado = "ocupada"
    }

    fun liberarMesa() {
        estado = "libre"
    }

    fun agregarPedido(pedido: Pedido) {
        pedidos.add(pedido)
    }

    override fun toString(): String {
        return "Mesa número $numero Capacidad: $capacidad Estado: $estado"
    }

}

class SistemaGestionRestaurante(private val mesas: List<Mesa>) {

    fun realizarPedido(numeroMesa: Int, pedido: Pedido) {
        val mesa = mesas.find { it.numero == numeroMesa }
        if (mesa != null && mesa.estado == "ocupada") {
            mesa.agregarPedido(pedido)
        }
    }

    fun cerrarPedido(numeroMesa: Int, numeroPedido: Int? = null) {
        val mesa = mesas.find { it.numero == numeroMesa }
        if (mesa != null && mesa.estado == "ocupada") {
            val pedido = if (numeroPedido != null) mesa.pedidos.find { it.numero == numeroPedido } else mesa.pedidos.lastOrNull()
            if (pedido != null) {
                pedido.estado = "servido"
            }
        }
    }

    fun cerrarMesa(numeroMesa: Int) {
        val mesa = mesas.find { it.numero == numeroMesa }
        if (mesa != null && mesa.estado == "ocupada") {
            if (mesa.pedidos.all { it.estado == "servido" }) {
                mesa.liberarMesa()
            }
        }
    }

    fun buscarPlatos(): List<String>? {
        val platos = mesas.flatMap { it.pedidos }.flatMap { it.platos }.map { it.nombre }
        return platos.ifEmpty { null }
    }

    fun contarPlato(nombre: String): Int? {
        val count = mesas.flatMap { it.pedidos }
            .flatMap { it.platos }
            .count { it.nombre == nombre }
        return if (count > 0) count else null
    }

    fun buscarPlatoMasPedido(): List<String>? {
        val platoCounts = mesas.flatMap { it.pedidos }
            .flatMap { it.platos }
            .groupingBy { it.nombre }
            .eachCount()

        val maxCount = platoCounts.maxByOrNull { it.value }?.value
        return maxCount?.let { max -> platoCounts.filter { it.value == max }.keys.toList() }
    }
}


fun main() {// Inicialización de mesas
    val mesas = listOf(
        Mesa(numero = 1, capacidad = 4),
        Mesa(numero = 2, capacidad = 2),
        Mesa(numero = 3, capacidad = 6)
    )

    // Inicialización del sistema de gestión de restaurante
    val sistema = SistemaGestionRestaurante(mesas)

    // Creación de platos
    val plato1 = Plato("Hamburguesa", 9.99, 8, mutableListOf("carne", "huevo", "queso", "pan", "tomate"))
    val plato2 = Plato("Ensalada", 7.99, 5, mutableListOf("lechuga", "tomate", "zanahoria", "maíz"))
    val plato3 = Plato("Tortilla", 5.99, 10, mutableListOf("huevo", "patata"))
    val plato4 = Plato("Serranito", 6.00, 4, mutableListOf("carne", "pimiento", "pan", "jamón serrano"))
    val plato5 = Plato("Spagetti carbonara", 6.00, 12, mutableListOf("huevo", "pasta", "bacon", "nata"))
    val plato6 = Plato("Rissotto setas", 6.00, 12, mutableListOf("arroz", "setas", "gambas", "nata"))

    // Agregar ingredientes
    plato1.agregarIngrediente("salsa")
    plato2.agregarIngrediente("atún")

    //Simular el registro de comensales a una mesa
    mesas[0].ocuparMesa() // Ocupar mesa 1

    // Creación de pedidos
    val pedido1 = Pedido(1, mutableListOf(plato1, plato2, plato3, plato4), "pendiente")
    pedido1.agregarPlato(plato1)
    pedido1.agregarPlato(plato2)
    pedido1.agregarPlato(plato3)
    pedido1.agregarPlato(plato4)

    println("***** Pedido ${pedido1.numero} *****")
    println(pedido1)

    // Simulación del proceso de los pedidos
    sistema.realizarPedido(1, pedido1)

    println("***** Mesa ${mesas[0].numero} *****")
    println(mesas[0])

    //Simular el registro de comensales a una mesa
    mesas[1].ocuparMesa() // Ocupar mesa 2

    //Crear otro pedido
    val pedido2 = Pedido(2, mutableListOf(plato2, plato3, plato4), "pendiente")
    pedido2.agregarPlato(plato2)
    pedido2.agregarPlato(plato3)
    pedido2.agregarPlato(plato4)

    // Simulación del proceso de los pedidos
    sistema.realizarPedido(2, pedido2)

    //Crear un segundo pedido para la mesa 2
    val pedido3 = Pedido(3, mutableListOf(plato5, plato6), "pendiente")
    pedido3.agregarPlato(plato5)
    pedido3.agregarPlato(plato6)

    // Simulación del proceso de los pedidos
    sistema.realizarPedido(2, pedido3)

    println("***** Mesa ${mesas[1].numero} *****")
    println(mesas[1])

    // Cerrar pedidos y liberar mesas
    sistema.cerrarPedido(1)
    sistema.cerrarMesa(1)

    sistema.cerrarPedido(2)
    sistema.cerrarMesa(2)

    // Buscar platos y contar pedidos
    val platosPedidos = sistema.buscarPlatos() ?: listOf()
    if (platosPedidos.isNotEmpty()) {
        println("Platos pedidos: ${platosPedidos.joinToString()}")
    }
    else {
        println("No existen platos.")
    }

    val contPlato = sistema.contarPlato("Ensalada") ?: 0
    println("El plato 'Ensalada' fue pedido $contPlato " +
            "${if (contPlato == 1) "vez" else "veces" }.")

    val platosMasPedidos = sistema.buscarPlatoMasPedido()
    if (platosMasPedidos != null) {
        println("${if (platosMasPedidos.size == 1)
            "El plato más pedido es " else "Los platos más pedidos son "} " +
                "${platosMasPedidos.joinToString()}.")
    }
    else {
        println("No existen platos.")
    }

}
