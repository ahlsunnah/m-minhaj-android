package raslan.learn.islam.fragments.intro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import raslan.learn.islam.R

/**
 * A simple [Fragment] subclass.
 *
 */
class IntroFragmentOne : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.intro_fragment_one, container, false)
    }


}
