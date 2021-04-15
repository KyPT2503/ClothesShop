package project.clothes_shop.service.clothes_detail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.dto.ClothesSearchDTO;
import project.clothes_shop.model.Brand;
import project.clothes_shop.model.Category;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
import project.clothes_shop.repo.ClothesDetailRepo;
import project.clothes_shop.service.clothes_image.ClothesImageService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClothesDetailService implements IClothesDetailService {
    @Autowired
    private ClothesDetailRepo clothesDetailRepo;
    @Autowired
    private ClothesImageService clothesImageService;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClothesDetail> findAll() {
        return null;
    }

    @Override
    public ClothesDetail add(ClothesDetail clothesDetail) {
        return clothesDetailRepo.save(clothesDetail);
    }

    @Override
    public boolean remove(ClothesDetail clothesDetail) {
        return false;
    }

    @Override
    public ClothesDetail update(ClothesDetail clothesDetail) {
        return null;
    }

    @Override
    public ClothesDetail findById(Long id) {
        ClothesDetail clothesDetail = clothesDetailRepo.getOne(id);
        List<ClothesImage> clothesImages = clothesImageService.findByClothesDetail(clothesDetail);
        List<String> sources = new ArrayList<>();
        for (ClothesImage clothesImage : clothesImages) {
            sources.add(clothesImage.getSource());
        }
        clothesDetail.setSources(sources);
        return clothesDetail;
    }

    @Override
    public boolean isExist(ClothesDetail clothesDetail) {
        return clothesDetailRepo.existsById(clothesDetail.getId());
    }

    @Override
    public List<ClothesDetail> searchDTO(ClothesSearchDTO clothesSearchDTO) {
        StringBuilder query = new StringBuilder();
        query.append("from ClothesDetail where 1=1 ");
        if (clothesSearchDTO.getName() != null) {
            query.append("and name like '%" + clothesSearchDTO.getName().trim() + "%' ");
        }
        if (clothesSearchDTO.getCategoryId() != null) {
            query.append("and category_id=" + clothesSearchDTO.getCategoryId() + " ");
        }
        if (clothesSearchDTO.getBrandId() != null) {
            query.append("and brand_id=" + clothesSearchDTO.getBrandId() + " ");
        }
        if (clothesSearchDTO.getColorId() != null) {
            query.append("and color_id=" + clothesSearchDTO.getColorId() + " ");
        }
        if (clothesSearchDTO.getSizeId() != null) {
            query.append("and size_id=" + clothesSearchDTO.getSizeId() + " ");
        }
        if (clothesSearchDTO.getPriceTo() > 0) {
            query.append("and price>=" + clothesSearchDTO.getPriceFrom() + " and price<=" + clothesSearchDTO.getPriceTo() + " ");
        }
        return entityManager.createQuery(query.toString()).getResultList();
    }

    @Override
    public void upViewCount(ClothesDetail clothesDetail) {
        clothesDetail.setViewCount(clothesDetail.getViewCount() + 1);
        clothesDetailRepo.save(clothesDetail);
    }

    @Override
    public void updateSoldAmountAndQuantity(ClothesDetail clothesDetail, int soldAmount, int quantity) {
        clothesDetail.setQuantity(quantity);
        clothesDetail.setSoldAmount(soldAmount);
        clothesDetailRepo.save(clothesDetail);
    }

    @Override
    public List<ClothesDetail> findByName(String name) {
        return clothesDetailRepo.findAllByNameContaining(name);
    }

    @Override
    public List<ClothesDetail> findByBrand(Brand brand) {
        return clothesDetailRepo.findAllByBrand(brand);
    }

    @Override
    public List<ClothesDetail> findByCategory(Category category) {
        return clothesDetailRepo.findAllByCategory(category);
    }

    @Override
    public List<ClothesDetail> findTop5BySoldAmount(int soldAmount) {
        return clothesDetailRepo.findTop5ByOrderBySoldAmountDesc();
    }

    @Override
    public List<ClothesDetail> findTop5ByViewCount(int viewCount) {
        return clothesDetailRepo.findTop5ByOrderByViewCountDesc();
    }
}
