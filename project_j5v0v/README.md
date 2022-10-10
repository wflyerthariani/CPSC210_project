# My Personal Project - Library Loan Tracker

## Proposal

The goal for this application is to design a means for people to keep track of the loans they have taken from different libraries. The loans can have multiple items as you can oftentimes borrow multiple items at a time from a library. The application is also intended to work for books as well as films because these are also usually available at libraries. The user should be able to use the application to keep track of multiple loans and when they are due to be returned and then they can also view returned loans so as to keep track of what they have already read. The target user is a person that uses libraries frequently as a means to borrow books and needs a way to keep track of the loans. This includes students who would be likely to need to keep track of books borrowed from a school or university library.

I *personally* am interested in this project because it acts as an extension to a project I had worked on earlier to design a book recommendation system using a content-based filtering algorithm for my high school library. While this is very different in that my prior project was a website, the fact that this is an application may allow me to expand my abilities and learn more. Additionally I know a lot of people who have a hard time keeping track of when to return things and thought that this could be very practical in everyday use.

##User stories:
- As a user, I want to be able to add a new loan to a list of loans
- As a user, I want to be able to add an item (book or film) to a loan
- As a user, I want to be able to change the due date for the loan
- As a user, I want to be able to mark the loan as returned
- As a user, I want to be able to see all my loans and their respective return statuses
- As a user, I want to be able to remove an item from a loan
- As a user, I want to be able to see how many days are left till a loan is due or if it is overdue
- As a user, I want to be able to save loans list to file 
- As a user, I want to be able to be able to load my loans list from file

The solution uses code from the following source as a basis for data persistence:

Sean Leary, JSON-java, (2021), GitHub Repository
https://github.com/stleary/JSON-java.git

##Phase 4: Task 2

Tue Mar 29 21:18:48 PDT 2022
Added title to loan taken on 2022-03-03

Tue Mar 29 21:18:48 PDT 2022
Added titre to loan taken on 2022-03-03

Tue Mar 29 21:18:48 PDT 2022
Added book to loan taken on 2022-03-28

##Phase 4: Task 3

The main change I would make if I had more time was that I would make Item an abstract class. This is because Item is
never used as an object however the two classes that inherit from it (Book and Film) are used. I would also make a
better way to return the relevant information from each item as a single method that would be overridden by the
two classes. Currently, the code has to check whether it is a book of a film to determine the information. In order to
make this change I would add a method that returns a dictionary that has the names of the fields i.e. title and author
for books and title, producer and director for films. This would standardise the process of getting the details.

