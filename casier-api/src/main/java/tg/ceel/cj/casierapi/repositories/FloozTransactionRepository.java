package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.FloozTransaction;

import java.util.List;

public interface FloozTransactionRepository extends JpaRepository<FloozTransaction, Long> {
    List<FloozTransaction> findByOpStatusEquals(Integer opStatus);
    FloozTransaction findByDemandeId(Long integer);

}