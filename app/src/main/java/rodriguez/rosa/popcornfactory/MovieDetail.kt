package rodriguez.rosa.popcornfactory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = intent.extras!!.getString("titulo")
        val header = intent.extras!!.getInt("header")
        val desc   = intent.extras!!.getString("sinopsis")
        val numberOfSeats = intent.extras!!.getInt("numberOfSeats")
        val pos    = intent.extras!!.getInt("pos")

        val imageHeader: ImageView = findViewById(R.id.image_movie_cell)
        val title: TextView = findViewById<TextView>(R.id.movie_title_cell)
        val description: TextView = findViewById(R.id.descripcion_pelicula)
        val seatsText: TextView = findViewById(R.id.seatsLeft)
        val buyTicketsButton: Button = findViewById<Button>(R.id.buy_tickets)

        imageHeader.setImageResource(header)
        title.text = titulo
        description.text = desc
        seatsText.text = String.format("Number of seats available $numberOfSeats")

        if (numberOfSeats == 0) {
            buyTicketsButton.isEnabled = false
        }

        buyTicketsButton.setOnClickListener {
            val intento: Intent = Ticketselection.getIntent(this)
            intento.putExtra("id", pos)
            intento.putExtra("title", titulo)
            this.startActivity(intento)
        }

    }
}