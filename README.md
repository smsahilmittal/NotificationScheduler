

Requirements
Java - 8

Maven - 3.x.x

MySQL - 5.x.x

Steps to Setup
1. Clone the application


2. Setup Spring Mail

The project is using Gmail's SMTP server for sending emails. Whether you use Gmail or any other SMTP server, you'll need to configure the following mail properties accordingly -

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=smsahilmittal123@gmail.com
spring.mail.password= Not pasting here due to security concerns

3. Build and run the app using maven

Finally, You can run the app by typing the following command from the root directory of the project -

mvn spring-boot:run


-------- Schedule a mail ----------

Scheduling an Email using the /schedule/email API
Note : dateTime should be of future

curl --location --request POST 'localhost:8080/schedule/email' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"smsahilmittal123@gmail.com",
    "cartId":"sahil_cart_1",
    "dateTime":"2022-06-25T16:28:50",
    "timeZone":"Asia/Kolkata",
    "orderId":"sahil_order_1"
}'


-------------------------------------


------- Update Already scheduled mail -------

curl --location --request POST 'localhost:8080/update/schedule/email' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"smsahilmittal123@gmail.com",
    "cartId":"sahil_cart_1",
    "dateTime":"2022-06-25T16:28:50",
    "timeZone":"Asia/Kolkata",
    "orderId":"sahil_order_1"
}'
------------------------------------


