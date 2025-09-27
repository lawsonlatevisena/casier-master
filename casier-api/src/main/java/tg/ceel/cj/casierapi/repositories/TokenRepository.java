package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByApi(String api);
}