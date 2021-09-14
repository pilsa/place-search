package com.pilsa.place.biz.service.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PlaceTransactionDTO {

    private String query;       /* 질의어 */
    private int rank;           /* 순위 */
    private int queryCnt;       /* 조회횟수 */

}
