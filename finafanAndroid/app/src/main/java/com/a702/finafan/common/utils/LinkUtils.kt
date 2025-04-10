package com.a702.finafan.common.utils


import com.a702.finafan.domain.link.model.ExtractedLinksResult
import com.a702.finafan.domain.link.model.LinkPreviewMeta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

object LinkUtils {
    private val markdownLinkRegex = Regex("""\[(.*?)\]\((https?://[^\s)]+)\)""")
    private val urlRegex = Regex("""(https?://[^\s]+)""")

    fun extractLinksAndCleanText(
        text: String
    ): ExtractedLinksResult {
        val regex = Regex("\\[(.*?)]\\((https?://.*?)\\)")
        val matches = regex.findAll(text)

        val links = matches.map { it.groupValues[2] }.toList()
        val cleanText = regex.replace(text, "").trim()

        return ExtractedLinksResult(cleanText, links)
    }


    /**
     * OG(Open Graph) 메타데이터 비동기로 가져오기
     */
    suspend fun fetchMetadata(url: String): LinkPreviewMeta? = withContext(Dispatchers.IO) {
        return@withContext try {
            val doc = Jsoup.connect(url).get()
            val title = doc.select("meta[property=og:title]").attr("content")
            val desc = doc.select("meta[property=og:description]").attr("content")
            val image = doc.select("meta[property=og:image]").attr("content")

            if (title.isBlank() && desc.isBlank() && image.isBlank()) null
            else LinkPreviewMeta(title, desc, image, url)
        } catch (e: Exception) {
            null
        }
    }
}
