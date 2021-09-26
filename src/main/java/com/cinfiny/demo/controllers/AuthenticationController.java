package com.cinfiny.demo.controllers;

import com.cinfiny.demo.data.DesignerRepository;
import com.cinfiny.demo.data.InstallerRepository;
import com.cinfiny.demo.models.Designer;
import com.cinfiny.demo.models.Installer;
import com.cinfiny.demo.models.dto.RegisterFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthenticationController {

    InstallerRepository installerRepository;

    DesignerRepository designerRepository;

    private static final String designerSessionKey = "designer";
    private static final String installerSessionKey = "installer";

    public Designer getDesignerFromSession(HttpSession session) {
        Integer designerId = (Integer) session.getAttribute(designerSessionKey);
        if (designerId == null) {
            return null;
        }

        Optional<Designer> designer = designerRepository.findById(designerId);

        if (designer.isEmpty()) {
            return null;
        }

        return designer.get();
    }

    private static void setDesignerInSession(HttpSession session, Designer designer) {
        session.setAttribute(designerSessionKey, designer.getId());
    }

    public Installer getInstallerFromSession(HttpSession session) {
        Integer installerId = (Integer) session.getAttribute(installerSessionKey);
        if (installerId == null) {
            return null;
        }

        Optional<Installer> installer = installerRepository.findById(installerId);

        if (installer.isEmpty()) {
            return null;
        }

        return installer.get();
    }

    private static void setInstallerInSession(HttpSession session, Installer installer) {
        session.setAttribute(installerSessionKey, installer.getId());
    }

    @GetMapping("/reg")
    public String displayRegistrationForm(Model model) {

        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register for Cinphiny");

        return "reg";
    }

    @PostMapping("/reg")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, Model model) {

        if(errors.hasErrors()) {
            model.addAttribute("title", "Register for Cinphiny");
            return "reg";
        }


    }
}
