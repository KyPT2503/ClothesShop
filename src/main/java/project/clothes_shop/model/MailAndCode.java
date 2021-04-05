package project.clothes_shop.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table
public class MailAndCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email(message = "Email không hợp lệ")
    private String email;
    private String code;

    public MailAndCode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
