package project.clothes_shop.model;


import javax.persistence.*;

@Entity
@Table(name = "clothes_image")
public class ClothesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;
    @ManyToOne
    private ClothesDetail clothesDetail;

    public ClothesImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ClothesDetail getClothesDetail() {
        return clothesDetail;
    }

    public void setClothesDetail(ClothesDetail clothesImage) {
        this.clothesDetail = clothesImage;
    }
}
