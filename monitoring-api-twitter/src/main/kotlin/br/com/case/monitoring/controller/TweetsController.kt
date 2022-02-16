package br.com.case.monitoring.controller

import br.com.case.monitoring.model.entities.ResultAggByDate
import br.com.case.monitoring.model.entities.ResultAggByTagAngLang
import br.com.case.monitoring.model.entities.TweetResponse
import br.com.case.monitoring.service.TweetService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TweetsController(
    private val tweetService: TweetService
) {
    val logger = KotlinLogging.logger("tweets")

    @GetMapping("/users-with-most-followers")
    fun getUserWithMostFollowers(): List<TweetResponse>{
        logger.info { "getUserWithMostFollowers: got five users with most followers" }
        return tweetService.getUsersWithMostFollowers().also {
            logger.info { "getUserWithMostFollowers: get five users with most followers success" }
        }
    }

    @GetMapping("/tweets/date")
    fun getCountTweetsByDate(): List<ResultAggByDate> {
        logger.info { "getCountTweets: get count tweets by time" }
        return tweetService.getCountPostTagsByDate().also {
            logger.info { "getCountTweets: get count tweets by time with success" }
        }
    }

    @GetMapping("/tweets/lang")
    fun getCountTweetsByLang(): List<ResultAggByTagAngLang> {
        logger.info { "getCountTweetsByLang: get count tweets by lang" }
        return tweetService.getCountPostTagsByLang().also {
            logger.info { "getCountTweetsByLang: get count tweets by lang with success" }
        }
    }
}