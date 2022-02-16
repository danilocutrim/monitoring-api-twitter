package br.com.case.monitoring.service

import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.entities.ResultAggByDate
import br.com.case.monitoring.model.entities.ResultAggByTagAngLang
import br.com.case.monitoring.model.entities.TweetResponse
import br.com.case.monitoring.repository.TweetRepositoryCustom
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class TweetService(
    private val tweetRepositoryCustom: TweetRepositoryCustom
) {
    private val logger = KotlinLogging.logger {}

    fun getUsersWithMostFollowers(pageRequestSize: Int = 5): List<TweetResponse> {
        val query = Query()
        query.with(Sort.by(Sort.Direction.DESC, "user.followersCount")).with(PageRequest.of(0, pageRequestSize))
        return try {
            tweetRepositoryCustom.getUsersWithMostFollowers(5)
        } catch (e: NotFoundException) {
            logger.warn { "getUsersWithMostFollowers: tweets not found" }
            throw e
        } catch (e: Exception) {
            logger.error(e) { "getUsersWithMostFollowers: error while get users with most followers" }
            throw e
        }
    }

    fun getCountPostTagsByDate(): List<ResultAggByDate> {
        return try {
            tweetRepositoryCustom.getCountPostTagsAggregateByDate()
        } catch (e: NotFoundException) {
            logger.warn { "getCountPostTags: cannot find tweets" }
            throw e
        } catch (e: Exception) {
            logger.error(e) { "getCountPostTags: error while get tweets" }
            throw e
        }
    }

    fun getCountPostTagsByLang(): List<ResultAggByTagAngLang> {
        return try {
            tweetRepositoryCustom.getCountPostTagsAggregateByTagAndLang()
        } catch (e: NotFoundException) {
            logger.warn { "getCountPostTagsByLang: cannot find tweets" }
            throw e
        } catch (e: Exception) {
            logger.error(e) { "getCountPostTagsByLang: error while get tweets" }
            throw e
        }
    }

}
