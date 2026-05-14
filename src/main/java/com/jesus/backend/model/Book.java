package com.jesus.backend.model;

import com.jesus.backend.model.enums.BookGenre;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "books")
public class Book extends Auditable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", unique = true, nullable = false, length = 13)
    private String isbn;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "author", length = 100)
    private String author;

    @Column(name = "editorial", length = 50)
    private String editorial;

    @Column(name = "synopsis")
    private String synopsis;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(name = "availableCopies")
    private Integer availableCopies = 0;

    @Column(name = "image_url")
    private String imageUrl;

    // Relationships
    @OneToMany(mappedBy = "book")
    private List<LoanItem> loanItems = new ArrayList<>();

    // Constructor
    public Book() {
    }

    public Book(UUID id, String isbn, String title, String author, String editorial, String synopsis, BookGenre genre, Integer availableCopies, String imageUrl, List<LoanItem> loanItems) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.editorial = editorial;
        this.synopsis = synopsis;
        this.genre = genre;
        this.availableCopies = availableCopies;
        this.imageUrl = imageUrl;
        this.loanItems = loanItems;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public BookGenre getGenre() {
        return genre;
    }

    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<LoanItem> getLoanItems() {
        return loanItems;
    }

    public void setLoanItems(List<LoanItem> loanItems) {
        this.loanItems = loanItems;
    }
}
