package com.pilsa.place.couchbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * 설명 :
 * </pre>
 *
 * @author : pilsa_internet
 * @since : 2022-02-15 오전 10:15
 */
@Slf4j
@RequestMapping("/couchbase")
@RestController
public class CouchbaseTestController {

    @GetMapping("/get")
    public void getTest(){

    }

}
