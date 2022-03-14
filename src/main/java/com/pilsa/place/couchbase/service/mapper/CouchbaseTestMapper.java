package com.pilsa.place.couchbase.service.mapper;

import com.pilsa.place.couchbase.service.dto.CouchbaseTestDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-15 오후 4:36
 */
public interface CouchbaseTestMapper extends CrudRepository <CouchbaseTestDTO,String>{
    List<CouchbaseTestDTO> findAllById(String id);
    Optional<CouchbaseTestDTO> findById(String id);
    List<CouchbaseTestDTO> findAllByCity(String city);

    @Override
    void deleteAll();


    @Override
    void deleteById(String s);

    @Override
    void delete(CouchbaseTestDTO entity);
}
