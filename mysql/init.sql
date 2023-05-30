USE KREAMISHDB;

CREATE TABLE MEMBER_ROLE
(
    MEMBER_ROLE_ID BIGINT      NOT NULL AUTO_INCREMENT,
    NAME           VARCHAR(16) NOT NULL,
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT MEMBER_ROLE_PK PRIMARY KEY (MEMBER_ROLE_ID)
);

CREATE TABLE MEMBER
(
    MEMBER_ID      BIGINT       NOT NULL AUTO_INCREMENT,
    MEMBER_ROLE_ID BIGINT       NOT NULL,
    EMAIL          VARCHAR(200) NOT NULL,
    PASSWORD       VARCHAR(256) NOT NULL,
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT MEMBER_PK PRIMARY KEY (MEMBER_ID),
    CONSTRAINT MEMBER_MEMBER_ROLE_FK FOREIGN KEY (MEMBER_ROLE_ID) REFERENCES MEMBER_ROLE (MEMBER_ROLE_ID)
);

CREATE TABLE BRAND
(
    BRAND_ID   BIGINT       NOT NULL AUTO_INCREMENT,
    NAME       VARCHAR(200) NOT NULL,
    CREATED_AT DATE         NOT NULL,
    UPDATED_AT DATE,
    CREATE_ID  VARCHAR(200),
    UPDATE_ID  VARCHAR(200),
    CONSTRAINT BRAND_PK PRIMARY KEY (BRAND_ID)
);

CREATE TABLE CATEGORY
(
    CATEGORY_ID BIGINT       NOT NULL AUTO_INCREMENT,
    NAME        VARCHAR(200) NOT NULL,
    CREATED_AT  DATE         NOT NULL,
    UPDATED_AT  DATE,
    CREATE_ID   VARCHAR(200),
    UPDATE_ID   VARCHAR(200),
    CONSTRAINT CATEGORY_PK PRIMARY KEY (CATEGORY_ID)
);

CREATE TABLE CATEGORY_DETAIL
(
    CATEGORY_DETAIL_ID BIGINT       NOT NULL AUTO_INCREMENT,
    CATEGORY_ID        BIGINT       NOT NULL,
    NAME               VARCHAR(200) NOT NULL,
    CREATED_AT         DATE         NOT NULL,
    UPDATED_AT         DATE,
    CREATE_ID          VARCHAR(200),
    UPDATE_ID          VARCHAR(200),
    CONSTRAINT CATEGORY_DETAIL_PK PRIMARY KEY (CATEGORY_DETAIL_ID),
    CONSTRAINT CATEGORY_DETAIL_CATEGORY_FK FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (CATEGORY_ID)
);

CREATE TABLE ITEM
(
    ITEM_ID              BIGINT       NOT NULL AUTO_INCREMENT,
    BRAND_ID             BIGINT       NOT NULL,
    CATEGORY_ID          BIGINT       NOT NULL,
    CATEGORY_DETAIL_ID   BIGINT       NOT NULL,
    NAME                 VARCHAR(200) NOT NULL,
    SUB_NAME             VARCHAR(200),
    MODEL_CODE           VARCHAR(50),
    RELEASE_DATE         DATE,
    RELEASE_PRICE        BIGINT,
    REPRESENTATIVE_COLOR VARCHAR(50),
    IMAGE_URL            VARCHAR(200),
    CREATED_AT           DATE         NOT NULL,
    UPDATED_AT           DATE,
    CREATE_ID            VARCHAR(200),
    UPDATE_ID            VARCHAR(200),
    CONSTRAINT ITEM_PK PRIMARY KEY (ITEM_ID),
    CONSTRAINT ITEM_BRAND_FK FOREIGN KEY (BRAND_ID) REFERENCES BRAND (BRAND_ID),
    CONSTRAINT ITEM_CATEGORY_FK FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY (CATEGORY_ID),
    CONSTRAINT ITEM_CATEGORY_DETAIL_FK FOREIGN KEY (CATEGORY_DETAIL_ID) REFERENCES CATEGORY_DETAIL (CATEGORY_DETAIL_ID)
);

CREATE TABLE COMMENT
(
    COMMENT_ID BIGINT        NOT NULL AUTO_INCREMENT,
    ITEM_ID    BIGINT        NOT NULL,
    MEMBER_ID  BIGINT        NOT NULL,
    VALUE      VARCHAR(2000) NOT NULL,
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT COMMENT_PK PRIMARY KEY (COMMENT_ID),
    CONSTRAINT COMMENT_ITEM_FK FOREIGN KEY (ITEM_ID) REFERENCES ITEM (ITEM_ID),
    CONSTRAINT COMMENT_MEMBER_FK FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID)
);

CREATE TABLE COLLECTION
(
    COLLECTION_ID BIGINT NOT NULL AUTO_INCREMENT,
    NAME          VARCHAR(50),
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT COLLECTION_PK PRIMARY KEY (COLLECTION_ID)
);

CREATE TABLE ITEM_COLLECTION_REL
(
    ITEM_COLLECTION_REL_ID BIGINT NOT NULL AUTO_INCREMENT,
    ITEM_ID                BIGINT NOT NULL,
    COLLECTION_ID          BIGINT NOT NULL,
    CREATED_AT             DATE   NOT NULL,
    UPDATED_AT             DATE,
    CREATE_ID              VARCHAR(200),
    UPDATE_ID              VARCHAR(200),
    CONSTRAINT ITEM_COLLECTION_REL_PK PRIMARY KEY (ITEM_COLLECTION_REL_ID),
    CONSTRAINT ITEM_COLLECTION_REL_ITEM_FK FOREIGN KEY (ITEM_ID) REFERENCES ITEM (ITEM_ID),
    CONSTRAINT ITEM_COLLECTION_REL_COLLECTION_FK FOREIGN KEY (COLLECTION_ID) REFERENCES COLLECTION (COLLECTION_ID)
);

CREATE TABLE ITEM_SIZES
(
    ITEM_SIZES_ID BIGINT NOT NULL AUTO_INCREMENT,
    ITEM_ID       BIGINT NOT NULL,
    SIZE          VARCHAR(50),
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT ITEM_SIZES_PK PRIMARY KEY (ITEM_SIZES_ID),
    CONSTRAINT ITEM_SIZES_ITEM_FK FOREIGN KEY (ITEM_ID) REFERENCES ITEM (ITEM_ID)
);

CREATE TABLE PURCHASE
(
    PURCHASE_ID     BIGINT       NOT NULL AUTO_INCREMENT,
    ITEM_SIZES_ID   BIGINT       NOT NULL,
    MEMBER_ID       BIGINT       NOT NULL,
    PURCHASE_PRICE  BIGINT       NOT NULL,
    PURCHASE_STATUS VARCHAR(100) NOT NULL,
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT PURCHASE_PK PRIMARY KEY (PURCHASE_ID),
    CONSTRAINT PURCHASE_ITEM_SIZES_FK FOREIGN KEY (ITEM_SIZES_ID) REFERENCES ITEM_SIZES (ITEM_SIZES_ID),
    CONSTRAINT PURCHASE_MEMBER_FK FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID)
);

CREATE TABLE SALE
(
    SALE_ID        BIGINT       NOT NULL AUTO_INCREMENT,
    ITEM_SIZES_ID  BIGINT       NOT NULL,
    MEMBER_ID      BIGINT       NOT NULL,
    SALE_ID_PRICE  BIGINT       NOT NULL,
    SALE_ID_STATUS VARCHAR(100) NOT NULL,
    CREATED_AT    DATE   NOT NULL,
    UPDATED_AT    DATE,
    CREATE_ID     VARCHAR(200),
    UPDATE_ID     VARCHAR(200),
    CONSTRAINT SALE_PK PRIMARY KEY (SALE_ID),
    CONSTRAINT SALE_ITEM_SIZES_FK FOREIGN KEY (ITEM_SIZES_ID) REFERENCES ITEM_SIZES (ITEM_SIZES_ID),
    CONSTRAINT SALE_MEMBER_FK FOREIGN KEY (MEMBER_ID) REFERENCES MEMBER (MEMBER_ID)
);

CREATE TABLE TRADE
(
    TRADE_ID    BIGINT NOT NULL AUTO_INCREMENT,
    PURCHASE_ID BIGINT NOT NULL,
    SALE_ID BIGINT NOT NULL,
    TRADE_PRICE BIGINT NOT NULL,
    CONSTRAINT TRADE_PK PRIMARY KEY (TRADE_ID),
    CONSTRAINT TRADE_PURCHASE_FK FOREIGN KEY (PURCHASE_ID) REFERENCES PURCHASE(PURCHASE_ID),
    CONSTRAINT TRADE_SALE_FK FOREIGN KEY (SALE_ID) REFERENCES SALE(SALE_ID)
);


INSERT INTO category(category_id, name, created_at, updated_at, CREATE_ID, UPDATE_ID)
VALUES (1, '신발', now(), now(), 'adm', 'adm'),
       (2, '상의', now(), now(), 'adm', 'adm'),
       (3, '하의', now(), now(), 'adm', 'adm'),
       (4, '아우터', now(), now(), 'adm', 'adm');

INSERT INTO CATEGORY_DETAIL(CATEGORY_DETAIL_ID, CATEGORY_ID, NAME, CREATED_AT, UPDATED_AT,
                            CREATE_ID, UPDATE_ID)
VALUES (1, 1, '스니커즈', now(), now(), 'adm', 'adm'),
       (2, 1, '구두', now(), now(), 'adm', 'adm'),
       (3, 1, '샌들/슬리퍼', now(), now(), 'adm', 'adm'),
       (4, 1, '부츠', now(), now(), 'adm', 'adm'),
       (5, 3, '긴바지', now(), now(), 'adm', 'adm'),
       (6, 3, '반바지', now(), now(), 'adm', 'adm'),
       (7, 3, '스커트', now(), now(), 'adm', 'adm'),
       (8, 3, '레깅스', now(), now(), 'adm', 'adm'),
       (9, 4, '가디건', now(), now(), 'adm', 'adm'),
       (12, 4, '니트', now(), now(), 'adm', 'adm'),
       (13, 4, '코트', now(), now(), 'adm', 'adm'),
       (14, 4, '자켓', now(), now(), 'adm', 'adm'),
       (15, 4, '아노락', now(), now(), 'adm', 'adm'),
       (16, 4, '패딩', now(), now(), 'adm', 'adm'),
       (10, 2, '긴팔 티셔츠', now(), now(), 'adm', 'adm'),
       (11, 2, '반팔 티셔츠', now(), now(), 'adm', 'adm');

INSERT INTO BRAND(BRAND_ID, NAME, CREATED_AT, UPDATED_AT, CREATE_ID, UPDATE_ID)
VALUES (1, 'Rab', now(), now(), 'adm', 'adm'),
       (2, 'Fjallraven', now(), now(), 'adm', 'adm'),
       (3, 'Converse', now(), now(), 'adm', 'adm'),
       (4, 'The North Face', now(), now(), 'adm', 'adm'),
       (5, 'New Balance', now(), now(), 'adm', 'adm'),
       (6, 'Under Armour', now(), now(), 'adm', 'adm'),
       (7, 'Guess', now(), now(), 'adm', 'adm'),
       (8, 'Nike', now(), now(), 'adm', 'adm'),
       (9, 'Lee', now(), now(), 'adm', 'adm'),
       (10, 'Reebok', now(), now(), 'adm', 'adm'),
       (11, 'Champion', now(), now(), 'adm', 'adm'),
       (12, 'Helly Hansen', now(), now(), 'adm', 'adm'),
       (13, 'Adidas', now(), now(), 'adm', 'adm'),
       (14, 'Arc\'teryx', now(), now(), 'adm', 'adm'),
       (15, 'Columbia', now(), now(), 'adm', 'adm'),
       (16, 'Asics', now(), now(), 'adm', 'adm'),
       (17, 'Dickies', now(), now(), 'adm', 'adm'),
       (18, 'Skechers', now(), now(), 'adm', 'adm'),
       (19, 'Canada Goose', now(), now(), 'adm', 'adm'),
       (20, 'Puma', now(), now(), 'adm', 'adm'),
       (21, 'Calvin Klein', now(), now(), 'adm', 'adm'),
       (22, 'Levi\'s', now(), now(), 'adm', 'adm'),
       (23, 'Carhartt', now(), now(), 'adm', 'adm'),
       (24, 'Wrangler', now(), now(), 'adm', 'adm'),
       (25, 'Marmot', now(), now(), 'adm', 'adm'),
       (26, 'Mammut', now(), now(), 'adm', 'adm'),
       (27, 'Vans', now(), now(), 'adm', 'adm'),
       (28, 'Patagonia', now(), now(), 'adm', 'adm'),
       (29, 'Tommy Hilfiger', now(), now(), 'adm', 'adm'),
       (30, 'Zara', now(), now(), 'adm', 'adm'),
       (31, 'H&M', now(), now(), 'adm', 'adm');

LOAD DATA INFILE '/var/lib/mysql-files/final_items.csv'
    INTO TABLE ITEM
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS
( NAME,
    IMAGE_URL,
    RELEASE_PRICE,
    SUB_NAME,
    @release_date,
    MODEL_CODE,
    CATEGORY_ID,
    REPRESENTATIVE_COLOR,
    CATEGORY_DETAIL_ID,
    BRAND_ID,
    @created_at,
    @updated_at,
    @create_id,
    @update_id)
SET
    RELEASE_DATE = STR_TO_DATE(@release_date, '%Y/%m/%d'),
    CREATED_AT = CURDATE(),
    UPDATED_AT = CURDATE(),
    CREATE_ID = 'adm',
    UPDATE_ID = 'adm';
