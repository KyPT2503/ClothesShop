package project.clothes_shop.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.clothes_shop.model.MailAndCode;
import project.clothes_shop.repo.MailAndCodeRepo;

import java.util.List;

@Service
public class MailAndCodeService implements IMailAndCodeService {
    @Autowired
    private MailAndCodeRepo mailAndCodeRepo;

    @Override
    public List<MailAndCode> findAll() {
        return null;
    }

    @Override
    public MailAndCode add(MailAndCode mailAndCode) {
        MailAndCode mailAndCodeExist = mailAndCodeRepo.findFirstByEmail(mailAndCode.getEmail());
        if (mailAndCodeExist != null) {
            mailAndCodeRepo.delete(mailAndCodeExist);
        }
        return mailAndCodeRepo.save(mailAndCode);
    }

    @Override
    public boolean remove(MailAndCode mailAndCode) {
        mailAndCodeRepo.delete(mailAndCode);
        return true;
    }

    @Override
    public MailAndCode update(MailAndCode mailAndCode) {
        return null;
    }

    @Override
    public MailAndCode findById(Long id) {
        return null;
    }

    @Override
    public boolean isValidEmail(String email, String code) {
        MailAndCode mailAndCode = mailAndCodeRepo.findFirstByEmail(email);
        if (mailAndCode == null) return false;
        return mailAndCode.getCode().equals(code);
    }
}
