# Authentication and Authorization Documentation

 

### We, the developers of Athletic Storm, have taken many measures in the code to prevent unauthorized users from accessing forbidden content.

 

## **Login and Registration**

On registration, a new user will need to select a valid password, as well as a username that is not already stored in the database. The password will be stored in the database after being encrypted to maintain security. The user will also need to select a subscription role which will be used in the application to determine which content the user can access.

 

After signing up, the user can then log in. Login information given by the user is authenticated against the database information. If authentication is successful, a **javascript web token** is granted which will allow the user to gain access to functionality of Athletic Storm based on the user’s subscription role.

 

## **Endpoint Protection**

Each endpoint which sends the front-end data for the user to access is protected by requiring the pre-requisite of the user being signed in and authorized based on their role. As such, trying to manually access an endpoint for information through an HTTP request or using software such as postman would not result in data being returned.

 

## **Roles** 

Many measures have been taken in the Athletic Storm code to prevent a user from accessing content that is not available to their specific subscription tier. Endpoints for schools, players and coaches all have checks in place to ensure that data cannot be manually accessed by an endpoint entered into the web browser. Other data that cannot be accessed by endpoint such as players for a specific year are not allowed to be accessed by lower subscription tiers. Users trying to access such information will be given an error.

 

 