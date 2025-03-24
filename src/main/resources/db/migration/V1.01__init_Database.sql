
CREATE TABLE IF NOT EXISTS public.user_type
(
    id_user_type bigint NOT NULL,
    is_active boolean,
    type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_type_pkey PRIMARY KEY (id_user_type),
    CONSTRAINT uc_usertype UNIQUE (type)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id_user bigint NOT NULL,
    document character varying(15) COLLATE pg_catalog."default" NOT NULL,
    email character varying(60) COLLATE pg_catalog."default" NOT NULL,
    is_active boolean,
    is_receive_money boolean,
    is_send_money boolean,
    money_balance numeric(38,2) NOT NULL,
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    password character varying(30) COLLATE pg_catalog."default" NOT NULL,
    user_type bigint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id_user),
    CONSTRAINT uc_userdoc UNIQUE (document),
    CONSTRAINT uc_usermail UNIQUE (email),
    CONSTRAINT fkas1xh9ffcl617omqb321ivj67 FOREIGN KEY (user_type)
        REFERENCES public.user_type (id_user_type) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS public.transaction
(
    id_transaction bigint NOT NULL,
    create_date timestamp(6) without time zone NOT NULL,
    moviment_date timestamp(6) without time zone NOT NULL,
    value numeric(38,2) NOT NULL,
    user_payee bigint NOT NULL,
    user_payer bigint NOT NULL,
    CONSTRAINT transaction_pkey PRIMARY KEY (id_transaction),
    CONSTRAINT fkp5go4nlohjho7jxumr72acptq FOREIGN KEY (user_payee)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fktfmpfgsbfcgl3e3kcodsev10r FOREIGN KEY (user_payer)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


INSERT INTO public.user_type (id_user_type, type, is_active) VALUES
(nextval('seqpkusertype'), 'USER', true),
(nextval('seqpkusertype'), 'SHOPKEEPER', true);