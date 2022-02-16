package br.com.case.monitoring.repository

import br.com.case.monitoring.constants.TWEET_NOT_FOUND
import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.entities.ResultAggByDate
import br.com.case.monitoring.model.entities.ResultAggByTagAngLang
import br.com.case.monitoring.model.entities.TweetResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.group
import org.springframework.data.mongodb.core.aggregation.Aggregation.project
import org.springframework.data.mongodb.core.aggregation.Aggregation.sort
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TweetRepositoryCustom(private val mongoTemplate: MongoTemplate) {

    fun getUsersWithMostFollowers(usersQtd: Int): List<TweetResponse> {
        val query = Query()
        val sortBy = Sort.by(Sort.Direction.DESC, "user.followersCount")
        val pageRequest = PageRequest.of(0, usersQtd)
        query.with(sortBy).with(pageRequest)
        return mongoTemplate.find(query, TweetResponse::class.java)
            ?: throw NotFoundException(TWEET_NOT_FOUND)
    }

    fun getCountPostTagsAggregateByDate(): List<ResultAggByDate> {
        val projection = project("count")
            .and("date")
            .dateAsFormattedString("%Y-%m-%dT%H:%M").`as`("date").and("date")
            .previousOperation()

        val group = group("date").count().`as`("count")
        val sort = sort(Sort.Direction.DESC, "date")
        val agg = Aggregation.newAggregation(
            group,
            projection,
            sort
        )
        return mongoTemplate.aggregate(agg, TweetResponse::class.java, ResultAggByDate::class.java).mappedResults
            ?: throw NotFoundException(TWEET_NOT_FOUND)
    }

    fun getCountPostTagsAggregateByTagAndLang(): List<ResultAggByTagAngLang> {
        val projection = project()
            .and("hashtag").`as`("hashtag")
            .and("lang").`as`("lang")
            .and("count").`as`("count")


        val group = group("hashtag", "lang")
            .count().`as`("count")
        val sort = sort(Sort.Direction.DESC, "count")
        val agg = Aggregation.newAggregation(
            group,
            projection,
            sort
        )
        return mongoTemplate.aggregate(agg, TweetResponse::class.java, ResultAggByTagAngLang::class.java).mappedResults
            ?: throw NotFoundException(TWEET_NOT_FOUND)
    }

}