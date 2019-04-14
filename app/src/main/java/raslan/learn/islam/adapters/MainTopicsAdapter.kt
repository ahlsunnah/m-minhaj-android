package raslan.learn.islam.adapters

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.sample.MyTracksQuery
import kotlinx.android.synthetic.main.main_topic_item.view.*
import raslan.learn.islam.R

class MainTopicsAdapter : RecyclerView.Adapter<MainTopicsAdapter.ViewHolder>() {

    private var data: List<MyTracksQuery.Edge>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.main_topic_item, parent, false)
        )
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position])
    }


    fun setItems(data: List<MyTracksQuery.Edge>) {
        this.data = data
        notifyDataSetChanged()
    }

    @Suppress("DEPRECATION")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(topics: MyTracksQuery.Edge) {
            itemView.run {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    description.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.description(), Html.FROM_HTML_MODE_COMPACT)
                    title.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.title(), Html.FROM_HTML_MODE_COMPACT)
                }else{
                    description.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.description())
                    title.text = Html.fromHtml(topics.node()!!.translations()!!.edges()[0].node()!!.title())
                }
                if (topics.node()!!.soon()){
                    startButton.text = context.getString(R.string.soon)
                    startButton.isEnabled = false
                }

                startButton.setOnClickListener {

                }
            }
        }
    }
}