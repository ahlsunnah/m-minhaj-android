package raslan.learn.islam.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.sample.MyTracksQuery
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import raslan.learn.islam.MyApplication

import raslan.learn.islam.R
import raslan.learn.islam.adapters.MainTopicsAdapter
import raslan.learn.islam.adapters.SubTopicsAdapter
import raslan.learn.islam.databinding.FragmentSubTopicsBinding
import raslan.learn.islam.util.AppPreference


class SubTopicsFragment : Fragment() {
    var job: Job? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentSubTopicsBinding>(
                inflater, R.layout.fragment_sub_topics, container, false)

        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val position = arguments!!.getString("index").toInt()
                val data = (requireActivity().applicationContext as MyApplication)
                        .apolloClient.query(MyTracksQuery(AppPreference.lang)).toDeferred().await()
                        .data()!!.allTracks()!!.edges()[position].node()!!.courseSet()!!.edges()
                val adapter = SubTopicsAdapter()
                adapter.setItems(data)
                binding.recycler.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                binding.recycler.adapter = adapter
                Log.i("main", data.toString())
            } catch (e: Exception) {
                //tvError.visibility = View.VISIBLE
                //tvError.text = e.localizedMessage
                Toast.makeText(activity, e.localizedMessage, Toast.LENGTH_LONG).show()
            } finally {
                progress.visibility = View.GONE
            }
        }




        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
