package raslan.learn.islam.fragments.tabs


import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import raslan.learn.islam.R
import raslan.learn.islam.databinding.FragmentSoundBinding


class SoundFragment : Fragment() {

    fun newInstance(sound: String): SoundFragment {
        val fragment = SoundFragment()
        val bundle = Bundle()
        bundle.putString("sound", sound)
        fragment.arguments = bundle

        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSoundBinding>(
            inflater, R.layout.fragment_sound, container, false
        )

        binding.soundHint.setOnClickListener {
            val url = Uri.parse(arguments!!.getString("sound"))
            try {
                val intent = Intent("android.intent.action.MAIN")
                intent.component = ComponentName(activity, "com.android.chrome/com.android.chrome.Main")
                intent.addCategory("android.intent.category.LAUNCHER")
                intent.data = url
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                // Chrome is not installed
                val intent = Intent(Intent.ACTION_VIEW, url)
                startActivity(intent)
            }
        }


        return binding.root
    }


}
