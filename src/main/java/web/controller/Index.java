package web.controller;

import module.library.api.INameService;
import module.security.api.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    @Autowired
    INameService getNameService;
    @Autowired
    private IValidationService validationService;
    @RequestMapping("/")
    public String index() {
        var IsThisTrue = validationService.validateUserName("Salle");
        if (IsThisTrue) {
            return "hello " + getNameService.getName("Salle");
        }
        else {
            return "hello without name";
        }
    }
}
