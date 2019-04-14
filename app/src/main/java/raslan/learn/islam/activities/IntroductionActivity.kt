package raslan.learn.islam.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_introduction.*
import raslan.learn.islam.MainActivity
import raslan.learn.islam.R
import raslan.learn.islam.adapters.IntroStepperAdapter
import raslan.learn.islam.databinding.ActivityIntroductionBinding
import raslan.learn.islam.fragments.intro.IntroFragmentOne
import raslan.learn.islam.fragments.intro.IntroFragmentSecond
import raslan.learn.islam.fragments.intro.IntroFragmentThird
import raslan.learn.islam.util.AppPreference

class IntroductionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityIntroductionBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_introduction
        )

        val adapter = IntroStepperAdapter(
            supportFragmentManager, listOf(
                IntroFragmentOne(), IntroFragmentSecond()
                , IntroFragmentThird()
            )
        )

        binding.next.setOnClickListener {
            if (pager.currentItem + 1 >= adapter.fragmentList.size) {
                startActivity(Intent(this, MainActivity::class.java))
                AppPreference.firstRun = false
                finish()
            } else
                pager.currentItem = pager.currentItem + 1
        }



        binding.pager.adapter = adapter
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                binding.indicator.selection = position
                if (position == 2)
                    binding.next.text = getString(R.string.finish)
                else binding.next.text = getString(R.string.next)
            }

        })


    }


}
