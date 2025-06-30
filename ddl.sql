CREATE TABLE public.users (
id bigserial NOT NULL,
email varchar(100) NOT NULL,
"password" varchar(255) NOT NULL,
nickname varchar(50) COLLATE "ko_KR.utf8" NOT NULL,
"role" varchar(50) DEFAULT 'ROLE_USER' NULL,
status varchar(50) NOT NULL,
created_at timestamptz NULL,
updated_at timestamptz NULL,
last_login_at timestamptz NULL,
CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE TABLE public.posts (
id bigserial NOT NULL,
user_id int8 NOT NULL,
title varchar(200) COLLATE "ko_KR.utf8" NOT NULL,
`content` text COLLATE "ko_KR.utf8" NOT NULL,
status varchar(50) NOT NULL,
created_at timestamptz NULL,
updated_at timestamptz NULL,
CONSTRAINT posts_pk PRIMARY KEY (id)
);

CREATE TABLE public."comments" (
id bigserial NOT NULL,
post_id int8 NOT NULL,
user_id int8 NOT NULL,
"content" text COLLATE "ko_KR.utf8" NOT NULL,
status varchar(50) NOT NULL,
created_at timestamptz NULL,
updated_at timestamptz NULL,
CONSTRAINT comments_pk PRIMARY KEY (id)
);