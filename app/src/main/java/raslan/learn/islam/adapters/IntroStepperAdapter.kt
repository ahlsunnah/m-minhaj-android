package raslan.learn.islam.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import raslan.learn.islam.fragments.intro.IntroFragmentOne
import raslan.learn.islam.fragments.intro.IntroFragmentSecond
import raslan.learn.islam.fragments.intro.IntroFragmentThird

class IntroStepperAdapter(fm : FragmentManager,  context : Context) : AbstractFragmentStepAdapter(fm, context) {


    override fun createStep(position: Int): Step {
        val step = IntroFragmentOne()
        val bundle = Bundle()
        bundle.putInt("current_position", position);
        step.arguments = bundle
        return when (position) {
            0 -> IntroFragmentOne()
            1 -> IntroFragmentSecond()
            else -> IntroFragmentThird()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getViewModel(position: Int): StepViewModel {
        return super.getViewModel(position)
    }


}
