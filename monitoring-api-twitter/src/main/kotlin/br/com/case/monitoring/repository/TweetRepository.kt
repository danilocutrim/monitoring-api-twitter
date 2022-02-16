package br.com.case.monitoring.repository

import br.com.case.monitoring.model.request.TweetResponse
import org.springframework.data.mongodb.repository.MongoRepository

interface TweetRepository: MongoRepository<TweetResponse, String>
