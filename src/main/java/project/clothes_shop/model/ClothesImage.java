package project.clothes_shop.model;


import javax.persistence.*;

@Entity
@Table
public class ClothesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
}
