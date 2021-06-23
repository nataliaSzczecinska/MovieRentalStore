# MovieRentalStore
The REST API which based on SpringBoot, Java, Lombok, Hibernate, JUnit and SQL database.

## Table of contents
* [General Information](##General Information)
* [Screenshots](##Screenshots)
* [External Api](##External Api)
* [Technologies](##Technologies)
* [Setup](##Setup)

## General Information
The Movie Rental Store is simple api where there is a possibility to borrow copy of the movie. There are to type of media: DVD or Blu-Ray. This Api has the capabilities to:
Movies:
* get all movies
* get the movie by id
* get movies by a title fragment, director fragment, description fragment, type or year
* create new movie
* update the movie
Copies:
* get all copies
* get the copy by id
* get copies which is available, DVD, Blu-Ray or combinations of this
* create new copy
* update the copy
* delete the copy
Borrows:
* get all borrows
* get the borrow by id
* get borrows by the movie or the customer
* create new borrow
* change the return date of the borrow
* finalised the borrow
Customers:
* get all customers
* get the customer by id
* create new customer
* update the customer
* delete the customer
Archive Data:
* get all realised borrows
* get all deleted copies
* get all deleted customers
* get finished borrows by movie
* get finished borrows by customer

## Screenshots

### Movie Endpoints:

    * Get all movies (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMovies.png)

    * Get movie by id (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMovieById.png)

    * Get movies by title fragment (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMoviesByTitle.png)

    * Get movies by director fragment (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMoviesByDirector.png)   

    * Get movies by description fragment (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMoviesByDescription.png)   

    * Get movies by type of movie (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMoviesByType.png)   

    * Get movies by year production (http method GET)
![Algorithm](src/main/resources/screen/MovieController/getMoviesByYear.png)  

    * Create movie (http method POST)
    * Update movie (http method PUT)

### Copy endpoints:

    * Get all copies (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getAllCopies.png)

    * Get copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getCopiesByMovieId.png)

    * Get available copies (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getAvailableCopies.png)

    * Get available copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getAvailableCopiesByMoviId.png)   

    * Get DVD copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getDVDCopiesByMovieId.png)   

    * Get available DVD copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getDVDAvailableCopiesByMoviId.png)   

    * Get Blu-Ray copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getBlu-RayCopiesByMovieId.png)  

    * Get available Blu-Ray copies for movie (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getBly-RayAvailableCopiesByMovieId.png)  

    * Get copy by id (http method GET)
![Algorithm](src/main/resources/screen/CopyController/getCopyById.png)  

    * Create copy (http method POST)
    * Update copy (http method PUT)
    * Delete copy (http method DELETE)
    
### Borrow endpoints:

    * Get all borrows (http method GET)
![Algorithm](src/main/resources/screen/BorrowController/getAllBorrows.png)
    
    * Get borrow by id (http method GET)
![Algorithm](src/main/resources/screen/BorrowController/getBorrowById.png)  

    * Get borrows for movie (http method GET)
![Algorithm](src/main/resources/screen/BorrowController/getBorrowsByMovieId.png)

    * Get borrows for customer (http method GET)
![Algorithm](src/main/resources/screen/BorrowController/getBorrowsByCustomerId.png)  

    * Create borrow (http method POST)
    * Change borrow return date (http method PUT)
    * Borrow is finish (http method DELETE)
    
### Customer endpoints:

    * Get all customers (http method GET)
![Algorithm](src/main/resources/screen/CustomerController/getAllCustomers.png)

    * Get customer by id (http method GET)
![Algorithm](src/main/resources/screen/CustomerController/getCustomerById.png)

    * Create customer (http method POST)
    * Update customer (http method PUT)
    * Delete customer (http method DELETE)
    
### Archive endpoints:

    * Get all realised borrows (borrows from archive, http method GET)
![Algorithm](src/main/resources/screen/ArchiveDataController/getArchiveBorrows.png)

    * Get all copies which were deleted (deleted copies, http method GET)
![Algorithm](src/main/resources/screen/ArchiveDataController/getDeleteCopies.png)

    * Get all customers which were deleted (deleted customers, http method GET)
![Algorithm](src/main/resources/screen/ArchiveDataController/getDeleteCustomers.png)

    * Get realised borrows for movie (http method GET)
![Algorithm](src/main/resources/screen/ArchiveDataController/getArchiveBorrowsByMovie.png)   

    * Get realised borrows for customer (http method GET)
![Algorithm](src/main/resources/screen/ArchiveDataController/getArchiveBorrowsByCustomer.png)   

### Endpoints from external api:

    * Get more details for movie (http method GET)
![Algorithm](src/main/resources/screen/ExternalApiController/getMovieDetailsFromOMDbApi.png)

    * Get review from movie (http method GET)
![Algorithm](src/main/resources/screen/ExternalApiController/getMovieReviewFromMovieReviewApi.png)

## External Api
* Open Movie Database (OMDb) Api https://www.omdbapi.com
* Movie Review Api https://developer.nytimes.com/docs/movie-reviews-api/1/overview

## Technologies
* JAVA 11
* Spring
* SpringBoot
* JUnit
* MySQL Database
* H2 Database
* Gradle

## Setup
1. Download code from: https://github.com/nataliaSzczecinska/MovieRentalStore

2. Run a backend application in IntelliJ IDEA or Eclipse
 
## Licence
Freeware
