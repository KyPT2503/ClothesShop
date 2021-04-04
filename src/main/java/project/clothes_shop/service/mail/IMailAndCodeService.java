package project.clothes_shop.service.mail;

import project.clothes_shop.model.MailAndCode;
import project.clothes_shop.service.IGeneralService;

public interface IMailAndCodeService extends IGeneralService<MailAndCode> {
    boolean isValidEmail(String email, String code);
}
