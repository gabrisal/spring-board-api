DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    user_id                   VARCHAR2(15)    PRIMARY KEY,
    password                  VARCHAR2(100)   NOT NULL,
    name                      VARCHAR2(15)    NOT NULL,
    nick                      VARCHAR2(15)    NOT NULL,
    birthday                  VARCHAR2(8)     NOT NULL,
    gender                    VARCHAR2(1)     NOT NULL,
    email_addr                VARCHAR2(100)   NOT NULL,
    del_yn                    BOOLEAN         NOT NULL        DEFAULT FALSE,
    frst_reg_user_id          VARCHAR2(15)    NOT NULL,
    frst_reg_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    last_upd_user_id          VARCHAR2(15)    NOT NULL,
    last_upd_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users
( user_id
, password
, name
, nick
, birthday
, gender
, email_addr
, frst_reg_user_id
, last_upd_user_id )
VALUES
    ('gabrisal'
    , 'password1!'
    , '김지영'
    , '착한사람'
    , '19940806'
    , '2'
    , 'jy942013@naver.com'
    , 'gabrisal'
    , 'gabrisal');

DROP TABLE IF EXISTS board;

CREATE TABLE board
(
  board_id                  INT             AUTO_INCREMENT  PRIMARY KEY,
  board_title               VARCHAR2(100)   NOT NULL,
  board_content             NTEXT           NOT NULL,
  del_yn                    BOOLEAN         NOT NULL        DEFAULT FALSE,
  frst_reg_user_id          VARCHAR2(15)    NOT NULL,
  frst_reg_user_ip_addr     VARCHAR2(15),
  frst_reg_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
  last_upd_user_id          VARCHAR2(15)    NOT NULL,
  last_upd_user_ip_addr     VARCHAR2(15),
  last_upd_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO board
( board_title
, board_content
, frst_reg_user_id
, last_upd_user_id )
VALUES
    ('테스트'
    , '내용'
    , 'gabrisal'
    , 'gabrisal');

INSERT INTO board
( board_title
, board_content
, frst_reg_user_id
, last_upd_user_id )
VALUES
    ('테스트2'
    , '내용2'
    , 'gabrisal'
    , 'gabrisal');

DROP TABLE IF EXISTS tags;

CREATE TABLE tags
(
    tag_id                    INT             AUTO_INCREMENT  PRIMARY KEY,
    tag_name                  VARCHAR2(100)   NOT NULL,
    del_yn                    BOOLEAN         NOT NULL        DEFAULT FALSE,
    frst_reg_user_id          VARCHAR2(15)    NOT NULL,
    frst_reg_user_ip_addr     VARCHAR2(15),
    frst_reg_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    last_upd_user_id          VARCHAR2(15)    NOT NULL,
    last_upd_user_ip_addr     VARCHAR2(15),
    last_upd_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS board_tag_relation;

CREATE TABLE board_tag_relation
(
    board_id                  INT,
    tag_id                    INT,
    frst_reg_user_id          VARCHAR2(15)    NOT NULL,
    frst_reg_user_ip_addr     VARCHAR2(15),
    frst_reg_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    last_upd_user_id          VARCHAR2(15)    NOT NULL,
    last_upd_user_ip_addr     VARCHAR2(15),
    last_upd_dttm             DATETIME        NOT NULL        DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (board_id, tag_id)
);