package raslan.learn.islam.util

import android.content.Context
import android.content.SharedPreferences

object AppPreference {
    private const val NAME = "LEARN_ISLAM.APP"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences
    // list of app specific preferences
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", true)
    private val CURRENT_LANGUAGE = "current_language"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            NAME,
            MODE
        )
    }

    /**
     * SharedPreferences extension function, so we won't need to call edit()
    and apply()
     * ourselves on every SharedPreferences operation.
     */
    private inline fun SharedPreferences.edit(
        operation:
            (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var lang: String
        get() = preferences.getString(CURRENT_LANGUAGE, "ar")
        set(value) = preferences.edit {
            it.putString(CURRENT_LANGUAGE, value)
        }


    var firstRun: Boolean
        // custom getter to get a preference of a desired type, with a predefined default value
        get() = preferences.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        // custom setter to save a preference back to preferences file
        set(value) = preferences.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }

}
