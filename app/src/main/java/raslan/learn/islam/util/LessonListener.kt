package raslan.learn.islam.util

import com.apollographql.apollo.sample.GetCourseDataQuery

interface LessonListener {

    fun onLessonSelected(position: Int, node: GetCourseDataQuery.Node) {

    }
}