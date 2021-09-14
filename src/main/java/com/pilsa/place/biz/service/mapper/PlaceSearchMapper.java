package com.pilsa.place.biz.service.mapper;

import com.pilsa.place.biz.service.dto.PlaceCondition;
import com.pilsa.place.biz.service.dto.PlaceTransactionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlaceSearchMapper {

    void insertSearchHistory(PlaceTransactionDTO transactionDTO);

    List<PlaceTransactionDTO> selectPopularKeywords(PlaceCondition condition);

}
