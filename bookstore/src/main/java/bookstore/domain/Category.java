package bookstore.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties("category")
    private List<Book> books;

    //create a null object so Spring can create an empty form
        //before the user enters data
    public Category(){
        this.id = null;
        this.name = null;
        this.books = new ArrayList<>();
    }

    public Category(String name){
        this.name = name;
        this.books = new ArrayList<>();
    }

    // getter: returns the field value
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public List<Book> getBooks(){
        return books;
    }

    //setter: updates the field value
    public void setName(String name){
        this.name = name;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }
}