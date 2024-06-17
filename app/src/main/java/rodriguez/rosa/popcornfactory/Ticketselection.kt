package rodriguez.rosa.popcornfactory

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.forEach

class Ticketselection : AppCompatActivity() {

    private var ticketsVendidos: SoldTickets? = null
    private var radioGroup1: RadioGroup? = null
    private var radioGroup2: RadioGroup? = null
    private var radioGroup3: RadioGroup? = null
    private var radioGroup4: RadioGroup? = null
    private var peliculaId: String = "-1"

    companion object {

        private var intent: Intent? = null
        fun getIntent(context: Context): Intent {
            if (intent == null) {
                intent = Intent(context, Ticketselection::class.java)
            }

            return intent as Intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tickets_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ticketsVendidos = SoldTickets.getInstance()

        val movieTitle: TextView  = findViewById(R.id.titleSeats)
        val confirmButton: Button = findViewById<Button>(R.id.confirmButton)

        if (intent.extras == null) {
            return;
        }

        val textMovieTitle = intent.extras!!.getString("title")
        val posMovie       = intent.extras!!.getInt("id")

        movieTitle.text = textMovieTitle
        this.peliculaId = posMovie.toString()

        confirmButton.setOnClickListener {

            val asientoSelccionado: AsientoSeleccionado = obtenerAsientoSelecionado()
            if(asientoSelccionado.fila == -1) {
                return@setOnClickListener
            }

            ticketsVendidos!!.agregarTicket(
                posMovie.toString(), Ticket(asientoSelccionado.fila, asientoSelccionado.asientoid)
            )

            val intent: Intent = Intent(this, CompraExitosa::class.java)
            this.startActivity(intent)
        }

        radioGroup1 = findViewById<RadioGroup>(R.id.rowOne)
        radioGroup2 = findViewById(R.id.rowTwo)
        radioGroup3 = findViewById(R.id.rowThree)
        radioGroup4 = findViewById(R.id.rowFour)

        radioGroup1!!.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId > -1) {
                radioGroup2!!.clearCheck()
                radioGroup3!!.clearCheck()
                radioGroup4!!.clearCheck()
                radioGroup1!!.check(checkedId)
            }
        }

        radioGroup2!!.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId > -1) {
                radioGroup1!!.clearCheck()
                radioGroup3!!.clearCheck()
                radioGroup4!!.clearCheck()
                radioGroup2!!.check(checkedId)
            }
        }

        radioGroup3!!.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId > -1) {
                radioGroup2!!.clearCheck()
                radioGroup4!!.clearCheck()
                radioGroup1!!.clearCheck()
                radioGroup3!!.check(checkedId)
            }
        }

        radioGroup4!!.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId > -1) {
                radioGroup2!!.clearCheck()
                radioGroup3!!.clearCheck()
                radioGroup1!!.clearCheck()
                radioGroup4!!.check(checkedId)
            }
        }

        verificarAsientosSeleccionados()

    }

    private fun verificarAsientosSeleccionados(): Unit {
        radioGroup1!!.forEach {
            if(ticketsVendidos!!.esAsientoComprado(peliculaId, it.id, 1)) {
                it.isEnabled = false
            }
        }
        radioGroup2!!.forEach {
            if(ticketsVendidos!!.esAsientoComprado(peliculaId, it.id, 2)) {
                it.isEnabled = false
            }
        }
        radioGroup3!!.forEach {
            if(ticketsVendidos!!.esAsientoComprado(peliculaId, it.id, 3)) {
                it.isEnabled = false
            }
        }
        radioGroup4!!.forEach {
            if(ticketsVendidos!!.esAsientoComprado(peliculaId, it.id, 4)) {
                it.isEnabled = false
            }
        }

    }

    private fun obtenerAsientoSelecionado(): AsientoSeleccionado {
        return if (radioGroup1!!.checkedRadioButtonId != -1) {
            AsientoSeleccionado(radioGroup1!!.checkedRadioButtonId, 1)
        } else if (radioGroup2!!.checkedRadioButtonId != -1) {
            AsientoSeleccionado(radioGroup2!!.checkedRadioButtonId, 2)
        } else if (radioGroup3!!.checkedRadioButtonId != -1) {
            AsientoSeleccionado(radioGroup3!!.checkedRadioButtonId, 3)
        } else if (radioGroup4!!.checkedRadioButtonId != -1){
            AsientoSeleccionado(radioGroup4!!.checkedRadioButtonId, 4)
        } else {
            AsientoSeleccionado(-1, -1)
        }
    }

    data class AsientoSeleccionado(val asientoid: Int, val fila: Int);

}