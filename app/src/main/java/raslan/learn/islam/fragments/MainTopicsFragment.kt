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
import androidx.recyclerview.widget.RecyclerView.VERTICAL
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
import raslan.learn.islam.databinding.FragmentMainBinding
import raslan.learn.islam.util.AppPreference


/**
 * A simple [Fragment] subclass.
 *
 */
class MainTopicsFragment : Fragment() {
    var job: Job? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
                inflater, R.layout.fragment_main, container, false)


        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val data = (requireActivity().applicationContext as MyApplication)
                        .apolloClient.query(MyTracksQuery(AppPreference.lang)).toDeferred().await()
                        .data()!!.allTracks()
                val adapter = MainTopicsAdapter()
                adapter.setItems(data!!.edges())
                binding.recycler.layoutManager = LinearLayoutManager(activity, VERTICAL, false)
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
