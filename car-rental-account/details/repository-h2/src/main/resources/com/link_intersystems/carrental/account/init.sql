CREATE SCHEMA IF NOT EXISTS ACCOUNT;
SET SCHEMA ACCOUNT;

DROP TABLE IF EXISTS LOGIN;

CREATE TABLE LOGIN
(
    ID               BIGINT   NOT NULL,
    USERNAME         VARCHAR(50)   NOT NULL,
    PASS             CHAR(64)   NOT NULL,
    CONSTRAINT LOGIN_PK PRIMARY KEY (ID),
    CONSTRAINT LOGIN_USERNAME_UNIQUE UNIQUE(USERNAME)
);

INSERT INTO LOGIN (ID, USERNAME, PASS)
VALUES (1, 'nick.wahlberg', '7f0b629cbb9d794b3daf19fcd686a30a039b47395545394dadc0574744996a87'),    -- pass nick
       (2, 'rene.link', '63c5d687c64d48947a75bf25daa7a128d0c9a8fe1205c7ae3728224015ee7c0e'),        -- pass rene
       (3, 'kevin.bloom', '85f5e10431f69bc2a14046a13aabaefc660103b6de7a84f75c4b96181d03f0b5'),      -- pass kevin
       (4, 'penelope.guiness', 'ac7a89c282194aa6fd19ee0916325e2e5a7ccb0635056a2dcbcb084bc1f71fd8'), -- pass penelope
       (5, 'john.doe', '96d9632f363564cc3032521409cf22a852f2032eec099ed5967c0d000cec607a');         -- pass john



