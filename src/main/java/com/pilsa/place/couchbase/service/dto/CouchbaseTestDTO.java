package com.pilsa.place.couchbase.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import java.util.List;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-15 오전 10:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class CouchbaseTestDTO {
    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;
    @Field
    private double abv;
    @Field
    private List address;
    @Field
    private String brewery_id;
    @Field
    private String category;
    @Field
    private String city;
    @Field
    private String code;
    @Field
    private String country;
    @Field
    private String description;
    @Field
    private Geo geo;
    @Field
    private int ibu;
    @Field
    private String name;
    @Field
    private String phone;
    @Field
    private int srm;
    @Field
    private String state;
    @Field
    private String style;
    @Field
    private String type;
    @Field
    private int upc;
    @Field
    private String updated;
    @Field
    private String website;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Geo {
        @Field
        private String accuracy;
        @Field
        private double lat;
        @Field
        private double lon;
    }
}
