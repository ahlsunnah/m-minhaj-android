package raslan.learn.islam.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.fragment_settings.*

import raslan.learn.islam.R
import raslan.learn.islam.adapters.SpinnerAdapter
import raslan.learn.islam.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSettingsBinding>(
                inflater, R.layout.fragment_settings, container, false
        )

        SpinnerAdapter(requireContext(), listOf(getString(R.string.arabic), getString(R.string.french),
                getString(R.string.choose_language)))
                .setAdapter(binding.changeLanguage)

        return binding.root
    }


}
