package raslan.learn.islam

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.ResponseField
import com.apollographql.apollo.cache.normalized.CacheKey
import com.apollographql.apollo.cache.normalized.CacheKeyResolver
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import raslan.learn.islam.util.AppPreference
import android.provider.Settings.System.DATE_FORMAT
import com.apollographql.apollo.response.CustomTypeValue
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.sample.type.CustomType
import raslan.learn.islam.config.Config
import raslan.learn.islam.model.QuizData
import java.text.ParseException


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreference.init(this)
    }

    val apolloClient: ApolloClient by lazy {
        val apolloSqlHelper = ApolloSqlHelper.create(this, "learn_islam")
        val sqlNormalizedCacheFactory = SqlNormalizedCacheFactory(apolloSqlHelper)
        val cacheKeyResolver = object : CacheKeyResolver() {
            override fun fromFieldRecordSet(field: ResponseField, recordSet: MutableMap<String, Any>): CacheKey {
                return if (recordSet["__typename"] == "Repository") {
                    CacheKey.from(recordSet["id"] as String)
                } else {
                    CacheKey.NO_KEY
                }
            }

            override fun fromFieldArguments(field: ResponseField, variables: Operation.Variables): CacheKey {
                return CacheKey.NO_KEY
            }
        }

        val dateCustomTypeAdapter = object : CustomTypeAdapter<String> {
            override fun decode(value: CustomTypeValue<*>): String {
                try {
                    return value.value as String
                } catch (e: ParseException) {
                    throw RuntimeException(e)
                }

            }

            override fun encode(value: String): CustomTypeValue<*> {
                return CustomTypeValue.GraphQLString(value)
            }
        }

        ApolloClient.builder()
            .serverUrl(Config.URL)
            .normalizedCache(sqlNormalizedCacheFactory, cacheKeyResolver)
            .addCustomTypeAdapter(CustomType.JSONSTRING, dateCustomTypeAdapter)
            //.okHttpClient(okHttpClient)
            .build()
    }
}