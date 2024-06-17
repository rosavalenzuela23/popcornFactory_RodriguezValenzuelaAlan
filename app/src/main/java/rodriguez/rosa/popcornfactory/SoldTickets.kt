package rodriguez.rosa.popcornfactory

class SoldTickets(private val ticketsComprados: HashMap<String, ArrayList<Ticket>>) {

    companion object {

        private var instance: SoldTickets? = null

        fun getInstance(): SoldTickets {
            if (this.instance == null) {
                instance = SoldTickets(HashMap<String, ArrayList<Ticket>>())
            }
            return instance as SoldTickets
        }

    }

    fun agregarTicket(id: String, ticket: Ticket) {

        if (!ticketsComprados.containsKey(id)) {
            ticketsComprados[id] = arrayListOf(ticket)
            return
        }

        val listaTickets = ticketsComprados[id]
        listaTickets!!.add(ticket)
    }

    fun esAsientoComprado(id: String, idAsiento: Int, fila: Int): Boolean {

        if (!ticketsComprados.containsKey(id)) {
            return false
        }

        val listaTickets = ticketsComprados[id] as ArrayList<Ticket>

        for (ticket in listaTickets) {

            if (ticket.row == fila && ticket.id == idAsiento ) {
                return true
            }

        }

        return false
    }

    fun obtenerAsientosVendidos(id: String): Int {
        return if (ticketsComprados[id] == null) {
            0
        } else {
            ticketsComprados[id]!!.size
        }
    }

}