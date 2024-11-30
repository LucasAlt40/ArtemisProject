create table POST
(
    ID             NUMBER        not null
        primary key,
    CONTENT        VARCHAR2(240) not null,
    LIKES_QUANTITY NUMBER default 0,
    POST_DATE      DATE          not null,
    USER_ID        NUMBER        not null
        references USER_ARTEMIS,
    THREAD_ID      NUMBER
)

create table USER_ARTEMIS
(
    ID                   NUMBER        not null
        primary key,
    USERNAME             VARCHAR2(70)  not null,
    EMAIL                VARCHAR2(255) not null
        unique,
    PATH_PROFILE_PICTURE VARCHAR2(255),
    BIOGRAFY             VARCHAR2(120),
    PASSWORD             VARCHAR2(32)  not null,
    FRIENDS_QUANTITY     NUMBER default 0,
    NAME                 VARCHAR2(120)
)

create table POST_IMAGES
(
    ID      NUMBER not null,
    ID_POST NUMBER not null
        references POST,
    PATH    VARCHAR2(255),
    primary key (ID, ID_POST)
)

create table POST_LIKES
(
    ID_POST NUMBER not null
        references POST,
    ID_USER NUMBER not null
        references USER_ARTEMIS,
    primary key (ID_POST, ID_USER)
)

create table REQUEST
(
    ID_USER_RECEIVED  NUMBER                        not null
        references USER_ARTEMIS,
    ID_USER_SUBMITTED NUMBER                        not null
        references USER_ARTEMIS,
    STATUS            VARCHAR2(9) default 'PENDING' not null,
    primary key (ID_USER_RECEIVED, ID_USER_SUBMITTED)
)

create table FRIENDS
(
    ID_USER_01 NUMBER not null
        references USER_ARTEMIS,
    ID_USER_02 NUMBER not null
        references USER_ARTEMIS,
    primary key (ID_USER_01, ID_USER_02)
)

