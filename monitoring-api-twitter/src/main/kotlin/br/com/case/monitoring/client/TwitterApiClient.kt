package br.com.case.monitoring.client

import br.com.case.monitoring.model.request.Statuses
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class TwitterApiClient(
    @Value("\${twitter.host}")
    private val host: String,
    @Value("\${twitter.api.token}")
    private val token: String,
    private val restTemplate: RestTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun getTweetByHashtag(hashtag: String): Statuses {
        logger.debug { "getTweetByHashtag: get tweet by hashtags= $hashtag" }
        val builder = UriComponentsBuilder.fromHttpUrl(host).path("/1.1/search/tweets.json")
            .queryParam("q", hashtag)
            .queryParam("count", 100)
        val header = HttpHeaders()
        header.setBearerAuth(token)
        try {
            return restTemplate.exchange(
                builder.build().toUri(),
                HttpMethod.GET,
                HttpEntity(null, header),
                Statuses::class.java
            ).body!!
        } catch (ex: HttpClientErrorException) {
            logger.warn { "getTweeByHashtag: Failed while get tweet by hashtag= $hashtag, ex: ${ex.responseBodyAsString}" }
            throw ex
        } catch (ex: Exception) {
            logger.error(ex) { "getTweeByHashtag: Error while get tweet by hashtag= $hashtag, ex: ${ex.message}" }
            throw ex
        }
    }

}