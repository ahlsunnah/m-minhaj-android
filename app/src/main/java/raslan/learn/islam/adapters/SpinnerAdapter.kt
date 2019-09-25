package raslan.learn.islam.adapters

import android.widget.AdapterView
import android.widget.TextView

import androidx.annotation.NonNull
import android.R.attr.colorAccent
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.Nullable


class SpinnerAdapter(private val context: Context, private val list: List<String>) {
    private var position: Int = 0

    fun setAdapter(@NonNull spinner: Spinner) {
        val adapter = object : ArrayAdapter<String>(context, android.R.layout.simple_spinner_item) {

            override fun getView(position: Int, convertView: View, parent: ViewGroup): View {

                val v = super.getView(position, convertView, parent)
                if (position == count) {
                    (v.findViewById(android.R.id.text1) as TextView).text = ""
                    (v.findViewById(android.R.id.text1) as TextView).hint = getItem(count) //"Hint to be displayed"
                    (v.findViewById(android.R.id.text1) as TextView).setTextColor(Color.BLACK)
                    (v.findViewById(android.R.id.text1) as TextView).setHintTextColor(context.resources.getColor(android.R.color.background_light))
                    (v.findViewById(android.R.id.text1) as TextView).highlightColor = Color.BLACK

                }
                (v.findViewById(android.R.id.text1) as TextView).setTextColor(Color.BLACK)
                (v.findViewById(android.R.id.text1) as TextView).setHintTextColor(Color.BLACK)
                (v.findViewById(android.R.id.text1) as TextView).highlightColor = Color.BLACK
                return v
            }

            override fun getDropDownView(position: Int, @Nullable convertView: View, @Nullable parent: ViewGroup): View {
                val v = super.getDropDownView(position, convertView, parent)
                (v.findViewById(android.R.id.text1) as TextView).setTextColor(Color.BLACK)
                (v.findViewById(android.R.id.text1) as TextView).setHintTextColor(Color.BLACK)
                (v.findViewById(android.R.id.text1) as TextView).highlightColor = Color.BLACK

                return v
            }

            override fun getCount(): Int {
                return super.getCount() - 1 // you dont display last item. It is used as hint.
            }

        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        for (i in list.indices) {
            adapter.add(list[i])
        }

        spinner.adapter = adapter
        spinner.setSelection(adapter.count)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                // position = list[i]

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
    }

    fun setPosition(position: Int) {

    }

    fun getPosition(): Int {
        return position
    }

    companion object {
        private val TAG = "SpinnerAdapter"
    }
}
