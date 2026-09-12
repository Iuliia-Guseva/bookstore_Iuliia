package bookstore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear; //Integer = can be null; int = cannot be null.
    private String isbn;
    private Double price; //Double = can be null; double = cannot be null.

    public Book(String title, String author, int publicaionYear, 
        String isbn, double price){

        this.title = title;
        this.author = author;
        this.publicationYear = publicaionYear;
        this.isbn = isbn;
        this.price = price;   
    }

    //create a null object so Spring can create an empty form 
        //before the user enters data
    public Book(){
        this.id = null;
        this.title = null;
        this.author = null;
        this.publicationYear = null;
        this.isbn = null;
        this.price = null;
    }

    // getter: returns the field value
    public Long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public Integer getPublicationYear(){
        return publicationYear;
    }
    public String getIsbn(){
        return isbn;
    }
    public Double getPrice(){
        return price;
    }

    //setter: updates the field value
    public void setId(Long id){
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public void setPublicationYear(Integer publicationYear){
        this.publicationYear = publicationYear;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
    public void setPrice(Double price){
        this.price = price;
    }
}
