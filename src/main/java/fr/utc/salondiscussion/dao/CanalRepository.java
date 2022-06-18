package fr.utc.salondiscussion.dao;


import fr.utc.salondiscussion.model.Canal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanalRepository extends JpaRepository<Canal,Integer> {
    Optional<Canal> findById(Integer id);

}
