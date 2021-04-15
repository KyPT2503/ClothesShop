package project.clothes_shop.service.clothes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import project.clothes_shop.model.*;
import project.clothes_shop.repo.ClothesRepo;
import project.clothes_shop.service.clothes_detail.IClothesDetailService;
import project.clothes_shop.service.clothes_image.IClothesImageService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClothesService implements IClothesService {
    @Autowired
    private Environment environment;
    @Autowired
    private ClothesRepo clothesRepo;
    @Autowired
    private IClothesDetailService clothesDetailService;
    @Autowired
    private IClothesImageService clothesImageService;

    @Override
    public List<Clothes> findAll() {
        return (List<Clothes>) clothesRepo.findAll();
    }

    @Override
    @Transactional
    public Clothes add(Clothes clothes) {
        // 1. save clothes_detail, will be turn new (has id)?
        boolean isExist = false;
        if (clothes.getId() != null) {
            isExist = clothesRepo.existsById(clothes.getId());
        }
        ClothesDetail clothesDetail = clothes.getClothesDetail();
        // if is update, set it to exist detail of clothes (sold amount and view count)
        if (isExist) {
            ClothesDetail originClothesDetail = this.findById(clothes.getId()).getClothesDetail();
            clothesDetail.setId(originClothesDetail.getId());
            clothesDetail.setSoldAmount(originClothesDetail.getSoldAmount());
            clothesDetail.setViewCount(originClothesDetail.getViewCount());
            // remove all images source
            clothesImageService.removeByClothesDetail(clothesDetail);
        } else {
            clothesDetail.setSoldAmount(0);
            clothesDetail.setViewCount(0);
        }
        clothesDetailService.add(clothesDetail);

        // 2. set multipartFile source list, save file to user/image directory
        List<String> sources = new ArrayList<>();
        if (clothesDetail.getImageFiles() != null) {
            for (MultipartFile image : clothesDetail.getImageFiles()) {
                String path = image.getOriginalFilename();
                // save image file
                try {
                    FileCopyUtils.copy(image.getBytes(), new File(environment.getProperty("IMAGE_SOURCE") + path));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // add to sources
                sources.add(path);
            }
        }
        // 3. save images by source list
        for (String source : sources) {
            ClothesImage clothesImage = new ClothesImage();
            clothesImage.setClothesDetail(clothesDetail);
            clothesImage.setSource(source);
            clothesImageService.add(clothesImage);
        }
        // 4. save new clothes by clothes detail saved
        clothes.setStatus(true);
        return clothesRepo.save(clothes);
    }

    @Override
    public boolean remove(Clothes clothes) {
        clothes.setStatus(false);
        return true;
    }

    @Override
    public Clothes update(Clothes clothes) {
        return this.add(clothes);
    }

    @Override
    public Clothes findById(Long id) {
        Clothes clothes = clothesRepo.findById(id).get();
        this.setSourcesForClothesDetail(clothes.getClothesDetail());
        return clothes;
    }

    @Override
    public boolean isExist(Clothes clothes) {
        return clothesRepo.existsById(clothes.getId());
    }

    @Override
    public Page<Clothes> findPageable(Pageable pageable) {
        Page<Clothes> clothes = clothesRepo.findAllByStatus(true, pageable);
        this.setAllSourcePageClothes(clothes);
        return clothes;
    }

    @Override
    public void disable(Clothes clothes) {
        clothes.setStatus(false);
        clothesRepo.save(clothes);
    }

    @Override
    public List<Clothes> fromDetailToClothes(List<ClothesDetail> clothesDetails) {
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    private void setSourcesForClothesDetail(ClothesDetail clothesDetail) {
        List<ClothesImage> clothesImages = clothesImageService.findByClothesDetail(clothesDetail);
        List<String> sources = new ArrayList<>();
        for (ClothesImage clothesImage : clothesImages) {
            sources.add(clothesImage.getSource());
        }
        clothesDetail.setSources(sources);
    }

    @Override
    public void setAllSourceListClothes(List<Clothes> clothes) {
        for (Clothes cloth : clothes) {
            this.setSourcesForClothesDetail(cloth.getClothesDetail());
        }
    }

    @Override
    public void setSourceForClothes(Clothes clothes) {
        this.setSourcesForClothesDetail(clothes.getClothesDetail());
    }

    @Override
    public List<Clothes> findByName(String name) {
        List<ClothesDetail> clothesDetails = clothesDetailService.findByName(name);
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    @Override
    public List<Clothes> findByCategory(Category category) {
        List<ClothesDetail> clothesDetails = clothesDetailService.findByCategory(category);
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    @Override
    public List<Clothes> findByBrand(Brand brand) {
        List<ClothesDetail> clothesDetails = clothesDetailService.findByBrand(brand);
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    @Override
    public List<Clothes> findTop5ByViewCount(int viewCount) {
        List<ClothesDetail> clothesDetails = clothesDetailService.findTop5ByViewCount(viewCount);
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    @Override
    public List<Clothes> findTop5BySoldAmount(int soldAmount) {
        List<ClothesDetail> clothesDetails = clothesDetailService.findTop5BySoldAmount(soldAmount);
        List<Clothes> clothes = new ArrayList<>();
        for (ClothesDetail clothesDetail : clothesDetails) {
            clothes.add(clothesRepo.findFirstByClothesDetail(clothesDetail));
        }
        this.setAllSourceListClothes(clothes);
        return clothes;
    }

    private void setAllSourcePageClothes(Page<Clothes> clothes) {
        this.setAllSourceListClothes(clothes.toList());
    }
}
