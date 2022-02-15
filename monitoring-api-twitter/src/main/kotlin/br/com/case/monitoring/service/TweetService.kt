package br.com.case.monitoring.service

import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.request.Result
import br.com.case.monitoring.model.request.TweetResponse
import br.com.case.monitoring.repository.TweetRepository
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.group
import org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.project
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class TweetService(
    private val mongoTemplate: MongoTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun getUsersWithMostFollowers(): List<TweetResponse> {
        val query = Query()
        query.with(Sort.by(Sort.Direction.DESC, "user.followersCount")).with(PageRequest.of(0, 5))
        return try {
            mongoTemplate.find(query, TweetResponse::class.java)
                ?: throw NotFoundException("cannot find tweets").also {
                    logger.warn { "getUsersWithMostFollowers: error while get users with most followers" }
                }
        } catch (e: Exception) {
            logger.error(e) { "getUsersWithMostFollowers: error while get users with most followers" }
            throw e
        }
    }

    fun getCountPostTags(): List<Result> {
        val agg = newAggregation(
            group("createdAt").count().`as`("total"),
            project("total").and("createdAt").previousOperation()
        )
        return mongoTemplate.aggregate(agg, TweetResponse::class.java, Result::class.java).mappedResults
            ?: throw NotFoundException("cannot find tweets").also {
                logger.warn { "getCountPostTags: cannot find tweets" }
            }
    }

}
