package br.com.case.monitoring.repository

import br.com.case.monitoring.exception.NotFoundException
import br.com.case.monitoring.model.request.Result
import br.com.case.monitoring.model.request.TweetResponse
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
class TweetRepositoryImpl(private val mongoTemplate: MongoTemplate) {

    fun getUsersWithMostFollowers(usersQtd: Int): List<TweetResponse> {
        val query = Query()
        val sortBy = Sort.by(Sort.Direction.DESC, "user.followersCount")
        val pageRequest = PageRequest.of(0, usersQtd)
        query.with(sortBy).with(pageRequest)
        return mongoTemplate.find(query, TweetResponse::class.java)
            ?: throw NotFoundException("cannot find tweets")
    }

    fun getCountPostTags(): List<Result> {
        val projection = project("total")
            .and("date")
            .dateAsFormattedString("%Y-%m-%dT%H:%M").`as`("date").and("date")
            .previousOperation()

        val group = group("date").count().`as`("total")
        val sort = sort(Sort.Direction.DESC, "date")
        val agg = Aggregation.newAggregation(
            group,
            projection,
            sort
        )
        return mongoTemplate.aggregate(agg, TweetResponse::class.java, Result::class.java).mappedResults
            ?: throw NotFoundException("cannot find tweets")
    }

}