package web.controller;

import module.library.api.INameService;
import module.security.api.IValidationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController

public class Index {

    @Autowired
    INameService getNameService;
    @Autowired
    private IValidationService validationService;
    @PreAuthorize("permitAll()")
    @RequestMapping("/")
    public String index() {
        if (validationService.validateUserName("Salle")) {
            return "hello " + getNameService.getName("Salle");
        }
        else return "hello without a name";
    }
}
