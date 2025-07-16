package com.library.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
@SQLDelete(sql = "UPDATE books SET deleted = true WHERE id = ?")
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private int totalQuantity;

    private int availableQuantity;

    private String category;

    private Boolean deleted = false;
}
