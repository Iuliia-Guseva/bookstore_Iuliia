package bookstore.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String author;
    private Integer publicationYear; //Integer = can be null; int = cannot be null.
    private String isbn;
    private BigDecimal price;
    @ManyToOne
    @JsonIgnoreProperties("books") // // a way to avoid infinite loop during JSON serialization/deserialization
    private Category category;

    public Book(String title, String author, int publicationYear, 
        String isbn, BigDecimal price){

        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
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
        this.price = BigDecimal.ZERO;
        this.category = null;
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
    public BigDecimal getPrice(){
        return price;
    }

    public Category getCategory(){
        return category;
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
    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setCategory(Category category){
        this.category = category;
    }
}
