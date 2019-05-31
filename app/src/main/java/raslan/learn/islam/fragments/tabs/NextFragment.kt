package raslan.learn.islam.fragments.tabs


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import raslan.learn.islam.R
import raslan.learn.islam.databinding.FragmentNextBinding


class NextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNextBinding>(
                inflater, R.layout.fragment_next, container, false)



        return binding.root
    }


}
