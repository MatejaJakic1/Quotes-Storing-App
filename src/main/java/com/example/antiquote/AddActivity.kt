package com.example.antiquote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class AddActivity : AppCompatActivity() {

    val database : DatabaseReference = FirebaseDatabase.getInstance("https://antiquote-5d810-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Quotes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        //val idCount = findViewById<TextView>(R.id.text_id_count)
        var id = intent.extras!!.getString("id_count")!!.toInt()

        val backButton = findViewById<ImageButton>(R.id.image_btn_back)
        val addButton = findViewById<Button>(R.id.button_add)
        val enterQuote = findViewById<EditText>(R.id.enter_quote)
        val constraintAdd = findViewById<ConstraintLayout>(R.id.constraint_add)
        val enterTitle = findViewById<EditText>(R.id.enter_title)
        val clearButton = findViewById<ImageButton>(R.id.image_btn_clear)

        constraintAdd.setBackground(ContextCompat.getDrawable(applicationContext, BackgroundSetter.getBackgrounds()))

        backButton.setOnClickListener {
            val newIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(newIntent)
            overridePendingTransition(0, 0)
            finish()
        }

        addButton.setOnClickListener {
            id += 1
            val quote = enterQuote.text.toString()
            val title = enterTitle.text.toString()
            val readyQuote = Quotes(id, quote, title)
            val quoteID = database.push().key

            if (quoteID != null && enterQuote.text.isNotEmpty()) {
                database.child(quoteID).setValue(readyQuote).addOnCompleteListener {
                    enterQuote.text.clear()
                    Toast.makeText( applicationContext, "Uspješno pohranjen citat", Toast.LENGTH_SHORT).show()
                }
             }
        }
        clearButton.setOnClickListener {
            enterTitle.text.clear()
        }
    }
}