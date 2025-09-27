package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import tg.ceel.cj.casierapi.entities.BaseEntity;

@NoRepositoryBean
public interface BaseEntityRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {
}