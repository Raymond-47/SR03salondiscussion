package fr.utc.salondiscussion.dao;

import fr.utc.salondiscussion.model.UtilisateurCanal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurCanalRepository extends JpaRepository<UtilisateurCanal,Integer> {
    List<UtilisateurCanal> findByUtilisateur(Integer utilisateur);
    Optional<UtilisateurCanal> findById(Integer id);
}
