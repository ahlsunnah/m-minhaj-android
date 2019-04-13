package raslan.learn.islam.worker

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.work.*
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloCallback
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.sample.MyTracksQuery
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import raslan.learn.islam.MyApplication
import raslan.learn.islam.R
import raslan.learn.islam.util.AppPreference
import raslan.learn.islam.util.Utils.Companion.haveNetworkConnection
import kotlin.coroutines.suspendCoroutine


class ResponseWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val TAG by lazy { ResponseWorker::javaClass.name }


    override suspend fun doWork(): Result {

        val data = Data.Builder()
        if (!haveNetworkConnection(applicationContext)) {
            data.putInt(RESULT_CODE, FAIL)
            data.putString(RESULT_MESSAGE, applicationContext.getString(R.string.check_internet_connection))
            return Result.failure(data.build())
        }

        return try {
            val repositoriesQuery = MyTracksQuery.builder()
                .locale(AppPreference.lang)
                .build()

            (applicationContext as MyApplication).apolloClient
                .query(repositoriesQuery)
                .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .toDeferred().await().apply {
                    Log.i("Result", data().toString())
                    if (hasErrors()) {
                        data.putInt(RESULT_CODE, FAIL)
                        data.putString(RESULT_MESSAGE, errors().toList().toString())
                    } else {
                        data.putInt(RESULT_CODE, SUCCESS)
                        data.putString(RESULT_MESSAGE, "success")
                    }

                }

            Result.success(data.build())
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure(data.build())
        }


    }

    private fun mapResponseToRepositories(response: Response<MyTracksQuery.Data>): List<MyTracksQuery.Edge?> {
        return response.data()?.allTracks()?.edges()?.map { it } ?: emptyList()
    }


    companion object {
        val RESULT_CODE = "result_ok"
        val RESULT_MESSAGE = "result_message"

        val FAIL = 400
        val SUCCESS = 200
    }
}