package kz.ibrahim.SoftwareProject.util;

import io.micrometer.common.lang.NonNullApi;
import kz.ibrahim.SoftwareProject.models.Admin;
import kz.ibrahim.SoftwareProject.services.AdminService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@NonNullApi
@Component
public class AdminValidator implements Validator {


    private final AdminService adminService;

    public AdminValidator(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Admin.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Admin admin = (Admin) target;
        Optional<Admin> foundAdmin = adminService.findByUsernameAndPassword(admin.getUsername(), admin.getName());
        if (foundAdmin.isEmpty()) {
            errors.rejectValue("password", "there is no such admin, or password is incorrect");
        }
    }
}
