Some articles
https://spring.io/guides/gs/securing-web     ----original code

NOTE:
This will be the base for the DataCard UI upgrade.



Source
C:\Projects\Java Source\gs-securing-web-datacardui



Project
C:\Projects\EC_2023\SpringSecurityWebMain

login with: user   password

The UserDetailsService bean sets up an in-memory user store with a single user. That user is given a user name of user, a password of password, 
and a role of USER.

***Note
review this class MvcConfig.  It is registering the end points.



I have added anonymous user to this.				.anyRequest().permitAll()


http
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/restricted").authenticated()         //for authenticated
				.requestMatchers("/", "/home").permitAll()				//anonymous pages that do not require authentication
				.anyRequest().permitAll()
				
				
				
				
				
http://localhost:8084/home
http://localhost:8084/schedules
http://localhost:8084/regions			
http://localhost:8084/ports				
				
				
				
---------------------
http://datacard.rccl.com:8080/transmissionTypes
http://datacard.rccl.com:8080/ships		
http://datacard.rccl.com:8080/brands		
http://datacard.rccl.com:8080/schedules
http://datacard.rccl.com:8080/calendar
http://datacard.rccl.com:8080/business_rules
http://datacard.rccl.com:8080/ports
http://datacard.rccl.com:8080/regions
http://datacard.rccl.com:8080/shipClass
http://datacard.rccl.com:8080/pmss



Next Steps:
Figure out what is executed when you go to http://datacard.rccl.com:8080/regions on the existing App.
The only focus on that on the new one.


Working with PMS Controller & Repository only.  
The localhost:8080\pmss works as unauthenticated but it does not show the page format and css.


What does the notification service do?  
