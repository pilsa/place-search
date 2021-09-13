-- 투자상품 거래내역
/** ======================================================================================
 * 테이블 생성 : 장소검색이력
======================================================================================= */
CREATE TABLE PLACE_SEARCH_TH
(
    trans_num   int(11) NOT NULL PRIMARY KEY  AUTO_INCREMENT COMMENT '거래일련번호',
    trans_at    int(11)            NOT NULL COMMENT '거래일시',
    query       VARCHAR(100)       NOT NULL COMMENT '질의어',
    frst_rgs_at DATETIME           NULL COMMENT '최초등록일시'
)
;

SELECT UNIX_TIMESTAMP()
from dual;

SELECT FROM_UNIXTIME(1631508373)
from dual;

SELECT UNIX_TIMESTAMP(now());

/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table INVEST_PRODUCT_TH;
truncate table INVEST_PRODUCT_TH;
/** ======================================================================================
 * 인덱스생성
======================================================================================= */
CREATE
    INDEX IDX_SEARCH_TH_X01 ON PLACE_SEARCH_TH (TRANS_AT, QUERY);

DROP
    INDEX IDX_SEARCH_TH_X01;

CREATE
    INDEX IDX_PRD_TH_PRODUCTID ON INVEST_PRODUCT_TH (product_id);
DROP
    INDEX IDX_PRD_TH_PRODUCTID;


select
    trans_num, FROM_UNIXTIME(trans_at) as a, trans_at, query, frst_rgs_at
from PLACE_SEARCH_TH
where 1=1;

select
    trans_num, FROM_UNIXTIME(trans_at) as a, trans_at, query, frst_rgs_at
from PLACE_SEARCH_TH
where 1=1
AND trans_at <= UNIX_TIMESTAMP(NOW()) AND trans_at >= UNIX_TIMESTAMP('2021-09-13 13:40:55')
;

insert into PLACE_SEARCH_TH
(trans_at, query, frst_rgs_at)
values ( UNIX_TIMESTAMP(), 'TEST3', now() )
;



