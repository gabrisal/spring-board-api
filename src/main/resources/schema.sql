DROP TABLE IF EXISTS board;

CREATE TABLE board
(
  board_id                  INT             AUTO_INCREMENT  PRIMARY KEY,
  board_title               VARCHAR2(100)   NOT NULL,
  board_content             NTEXT           NOT NULL,
  del_yn                    BOOLEAN         NOT NULL        DEFAULT FALSE,
  frst_reg_user_id          VARCHAR2(15)    NOT NULL,
  frst_reg_user_ip_addr     VARCHAR2(15),
  frst_reg_dttm             DATE            NOT NULL        DEFAULT CURRENT_TIMESTAMP,
  last_upd_user_id          VARCHAR2(15)    NOT NULL,
  last_upd_user_ip_addr     VARCHAR2(15),
  last_upd_dttm             DATE            NOT NULL        DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO board
( board_id
, board_title
, board_content
, frst_reg_user_id
, last_upd_user_id )
VALUES
    (1
    , '테스트'
    , '내용'
    , 1
    , 1);