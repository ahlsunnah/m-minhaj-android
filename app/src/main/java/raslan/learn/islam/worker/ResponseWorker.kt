package raslan.learn.islam.worker

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloCallback
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.sample.MyTracksQuery
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import raslan.learn.islam.MyApplication
import raslan.learn.islam.util.AppPreference


class ResponseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG by lazy{ ResponseWorker::javaClass.name }
    override fun doWork(): Result {
        return try {


            val repositoriesQuery = MyTracksQuery.builder()
                .locale(AppPreference.lang)
                .build()

            val callback = ApolloCallback.wrap(object : ApolloCall.Callback<MyTracksQuery.Data>() {
                override fun onResponse(response: Response<MyTracksQuery.Data>) {
                    val repositories = mapResponseToRepositories(response)
                    // push to view model

                }

                override fun onFailure(e: ApolloException) {
                    e.printStackTrace()
                }

            }, Handler(Looper.getMainLooper()))

            (MyApplication()).apolloClient
                .query(repositoriesQuery)
                .httpCachePolicy(HttpCachePolicy.CACHE_FIRST)
                .enqueue(callback)




            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }
    private fun mapResponseToRepositories(response: Response<MyTracksQuery.Data>): List<MyTracksQuery.Edge?> {
        return response.data()?.allTracks()?.edges()?.map { it } ?: emptyList()
    }
}