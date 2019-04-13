package raslan.learn.islam.util

import android.content.Context
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.util.Log
import java.util.*


class Utils{

    companion object {

        fun changeLanguage(context: Context, lang: String) {
            //Log.e(context.packageName, lang)
            AppPreference.lang = lang
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = context.resources.configuration
            config.setLocale(locale)
            context.createConfigurationContext(config)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context.resources.configuration.setLocale(locale)
        }

        fun haveNetworkConnection(context: Context): Boolean {
            var haveConnectedWifi = false
            var haveConnectedMobile = false

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals("WIFI", ignoreCase = true))
                    if (ni.isConnected)
                        haveConnectedWifi = true
                if (ni.typeName.equals("MOBILE", ignoreCase = true))
                    if (ni.isConnected)
                        haveConnectedMobile = true
            }
            return haveConnectedWifi || haveConnectedMobile
        }
    }
}