-- API정보
/** ======================================================================================
 * 테이블 생성 : API정보 MASTER
======================================================================================= */
CREATE TABLE API_TM
(
    API_CODE     VARCHAR(10)  NOT NULL PRIMARY KEY COMMENT 'API구분코드',
    ALIN_CODE    VARCHAR(10)  NOT NULL COMMENT '기관구분코드',
    API_VER      VARCHAR(10)  NOT NULL COMMENT 'API버전',
    API_URI      VARCHAR(100) NOT NULL COMMENT 'APIURI',
    API_NM       VARCHAR(100) NULL COMMENT 'API이름',
    API_DESC_URI VARCHAR(200) NULL COMMENT 'API설명URI',
    FRST_RGS_AT  DATETIME     NULL COMMENT '최초등록일시',
    FRST_RGS_ID  VARCHAR(10)  NULL COMMENT '최초등록자ID',
    LST_CHNG_AT  DATETIME     NULL COMMENT '최종변경일시',
    LST_CHNG_ID  VARCHAR(10)  NULL COMMENT '최종변경자ID'
)
;
/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table API_TM;
truncate table API_TM;

/** ======================================================================================
 * 조회
======================================================================================= */
select * from API_TM;

/** ======================================================================================
 * 기본 정보 입력
======================================================================================= */
INSERT INTO PUBLIC.API_TM (API_CODE, ALIN_CODE, API_VER, API_URI, API_NM, API_DESC_URI, FRST_RGS_AT, FRST_RGS_ID, LST_CHNG_AT, LST_CHNG_ID)
VALUES
 ('KAKAO00001', 'KAKAO_API', 'V2', 'local/search/keyword.json', '키워드로 장소 검색', 'https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword', now(), 'pilsa', now(), 'pilsa')
,('NAVER00001', 'NAVER_API', 'V1', 'search/local.json', '지역 검색', 'https://developers.naver.com/docs/search/local/', now(), 'pilsa', now(), 'pilsa');
