package com.cinfiny.demo.controllers;

import com.cinfiny.demo.data.DesignerRepository;
import com.cinfiny.demo.data.InstallerRepository;
import com.cinfiny.demo.models.Designer;
import com.cinfiny.demo.models.Installer;
import com.cinfiny.demo.models.dto.LoginFormDTO;
import com.cinfiny.demo.models.dto.RegisterFormDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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

        List<String> choices = new ArrayList<>();
        choices.add("designer");
        choices.add("installer");
        model.addAttribute("choices", choices);
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register for Cinphiny");

        return "reg";
    }

    @PostMapping("/reg")
    public String processRegistrationForm(@ModelAttribute @Valid RegisterFormDTO registerFormDTO,
                                          Errors errors, Model model, String choice) {

        if (errors.hasErrors()) {
            List<String> choices = new ArrayList<>();
            choices.add("designer");
            choices.add("installer");
            model.addAttribute("choices", choices);
            model.addAttribute("title", "Register for Cinphiny");
            return "reg";
        }

        Designer existingDesigner =
                designerRepository.findByEmployeeNumber(registerFormDTO.getEmployeeNumber());
        Installer existingInstaller =
                installerRepository.findByEmployeeNumber(registerFormDTO.getEmployeeNumber());

        if (existingDesigner != null && existingInstaller != null) {
            errors.rejectValue("employeeNumber", "employeeNumber.alreadyexists", "That employee number is " +
                    "already registered");
            List<String> choices = new ArrayList<>();
            choices.add("designer");
            choices.add("installer");
            model.addAttribute("choices", choices);
            model.addAttribute("title", "Register for Cinphiny");
            return "reg";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();

        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            List<String> choices = new ArrayList<>();
            choices.add("designer");
            choices.add("installer");
            model.addAttribute("choices", choices);
            model.addAttribute("title", "Register for Cinphiny");
            return "reg";
        }

        if (choice == "designer") {

            Designer newDesigner = new Designer(registerFormDTO.getEmployeeNumber(),
                    registerFormDTO.getEmail(),registerFormDTO.getPassword());

            designerRepository.save(newDesigner);
        } else {

            Installer newInstaller = new Installer(registerFormDTO.getEmployeeNumber(),
                    registerFormDTO.getEmail(), registerFormDTO.getPassword());

            installerRepository.save(newInstaller);
        }

        return "redirect:login";

    }

    @GetMapping("/login")
    public String displayLoginForm(Model model) {

        model.addAttribute(new LoginFormDTO());
        model.addAttribute("title", "Log In");
        return "login";
    }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors,
                                   HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In");
            return "login";
        }

        Designer theDesigner = designerRepository.findByEmployeeNumber(loginFormDTO.getEmployeeNumber());
        Installer theInstaller = installerRepository.findByEmployeeNumber(loginFormDTO.getEmployeeNumber());

        if (theDesigner == null && theInstaller == null) {
            errors.rejectValue("employeeNumber", "employee.invalid", "The given employee number does not " +
                    "exist or have permissions in this application");
            model.addAttribute("title", "Log In");
            return "login";
        }

        String password = loginFormDTO.getPassword();

        if (!theDesigner.isMatchingPassword(password) && !theInstaller.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password!");
            model.addAttribute("title", "log In");
            return "login";
        }

        if (theDesigner != null && theInstaller == null) {
            setDesignerInSession(request.getSession(), theDesigner);
            return "redirect:/designer/index";
        }

        if (theDesigner == null && theInstaller != null) {
            setInstallerInSession(request.getSession(), theInstaller);
        }
         return "redirect:/installer/index";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
