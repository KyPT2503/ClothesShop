package project.clothes_shop.model;

import javax.persistence.*;

@Entity
@Table
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
