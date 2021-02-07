## MovieReviewService

### How to run the Application
#### Prerequisites
* JDK 1.8 or later
* Maven 3.5.4 com

#### Starting the application
```
git clone git@github.com:anusha94/MovieReviewService.git
cd MovieReviewService
mvn org.springframework.boot:spring-boot-maven-plugin:run
```

### Use cases covered
* Add a new user (by default has viewer profile) -> throws `UserAlreadyExistsException`
* Add a new movie -> throws `MovieAlreadyExistsException`
* User rates a movie -> throws `UserNotFoundException`, `MovieNotFoundException`, `InvalidRatingException`, `MultipleReviewException`
* After 3 reviews, viewer gets promoted as a critic. Logic follows for promotion to expert and admin
* can get top N movies for a given genre and user profile -> throws `UserProfileNotFoundException`
* get average review score in a particular year of release
* get average review score for a particular movie
* get all users for a given profile -> throws `UserProfileNotFoundException`