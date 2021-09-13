-- 투자연계금융기관  기본
/** ======================================================================================
 * 테이블 생성
======================================================================================= */
CREATE TABLE place_search_tm
(
    query        VARCHAR(100) PRIMARY KEY COMMENT '투자연계금융기관코드',
    alliance_nm          BIGINT NOT NULL COMMENT '투자연계금융기관명',
    online_invest_law_yn VARCHAR(1)  NOT NULL COMMENT '온투업기관여부',
    total_limit_amount   INT         NOT NULL COMMENT '전체한도금액',
    onec_limit_amount    INT         NOT NULL COMMENT '1회투자한도금액',
    realty_limit_amount  INT         NOT NULL COMMENT '부동산투자한도금액',
    credit_limit_amount  INT         NOT NULL COMMENT '동일차주한도금액',
    use_yn               VARCHAR(1)  NOT NULL COMMENT '사용여부',
    frst_rgs_at          DATETIME    NULL COMMENT '최초등록일시',
    frst_rgs_id          VARCHAR(10) NULL COMMENT '최초등록자ID',
    lst_chng_at          DATETIME    NULL COMMENT '최종변경일시',
    lst_chng_id          VARCHAR(10) NULL COMMENT '최종변경자ID'
)
;
/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table INVEST_ALLIANCE_TM;

/** ======================================================================================
 * 기본 데이터 입력
======================================================================================= */
insert into INVEST_ALLIANCE_TM (ALLIANCE_CODE, ALLIANCE_NM, ONLINE_INVEST_LAW_YN, TOTAL_LIMIT_AMOUNT, ONEC_LIMIT_AMOUNT,
                                REALTY_LIMIT_AMOUNT, CREDIT_LIMIT_AMOUNT, USE_YN,FRST_RGS_AT, FRST_RGS_ID, LST_CHNG_AT,
                                LST_CHNG_ID)
values ('PPF', '피플펀드', 'Y', 30000000, 5000000, 10000000, 5000000,'Y', NOW(), 'pilsa', NOW(), 'pilsa')
     , ('TGF', '투게더펀딩', 'N', 10000000, 2000000, 5000000, 5000000,'Y', NOW(), 'pilsa', NOW(), 'pilsa')
     , ('TRF', '테라펀딩', 'N', 10000000, 2000000, 5000000, 5000000,'Y', NOW(), 'pilsa', NOW(), 'pilsa');

select *
from INVEST_ALLIANCE_TM;





search_count
