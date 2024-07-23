package com.example.antiquote

import android.content.Context
import android.content.SharedPreferences

object SharedQuotePrefs {
    fun getQuotePreference(context: Context) : String? {
        return context.getSharedPreferences("QUOTE_PREFERENCE", Context.MODE_PRIVATE).getString("saved_quote", null)
    }

    fun setQuotePreference(context: Context, quote : String){
        val quotePreference = context.getSharedPreferences("QUOTE_PREFERENCE", Context.MODE_PRIVATE)
        val editor = quotePreference.edit()
        editor.putString("saved_quote", quote)
        editor.apply()
    }

    fun getShouldISaveQuote(context: Context) : Boolean?{
        return context.getSharedPreferences("SAVE_PREFERENCE", Context.MODE_PRIVATE).getBoolean("should_save", true)
    }

    fun setShouldISaveQuote(context: Context, save : Boolean){
        val savePreference = context.getSharedPreferences("SAVE_PREFERENCE", Context.MODE_PRIVATE)
        val editor = savePreference.edit()
        editor.putBoolean("should_save", save).apply()
    }
}