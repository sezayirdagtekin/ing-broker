# ING-broker Design and Implementation
 
Database:   H2 Database (url jdbc:h2:mem:testdb, username:sa , paswword is empty)


Deployment: Added Deployment.yml file for deployment to any environment

Database Poller: Polls the database every 15 seconds to process customer PENDING oders with spring  @Scheduled. It is matched randomly. Follow the logs or
or connect to db and check

There are Controller, Service and Repository,Schedule... advice packages in the application. There are related files under each package.(UserController, UserService...)

And also, Dockerfile and deployment.yml files have been added to project
The code base structure is as follows:

![image](https://github.com/user-attachments/assets/6c06164f-b042-43a8-aa54-a4e141d390fc)

The database automatically creates the following tables when the application starts up.

![image](https://github.com/user-attachments/assets/65958375-e758-482d-b34f-16c3eb411258)


### Run Locally
clone project:
```bash
git clone  https://github.com/sezayirdagtekin/ing-broker.git
```
build:
```bash
mvn clean install
```
To run test
```bash
mvn test
```

To run:

Start the Spring Boot application using 
```bash
mvn spring-boot:run
```
Access the H2 database console at. login with user sa and  empy password
```bash
 http://localhost:8080/h2-console.
```

![image](https://github.com/user-attachments/assets/ae02b4b8-4ba5-4d10-9ccf-b8ad92e93f97)

admin user and role are defined as default. The script runs when the application starts.

![image](https://github.com/user-attachments/assets/a3cf1564-8607-4d9a-a891-b25877c0068e)


The following asset codes are defined by default. When the application is started, it is added with sql script. If you want, you can enter new assets with the admin role. You must enter a defined asset when ordering. You will receive a warning when you order with an undefined asset.

![image](https://github.com/user-attachments/assets/7feb7297-baf0-4006-9d99-256ec512fd6e)


You can test the application from endpoints on swagger. Please open swagger url.

http://localhost:8080/swagger-ui/index.html

You need to generate tokens to access end points. To login, admin/admin is already defined in the database.
{
  "username": "admin",
  "password": "admin"
}

![image](https://github.com/user-attachments/assets/e5ea747f-3a0a-493f-b37f-41d3f8c6edd7)

jwt token:

![image](https://github.com/user-attachments/assets/e09a5e3d-8421-4a57-a4d5-f2391899c37f)

Enter authorization with JWT token

![image](https://github.com/user-attachments/assets/38e16cdd-9000-4bd4-86ce-8f9241b86183)


![image](https://github.com/user-attachments/assets/7d3428df-ffe9-410d-a6f9-3877ab9e56ae)

### You can do the following operations:

1- Create Employee/customer 

2- Create  bank account for customer.

3- Deposit money to the created account (IBAN). Display  balance with IBAN

4-  You can withdraw a small amount for testing purposes. It will show you the remaining balance.

5- Create Buy Order  with PENDING status

6 -Every 15 seconds a scheduler job runs in the background
 and randomly query the order with the pending status to the match status. Please follow the logs.

6- Check order list with range for date or simple customer username


There are many checks in the application. For example, when you create a customer, you must create a bank account and deposit money into the bank account. Otherwise, you will see one of the following errors. And everything works through the username.


![image](https://github.com/user-attachments/assets/cb65fe5b-cb04-4af2-8282-090154fa1cb5)



Sample console output:

(I did these operations with admin, but you can log in and do them with employee.)
After each order is matched, the asset usablesize is updated after each order is matched. Customer's bank account was not updated and forgotten.
```bash
2024-10-08T23:07:43.825+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-1] c.ing.security.UserDetailsServiceImpl    : authorities: [ROLE_ADMIN]
2024-10-08T23:07:43.925+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-1] com.ing.service.CustomerService          : Customer sezayir  dagtekin create  with role [Authorization(authId=1, auth=ROLE_USER, user=com.ing.entity.User@1383c108)]
2024-10-08T23:08:02.006+03:00  INFO 18296 --- [demo-ing] [io-8080-exec-10] c.ing.security.UserDetailsServiceImpl    : loadUserByUsername admin
2024-10-08T23:08:02.008+03:00  INFO 18296 --- [demo-ing] [io-8080-exec-10] c.ing.security.UserDetailsServiceImpl    : authorities: [ROLE_ADMIN]
2024-10-08T23:08:02.026+03:00  INFO 18296 --- [demo-ing] [io-8080-exec-10] com.ing.advice.GlobalExceptionHandler    : Call GlobalExceptionHandler: Account Not Found Exception
2024-10-08T23:09:38.450+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-7] c.ing.security.UserDetailsServiceImpl    : loadUserByUsername admin
2024-10-08T23:09:38.450+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-7] c.ing.security.UserDetailsServiceImpl    : authorities: [ROLE_ADMIN]
2024-10-08T23:09:38.507+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-7] com.ing.service.AccountService           : For user sezo  account is created with currency code TRY 
2024-10-08T23:10:11.858+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-9] c.ing.security.UserDetailsServiceImpl    : loadUserByUsername admin
2024-10-08T23:10:11.858+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-9] c.ing.security.UserDetailsServiceImpl    : authorities: [ROLE_ADMIN]
2024-10-08T23:10:11.892+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-9] com.ing.service.AccountService           : 5000 amount was deposited to iban numberTR607328290805977239401725 
2024-10-08T23:10:33.624+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-2] c.ing.security.UserDetailsServiceImpl    : loadUserByUsername admin
2024-10-08T23:10:33.624+03:00  INFO 18296 --- [demo-ing] [nio-8080-exec-2] c.ing.security.UserDetailsServiceImpl    : authorities: [ROLE_ADMIN]
2024-10-08T23:10:41.549+03:00  INFO 18296 --- [demo-ing] [   scheduling-1] com.ing.schedule.OrderProcessor          : Processing orders:BIM 
2024-10-08T23:10:41.549+03:00  INFO 18296 --- [demo-ing] [   scheduling-1] com.ing.schedule.OrderProcessor          : Order BIM matched at price:20.00
2024-10-08T23:10:41.549+03:00  INFO 18296 --- [demo-ing] [   scheduling-1] com.ing.service.AssetService             : Asset table updated for BIM  asset last usablesize: 990

```






