
-- Admin user and role
INSERT INTO USERS (ID, USERNAME, PASSWORD, ENABLED) VALUES (777,'admin', '$2a$12$/z3eMYKC4wbYuPrsIOW6WuP0fz5jmPfplXaLwDlJMLzp9OLwE/Rca', 'true');

INSERT INTO USER_AUTHORIZATION  (AUTH_ID,AUTH,USER_ID) VALUES(999,'ROLE_ADMIN',777);

--- Assets
INSERT INTO ASSETS (ID,ASSET_CODE,ASSET_NAME,SIZE,USABLE_SIZE) VALUES(1001,'BIM','Bim Magazalar',1000,1000);
INSERT INTO ASSETS (ID,ASSET_CODE,ASSET_NAME,SIZE,USABLE_SIZE) VALUES(1002,'AKBNK','Akbnak A.S',3000,3000);
INSERT INTO ASSETS (ID,ASSET_CODE,ASSET_NAME,SIZE,USABLE_SIZE) VALUES(1004,'ENKA','ENKA Insaat A.S',2000,2000);
INSERT INTO ASSETS (ID,ASSET_CODE,ASSET_NAME,SIZE,USABLE_SIZE) VALUES(1005,'KCHOL','Koc Holding',2000,2000);
