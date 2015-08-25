-- Table: account

-- DROP TABLE account;

CREATE TABLE account
(
  account_id character varying(200) NOT NULL,
  password character varying(200) NOT NULL,
  mail character varying(200),
  secret_key character varying(200),
  CONSTRAINT account_pk PRIMARY KEY (account_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO springboot;
