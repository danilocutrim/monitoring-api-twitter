package br.com.case.monitoring.config

import br.com.case.monitoring.client.TwitterApiClient
import br.com.case.monitoring.repository.TweetRepository
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(0)
class AppListener(private val twitterApiClient: TwitterApiClient, private val tweetRepository: TweetRepository) :
    ApplicationListener<ApplicationReadyEvent> {
    val logger = KotlinLogging.logger {}
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        logger.info { "onApplicationEvent: start save tweest on db" }
        val hashTags = listOf(
            "#openbanking",
            "#remediation",
            "#devops",
            "#sre",
            "#microservices",
            "#observability",
            "#oauth",
            "#metrics",
            "#logmonitoring",
            "#opentracing"
        )
        try {
            hashTags.forEach { tweet ->
                twitterApiClient.getTweetByHashtag(tweet).statuses.forEach {
                    it.hashtag = tweet
                    tweetRepository.save(it)
                }.also {
                    logger.info { "onApplicationEvent: save tweet with hashtag $tweet with success" }
                }
            }
        } catch (ex: Exception) {
            logger.error {
                "onApplicationEvent: error while save tweet in database"
            }
        }
    }
}