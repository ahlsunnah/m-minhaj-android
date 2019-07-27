package raslan.learn.islam.fragments

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.sample.GetCourseDataQuery
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import raslan.learn.islam.MyApplication
import raslan.learn.islam.R
import raslan.learn.islam.adapters.SectionListAdapter
import raslan.learn.islam.util.AppPreference
import raslan.learn.islam.util.DataHelper
import raslan.learn.islam.util.LessonListener
import java.util.ArrayList

class SectionsListFragment : BottomSheetDialogFragment(), LessonListener {

    fun newInstance(data: List<GetCourseDataQuery.Edge>): SectionsListFragment {
        val fragment = SectionsListFragment()
        val bundle = Bundle()
        bundle.putParcelable("data", DataHelper(data))
        fragment.arguments = bundle

        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_sections_list, container, false)

        val courseList = view.findViewById<RecyclerView>(R.id.courseList)

        val data = arguments!!.getParcelable<DataHelper>("data")

        val adapter = SectionListAdapter(this)
        adapter.setItems(data.data)
        courseList.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        courseList.adapter = adapter
        Log.i("SectionsListFragment", data.data.toString())




        return view;
    }

//    override fun onLessonSelected(position: Int) {
//        print(position)
//    }

}
