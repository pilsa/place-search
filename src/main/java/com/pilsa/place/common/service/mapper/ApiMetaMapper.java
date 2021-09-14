package com.pilsa.place.common.service.mapper;

import com.pilsa.place.common.service.dto.ApiMetaDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author pilsa_home1
 * @since 2021-09-13 오전 12:30
 */
@Mapper
@Repository
public interface ApiMetaMapper {

    List<ApiMetaDTO> selectApiMeta();
}
