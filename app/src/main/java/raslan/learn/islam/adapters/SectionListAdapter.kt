package raslan.learn.islam.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.sample.GetCourseDataQuery
import kotlinx.android.synthetic.main.item_section_list.view.*
import raslan.learn.islam.R
import raslan.learn.islam.util.LessonListener

public class SectionListAdapter(val lessonListener: LessonListener) : RecyclerView.Adapter<SectionListAdapter.ViewHolder>() {

    private var data: List<GetCourseDataQuery.Edge>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_section_list, parent, false)
        )
    }

    override fun getItemCount() = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data!![position], position)
        holder.itemView.item.setOnClickListener {
            lessonListener.onLessonSelected(position, data!![position].node()!!)
        }
    }


    fun setItems(data: List<GetCourseDataQuery.Edge>) {
        this.data = data
        notifyDataSetChanged()
    }

    @Suppress("DEPRECATION")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(topics: GetCourseDataQuery.Edge, position: Int) {
            itemView.run {

                title.text = topics.node()!!.translations()!!.edges()[0].node()!!.title()
            }
        }
    }
}