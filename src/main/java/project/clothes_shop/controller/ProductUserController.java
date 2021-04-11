package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.clothes_shop.dto.ClothesSearchDTO;
import project.clothes_shop.model.*;
import project.clothes_shop.service.brand.IBrandService;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.category.ICategoryService;
import project.clothes_shop.service.clothes.IClothesService;
import project.clothes_shop.service.clothes_detail.IClothesDetailService;
import project.clothes_shop.service.color.IColorService;
import project.clothes_shop.service.size.ISizeService;
import project.clothes_shop.service.user.IAppUserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductUserController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IClothesService clothesService;
    @Autowired
    private IClothesDetailService clothesDetailService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private IBrandService brandService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICartService cartService;

    @ModelAttribute("current_cart")
    public Cart getCurrentCart(HttpSession session) {
        return cartService.getCurrentCart(session);
    }

    @ModelAttribute("user")
    public AppUser getCurrentUser() {
        return appUserService.getCurrentUser();
    }

    @ModelAttribute("brands")
    public List<Brand> getAllBrand() {
        return brandService.findAll();
    }

    @ModelAttribute("categories")
    public List<Category> getAllCategory() {
        return categoryService.findAll();
    }

    @ModelAttribute("colors")
    public List<Color> getAllColor() {
        return colorService.findAll();
    }

    @ModelAttribute("sizes")
    public List<Size> getAllSize() {
        return sizeService.findAll();
    }

    @PostMapping("/search")
    public List<Clothes> searchClothes(@RequestBody ClothesSearchDTO clothesSearchDTO) {
        return clothesService.fromDetailToClothes(clothesDetailService.searchDTO(clothesSearchDTO));
    }

    @GetMapping("")
    public ModelAndView showProducts(@PageableDefault(size = 9) Pageable pageable) {
        Page<Clothes> clothesPage = clothesService.findPageable(pageable);
        return new ModelAndView("user/products", "clothes", clothesService.findPageable(pageable));
    }

    @GetMapping("/detail/{id}")
    public ModelAndView showProductDetail(@PathVariable("id") Clothes clothes) {
        //update view
        clothesDetailService.upViewCount(clothes.getClothesDetail());
        //
        ModelAndView modelAndView = new ModelAndView("user/detail");
        clothesService.setSourceForClothes(clothes);
        modelAndView.addObject("clothes", clothes);
        return modelAndView;
    }
}
