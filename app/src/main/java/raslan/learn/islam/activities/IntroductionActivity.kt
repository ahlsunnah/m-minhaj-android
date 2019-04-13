package raslan.learn.islam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.activity_introduction.*
import raslan.learn.islam.MainActivity
import raslan.learn.islam.R
import raslan.learn.islam.adapters.IntroStepperAdapter
import raslan.learn.islam.databinding.ActivityIntroductionBinding
import raslan.learn.islam.util.AppPreference

class IntroductionActivity : AppCompatActivity() , StepperLayout.StepperListener {

    var binder : ActivityIntroductionBinding? = null
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        val binding : ActivityIntroductionBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_introduction)

        binding.stepper.adapter = IntroStepperAdapter(supportFragmentManager, this)
        binding.stepper.setListener(this)
        binder = binding
        binding.next.setOnClickListener {
            binding.stepper.OnNextClickedCallback()
            binding.stepper.proceed()
        }


    }


    override fun onStepSelected(newStepPosition: Int) {
        binder?.indicator!!.selection = newStepPosition
        if (newStepPosition == 2)
            binder!!.next.text= getString(R.string.finish)

    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }

    override fun onCompleted(completeButton: View?) {
        startActivity(Intent(this, MainActivity::class.java))
        AppPreference.firstRun = false
    }
}
