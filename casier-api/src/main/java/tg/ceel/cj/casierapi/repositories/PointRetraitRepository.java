package tg.ceel.cj.casierapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.cj.casierapi.entities.AutrePointRetrait;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.ints.IPoste;
import tg.ceel.cj.casierapi.ints.PointRetraitInt;

import java.util.List;

public interface PointRetraitRepository extends JpaRepository<PointRetrait, Long> {
    @Query(value = "SELECT a FROM AutrePointRetrait a INNER JOIN a.typeDemandes td WHERE  a.isActive = true AND td.code = ?2 ORDER BY a.libelle ASC")
    List<PointRetrait> getAll(String code);

    @Query(value = "SELECT a FROM AutrePointRetrait a INNER JOIN a.typeDemandes td WHERE a.typeAutrePointRetrait.code = ?1 AND td.code = ?2")
    List<PointRetrait> getAllJuridictions(String typeAutrePointRetrait, String code);

    @Query(value = "select pr.id ,pr.labelle_affichage  as libelle_juridiction,pr.localites_code as juridiction_code ,pr.localites_code as localite,pr.code, pr.libelle_long as libelleLong  from points_retraits pr, types_demandes_points_retraits tdpr    , types_demandes td  where dtype =?1  and td.code =?2 and tdpr.id_point_retrait =pr .id  and tdpr.id_type_demande =td.id  and pr.point_retrait is true and pr.is_active order by pr.id asc", nativeQuery = true)
    List<PointRetraitInt> getAllTribunaux(String dtype, String typeDemande);

    @Query(value = "select j From PointRetrait j  where j.pointRetrait is true and j.isActive=true")
    List<PointRetrait> getAllTribunaux();

    @Query(value = "SELECT a FROM AutrePointRetrait a INNER JOIN a.typeDemandes td WHERE a.typeAutrePointRetrait.code = ?1 ")
    List<PointRetrait> getAllJuridictionByTypeAutrePointRetrait(String typeAutrePointRetrait);

    @Query(value = "select j From Juridiction j  where j.pointRetrait is true")
    List<PointRetrait> getAllTribunaux(String code);

    @Query(value = "SELECT a FROM AutrePointRetrait a")
    List<PointRetrait> getAllPointsRetraits();

    @Query(value = "SELECT * FROM points_retraits WHERE types_autres_points_retraits_code = '1' AND id IN (SELECT id_point_retrait FROM types_demandes_points_retraits WHERE id_type_demande = 3)", nativeQuery = true)
    List<PointRetrait> getAllANCPointRetraits();

    @Query(value = "SELECT a FROM AutrePointRetrait a WHERE a.typeAutrePointRetrait.code = '1'")
    List<PointRetrait> getAllCasierPointRetraits();

    @Query(value = "SELECT a FROM AutrePointRetrait a INNER JOIN a.typeDemandes td WHERE a.typeAutrePointRetrait.code = ?1 AND a.isActive = true AND td.code = ?2 ORDER BY a.libelle ASC")
    List<AutrePointRetrait> getAllBureauxPoste(String typePoint, String typeDemandeCode);

    @Query(value = "select p from AutrePointRetrait  p where p.isActive is true and p.localite=?1 and p.typeAutrePointRetrait.code='3'")
    List<PointRetrait> getAllCPosteByPointRetraitLocalite(String localitecode);

    @Query(value = "select p from AutrePointRetrait  p where p.isActive is true  and p.typeAutrePointRetrait.code='3'")
    List<PointRetrait> getAllCPosteByPointRetraitCentreNational();

    @Query(value = "select pr.id ,pr.libelle_long as libelle_poste,pr.code ,pr.localites_code as juridiction_code from points_retraits pr where code is not null and point_retrait is false or point_retrait is null and localites_code not like '%Poste%'", nativeQuery = true)
    List<IPoste> getAllBureauxIPoste();
}