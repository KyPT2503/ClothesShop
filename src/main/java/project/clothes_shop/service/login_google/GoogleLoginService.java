package project.clothes_shop.service.login_google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.AppUser;
import project.clothes_shop.model.Cart;
import project.clothes_shop.service.cart.ICartService;
import project.clothes_shop.service.role.IAppRoleService;
import project.clothes_shop.service.user.IAppUserService;

import java.io.IOException;

@Service
public class GoogleLoginService implements IGoogleLoginService {
    @Autowired
    private Environment environment;
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private IAppRoleService roleService;
    @Autowired
    private ICartService cartService;

    @Override
    public String getToken(String code) throws IOException {
        String response = Request.Post("https://accounts.google.com/o/oauth2/token")
                .bodyForm(Form.form()
                        .add("client_id", environment.getProperty("clientId"))
                        .add("client_secret", environment.getProperty("clientSecret"))
                        .add("code", code)
                        .add("redirect_uri", "http://localhost:8080/login-google")
                        .add("grant_type", "authorization_code").build())
                .execute().returnContent().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(response).get("access_token");
        System.out.println(node.textValue());
        return node.textValue();
    }

    @Override
    public String getData(String token) throws IOException {
        return Request.Get("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token).execute().returnContent().asString();
    }

    @Override
    public void loginWithData(String data) throws IOException {
        System.out.println(data);
        ObjectMapper mapper = new ObjectMapper();
        String id = mapper.readTree(data).get("id").textValue();
        String email = mapper.readTree(data).get("email").textValue();
        String name = mapper.readTree(data).get("family_name").textValue() + " " + mapper.readTree(data).get("given_name").textValue();
        // if account with this gmail is not already exist, create an account
        if (appUserService.loadUserByUsername(email) == null) {
            // create new user
            AppUser appUser = new AppUser();
            appUser.setEmail(email);
            appUser.setName(name);
            appUser.setPassword(id);
            appUser.setRole(roleService.findById(1L));
            appUser.setAddress("Việt Nam");
            appUser.setPhoneNumber("0000000000");
            appUser = appUserService.add(appUser);
            // create cart for user
            Cart cart = new Cart();
            cart.setAppUser(appUser);
            cartService.add(cart);
        }
        // if account with this gmail is exist: get email, password and send request to /login
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(appUserService.loadUserByUsername(email), null, appUserService.loadUserByUsername(email).getAuthorities()));
    }
}
