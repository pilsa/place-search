package com.pilsa.place.couchbase.controller;

import com.pilsa.place.couchbase.service.dto.CouchbaseTestDTO;
import com.pilsa.place.couchbase.service.mapper.CouchbaseTestMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-15 오전 10:15
 */
@Slf4j
@AllArgsConstructor
@RequestMapping("/couchbase")
@RestController
public class CouchbaseTestController {

    CouchbaseTestMapper couchbaseTestMapper;

    @GetMapping("/get")
    public void getTest(@RequestParam String param){

        List<CouchbaseTestDTO> result1 = couchbaseTestMapper.findAllById(param);
        Optional<CouchbaseTestDTO> result2 = couchbaseTestMapper.findById(param);
        List<CouchbaseTestDTO> result3 = couchbaseTestMapper.findAllByCity(param);
        log.debug(result3.toString());

    }

}
