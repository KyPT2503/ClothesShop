package project.clothes_shop.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ISocialLoginService {
    String getToken(String code) throws IOException;

    String getData(String token) throws IOException;

    void loginWithData(String data) throws IOException;
}
