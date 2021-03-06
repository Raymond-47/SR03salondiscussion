package fr.utc.salondiscussion.controller;

import fr.utc.salondiscussion.security.AdminAuthenticationManager;
import fr.utc.salondiscussion.dao.CanalRepository;
import fr.utc.salondiscussion.dao.UtilisateurCanalRepository;
import fr.utc.salondiscussion.dao.UtilisateurRepository;
import fr.utc.salondiscussion.model.Canal;
import fr.utc.salondiscussion.model.Utilisateur;
import fr.utc.salondiscussion.model.UtilisateurCanal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UtilisateurCanalRepository utilisateurCanalRepository;

    @Autowired
    private CanalRepository canalRepository;

    private static final AdminAuthenticationManager adminAuthenticationManager = new AdminAuthenticationManager();

    @GetMapping("/login")
    private String getLogin(Model model){
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setMail("admin");
        utilisateur.setMotdepasse("admin");
        model.addAttribute("utilisateur",utilisateur);
        //        contrôler sécurité
        try{
            Authentication request = new UsernamePasswordAuthenticationToken("admin","admin");
            Authentication result = adminAuthenticationManager.authenticate(request);
            SecurityContextHolder.getContext().setAuthentication(result);
            System.out.println("Generate Token Successfully "+ SecurityContextHolder.getContext().getAuthentication());
        } catch (AuthenticationException authenticationException){
            System.out.println("Authentication failed: "+ authenticationException.getMessage());
        }
        return "userlogin";
    }



    @GetMapping("/admin/logincheck")
    private ModelAndView postLogin(@ModelAttribute Utilisateur utilisateur){
        System.out.println("user e-mail: "+ utilisateur.getMail()+" | password: "+utilisateur.getMotdepasse()+" | role: "+utilisateur.getRole());
        Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByMail(utilisateur.getMail());



        if (optionalUtilisateur.isPresent()){
            String passwordMD5 = null;
            try {
                BigInteger bigInteger = null;
                String inputStr = utilisateur.getMotdepasse();
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                byte[] inputData = inputStr.getBytes();
                messageDigest.update(inputData);
                bigInteger = new BigInteger(messageDigest.digest());
                passwordMD5 = bigInteger.toString(20);
                System.out.println("new password" + passwordMD5);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            if(optionalUtilisateur.get().getMotdepasse().equals(passwordMD5)){
                if(optionalUtilisateur.get().getRole().equals("admin")){
                    if(utilisateur.getRole().equals("admin")){
                        Utilisateur newUtilisateur = new Utilisateur();
                        System.out.println("admin login");
                        ModelAndView modelAndView = new ModelAndView("adminpage");
                        modelAndView.addObject("newUtilisateur",newUtilisateur);
                        modelAndView.addObject("utilisateurs",utilisateurRepository.findAll());




                        return modelAndView;
                    }else{
                        System.err.println("mode error");
                        ModelAndView modelAndView = new ModelAndView("userlogin");
                        return modelAndView;
                    }
                }else{
                    if (utilisateur.getRole().equals("client")) {
                        System.out.println("user login");
                        ModelAndView modelAndView = new ModelAndView("usercanal");
//                        trouver id de ce utilisateur
                        Integer userId = optionalUtilisateur.get().getId();
//                        trouver liste de id canal de cet utilisateur
                        List<Canal> listCanals = new ArrayList<>();
                        List<UtilisateurCanal> utilisateurCanals = utilisateurCanalRepository.findByUtilisateur(userId);
                        for(UtilisateurCanal utilisateurCanal: utilisateurCanals){
                            Canal canalTemp = canalRepository.findById(utilisateurCanal.getCanal()).get();
                            Date nowDate = new Date();
                            if(canalTemp.getDateExpire().getTime()>nowDate.getTime()){
                                listCanals.add(canalTemp);
                            }
                        }
                        modelAndView.addObject("canals", listCanals);

                        Canal newCanal = new Canal();
                        UtilisateurCanal newUtilisateurCanal = new UtilisateurCanal();
                        Utilisateur utilisateurConnect = utilisateurRepository.findById(userId).get();
                        modelAndView.addObject("newcanal",newCanal);
                        modelAndView.addObject("newutilisateurcanal",newUtilisateurCanal);
                        modelAndView.addObject("utilisateurconnect",utilisateurConnect);
                        return modelAndView;
                    }else {
                        System.err.println("mode error");
                        ModelAndView modelAndView = new ModelAndView("userlogin");
                        return modelAndView;
                    }
                }
            }else {
                System.err.println("motdepasse erreur");
                ModelAndView modelAndView = new ModelAndView("userlogin");
                return modelAndView;
            }
        }else {
            System.err.println("mail erreur");
            ModelAndView modelAndView = new ModelAndView("userlogin");
            return modelAndView;
        }
    }



}
