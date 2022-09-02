package eu.kanade.tachiyomi.animeextension.es.animelatinohd.extractors

import eu.kanade.tachiyomi.animesource.model.Video
import org.json.JSONObject
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.IOException

class FembedExtractor {
    fun videosFromUrl(url: String, qualityPrefix: String = ""): List<Video> {
        val videoList = mutableListOf<Video>()
        return try {
            val videoApi = url.replace("/v/", "/api/source/")
            val json = JSONObject(Jsoup.connect(videoApi).ignoreContentType(true).method(Connection.Method.POST).execute().body())
            val videoList = mutableListOf<Video>()
            val jsonArray = json.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                val `object` = jsonArray.getJSONObject(i)
                val videoUrl = `object`.getString("file")
                val quality = qualityPrefix + "Fembed:" + `object`.getString("label")
                videoList.add(Video(videoUrl, quality, videoUrl))
            }
            videoList
        } catch (e: IOException) {
            videoList
        }
    }
}