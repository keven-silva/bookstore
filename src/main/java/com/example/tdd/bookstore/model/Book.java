package com.example.tdd.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.tdd.bookstore.model.enums.BookStatusEnum;
import com.example.tdd.bookstore.controller.dto.BookRequestDTO;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "book_author")
    private String author;

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatusEnum status;

    @ManyToOne(optional = true)
    @JoinColumn(name = "person_id", nullable = true)
    private Person person;

    public Book(BookRequestDTO bookCreateDTO) {
        this.title = bookCreateDTO.title();
        this.author = bookCreateDTO.author();
        this.status = BookStatusEnum.BORROWED;
    }
}
