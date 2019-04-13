package raslan.learn.islam.model

import com.google.gson.annotations.SerializedName

data class QuizData(
        @SerializedName("text") val text : String? = null,
        @SerializedName("values") val values : Array<String>,
        @SerializedName("isTrue") val isTrue : Boolean
) {
}