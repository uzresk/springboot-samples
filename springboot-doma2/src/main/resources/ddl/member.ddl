CREATE TABLE member(user_id VARCHAR(50) PRIMARY KEY,
   password VARCHAR(60),
   name NVARCHAR(255),
   authority VARCHAR(50),
   gender VARCHAR(6),
   /* version BIGINT, */
   regist_user VARCHAR(255),
   regist_date TIMESTAMP
   );
