package com.scalastic.api.high.level.rest.client.search.api

import com.scalastic.api.utils.DataExtractor
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder


object SearchAPIs {

  def searchWithMatchAllQuery(indices: String*): List[Map[String, Any]] = {
    val searchSourceBuilder = new SearchSourceBuilder
    val builder = searchSourceBuilder.query(QueryBuilders.matchAllQuery())
    val searchRequest = new SearchRequest(indices: _*)
    DataExtractor.extractResult(searchRequest, builder)
  }

}
