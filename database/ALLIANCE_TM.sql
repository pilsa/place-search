-- 기관정보
/** ======================================================================================
 * 테이블 생성 : 기관정보 MASTER
======================================================================================= */
CREATE TABLE ALLIANCE_TM
(
    ALIN_CODE      VARCHAR(10) NOT NULL PRIMARY KEY COMMENT '기관구분코드',
    ALIN_NM        VARCHAR(30) NOT NULL COMMENT '기관이름',
    ALIN_DOMN      VARCHAR(60) NOT NULL COMMENT '기관도메인',
    ALIN_AUTH_DVCD VARCHAR(10) NULL COMMENT '기관인증구분코드',
    CLIENT_ID      VARCHAR(60) NULL COMMENT '클라이언트ID',
    CLIENT_SECRET  VARCHAR(60) NULL COMMENT '클라이언트SECRET',
    API_KEY        VARCHAR(60) NULL COMMENT 'API키',
    FRST_RGS_AT    DATETIME    NULL COMMENT '최초등록일시',
    FRST_RGS_ID    VARCHAR(10) NULL COMMENT '최초등록자ID',
    LST_CHNG_AT    DATETIME    NULL COMMENT '최종변경일시',
    LST_CHNG_ID    VARCHAR(10) NULL COMMENT '최종변경자ID'
)
;

/** ======================================================================================
 * 테이블 드랍
======================================================================================= */
DROP table ALLIANCE_TM;
truncate table ALLIANCE_TM;

/** ======================================================================================
 * 조회
======================================================================================= */
select * from ALLIANCE_TM;

/** ======================================================================================
 * 기본 정보 입력
======================================================================================= */
INSERT INTO PUBLIC.ALLIANCE_TM (ALIN_CODE, ALIN_NM, ALIN_DOMN, ALIN_AUTH_DVCD, CLIENT_ID, CLIENT_SECRET, API_KEY,FRST_RGS_AT, FRST_RGS_ID, LST_CHNG_AT, LST_CHNG_ID)
VALUES
 ('KAKAO_DEV', '카카오 API', 'https://dapi.kakao.com', 'ID_SECRET', null, null,'KakaoAK 1bff160ca1074e50c07585b91e47806b', now(), 'pilsa', now(),'pilsa')
,('NAVER_API', '네이버 OpenAPI', 'https://openapi.naver.com', 'API_KEY', 'bQA_gDDbrN0lWFXuhWeE', 'BES5HWgEZp', null, now(), 'pilsa', now(), 'pilsa')
;

