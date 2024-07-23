package com.example.antiquote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    val CHANNEL_ID = "channel_id"
    val CHANNEL_NAME = "channel_name"
    val database : DatabaseReference = FirebaseDatabase.getInstance("https://antiquote-5d810-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Quotes")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var randomCount = 0
        val listOfQuotes = ArrayList<Quotes>()

        val imageBtnAdd = findViewById<ImageButton>(R.id.image_btn_add)
        val quoteTextView = findViewById<TextView>(R.id.text_quote)
        val constraintMain = findViewById<ConstraintLayout>(R.id.constraint_main)
        val titleTextView = findViewById<TextView>(R.id.text_title)
        val settingsBtn = findViewById<ImageButton>(R.id.image_btn_settings)

        // code for notification
        createNotificationsChannels()
        ReminderManager.startReminder(this)

        //val customFont : Typeface? = ResourcesCompat.getFont(this, R.font.miltonian_tattoo)
        //quoteTextView.typeface = customFont

        constraintMain.setBackground(ContextCompat.getDrawable(applicationContext, BackgroundSetter.getBackgrounds()))

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                    for (quoteSnapshot in p0.children) {
                        randomCount += 1
                        val quote = quoteSnapshot.getValue(Quotes::class.java)
                        if (quote != null) {
                            listOfQuotes.add(quote)
                        }
                    }

                    // WOW!! So shuffle isn't repeating itself like random is !!!
                    val randomQuote = (1..randomCount).shuffled().last()
                    val randomSavedQuote = (1..randomCount).shuffled().last()

                for(quote in listOfQuotes){
                    if(quote.id == randomQuote){
                        quoteTextView.text = "\"" + quote.quote + "\""
                        titleTextView.text = quote.title

                    }
                    if(SharedQuotePrefs.getShouldISaveQuote(applicationContext) == true){
                        if(quote.id == randomSavedQuote){
                            SharedQuotePrefs.setQuotePreference(applicationContext, quote.quote)
                            SharedQuotePrefs.setShouldISaveQuote(applicationContext, false)
                        }
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.")
            }
        })

        imageBtnAdd.setOnClickListener {
            val intent = Intent(applicationContext, AddActivity::class.java)
            intent.putExtra("id_count", randomCount.toString())
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        settingsBtn.setOnClickListener {
            val window = PopupWindow(this)
            val view = layoutInflater.inflate(R.layout.settings_popup, null)
            window.contentView = view
            window.showAsDropDown(quoteTextView)
            constraintMain.setOnClickListener{
                window.dismiss()
            }
        }
    }


    private fun createNotificationsChannels() {
        // before version Oreo, we didn't need this notification channel
        // notification channel is used to configure and set rules for notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // importance high gives a sound to notification
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            ContextCompat.getSystemService(this, NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

}
