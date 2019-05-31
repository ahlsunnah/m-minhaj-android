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
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.sample.GetCourseDataQuery
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import raslan.learn.islam.MyApplication

import raslan.learn.islam.R
import raslan.learn.islam.adapters.TabsAdapter
import raslan.learn.islam.databinding.FragmentCourseBinding
import raslan.learn.islam.fragments.tabs.OutputTextFragment
import raslan.learn.islam.fragments.tabs.SoundFragment
import raslan.learn.islam.fragments.tabs.TranslationFragment
import raslan.learn.islam.util.AppPreference


/**
 * A simple [Fragment] subclass.
 *
 */
class CourseFragment : Fragment() {
    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCourseBinding>(
            inflater, R.layout.fragment_course, container, false
        )

        job = GlobalScope.launch(Dispatchers.Main) {
            try {
                val id = arguments!!.getString("id")
                Log.i("id: ", id)
                val data = (requireActivity().applicationContext as MyApplication)
                    .apolloClient.query(
                    GetCourseDataQuery.builder().id(id).locale(AppPreference.lang)
                        .build()
                ).toDeferred().await()
                val translation = data.data()!!.course()!!.chapterSet()!!.edges()[0 /* replace with saved index */]!!
                    .node()!!.translations()!!.edges()[0].node()!!.transcription()
                val output = data.data()!!.course()!!.chapterSet()!!.edges()[0 /* replace with saved index */]!!
                    .node()!!.translations()!!.edges()[0].node()!!.vocabulary()
                val audio = data.data()!!.course()!!.chapterSet()!!.edges()[0 /* replace with saved index */]!!
                    .node()!!.audio()
                var video = data.data()!!.course()!!.chapterSet()!!.edges()[0 /* replace with saved index */]!!
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
                binding.viewPager.adapter = adapter
                binding.tabLayout.setupWithViewPager(binding.viewPager)
                //if (video.isEmpty())
                    video = "https://www.youtube.com/watch?v=HIRXw_k0Adw"

                binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        video = video.substringAfter("v=")
                        Log.i("video: ", video)
                        youTubePlayer.loadVideo(video, 0F)
                        youTubePlayer.pause()
                    }

                    override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {

                    }
                })
                Log.i("main", data.data().toString())
            } catch (e: Exception) {
                //tvError.visibility = View.VISIBLE
                //tvError.text = e.localizedMessage
                job!!.start()
                Toast.makeText(activity, e.localizedMessage, Toast.LENGTH_LONG).show()
            } finally {
                //progress.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
