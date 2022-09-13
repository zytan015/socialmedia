# MyPage
MyPage is a social media platform for users to share information and create web content.  

## Technologies used
Java, Spring boot, Angular, MySql, Bootstrap 4

## Backend
### Entities
1.	Users (id, name, username, password, email, role)
2.	Posts (id, views, content (link/video), title, caption) extends Auditable
3.	Roles (id, role name) <br>
-> user (update posts that are created by the user) <br>
-> admin (update and delete posts)

### Relationship
1.	many to many mapping for user and role <br>
•	user can have multiple roles <br>
•	one role consists of many users
2.	one to many mapping for user and post <br>
•	one user can have many posts <br>
•	every unique post belongs to a particular user

### Audit 
1.	Auditable (createdBy, creationDate, lastModifiedBy, lastModifiedDate)

### Services 

1.	User service <br>
  •	Add new user <br>
  •	Get user by username <br>
    &ensp; -	validation for login and sign up <br>
    &ensp; -	username must be unique (email also?) <br>
  •	Update users (done by admin) <br>
  •	Delete users (done by admin)
  
2.	Post service <br>
  •	Create new post <br>
  •	Edit existing post <br>
  •	Delete post by id <br>
  •	Get all posts <br>
  •	Get post by id 
  
3.	Role service <br>
  •	Get role by id

### Controller
1.	User controller <br>
  •	Create new user (POST) <br>
  &ensp;-	Username and email should be unique <br>
  &ensp;-	Passwords encode (maybe) <br>
  •	Login (POST) <br>
  •	Delete user (DELETE) <br>
  •	Display all users (GET, for admin to view)

2.	Post controller 
  •	Create new post (POST) <br>
  •	Display posts (POST) <br>
  •	Update post (GET and PUT) <br>
  •	Delete post (DELETE) <br>

## Frontend
1. Admin login <br>
&ensp;1.1. Admin webpage <br>
&emsp;1.1.1. View all posts <br>
&emsp;&emsp;1.1.1.1. Update posts <br>
&emsp;&emsp; 1.1.1.2. Delete posts <br>
&emsp;1.1.2. View all users <br>
&emsp;&emsp;1.1.2.1. Update username, password <br>
&emsp;&emsp;1.1.2.2. Delete users <br>
      
2. User login <br>
&ensp;2.1. User homepage (10 posts on each page) <br>
&emsp;2.1.1. View all posts <br>
&emsp;&emsp;2.1.1.1 Update posts created by the user <br>
&emsp;&emsp;2.1.1.2 Create new posts <br>
&emsp;&emsp;2.1.1.3 View particular post <br>
