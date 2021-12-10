-- Create table
create table FABADM.EDU
(
    name             VARCHAR2(40) not null,
    lasteventname    VARCHAR2(40),
    lasteventtimekey VARCHAR2(40),
    lasteventtime    DATE,
    lasteventuser    VARCHAR2(40),
    lasteventcomment VARCHAR2(4000),
    description      VARCHAR2(40),
    num              VARCHAR2(40)
)
    tablespace USERS
    pctfree 10
    initrans 1
    maxtrans 255
    storage
(
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
);
-- Create/Recreate primary, unique and foreign key constraints
alter table FABADM.EDU
    add constraint EDU_KEY primary key (NAME)
        using index
            tablespace USERS
            pctfree 10
            initrans 2
            maxtrans 255
            storage
            (
            initial 64 K
            next 1 M
            minextents 1
            maxextents unlimited
            );
