-- Table: public.member

-- DROP TABLE public.member;

CREATE TABLE public.member
(
    member_order smallint NOT NULL,
    name character varying(20) COLLATE pg_catalog."default",
    birthday timestamp without time zone,
    birthday_withtimezone timestamp with time zone,
    CONSTRAINT "Member_pkey" PRIMARY KEY (member_order)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.member
    OWNER to eric;

-- Table: public."springSecurity_user"

-- DROP TABLE public."springSecurity_user";

CREATE TABLE public."springSecurity_user"
(
    user_id character varying(40) COLLATE pg_catalog."default" NOT NULL,
    user_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(70) COLLATE pg_catalog."default" NOT NULL,
    role character varying(30) COLLATE pg_catalog."default" NOT NULL,
    create_time timestamp without time zone NOT NULL,
    last_login_time timestamp without time zone,
    update_time timestamp without time zone,
    CONSTRAINT "springSecurity_user_pkey" PRIMARY KEY (user_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public."springSecurity_user"
    OWNER to eric;