package project.clothes_shop.dto;

public class ClothesSearchDTO {
    private String name;
    private Long categoryId;
    private Long brandId;
    private double priceFrom;
    private double priceTo;
    private Long sizeId;
    private Long colorId;

    public ClothesSearchDTO() {
        this.name = "";
        this.categoryId = null;
        this.brandId = null;
        this.priceFrom = 0;
        this.priceTo = 0;
        this.sizeId = null;
        this.colorId = null;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public double getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(double priceFrom) {
        this.priceFrom = priceFrom;
    }

    public double getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(double priceTo) {
        this.priceTo = priceTo;
    }
}
