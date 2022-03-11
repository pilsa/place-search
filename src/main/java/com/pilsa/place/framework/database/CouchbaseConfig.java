package com.pilsa.place.framework.database;

import com.couchbase.client.core.env.TimeoutConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.time.Duration;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-11 오후 4:10
 */

@Configuration
@EnableCouchbaseRepositories(basePackages = {"com.pilsa.place.couchbase.**.mapper"})
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
        return "travel-sample";
    }

    @Override
    public ClusterEnvironment couchbaseClusterEnvironment() {
        ClusterEnvironment env = ClusterEnvironment.builder()
                .timeoutConfig(TimeoutConfig
                        .kvTimeout(Duration.ofSeconds(5))
                        .queryTimeout(Duration.ofSeconds(10)))
                .build();
        return env;
    }
}
