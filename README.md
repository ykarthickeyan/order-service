# Order Service Micro service

* Simple microservice which is also a resource server.
* Needs to be used in combination with OAuth server https://github.com/ykarthickeyan/oauth-server
* Spin up the outh server first and change the properties as per the environment.
* Use Outh2 server to generate token first in the steps mentioned below
* Use the JWT token to extract the access token and pass it as a bearer token in the Authorization header to order service APIs
* MySql needs to be also setup and running before started this microservice. 
* @EnableMethod security needs to be provided in the configuration so that we can preauthorize with authorities. Default scope provide is openid and in 'hasAuthorities' must we prefixed with SCOPE_<value>
### Authorization code with PKCE

GET: Generate the authorization code
Use https://developer.pingidentity.com/en/tools/pkce-code-generator.html to generate the code challenge


http://localhost:9000/oauth2/authorize?response_type=code&client_id=test-client-id&scope=openid&redirect_uri=http://spring.io/auth&code_challenge=t2fZtq1uj60sf34xJrF9igPqEGLykaIpUxxlRdS2mKQ&code_challenge_method=S256


POST : Use the code from the above response in the next call. And code_verifier for the above challange

http://localhost:9000/oauth2/token?grant_type=authorization_code&client_id=test-client-id&redirect_uri=http://spring.io/auth&code=l2wvJ72Q9S6x14tDb4Hctn7rFSrGSvLVn1fQydWj8l-xBykZr0ZnzwVRZtMXdeBE1janJ2MvmYZeXP0sssm353SdvLtB6-SurQPZXc0HkC8fHtR702B8BrA9ISjavJIr&code_verifier=YvMw4RDos-TYchNkDl10c7ERDSaeFRdc9-RNawj__Pn21pU3GBrid0RMPfYI3l7PPDWcaaFwkcRscc57zvMqEB4YBlvR4-pzLc9HVD9nQj53QUvFMk0zeLuqsUDU_9U6

