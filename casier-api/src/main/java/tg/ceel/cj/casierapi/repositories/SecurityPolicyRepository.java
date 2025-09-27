package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tg.ceel.cj.casierapi.entities.SecurityPolicy;

public interface SecurityPolicyRepository extends JpaRepository<SecurityPolicy, String> {
}