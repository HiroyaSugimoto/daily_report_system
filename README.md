# ![DailyReportSystem_Titlelogo1](https://user-images.githubusercontent.com/89298806/136953812-524dcf32-f6a2-4e05-b48c-5febbbb04521.png)</br>
This app manages employee information and daily reports.

## ![DailyReportSystem_logo4](https://user-images.githubusercontent.com/89298806/136723873-67b6efde-c576-44dd-9679-b1d74d5183d3.png)</br>
- Java11
- Eclipse 2020-12(4.18.0)
- Maven(3.63)
- Tomcat9
- MySQL(8.0.25)

## ![DailyReportSystem_logo2](https://user-images.githubusercontent.com/89298806/136723870-0e177ba5-2cdc-4724-81a0-af4196dc14b2.png)</br>
### "Employee Management": </br>
- Register, update, and remove employee information.
- Duplicate employee code cannot be registered.  </br>
Note: This feature can only be used by employees whose authority is "admin".</br>
![DRS_illustration1](https://user-images.githubusercontent.com/89298806/136745530-8b71d7ca-6dd0-4a80-a9f9-797231458c33.png)</br>

### "Daily Report Management": </br>
- Daily report registration and update.
- The registered daily report can be viewed by everyone regardless of authority.</br>
  but, edit and update can only that report author. </br>
![DRS_illustration2](https://user-images.githubusercontent.com/89298806/136745532-cd6b5d27-c499-4011-aaf1-b1e473481275.png)</br>

### "Follow": </br>
- Employees can "Follow" each other.
- You can list the reports written by "Follow" employees.

## ![DailyReportSystem_logo6](https://user-images.githubusercontent.com/89298806/136979198-961804fb-2595-42ed-8e04-c6645bfa097a.png) </br>
Use Maven, Tomcat and MySQL. 
![DRS_illustration3](https://user-images.githubusercontent.com/89298806/136979207-accdeda7-1704-41be-bd5c-249c66afe578.png) </br>

## ![DailyReportSystem_logo5](https://user-images.githubusercontent.com/89298806/136747621-da69c83f-fd39-4ffb-92e6-28cde8af2f10.png) </br>
### "Security" </br>
- Concatenate the pepper string to the entered password and hash it with "SHA256". </br>
- The pepper string is defined in the "application.properties" file. </br>
Note: The above file has been added to ".gitignore" and is not subject to Git management.

### "Login": </br>
- The object of employee information is saved in the session scope at login. </br>
- Get information from the session to determine which employee is login. </br>

### "Admin Privileges": </br>
- To use each action of the "EmployeeAction" class, an employee with "admin" permission must be registered in the DB. </br>
![DRS_illustration4](https://user-images.githubusercontent.com/89298806/136979211-7c33eb89-cf79-49f6-ae6d-26c8c1c180fb.png) </br>

