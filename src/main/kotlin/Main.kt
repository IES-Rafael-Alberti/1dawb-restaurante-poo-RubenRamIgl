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


fun main() {
    val plato1 = Plato("Hamburguesa", 8.99, 8, mutableListOf("carne", "huevo", "queso", "pan", "tomate"))

    plato1.agregarIngrediente("lechuga")
    println(plato1)

    val pedido1 = Pedido(1, mutableListOf(), "pendiente")

    pedido1.agregarPlato(plato1)
    println(pedido1)
    println("Precio total del pedido: ${pedido1.calcularPrecio()}€")
    println("Tiempo total de preparación: ${pedido1.calcularTiempo()} minutos")

    val mesa1 = Mesa(1, 4)

    mesa1.ocuparMesa()
    mesa1.agregarPedido(pedido1)
    println(mesa1)
}
