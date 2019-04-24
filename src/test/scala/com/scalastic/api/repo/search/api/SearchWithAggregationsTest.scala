package com.scalastic.api.repo.search.api

import com.scalastic.api.repo.ElasticQueryBuilder
import org.elasticsearch.search.aggregations.bucket.MultiBucketAggregationBuilder
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder
import org.elasticsearch.search.aggregations.support.{ValuesSource, ValuesSourceAggregationBuilder}
import org.elasticsearch.search.aggregations.{Aggregation, AggregationBuilders}

object SearchWithAggregationsTest extends App {

  private val agg1: TermsAggregationBuilder = AggregationBuilders.terms("agg1").field("name")
  private val map: Map[String, ValuesSourceAggregationBuilder[_ >: ValuesSource.Numeric <: ValuesSource, _ >: TermsAggregationBuilder with DateHistogramAggregationBuilder <: ValuesSourceAggregationBuilder[_ >: ValuesSource.Numeric <: ValuesSource, _ >: TermsAggregationBuilder with DateHistogramAggregationBuilder] with MultiBucketAggregationBuilder] with MultiBucketAggregationBuilder] = Map("agg1" -> agg1)

  private val aggregations: List[Aggregation] = ElasticQueryBuilder.searchWithAggregations(map, "cities")

  aggregations.foreach(agg => {
    println(agg.getMetaData)
    println(agg.getName)
    println(agg.getType)
  })

  System.exit(0)
}
