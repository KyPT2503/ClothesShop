package project.clothes_shop.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ClothesDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private double price;
    private String description;
    private int quantity;
    private int viewCount;
    private int soldAmount;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "clothes_image", joinColumns = {@JoinColumn(name = "clothes_detail_id")}, inverseJoinColumns = {@JoinColumn(name = "clothes_image_id")})
    private List<ClothesImage> images;
    @Transient
    private List<MultipartFile> imageFiles;
}
