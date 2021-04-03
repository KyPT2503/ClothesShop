package project.clothes_shop.model;

import javax.persistence.*;

@Entity
@Table
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    @OneToOne
    private ClothesDetail detail;
}
