# System Hotel Task-02
(Maven version)

JD2 IT-Academy Task Application - Layered Architecture

This application is the search and reservation of rooms in the hotel according
 to the search terms: dates of stay, number of persons, and others.
 
 The application is implemented using the Servlet and JSP technology. 
 Architecture corresponds Layered Architecture and MVC.
 The interface is internationalized and supports the choice of languages: Russian and English.
 
 The data is stored in the database. Access technology JDBC with pattern DAO.
 

 # Example
 ![scrhotel](https://user-images.githubusercontent.com/42671888/45018239-4490e500-b032-11e8-9821-604c6951c314.JPG)
 ![scrhotel2](https://user-images.githubusercontent.com/42671888/45018267-570b1e80-b032-11e8-8c35-4d254605ebb4.JPG)
  ![scrhotel5](https://user-images.githubusercontent.com/42671888/45018345-8a4dad80-b032-11e8-97c2-178c5bee979f.JPG)
 ![scrhotel3](https://user-images.githubusercontent.com/42671888/45018289-65f1d100-b032-11e8-82cc-95e9227a2485.JPG)
 ![scrhotel4](https://user-images.githubusercontent.com/42671888/45018312-7a35ce00-b032-11e8-9b0d-a8b62b5bd629.JPG)

 
  # Usage
  
 You must edit file **mysql.properties** from directory _SystemHotel/src/main/resources/_
          
   ->>change user key and password key to access Database
  
 Example:
 user=root
 password=1234
 
 Run script file **HotelSystem.sql** from directory _SystemHotel/src/main/resources/_
to roll the database.

  This application has 2 roles :  _Administrator_ and _User_
  to test the administrator role use USERNAME "admin" PASSWORD "1111"
  
  Login under the administrator gives access to the closed administration page 
  where you can :
  1. You have access to information about customers who arrive today or have their departure day. 
  2. ......(You can correct reservation information for all reservation.) 
  3. ......
 
