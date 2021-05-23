Readme Contents 

1. About the Project 
2. Features and Functionality
	2.1 Readings 
	2.2 Stations
	2.3 Forms 
	2.4 Members
3. Heroku Deployment
4. Reference List
5. Image Credits
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

1.	About the project 

This project allows users to register for an account and log into a web interface and record readings from a personal weather station device, the WeatherTop 1000. Users can create, read and delete readings from their Weather Top 1000. They can create and update details related to individual WeatherTop 1000 devices [known as stations in the application]. They can also update/edit their user profile on the web interface to change details such as password or email used for log in. 

This project is a web application built on version 1.5.3 of the Play framework which is the final release of the Java based version of the framework. The Play framework adopts the model-view-controller design pattern. The front end aims to present all data stored in an attractive and user friendly format and relies on the fomantic-ui framework for much of the design. 

The initial project commit was cloned from the following github repository: 

https://github.com/wit-hdip-comp-sci-2020/play-template-1

The final project has the following structure: 

├─app			
│ ├──Bootstrap.java		
│ ├──controllers		
│ │ 	├──About.java	
│ │ 	├──Accounts.java	
│ │ 	├──Dashboard.java	
│ │ 	├──Start.java	
│ │ 	└──StationCtrl.java	
│ ├──models		
│ │ 	├──Member.java	
│ │ 	├──Reading.java	
│ │ 	└──Station.java	
│ └──views		
│  	├──about.html	
│  	├──dashboard.html	
│  	├──editprofile.html	
│  	├──errors	
│  	│ 	├──404.html
│  	│ 	└──500.html
│  	├──login.html	
│  	├──main.html	
│  	├──signup.html	
│  	├──start.html	
│  	├──station.html	
│  	└──tags	
│  	 	├──addstation.html
│  	 	├──lateststationreading.html
│  	 	├──listreadings.html
│  	 	├──menu.html
│  	 	└──welcomemenu.html
│ 			
├─ conf			
│ ├──application.conf		
│ ├──data.yml		
│ ├──	dependencies.yml		
│ ├──	messages		
│ └──	routes		
└── public			
     	├──images		
     	│   ├──	anemometer.jpg	
 	│   ├──	castanzo.png	
	│   ├──	esrafili.png	
	│   ├──	favicon.png	
	│   ├──	install.png	
	│   ├──	overview.png	
	│   ├──	register.png	
	│   ├──	weathertopdevice.png	
	│   ├──	weathertoplogo.png	
	│   ├──	which.png	
	│   └──	which2.png	
	└──style.css		

2. Functionality & Features 

2.1.	Readings 

A Reading object type (/app/models/Reading.java) was created to extend the Model class from the Play framework. 
(https://www.playframework.com/documentation/1.5.x/api/play/db/jpa/Model.html) 

This class allows the storage of custom data sets (ln 13-18) relevant to an individual reading from the Weather top application specifically:  a three-digit code of format [0-8]00 representing a weather condition, temperature, wind speed, pressure, and wind direction. 
Extending the model class provides access via inheritance to the superclass methods such as findById() and save() enabling persistence in the PostgreSQL database and the updating and modification of the records. The constructor for this class also records (ln 27-30) the time at which a new reading is added to the database using the java.time.LocalDateTime and java.time.format.DateTimeFormatter classes. The time is stored as a string for compatibility with the values held within the data.yml file loaded on the initial run of the programme. 
The specification provided by the client also required the development of bespoke methods to perform a range of calculations on the data associated with each reading 

A)	Conversion to Fahrenheit [toFahrenheit() lines 31-34] – this involved the basic arithmetic operators to return a double value. 

B)	Conversion of a Wind Speed to the Beaufort Scale and provision of an associated Beaufort label. [convertToBeaufort()- ln 128-183] . For simplicity these values were returned as a HashMap with the scale value as the index and the associated label as the value. A series of if statements was used to identify which set of values put into the HashMap. 

C)	Conversion of the integer weather code into an associated weather label [weatherlabel() – ln 90-121]. As the weather code value was an integer a switch case was used here to identify the correct label String to return. A second function [currentIcon() – ln 184- 216] was used to convert weather code into strings representing fomantic icon classes for display on the front end. 

D)	Conversion of Wind Direction angle to a string classifying the compass direction. [windDirectionLabel() – Ln 35-89]. Again a series of if statements were used to evaluate the angle against the range associated with each compass direction. 

E)	Wind Chill also needed to be calculated [calculateWindChill() – ln 123-124] this required the java.math package to apply exponents to the wind speed and temperature and enable to conversion.


2.2 Stations 
A Station object type was created extending the Play Framework Model Class to permit custom data to be stored about individual Weather Top devices in the database. In this release these are name, latitude and longitude and an ArrayList of type Reading which contains the readings input by the user for that station. There is one to many entity relationship between the Stations and Readings linking each individual station and its readings with the information contained about the reading in the database. 

The Station model also contains bespoke method that enable the calculation of features desired by the client. These are: 

A)	The calculation of Maximum and Minimum values for readings at each station. readingMaxMin() [ln 30-64] relied on the java.util.Collections package to iterate over the values for each reading and return the appropriate value. - following the examples outlined at https://www.geeksforgeeks.org/collections-max-method-in-java-with-examples/

B)	Emerging weather trends. To identify emerging weather trends it was necessary to first obtain the latest three reading using a bespoke method [getLatestThreeReadings() – ln 66-135] The Dashboard and StationCtrl call this method to iterate over the station’s readings to identify the three latest readings sorted based on the reading timestamp. It returns a HashMap of the database IDs of each reading in reverse chronological order. The resultant HashMap is used in the outputTrend() method [ln 148 to 167] to return a string representing a trend if present. 

Reading data is displayed in two front end locations. Both of these are represented related to the station in which they are contained. 

 i. The user’s dashboard, which requires the latest weather information for each of the user's stations to be displayed, and ii. the individual station page which requires the latest weather reading for that station and a list of all reading for that station. 

To present this on the front end a number of views [dashboard.html and station.html] and partial tags [lateststationreading.html and liststations.html] were created displaying the relevant information. The lateststationreading requires two pieces of data to be passed from the respective controller, i.e. a station and a latest reading id. To be able to pass a reading a further method was included in the Station model [findReadingById() - ln 180-184] this returns a single Reading object so that it can be used in the view. 

Play template #{if}{/if} (https://www.playframework.com/documentation/1.5.x/tags#anameififa)  statements are used on dashboard.html and station.html to prevent the application from encountering an error should a station not contain any readings. 

2.3 	HTML forms

HTML forms were built to accept user input on the front end of the site. 
i) to add a new station [addstation.html]
ii) to add a new reading [addreading.html]
iii) to sign up to the website [ signup.html] & edit profile [editprofile.html]
iv) to log into the website. [login.html] 

These forms rely on the following <input> types detailled at https://www.w3schools.com/tags/tag_input.asp: 

i) text ii) number iii) email   and also the <select> type - https://www.w3schools.com/tags/tag_select.asp

User input is validated on the client side at entry based on the native browser functionality and the following attributes. 
i) required - to require the user to complete the field  - https://www.w3schools.com/tags/att_input_required.asp
ii) On number fields the step attribute was used to restrict values entered to the appropriate number of decimal places. -https://www.w3schools.com/tags/att_input_step.asp
iii) max and min attributes were used to restrict an input value to within a set range of values. - https://www.w3schools.com/tags/att_input_max.asp and https://www.w3schools.com/tags/att_input_min.asp

The editprofile.html view also demonstrates how fomantic's form validation can be used as an alternative to HTML5 validation. - https://fomantic-ui.com/behaviors/form.html

2.4	Members
Account functionality was developed by modifying the code available in the following git repository
https://github.com/wit-hdip-comp-sci-2020/playlist/releases/tag/playlist.5.end

The Member object Model [Member.java] provides the structure for each member's record.
Accounts.java acts as controller for the activities related to user creation, editing and sign in. 

To build the additional functionality of editing a user's profile two new  routes and bespoke methods were created /editprofile Accounts.editProfile() and /updateprofile Accounts.updateProfile

An editprofile.html tag was also created. 

editProfile() retrieves the current logged in member and passes this to the editprofile.html view. The values associated with the member are then displayed in the relevant form fields. 

updateProfile() takes in the values sent via POST to /updateprofile and updates the values of the current member accordingly. It then rerenders editprofile with the new information and passes a string to the view notifying the user of the successful update. 



3. Heroku Deployment

To deploy this app first load the following build pack into a new heroku app 
https://github.com/heroku/heroku-buildpack-play 

use git clone https://github.com/StephenSwantonIRL/WeatherTop-beta.git to create your own  copy of the project.
Update the postgres database link in the conf/application.conf file contained in the repo to reflect the link to your own database. 
Push to your Github repo. 
Log into Heroku.com navigate to the new app and choose the deploy tab.
Link you new Github repo your Heroku account and click deploy. 



4. References 
Baeldung - Using Math.pow() - https://www.baeldung.com/java-math-pow
Geeks for Geeks - Collections max() method in Java - https://www.geeksforgeeks.org/collections-max-method-in-java-with-examples/
Fomantic UI Documentation - Card - https://fomantic-ui.com/views/card.html
Fomantic UI Documentation - Form Validation - https://fomantic-ui.com/behaviors/form.html
Fomantic UI Documentation - Icon - https://fomantic-ui.com/elements/icon.html
Fomantic UI Documentation - Image - https://fomantic-ui.com/elements/image.html
Heroku buildpack: Play! - https://github.com/heroku/heroku-buildpack-play 
Play Framework Documentation - JPA.Model API - https://www.playframework.com/documentation/1.5.x/api/play/db/jpa/Model.html
Play framework Documentation - tags - https://www.playframework.com/documentation/1.5.x/tags
Play framework Documentation - template syntax - https://www.playframework.com/documentation/1.5.x/templates#syntax
WIT CompSci2021 - Play project template - https://github.com/wit-hdip-comp-sci-2020/play-template-1
WIT CompSci2021 - Playlist version 5 - https://github.com/wit-hdip-comp-sci-2020/playlist/releases/tag/playlist.5.end
Stackoverflow - Sort Arraylist of Custom Objects by Property - https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property   [Kudos to Adam Gibbons for sharing to slack] 
W3schools -  HTML Input - https://www.w3schools.com/tags/tag_input.asp
W3schools -  HTML Input Max Attribute - https://www.w3schools.com/tags/att_input_max.asp 
W3schools -  HTML Input Min Attribute - https://www.w3schools.com/tags/att_input_min.asp 
W3schools -  HTML Input Required Attribute - https://www.w3schools.com/tags/att_input_required.asp 
W3schools -  HTML Input Step Attribute - https://www.w3schools.com/tags/att_input_step.asp 
W3schools -  HTML Input Required Attribute - https://www.w3schools.com/tags/att_input_required.asp 
W3schools -  HTML Select - https://www.w3schools.com/tags/tag_select.asp

5. Image & Content Credits 

Home page images & About page content - Weather Underground - wunderground.com 
Site Background - Jim Castanzo on Unsplash https://unsplash.com/@jcastanzo