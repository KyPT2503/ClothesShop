package project.clothes_shop.model;

import javax.persistence.*;

@Entity
@Table(name = "clothes")
public class Clothes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean status;
    @OneToOne
    @JoinColumn(name = "clothes_detail_id")
    private ClothesDetail clothesDetail = new ClothesDetail();

    public Clothes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ClothesDetail getClothesDetail() {
        return clothesDetail;
    }

    public void setClothesDetail(ClothesDetail clothesDetail) {
        this.clothesDetail = clothesDetail;
    }
}
