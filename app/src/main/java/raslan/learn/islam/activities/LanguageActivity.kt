package raslan.learn.islam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import raslan.learn.islam.R
import raslan.learn.islam.databinding.ActivityLanguageBinding
import raslan.learn.islam.util.Utils
import raslan.learn.islam.worker.ResponseWorker

class LanguageActivity : AppCompatActivity(), View.OnClickListener {


    var binder: ActivityLanguageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLanguageBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_language)
        binding.arabicCard.setOnClickListener(this)
        binding.frenchCard.setOnClickListener(this)
        binding.englishCard.setOnClickListener(this)

        binding.retry.setOnClickListener {
            loadData()
            binding.progressText.text = getString(R.string.initiating_data_for_first_time)
            it.visibility = GONE
        }
        binder = binding

    }

    override fun onClick(p0: View?) {
        when {
            p0!!.id == R.id.arabicCard -> Utils.changeLanguage(this, "ar")
            p0.id == R.id.frenchCard -> Utils.changeLanguage(this, "fr")
            p0.id == R.id.englishCard -> Toast.makeText(this, "Coming soon...", Toast.LENGTH_LONG).show()
        }

        if (p0!!.id != R.id.englishCard) {
            binder?.languageLayout!!.visibility = GONE
            binder?.loadDataLayout!!.visibility = VISIBLE
            loadData()
        }
    }

    private fun loadData() {
        val request = OneTimeWorkRequestBuilder<ResponseWorker>().build()
        WorkManager.getInstance().enqueue(request)

        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id).observe(this, Observer { workInfo ->
            if (workInfo != null && workInfo.state.isFinished) {
                val code = workInfo.outputData.getInt(ResponseWorker.RESULT_CODE, 0)
                val message = workInfo.outputData.getString(ResponseWorker.RESULT_MESSAGE)
                Log.i("Language Activity", "$code , $message")
                binder?.progress!!.visibility = GONE
                if (code != ResponseWorker.SUCCESS) {
                    binder?.progressText!!.text = message
                    binder?.retry!!.visibility = VISIBLE
                }

                if (code == ResponseWorker.SUCCESS) {
                    startActivity(Intent(this, IntroductionActivity::class.java))
                    finish()
                } else {

                }



            }
        })
    }


}
