


import android.content.Context
import android.content.SharedPreferences
import androidx.activity.ComponentActivity

import com.pr7.jc_prnote.data.local.room.CONTEXT


const val ONBOARDING="Onboarding"

object SharedPrefManager {

    fun saveString(key: String, value: String?) {
        val editor = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
            .edit() as SharedPreferences.Editor
        editor.putString(key, value)
        editor.commit()
    }

    fun loadString(key: String): String? {
        val sharedPreferences = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    fun saveBoolean(key: String, value: Boolean) {
        val editor = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
            .edit() as SharedPreferences.Editor
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun loadBoolean(key: String): Boolean {
        val sharedPreferences = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun saveInt(key: String, value: Int) {
        val editor = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
            .edit() as SharedPreferences.Editor
        editor.putInt(key, value)
        editor.commit()
    }

    fun loadInt(key: String): Int {
        val sharedPreferences = CONTEXT.getSharedPreferences("Pr", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0)
    }
//    fun setLocale(language: String?) {
//
//        if (Build.VERSION.SDK_INT >= TIRAMISU) {
//            CONTEXT.getSystemService(LocaleManager::class.java).applicationLocales =
//                LocaleList.forLanguageTags(language)
//        }
//        else {
//            val appLocale: LocaleListCompat =
//                LocaleListCompat.forLanguageTags(language)
//            AppCompatDelegate.setApplicationLocales(appLocale)
//
//        }


//    }




}