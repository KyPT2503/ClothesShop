package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.model.Clothes;
import project.clothes_shop.model.ClothesDetail;
import project.clothes_shop.model.Color;
import project.clothes_shop.model.Size;
import project.clothes_shop.service.clothes.IClothesService;
import project.clothes_shop.service.color.IColorService;
import project.clothes_shop.service.size.ISizeService;

import java.util.List;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private IClothesService clothesService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorService colorService;

    @ModelAttribute("colors")
    public List<Color> getAllColor() {
        return colorService.findAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getAllSize() {
        return sizeService.findAll();
    }

    @GetMapping("")
    public ModelAndView showProducts() {
        return new ModelAndView("admin/products");
    }

    @PostMapping("/add")
    public Clothes addOrSave(@RequestParam(value = "id") Long id, @RequestParam("name") String name, @RequestParam("code") String code, @RequestParam("price") double price, @RequestParam("description") String description, @RequestParam("quantity") int quantity, @RequestParam("size") Long sizeId, @RequestParam("color") Long colorId/*, @RequestParam(value = "images") List<MultipartFile> imageFiles*/) {
        // set clothes detail
        ClothesDetail clothesDetail = new ClothesDetail();
        clothesDetail.setName(name);
        clothesDetail.setCode(code);
        clothesDetail.setPrice(price);
        clothesDetail.setDescription(description);
        clothesDetail.setQuantity(quantity);
        clothesDetail.setSize(sizeService.findById(sizeId));
        clothesDetail.setColor(colorService.findById(colorId));
        /*clothesDetail.setImageFiles(imageFiles);*/

        // clothes
        Clothes clothes = new Clothes();
        clothes.setId(id);
        clothes.setClothesDetail(clothesDetail);
        return clothesService.add(clothes);
    }
}
