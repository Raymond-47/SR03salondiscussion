package fr.utc.salondiscussion.controller;

import fr.utc.salondiscussion.dao.UtilisateurRepository;
import fr.utc.salondiscussion.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("add")
    private ModelAndView addUtilisateur(@ModelAttribute Utilisateur newUtilisateur){
        ModelAndView modelAndView = new ModelAndView("adminpage");
        utilisateurRepository.save(newUtilisateur);
        modelAndView.addObject("newUtilisateur",newUtilisateur);
        modelAndView.addObject("utilisateurs",utilisateurRepository.findAll());
        return modelAndView;
    }

    @PostMapping("delete")
    private ModelAndView deleteUtilisateur(@ModelAttribute Utilisateur newUtilisateur){
        ModelAndView modelAndView = new ModelAndView("adminpage");
        utilisateurRepository.deleteById(newUtilisateur.getId());
        modelAndView.addObject("newUtilisateur",newUtilisateur);
        modelAndView.addObject("utilisateur",utilisateurRepository.findAll());
        return modelAndView;
    }
}
