package raslan.learn.islam.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.sample.MyTracksQuery
import kotlinx.android.synthetic.main.main_topic_item.view.*
import kotlinx.android.synthetic.main.main_topic_item.view.description
import kotlinx.android.synthetic.main.main_topic_item.view.startButton
import kotlinx.android.synthetic.main.main_topic_item.view.title
import kotlinx.android.synthetic.main.sub_main_topic_item.view.*
import raslan.learn.islam.R
import raslan.learn.islam.fragments.MainTopicsFragmentDirections
import raslan.learn.islam.fragments.SubTopicsFragmentDirections

class SubTopicsAdapter : RecyclerView.Adapter<SubTopicsAdapter.ViewHolder>() {

    private var data: List<MyTracksQuery.Edge2>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sub_main_topic_item, parent, false)
        )
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position], position)
    }


    fun setItems(data: List<MyTracksQuery.Edge2>) {
        this.data = data
        notifyDataSetChanged()
    }

    @Suppress("DEPRECATION")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(topics: MyTracksQuery.Edge2, position: Int) {
            itemView.run {
                slug.backgroundTintList = ColorStateList.valueOf(Color.parseColor(topics.node()!!.topic()!!.color()))
                slug.text = topics.node()!!.topic()!!.translations()!!.edges()[0].node()!!.title()
                if (topics.node()!!.topic()!!.level() == 1){
                    lessonLevel.text = context.getString(R.string.easy_level)
                }else if (topics.node()!!.topic()!!.level() == 2){
                    lessonLevel.text = context.getString(R.string.hard_level)
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.description(), Html.FROM_HTML_MODE_COMPACT)
                    title.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.title(), Html.FROM_HTML_MODE_COMPACT)
                }else{
                    description.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.description())
                    title.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.title())
                }

                startButton.setOnClickListener {
                    val direction = SubTopicsFragmentDirections.toCourse(topics.node()!!.id()
                            ,Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.title()).toString())
                    it.findNavController().navigate(direction)
                }
            }
        }
    }
}