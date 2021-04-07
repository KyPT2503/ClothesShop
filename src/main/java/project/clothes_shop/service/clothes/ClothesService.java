package project.clothes_shop.service.clothes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.ClothesImage;
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
        return null;
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
        return clothesRepo.findById(id).get();
    }

    @Override
    public boolean isExist(Clothes clothes) {
        return clothesRepo.existsById(clothes.getId());
    }
}
