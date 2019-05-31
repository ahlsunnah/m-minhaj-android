package raslan.learn.islam.fragments.tabs


import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import raslan.learn.islam.R
import raslan.learn.islam.databinding.FragmentOutputTextBinding

class OutputTextFragment : Fragment() {

    fun newInstance(content: String): OutputTextFragment{
        val fragment = OutputTextFragment()
        val bundle = Bundle()
        bundle.putString("content", content)
        fragment.arguments = bundle

        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentOutputTextBinding>(
                inflater, R.layout.fragment_output_text, container, false)

        binding.content.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(arguments!!.getString("content"), Html.FROM_HTML_MODE_COMPACT)
        else Html.fromHtml(arguments!!.getString("content"))

        return binding.root

    }


}
