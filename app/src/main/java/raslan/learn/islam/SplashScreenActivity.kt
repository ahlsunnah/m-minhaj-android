package raslan.learn.islam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import raslan.learn.islam.activities.LanguageActivity
import raslan.learn.islam.util.AppPreference
import raslan.learn.islam.util.Utils


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({

            var intent : Intent?

            Utils.changeLanguage(this, AppPreference.lang)
            intent = if (AppPreference.firstRun)
                Intent(Intent(this, LanguageActivity::class.java))
            else Intent(Intent(this, MainActivity::class.java))

            startActivity(intent)
            finish()
        }, 100)
    }

}
