package br.com.case.monitoring.controller

import br.com.case.monitoring.client.TwitterApiClient
import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.request.Result
import br.com.case.monitoring.model.request.Statuses
import br.com.case.monitoring.model.request.TweetResponse
import br.com.case.monitoring.service.TweetService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TweetsController(
    private val tweetService: TweetService
) {
    val logger = KotlinLogging.logger {  }

    @GetMapping("/users-with-most-followers")
    fun getUserWithMostFollowers(): List<TweetResponse>{
        logger.info { "getUserWithMostFollowers: got five users with most followers" }
        return tweetService.getUsersWithMostFollowers().also {
            logger.info { "getUserWithMostFollowers: get five users with most followers success" }
        }
    }

    @GetMapping("/get-count-tweets")
    fun getCountTweets(): List<Result> {
        logger.info { "getCountTweets: get count tweets by time" }
        return tweetService.getCountPostTags().also {
            logger.info { "getCountTweets: get count tweets by time with success" }
        }
    }
}