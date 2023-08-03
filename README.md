# Spring-Security-Demo-with-JWT

This project is a demo for securing a web resource with Json Web token (JWT). 
JWT are favoured for securing RESTful resouces due to their statelessnes. A JWT is a set of claims (JSON property–value pairs) that together make up a JSON object. It consists of three parts viz:
Header, Payload and Signature.

## How to use this Application
This application has been built with the latest version of springBoot (Version 3.1.1) as at the time of development. the following dependencies were included:
1. Spring starter web for building RestFul web application
2. Spring Oauth2 resource server for securing web application
3. Spring data JPA for database connection and manipulation
4. H2 in memory databse for database administation
5. Lombok for reducing boiler plates codes

To use this application, a user needs to be registered using the user registration endpoint, then sign in to recieve a jwt token which is then placed in the authorization header when atempting to access web resources

##  Spring Oauth2 resource server
The  Spring Oauth2 resource server dependency is part of the spring implementation of the Oauth2 specification others are Spring Oauth2 Authorization Server and Outh2 Resource client

To use a jwt mechanism for authentication in spring security, you will have to include the jwt auuthentication filter in the filterchain. 
JWT consists of three parts. Each is base64Url-encoded and separated from the next by a dot.

A JWT can be encrypted using either a symmetric key (shared secret) or asymmetric keys (the private key of a private-public pair).

a.)Symmetric key: The same key is used for both encryption (when the JWT is created) and decryption (MobileTogether Server uses the key to verify the JWT). 
The symmetric key—also known as the shared secret—is stored as a setting in MobileTogether Server.

b.) Asymmetric keys: Different keys are used for encryption (private key) and decryption (public key). The public key is stored as a setting in MobileTogether Server so that 
the JWT can be verified.

## 
In this application, I have created a customized authentication(Log in) mechanism which uses a username and pasword to authenticate and returns a jwt token.
Since I have not enabled basic authentication, the basic authentication filter is disabled which means i will have to created an authentication object explicitly.
Authentication is handled by the Authentication Manager which uses a provider manger to authenticate by calling the authentication method. The authentocation manager, Authentication provider were exposed as a bean so that it can be in the context.

## What is not included
Both unit and integration tests are not inculded in this repo at thus moment.

Finally, this is an exploratory project and part of my journey of learning what behind the scene in spring security.
