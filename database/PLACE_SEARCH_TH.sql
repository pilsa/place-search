-- 장소검색이력 테이블
/** ======================================================================================
 * 테이블 생성 : 장소검색이력
======================================================================================= */
CREATE TABLE PLACE_SEARCH_TH
(
    TRANS_NUM   INT(11)      NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '거래일련번호',
    TRANS_AT    INT(11)      NOT NULL COMMENT '거래일시',
    QUERY       VARCHAR(100) NOT NULL COMMENT '질의어',
    /*TRANSACTION_ID VARCHAR(10)  NOT NULL COMMENT '거래ID',*/
    FRST_RGS_AT DATETIME     NULL COMMENT '최초등록일시'
)
;

/** ======================================================================================
 * 인덱스생성
======================================================================================= */
CREATE INDEX IDX_SEARCH_TH_X01 ON PLACE_SEARCH_TH (TRANS_AT, QUERY);
DROP INDEX IDX_SEARCH_TH_X01;

/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table PLACE_SEARCH_TH;
truncate table PLACE_SEARCH_TH;

/** ======================================================================================
 * 조회
======================================================================================= */
select * from PLACE_SEARCH_TH;

select trans_num,
       FROM_UNIXTIME(trans_at) as a,
       trans_at,
       query,
       frst_rgs_at
from PLACE_SEARCH_TH
where 1 = 1
  AND trans_at <= UNIX_TIMESTAMP(NOW())
  AND trans_at >= UNIX_TIMESTAMP('2021-09-13 13:40:55')
;

/** ======================================================================================
 * UNIX_TIMESTAMP
======================================================================================= */
SELECT UNIX_TIMESTAMP();
SELECT UNIX_TIMESTAMP(now());
SELECT FROM_UNIXTIME(1631508373) from dual;

