package fr.utc.salondiscussion.controller;

import fr.utc.salondiscussion.dao.CanalRepository;
import fr.utc.salondiscussion.dao.UtilisateurCanalRepository;
import fr.utc.salondiscussion.dao.UtilisateurRepository;
import fr.utc.salondiscussion.model.Canal;
import fr.utc.salondiscussion.model.Utilisateur;
import fr.utc.salondiscussion.model.UtilisateurCanal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("canal")
public class CanalController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private UtilisateurCanalRepository utilisateurCanalRepository;

    @PostMapping("/create/{iduser}")
    private ModelAndView createCanal(@ModelAttribute("newcanal") Canal canal, @PathVariable("iduser") Integer userId){
        Canal addedCanal = canalRepository.save(canal);
        Integer idCanal = addedCanal.getId();
        UtilisateurCanal utilisateurCanalAdd = new UtilisateurCanal();
        utilisateurCanalAdd.setCanal(idCanal);
        utilisateurCanalAdd.setUtilisateur(userId);
        utilisateurCanalRepository.save(utilisateurCanalAdd);

        ModelAndView modelAndView = new ModelAndView("usercanal");
        List<Canal> listCanals = new ArrayList<>();
        List<UtilisateurCanal> utilisateurCanals = utilisateurCanalRepository.findByUtilisateur(userId);
        for(UtilisateurCanal utilisateurCanal: utilisateurCanals){
            listCanals.add(canalRepository.findById(utilisateurCanal.getCanal()).get());
        }
        System.out.println(listCanals);
        modelAndView.addObject("canals", listCanals);

        Canal newCanal = new Canal();
        UtilisateurCanal newUtilisateurCanal = new UtilisateurCanal();
        Utilisateur utilisateurConnect = utilisateurRepository.findById(userId).get();
        modelAndView.addObject("newcanal",newCanal);
        modelAndView.addObject("newutilisateurcanal",newUtilisateurCanal);
        modelAndView.addObject("utilisateurconnect",utilisateurConnect);
        return modelAndView;
    }

    @PostMapping("/addmember/{iduser}")
    private ModelAndView addMember(@ModelAttribute UtilisateurCanal utilisateurCanalAdd, @PathVariable("iduser") Integer userId){
        utilisateurCanalRepository.save(utilisateurCanalAdd);
        ModelAndView modelAndView = new ModelAndView("usercanal");
        List<Canal> listCanals = new ArrayList<>();
        List<UtilisateurCanal> utilisateurCanals = utilisateurCanalRepository.findByUtilisateur(userId);
        for(UtilisateurCanal utilisateurCanal: utilisateurCanals){
            listCanals.add(canalRepository.findById(utilisateurCanal.getCanal()).get());
        }
        System.out.println(listCanals);
        modelAndView.addObject("canals", listCanals);

        Canal newCanal = new Canal();
        UtilisateurCanal newUtilisateurCanal = new UtilisateurCanal();
        Utilisateur utilisateurConnect = utilisateurRepository.findById(userId).get();
        modelAndView.addObject("newcanal",newCanal);
        modelAndView.addObject("newutilisateurcanal",newUtilisateurCanal);
        modelAndView.addObject("utilisateurconnect",utilisateurConnect);
        return modelAndView;
    }

    @PostMapping("/entersalon/{username}")
    private String enterSalon(@ModelAttribute Canal canal,@PathVariable("username") String userName){
        Integer idCanal = canal.getId();
        String URL = "http://localhost:3000/chat/"+idCanal+"/"+userName;
        System.out.println(URL);
        return "redirect:"+URL;
    }

}
