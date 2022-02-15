package com.pilsa.place.framework.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-11 오후 4:10
 */

@Configuration
@EnableCouchbaseRepositories(basePackages = {"dd"})
public class CouchbaseConfig extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    @Override
    public String getUserName() {
        return "pilsa115";
    }

    @Override
    public String getPassword() {
        return "Fhsivmrkd00@";
    }

    @Override
    public String getBucketName() {
        return "beer-sample";
    }
}
