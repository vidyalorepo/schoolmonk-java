package com.dcc.schoolmonk.elasticsearch.util;

import java.util.ArrayList;
import java.util.List;

import com.dcc.schoolmonk.elasticsearch.document.PostFilterSearchRequestVo;
import com.dcc.schoolmonk.elasticsearch.document.SchoolMstDocument;
import com.dcc.schoolmonk.elasticsearch.document.SchoolSearchInputVo;

import org.apache.lucene.document.IntPoint;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

public final class SearchUtil {
    private SearchUtil() {
    }

    public static SearchRequest buildSearchRequest(String indexName, SchoolSearchInputVo inputVo) {
        try {
            int page = inputVo.getPage();
            int size = inputVo.getSize();
            int fromIndex = page <= 1 ? 0 : (page - 1) * size;
            String sortColumn = inputVo.getSortColumn();
            String sortOrder = inputVo.getSortOrder();
            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(fromIndex).size(size);

            if (inputVo.getSortColumn() != null && inputVo.getSortColumn().trim() != "")
                builder.sort(SortBuilders.fieldSort(inputVo.getSortColumn() + ".keyword")
                        .order(inputVo.getSortOrder().trim().toLowerCase().equals("asc") ? SortOrder.ASC
                                : SortOrder.DESC));
            if (!inputVo.getFees().isEmpty())
                builder.sort(SortBuilders.fieldSort("min_fees")
                        .order(SortOrder.ASC));
            builder.query(createSearchQuery(inputVo));
            SearchRequest request = new SearchRequest(indexName);

            String[] fields = { "city.keyword", "school_type.keyword", "school_board.keyword", "school_medium.keyword",
                    "state_name.keyword", "postal_code", "min_fees", "max_fees" };
            for (String field : fields) {

                AggregationBuilder aggregationBuilder = aggregationBuilder(field);
                builder.aggregation(aggregationBuilder);
            }
            request.source(builder);
            System.out.println(request);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildAutocompleteSearchRequest(String indexName, SchoolSearchInputVo inputVo) {
        try {

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(createMultiMatchQueryBuilder(inputVo));

            final SearchSourceBuilder builder = new SearchSourceBuilder().size(10).query(boolQueryBuilder);
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            System.out.println(request);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildAutocompleteSearchRequestCity(String indexName, SchoolSearchInputVo inputVo) {
        try {

            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(createMultiMatchQueryBuilder(inputVo));
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("in", "country");
            boolQueryBuilder.must(multiMatchQueryBuilder);

            final SearchSourceBuilder builder = new SearchSourceBuilder().size(10).query(boolQueryBuilder);
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            System.out.println(request);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest getFilteredResults(String indexName, PostFilterSearchRequestVo inputVo) {
        try {
            int page = inputVo.getPage();
            int size = inputVo.getSize();
            int fromIndex = page <= 1 ? 0 : (page - 1) * size;
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            SchoolSearchInputVo searchVo = new SchoolSearchInputVo();
            searchVo.setSearchTerm(inputVo.getSearchTerm());
            searchVo.setFields(inputVo.getFields());
            boolQueryBuilder.should(createMultiMatchQueryBuilder(searchVo));
            if (inputVo.getBoard().size() != 0) {
                for (String board : inputVo.getBoard()) {

                    MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("school_board",
                            board);
                    boolQueryBuilder.must(matchPhraseQueryBuilder);
                }

            }
            if (inputVo.getMedium().size() != 0) {
                for (String medium : inputVo.getMedium()) {

                    MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("school_medium",
                            medium);
                    boolQueryBuilder.must(matchPhraseQueryBuilder);
                }

            }
            if (inputVo.getSchoolType().size() != 0) {
                for (String type : inputVo.getSchoolType()) {

                    MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("school_type",
                            type);
                    boolQueryBuilder.must(matchPhraseQueryBuilder);
                }

            }
            if (inputVo.getCity().size() != 0) {
                for (String type : inputVo.getCity()) {

                    MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("city",
                            type);
                    boolQueryBuilder.must(matchPhraseQueryBuilder);
                }

            }

            final SearchSourceBuilder builder = new SearchSourceBuilder().from(fromIndex).size(size)
                    .query(boolQueryBuilder);
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);
            System.out.println(request);
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SearchRequest buildMltRequest(String indexName, SchoolMstDocument topHit, String[] fields,
            String[] likeTexts) {
        if (topHit == null)
            return null;

        SearchRequest request = null;
        try {
            Item item = new Item(indexName, String.valueOf(topHit.getId()));
            Item[] items = { item };
            item.index(indexName);
            MoreLikeThisQueryBuilder mltQueryBuilder = QueryBuilders.moreLikeThisQuery(fields, likeTexts, items);
            mltQueryBuilder.boost(1.0f).minTermFreq(1).minDocFreq(1).maxQueryTerms(12);
            SearchSourceBuilder builder = new SearchSourceBuilder().size(10)
                    .query(mltQueryBuilder);
            request = new SearchRequest(indexName);
            request.source(builder);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    private static QueryBuilder createMultiMatchQueryBuilder(SchoolSearchInputVo inputVo) {
        if (inputVo == null)
            return null;
        List<String> searchFields = inputVo.getFields();
        if (CollectionUtils.isEmpty(searchFields))
            return null;

        MultiMatchQueryBuilder builder = QueryBuilders.multiMatchQuery(inputVo.getSearchTerm()).type("bool_prefix")
                .fuzzyTranspositions(false);

        searchFields.forEach(builder::field);
        System.out.println(builder.toString());
        return builder;
    }

    private static QueryBuilder createSearchQuery(SchoolSearchInputVo inputVo) {
        if (inputVo == null)
            return null;
        List<String> searchFields = inputVo.getFields();
        if (CollectionUtils.isEmpty(searchFields))
            return null;

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (inputVo.getSearchTerm() != null && inputVo.getSearchTerm().trim() != "") {
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(inputVo.getSearchTerm())
                    .type("bool_prefix")
                    .fuzzyTranspositions(false);

            searchFields.forEach(multiMatchQueryBuilder::field);
            boolQueryBuilder.should(multiMatchQueryBuilder);
        } else {
            MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
            boolQueryBuilder.should(matchAllQueryBuilder);
        }

        if (inputVo.getBoardName() != null && inputVo.getBoardName().trim() != "") {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("school_board",
                    inputVo.getBoardName().toLowerCase());
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if (inputVo.getSchoolType() != null && inputVo.getSchoolType().trim() != "") {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("school_type",
                    inputVo.getSchoolType().toLowerCase());
            boolQueryBuilder.must(matchQueryBuilder);
        }
        if (inputVo.getMediumName() != null && inputVo.getMediumName().trim() != "") {

            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("school_medium",
                    inputVo.getMediumName().toLowerCase());
            boolQueryBuilder.must(matchQueryBuilder);
        }

        if (!inputVo.getLocation().isEmpty()) {
            GeoDistanceQueryBuilder geoDistanceQueryBuilder = QueryBuilders.geoDistanceQuery("location")
                    .distance(inputVo.getDistance(), DistanceUnit.KILOMETERS)
                    .point(inputVo.getLocation().getLat(), inputVo.getLocation().getLon());

            boolQueryBuilder.filter(geoDistanceQueryBuilder);
        }

        if (!inputVo.getFees().isEmpty()) {
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("min_fees")
                    .gte(inputVo.getFees().getMinFees()).lte(inputVo.getFees().getMaxFees());
            boolQueryBuilder.must(rangeQueryBuilder);
        }
        System.out.println(boolQueryBuilder.toString());
        return boolQueryBuilder;
    }

    private static AggregationBuilder buildAggregation(String[] fields, int i) {
        if (i == fields.length - 1)
            return aggregationBuilder(fields[i]);

        return aggregationBuilder(fields[i]).subAggregation(buildAggregation(fields, i + 1));
    }

    private static AggregationBuilder aggregationBuilder(String term) {
        return AggregationBuilders
                .terms(term)
                .field(term)
                .size(1000)
                .order(BucketOrder.count(false));
    }

}
