CREATE TABLE if not exists code(id VARCHAR(50),
   value VARCHAR(50),
   sort_order NVARCHAR(255),
   name VARCHAR(50),
   short_name VARCHAR(51),
   PRIMARY KEY(id,value)
   );

CREATE TABLE if not exists member(user_id VARCHAR(50) PRIMARY KEY,
   password VARCHAR(60),
   name NVARCHAR(255),
   authority VARCHAR(50),
   gender VARCHAR(6),
   /* version BIGINT, */
   regist_user VARCHAR(255),
   regist_date TIMESTAMP
   );