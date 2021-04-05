package project.clothes_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.clothes_shop.model.MailAndCode;
import project.clothes_shop.service.mail.MailAndCodeService;

import java.util.Random;

@RestController
@RequestMapping("/")
public class MailController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailAndCodeService mailAndCodeService;

    @GetMapping("/mail-validate/{email}")
    public void sendMail(@PathVariable("email") String email) {
        //prepare mail to send
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Validate account");
        int code = new Random().nextInt(8999) + 1000;
        mailMessage.setText("Your validation code: " + code);
        javaMailSender.send(mailMessage); // send email

        //prepare to save mail and code to DB, will be to check confirm email
        MailAndCode mailAndCode = new MailAndCode();
        mailAndCode.setEmail(email);
        mailAndCode.setCode(String.valueOf(code));
        mailAndCodeService.add(mailAndCode); //add to DB
    }
}
