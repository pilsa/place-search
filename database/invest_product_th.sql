-- 투자상품 거래내역
/** ======================================================================================
 * 테이블 생성
======================================================================================= */
CREATE TABLE INVEST_PRODUCT_TH
(
    transaction_id VARCHAR(30) PRIMARY KEY COMMENT '거래ID',
    member_num     BIGINT      NOT NULL COMMENT '회원번호',
    product_id     VARCHAR(10) NOT NULL COMMENT '상품ID',
    trans_type     VARCHAR(2)  NULL COMMENT '거래유형코드',
    trans_amount   BIGINT      NOT NULL COMMENT '거래금액',
    trans_at       DATETIME    NOT NULL COMMENT '거래일시',
    frst_rgs_at    DATETIME    NULL COMMENT '최초등록일시',
    frst_rgs_id    VARCHAR(10) NULL COMMENT '최초등록자ID',
    lst_chng_at    DATETIME    NULL COMMENT '최종변경일시',
    lst_chng_id    VARCHAR(10) NULL COMMENT '최종변경자ID'
)
;

/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table INVEST_PRODUCT_TH;
truncate table INVEST_PRODUCT_TH;
/** ======================================================================================
 * 인덱스생성
======================================================================================= */
CREATE INDEX IDX_PRD_TH_MEMBERNUM ON INVEST_PRODUCT_TH (member_num);
DROP INDEX IDX_PRD_TH_MEMBERNUM;

CREATE INDEX IDX_PRD_TH_PRODUCTID ON INVEST_PRODUCT_TH (product_id);
DROP INDEX IDX_PRD_TH_PRODUCTID;

select *
from INVEST_PRODUCT_TH
;

select PRODUCT_ID,
       PRODUCT_TYPE,
       PRODUCT_NAME,
       PRODUCT_DSC,
       INT_RATE,
       TOTAL_INVEST_AMOUNT,
       STARTED_AT,
       FINISHED_AT,
       ALLIANCE_CODE,
       INVEST_PERIOD
from INVEST_PRODUCT_TM;

SELECT TM.PRODUCT_ID /* 상품ID */
     , TM.PRODUCT_TYPE /* 상품구분코드 */
     , TM.PRODUCT_NAME /* 상품이름 */
     , TM.PRODUCT_DSC /* 상품설명 */
     , TM.INT_RATE /* 적용이율 */
     , TM.TOTAL_INVEST_AMOUNT /* 총모집금액 */
     , TM.STARTED_AT /* 투자시작일시 */
     , TM.FINISHED_AT /* 투자종료일시 */
     , TM.ALLIANCE_CODE /* 투자연계금융기관코드 */
     , TM.INVEST_PERIOD /* 투자기간 */
     , TF.PRODUCT_STATUS /* 상품상태코드 */
     , TF.CURRENT_INVEST_AMOUNT /* 현재모집금액 */
     , TF.CURRENT_INVEST_CNT /* 현재투자자수 */
FROM INVEST_PRODUCT_TM TM,
     INVEST_PRODUCT_TF TF
WHERE 1 = 1
  AND TM.PRODUCT_ID = TF.PRODUCT_ID
  AND TM.STARTED_AT <= NOW()
  AND TM.FINISHED_AT >= NOW()
ORDER BY INT_RATE DESC, FINISHED_AT
/*높은 수익률순*/
--  ORDER BY INVEST_PERIOD ASC, FINISHED_AT /*짧은 기간순*/
-- ORDER BY FINISHED_AT ASC, STARTED_AT /*마감임박순*/
;



