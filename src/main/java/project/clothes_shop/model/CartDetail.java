package project.clothes_shop.model;

import javax.persistence.*;

@Entity
@Table
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Clothes clothes;
    private int amount;
}
