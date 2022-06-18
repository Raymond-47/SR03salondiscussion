package fr.utc.salondiscussion.dao;

import fr.utc.salondiscussion.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByMail(String mail);

}
