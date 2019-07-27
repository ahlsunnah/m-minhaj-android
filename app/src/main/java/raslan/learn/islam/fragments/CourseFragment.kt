package raslan.learn.islam.fragments


import android.net.Uri
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
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.sample.GetCourseDataQuery
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_sections_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import raslan.learn.islam.MyApplication

import raslan.learn.islam.R
import raslan.learn.islam.adapters.MainTopicsAdapter
import raslan.learn.islam.adapters.SectionListAdapter
import raslan.learn.islam.adapters.TabsAdapter
import raslan.learn.islam.databinding.FragmentCourseBinding
import raslan.learn.islam.fragments.tabs.OutputTextFragment
import raslan.learn.islam.fragments.tabs.SoundFragment
import raslan.learn.islam.fragments.tabs.TranslationFragment
import raslan.learn.islam.util.AppPreference
import raslan.learn.islam.util.LessonListener


/**
 * A simple [Fragment] subclass.
 *
 */
class CourseFragment : Fragment(), LessonListener {
    var job: Job? = null
    var sectionsList: GetCourseDataQuery.Data? = null
    var currentIndex = 0;
    var binder: FragmentCourseBinding? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCourseBinding>(
                inflater, R.layout.fragment_course, container, false
        )

        binder = binding
        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val id = arguments!!.getString("id")
                Log.i("id: ", id)
                val data = (requireActivity().applicationContext as MyApplication)
                        .apolloClient.query(
                        GetCourseDataQuery.builder().id(id).locale(AppPreference.lang)
                                .build()
                ).toDeferred().await()
                sectionsList = data.data()
                setCurrentLesson(data.data()!!, currentIndex)
                Log.i("main", data.data().toString())
            } catch (e: Exception) {
                //tvError.visibility = View.VISIBLE
                //tvError.text = e.localizedMessage
                //job!!.start()
                //Toast.makeText(activity, e.localizedMessage, Toast.LENGTH_LONG).show()
            } finally {
                //progress.visibility = View.GONE
            }
        }

        binding.sectionsSheet.setOnClickListener {
            SectionsListFragment().newInstance(sectionsList!!.course()!!.chapterSet()!!.edges())
                    .show(fragmentManager!!, "TEST")
        }

        return binding.root
    }

    private fun setCurrentLesson(data: GetCourseDataQuery.Data, currentIndex: Int) {
        val translation = data!!.course()!!.chapterSet()!!.edges()[currentIndex]!!
                .node()!!.translations()!!.edges()[0].node()!!.transcription()

        val output = data!!.course()!!.chapterSet()!!.edges()[currentIndex]!!
                .node()!!.translations()!!.edges()[0].node()!!.vocabulary()
        val audio = data!!.course()!!.chapterSet()!!.edges()[currentIndex]!!
                .node()!!.audio()
        var video = data!!.course()!!.chapterSet()!!.edges()[currentIndex]!!
                .node()!!.translations()!!.edges()[0]!!.node()!!.video()

        val fragmentList = ArrayList<Fragment>()
        val fragmentTitles = ArrayList<String>()
        if (AppPreference.lang != "ar") {
            fragmentList.add(TranslationFragment().newInstance(translation))
            fragmentList.add(OutputTextFragment().newInstance(output))
            fragmentTitles.add(getString(R.string.translation))
            fragmentTitles.add(getString(R.string.output))
        } else {
            fragmentList.add(OutputTextFragment().newInstance(translation))
            fragmentTitles.add(getString(R.string.output))
        }
        fragmentList.add(SoundFragment().newInstance(audio))
        fragmentTitles.add(getString(R.string.audio))

        val adapter = TabsAdapter(childFragmentManager, fragmentList, fragmentTitles)
        binder!!.viewPager.adapter = adapter
        binder!!.tabLayout.setupWithViewPager(binder!!.viewPager)
        //if (video.isEmpty())
        video = "https://www.youtube.com/watch?v=HIRXw_k0Adw"

        binder!!.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                video = video.substringAfter("v=")
                Log.i("video: ", video)
                youTubePlayer.loadVideo(video, 0F)
                youTubePlayer.pause()
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    override fun onLessonSelected(position: Int) {
        print(position)
    }


}
