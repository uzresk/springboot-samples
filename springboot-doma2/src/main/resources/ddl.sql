CREATE TABLE member(user_id VARCHAR(50) PRIMARY KEY,
   password VARCHAR(60),
   name NVARCHAR(255),
   authority VARCHAR(50),
   version BIGINT,
   registered_person VARCHAR(255),
   registration_date TIMESTAMP,
   updater VARCHAR(255),
   update_date TIMESTAMP
   );
