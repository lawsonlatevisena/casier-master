package tg.ceel.cj.casierapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tg.ceel.cj.casierapi.dto.DemandeList;
import tg.ceel.cj.casierapi.entities.Demande;
import tg.ceel.cj.casierapi.entities.PointRetrait;
import tg.ceel.cj.casierapi.models.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    @Query(value = "select * from demandes limit 500", nativeQuery = true)
    List<Demande> getListe();

    /*    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande, p.datepayement  ,nom,prenom, record, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation " +
                "from demandes d , payements p  " +
                "where d.id_payement =p.id  " +
                "and p.regler =true  " +
                "and dtype =?1 " +
                "and p.datepayement >=?2 and p.datepayement <=?3 " +
                "and d.id_point_retrait in ?4 " +
                "and signee =?5 and invalidee =?6 " +
                "order by p.datepayement asc ", nativeQuery = true)*/
    @Query(value = "select       " +
            "     *       " +
            "from       " +
            "     (       " +
            "     select       " +
            "          d.id,       " +
            "          dtype as typeDemande ,       " +
            "          d.id_bureau_poste_retrait,       " +
            "          date_demande as dateDemande,       " +
            "          p.datepayement ,       " +
            "          nom,       " +
            "          prenom,   " +
            "          profession as emploi,    " +
            "          record,       " +
            "          date_naissance as dateNaissance ,       " +
            "          telephone,       " +
            "          email ,       " +
            "          nombre_copie as nombreCopie ,       " +
            "          valider ,       " +
            "          etape_1_valider etape_2_valider,       " +
            "          etape_3_valider,       " +
            "          disponible ,       " +
            "          date_validation as dateValidation ,       " +
            "          retirer,       " +
            "          signee ,       " +
            "          couleur ,       " +
            "          tracked,       " +
            "          tracking_notification_success ,       " +
            "          tracking_delivery_success ,       " +
            "          numero_demande as numeroDemande ,       " +
            "          invalidee ,       " +
            "          siege ,       " +
            "          traitee ,       " +
            "          denomination ,       " +
            "          numero_rccm as numeroRccm,       " +
            "          delivery_mode as deliveryMode ,       " +
            "          localite_residence_dirigeant,       " +
            "          titre_dirigeant,       " +
            "          telephone_dirigeant,       " +
            "          nom_complet_dirigeant,       " +
            "          type_personne_morale_id as type_personne_morale_code,       " +
            "          type_personne_morale_id as mobile_demandeur_personne_morale ,       " +
            "          nif ,       " +
            "          concat( concat (d.nom, ' '), d.prenom) as name,       " +
            "          motif_invalidation as motifInvalidation," +
            "          tribunal_destination,  personne_destinataire     " +
            "     from       " +
            "          demandes d ,       " +
            "          payements p       " +
            "     where       " +
            "          d.id_payement = p.id       " +
            "          and p.regler = true       " +
            "          and dtype =?1       " +
            "          and p.datepayement >=?2       " +
            "          and p.datepayement <=?3       " +
            "          and d.id_point_retrait in ?4       " +
            "          and signee =?5       " +
            "          and invalidee =?6  " +
            "          and d.id_bureau_poste_retrait IS NULL     " +
            "     order by       " +
            "          p.datepayement asc ) as rr       " +
            "left join (       " +
            "     select       " +
            "          id as id_point,       " +
            "           p.libelle as \"bureauPosteRetraitLibelle\"        " +
            "     from       " +
            "          points_retraits p) as pr on       " +
            "     rr.id_bureau_poste_retrait = pr.id_point order by rr.dateDemande asc", nativeQuery = true)
    List<DemandeList> findDemandes(String typeDemande, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean signee, Boolean invalidee);

@Query(value = "select       " +
            "     *       " +
            "from       " +
            "     (       " +
            "     select       " +
            "          d.id,       " +
            "          dtype as typeDemande ,       " +
            "          d.id_bureau_poste_retrait,       " +
            "          date_demande as dateDemande,       " +
            "          p.datepayement ,       " +
            "          nom,       " +
            "          prenom, " +
            "          profession as emploi,      " +
            "          record,       " +
            "          date_naissance as dateNaissance ,       " +
            "          telephone,       " +
            "          email ,       " +
            "          nombre_copie as nombreCopie ,       " +
            "          valider ,       " +
            "          etape_1_valider etape_2_valider,       " +
            "          etape_3_valider,       " +
            "          disponible ,       " +
            "          date_validation as dateValidation ,       " +
            "          retirer,       " +
            "          signee ,       " +
            "          couleur ,       " +
            "          tracked,       " +
            "          tracking_notification_success ,       " +
            "          tracking_delivery_success ,       " +
            "          numero_demande as numeroDemande ,       " +
            "          invalidee ,       " +
            "          siege ,       " +
            "          traitee ,       " +
            "          denomination ,       " +
            "          numero_rccm as numeroRccm,       " +
            "          delivery_mode as deliveryMode ,       " +
            "          localite_residence_dirigeant,       " +
            "          titre_dirigeant,       " +
            "          telephone_dirigeant,       " +
            "          nom_complet_dirigeant,       " +
            "          type_personne_morale_id as type_personne_morale_code,       " +
            "          type_personne_morale_id as mobile_demandeur_personne_morale ,       " +
            "          nif ,       " +
            "          concat( concat (d.nom, ' '), d.prenom) as name,       " +
            "          motif_invalidation as motifInvalidation  ," +
             "          tribunal_destination,  personne_destinataire     " +
            "     from       " +
            "          demandes d ,       " +
            "          payements p       " +
            "     where       " +
            "          d.id_payement = p.id       " +
            "          and p.regler = true       " +
            "          and dtype =?1       " +
            "          and p.datepayement >=?2       " +
            "          and p.datepayement <=?3       " +
            "          and d.id_point_retrait in ?4       " +
            "          and signee =?5       " +
            "          and invalidee =?6      " +
            "          and d.id_bureau_poste_retrait IS NOT NULL" +
            "     order by       " +
            "          p.datepayement asc ) as rr       " +
            "left join (       " +
            "     select       " +
            "          id as id_point,       " +
            "          p.libelle as bureauPosteRetraitLibelle       " +
            "     from       " +
            "          points_retraits p) as pr on       " +
            "     rr.id_bureau_poste_retrait = pr.id_point order by rr.dateDemande asc", nativeQuery = true)
    List<DemandeList> findDemandesPoste(String typeDemande, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean signee, Boolean invalidee);


    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande ,record,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode  ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale,nif ,concat( concat (d.nom, ' '), d.prenom) as name ,motif_invalidation as  motifInvalidation  ," +
            "          tribunal_destination,  personne_destinataire     " +
            "            from demandes d    where    " +
            "            dtype =?1  " +
            "            and d.date_demande >=?2 and d.date_demande <=?3  " +
            "            and d.id_point_retrait in ?4  " +
            "            and signee =?5 and invalidee =?6  order by d.date_demande asc", nativeQuery = true)
    List<DemandeList> findDemandesB1OrB2(String typeDemande, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean signee, Boolean invalidee);


    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande, p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation ," +
            "          tribunal_destination,  personne_destinataire     " +
            "from demandes d , payements p  " +
            "where d.id_payement =p.id  " +
            "and p.regler =true  " +
            "and dtype =?1 " +
            "and p.datepayement >=?2 and p.datepayement <=?3 " +
            "and d.id_point_retrait = ?4 " +
            "order by p.datepayement asc ", nativeQuery = true)
    List<DemandeList> findDemandes(String typeDemande, Date datePayementDebut, Date datePayementFin, Long pointRetrait);

    List<Demande> findByTypeDemandeEqualsIgnoreCaseAndPayement_DatePayementGreaterThanEqualAndPayement_DatePayementLessThanEqualAndPayement_ReglerAndPointRetraitInAndSigneeAndInvalideeOrderByIdAsc(String typeDemande, Date datePayement, Date datePayement1, Boolean regler, Collection<PointRetrait> pointRetraits, Boolean signee, Boolean invalidee);

    Demande findByNumeroDemande(String numero);
    Demande findByNumeroDemandeOrRecord(String numero,String record);

    @Query(value = "select d from Demande d  where (d.record=?1 or d.numeroDemande=?1) and d.disponible is true and d.traitee is true", nativeQuery = false)
    Demande loadDemandeTraitee(String numero);
    Demande findByNumeroDemandeAndTraiteeIsTrue(String numero);
    @Query(value = "select * from demandes d  where REPLACE(lower(d.nom) ,' ','') = ?1 and REPLACE(lower(d.prenom) ,' ','')=?2 and date_naissance =?3 and disponible  is true and traitee is true order by d.id limit 1",nativeQuery = true)
    Demande findTopByNomAndPrenomAndDateNaissanceAndTraiteeIsTrue(String nom,String prenom,Date dateNaissance);
    Demande findByRecord(String s);
    Long countByRecord(String record);

    List<Demande> findByValiderAndDisponibleAndTypeDemandeAndPointRetraitIn(Boolean disponible, Boolean valider, String type, List<PointRetrait> pointRetraits);
    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande, p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode  ,nif ,concat( concat (d.nom, ' '), d.prenom) as name ,motif_invalidation as  motifInvalidation ," +
            "          tribunal_destination,  personne_destinataire     " +
            "                       from demandes d , payements p  " +
            "                       where d.id_payement =p.id  " +
            "                       and p.regler =?1  " +
            "                       and dtype =?2 " +
            "                       and d.date_demande  >=?3 and d.date_demande  <=?4" +
            "                       and d.id_point_retrait = ?5 " +
            "                       and disponible  =?6 and invalidee =?7 " +
            "                       and valider =?8 " +
            "                       order by d.date_demande  asc",nativeQuery = true)
    List<DemandeList> findByValiderAndDisponibleAndTypeDemandeAndPointRetrait(Boolean  regle, String type , Date debut, Date fin,Long pointRetraitId,Boolean disponible, Boolean invalidee, Boolean valider);

    List<Demande> findByValiderAndTypeDemandeAndPointRetraitInOrderByIdAsc(Boolean disponible, String type, List<PointRetrait> pointRetraits);

    List<Demande> findByTypeDemandeEqualsAndDisponibleIsAndPayement_DatePayementGreaterThanEqualAndPayement_DatePayementLessThanEqualAndPointRetraitInAndInvalideeOrderByIdAsc(String typeDemande, Boolean disponible, Date datePayementDebut, Date datePayementFin, List<PointRetrait> pointRetraits, Boolean invalidee);

    /* @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande, p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation " +
             "            from demandes d , payements p  " +
             "            where d.id_payement =p.id  " +
             "            and p.regler =true  " +
             "            and dtype =?1 " +
             "            and p.datepayement >=?3 and p.datepayement <=?4" +
             "            and d.id_point_retrait in ?5" +
             "            and disponible  =?2 and invalidee =?6" +
             "            order by p.datepayement asc ", nativeQuery = true)*/
    @Query(value = "select          " +
            "             *          " +
            "            from          " +
            "             (          " +
            "             select d.id,dtype as typeDemande,date_demande as dateDemande, d.id_bureau_poste_retrait ,p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation      ," +
            "          tribunal_destination,  personne_destinataire     " +
            "                       from demandes d , payements p         " +
            "                       where d.id_payement =p.id         " +
            "                       and p.regler =true         " +
            "                       and dtype =?1        " +
            "                       and p.datepayement >=?3 and p.datepayement <=?4        " +
            "                       and d.id_point_retrait in ?5        " +
            "                       and disponible  =?2 and invalidee =?6        " +
            "                       order by p.datepayement asc ) as rr          " +
            "            left join (          " +
            "             select          " +
            "              id as id_point,          " +
            "              p.libelle as \"bureauPosteRetraitLibelle\"          " +
            "             from          " +
            "              points_retraits p) as pr on          " +
            "             rr.id_bureau_poste_retrait = pr.id_point order by rr.dateDemande asc", nativeQuery = true)
    List<DemandeList> findAllByTypeDemande(String typeDemande, Boolean disponible, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean invalidee);
@Query(value = "select          " +
            "             *          " +
            "            from          " +
            "             (          " +
            "             select d.id,dtype as typeDemande,date_demande as dateDemande, d.id_bureau_poste_retrait ,p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation       ," +
        "          tribunal_destination,  personne_destinataire     " +
            "                       from demandes d , payements p         " +
            "                       where d.id_payement =p.id         " +
            "                       and p.regler =true         " +
            "                       and dtype =?1        " +
            "                       and p.datepayement >=?3 and p.datepayement <=?4        " +
            "                       and d.id_point_retrait in ?5        " +
            "                       and disponible  =?2 and invalidee =?6      " +
            "                       and d.id_bureau_poste_retrait is not null  " +
            "                       order by p.datepayement asc ) as rr          " +
            "            left join (          " +
            "             select          " +
            "              id as id_point,          " +
            "              p.libelle as bureauPosteRetraitLibelle          " +
            "             from          " +
            "              points_retraits p) as pr on          " +
            "             rr.id_bureau_poste_retrait = pr.id_point order by rr.dateDemande asc", nativeQuery = true)
    List<DemandeList> findAllByTypeDemandePoste(String typeDemande, Boolean disponible, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean invalidee);

    /* @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande, p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm , delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation " +
             "            from demandes d , payements p  " +
             "            where d.id_payement =p.id  " +
             "            and p.regler =true  " +
             "            and dtype =?1 " +
             "            and p.datepayement >=?2 and p.datepayement <=?3" +
             "            order by p.datepayement asc ", nativeQuery = true)*/
    @Query(value = " select * from (select d.id,dtype as typeDemande, d.id_bureau_poste_retrait,date_demande as dateDemande, p.datepayement  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm , delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation   ," +
            "          tribunal_destination,  personne_destinataire     " +
            "                        from demandes d , payements p      " +
            "                        where d.id_payement =p.id      " +
            "                        and p.regler =true      " +
            "                        and dtype =?1     " +
            "                        and p.datepayement >=?2 and p.datepayement <=?3    " +
            "                        order by p.datepayement asc )  as rr   " +
            "            left  join ( select id as id_point,p.libelle as bureauPosteRetraitLibelle from points_retraits p) as pr  on rr.id_bureau_poste_retrait = pr.id_point order by rr.datepayement asc", nativeQuery = true)
    List<DemandeList> findAllByTypeDemandeB3AndANC(String typeDemande,  Date datePayementDebut, Date datePayementFin);

    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name,motif_invalidation as  motifInvalidation ," +
            "          tribunal_destination,  personne_destinataire     " +
            "            from demandes d " +
            "            where  dtype =?1 " +
            "            and d.date_demande >=?2 and d.date_demande <=?3" +
            "            order by d.date_demande asc ", nativeQuery = true)
    List<DemandeList> findAllByTypeDemandeB1AndB2(String typeDemande,  Date datePayementDebut, Date datePayementFin);



    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer,signee ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm, delivery_mode  as deliveryMode ,localite_residence_dirigeant,titre_dirigeant,telephone_dirigeant,nom_complet_dirigeant,type_personne_morale_id as type_personne_morale_code, type_personne_morale_id as  mobile_demandeur_personne_morale ,nif ,concat( concat (d.nom, ' '), d.prenom) as name ,motif_invalidation as  motifInvalidation ," +
            "          tribunal_destination,  personne_destinataire     " +
            "            from demandes d    " +
            "            where   " +
            "             dtype =?1   " +
            "            and d.date_demande >=?3 and d.date_demande <=?4  " +
            "            and d.id_point_retrait in ?5  " +
            "            and disponible  =?2 and invalidee =?6  " +
            "            order by d.date_demande asc ", nativeQuery = true)
    List<DemandeList> findAllByTypeDemandeB2orB1(String typeDemande, Boolean disponible, Date datePayementDebut, Date datePayementFin, Collection<Long> pointRetraits, Boolean invalidee);


    List<Demande> findByNomEqualsIgnoreCaseAndPrenomEqualsIgnoreCaseOrderByIdAsc(String nom, String prenom);

    List<Demande> findByNomEqualsIgnoreCaseAndPrenomEqualsIgnoreCaseAndDateDemandeGreaterThanEqualAndDateDemandeLessThanEqualOrderByDateDemandeDesc(String nom, String prenom, Date debut, Date fin);

    @Query(value = "SELECT * from demandes d where REPLACE(lower(d.nom) ,' ','') like ?1  and REPLACE(lower(d.prenom) ,' ','') like ?2 and d.date_demande >=?3 and d.date_demande <=?4",nativeQuery = true)
    List<Demande> loadSearcheDemandes(String nom, String prenom, Date debut, Date fin);

    Demande findByNumeroDemandeEquals(String numeroDemande);

    @Query(value = "select  count(d.id) as nombre from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2", nativeQuery = true)
    Long countTotalB3EtCJE(Date debut, Date fin);

    @Query(value = "select count(d.id) from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2", nativeQuery = true)
    Long countTotalB1EtB2(Date debut, Date fin);

    // Récupération du total général des demandes de tout type.
    @Query(value = "select sum (nombre) as total_general from (select  count(d.id) as nombre from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2 " +
            " union " +
            "select count(d.id) as test from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2 ) as total",nativeQuery = true)
    Long countTotalGeneral(Date debut, Date fin);

    @Query(value = "select gl.dtype as type, nombre as total, rejete as rejetee,encours,traitee from (select rr.*,coalesce(encours,0) as encours from (select recu.dtype,nombre,rejete from ( " +
            " select d.dtype , count(d.id) as nombre from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2  group by d.dtype  " +
            " union " +
            " select d.dtype , count(d.id) as nombre from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2   group by d.dtype  " +
            ") as recu " +
            " join ( " +
            " select d.dtype , count(d.id) as rejete from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2 and d.invalidee =true group by d.dtype  " +
            " union " +
            " select d.dtype , count(d.id) as rejete from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2  and d.invalidee =true  group by d.dtype  " +
            ") rejete on recu.dtype=rejete.dtype ) rr " +
            " left join ( " +
            " select d.dtype , count(d.id) as encours from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2 and d.invalidee = false and disponible = false group by d.dtype  " +
            " union " +
            " select d.dtype , count(d.id) as encours from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2  and d.invalidee =false and disponible = false  group by d.dtype " +
            ") encours on rr.dtype=encours.dtype ) gl " +
            " left join ( " +
            "  select d.dtype , count(d.id) as traitee  from demandes d , payements p where  d.id_payement =p.id  and p.regler = true and p.datepayement >=?1  and p.datepayement <=?2 and d.invalidee = false and disponible = true group by d.dtype  " +
            " union " +
            " select d.dtype , count(d.id) as traitee from demandes d where d.dtype in ('B1','B2') and d.date_demande  >=?1  and d.date_demande  <=?2  and d.invalidee =false and disponible = true  group by d.dtype " +
            ") traitee on gl.dtype=traitee.dtype",nativeQuery = true)
    List<DashboardModel> getTableauBord(Date debut, Date fin);
    @Query(value = "SELECT " +
            "    pr.libelle as centre, " +
            "    pr.localites_code as localite,  " +
            "    COUNT(CASE WHEN td.code = 'B1' THEN d.id ELSE NULL END) AS B1, " +
            "    COUNT(CASE WHEN td.code = 'B2' THEN d.id ELSE NULL END) AS B2, " +
            "    COUNT(CASE WHEN td.code = 'B3' THEN d.id ELSE NULL END) AS B3, " +
            "    COUNT(CASE WHEN td.code = 'ANC' THEN d.id ELSE NULL END) AS Anc " +
            "FROM " +
            "    demandes d " +
            "        INNER JOIN " +
            "    types_demandes td ON d.dtype = td.code " +
            "        INNER JOIN " +
            "    points_retraits pr ON d.id_point_retrait = pr.id " +
            "WHERE " +
            "      d.invalidee = FALSE " +
            "  AND d.disponible = TRUE " +
            "  AND d.date_demande >= :dateDebut AND d.date_demande <=:dateFin  " +
            "GROUP BY " +
            "    pr.libelle, " +
            "    pr.localites_code " +
            "ORDER BY pr.libelle DESC",nativeQuery = true)
    List<DemandeStatistiqueByCentreModel> getStatistiqueByCentreFilterByPeriode(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query(value = "select pr.id, libelle  ,  count(d.id) as copies,count(d.id)*500 as montant from points_retraits pr, demandes d, payements p  where point_retrait is true  " +
            "and d.id_point_retrait = pr.id and d.dtype ='B3' and d.date_demande  >=:dateDebut and date_demande  <=:dateFin and p.id = d.id_payement and p.regler is true  " +
            "group by pr.id, libelle order by pr.libelle desc;", nativeQuery = true)
    List<ExtraitB3> getStatistiqueB3Montant(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    @Query(value = "select distinct d.annee_demande from demandes d order by d.annee_demande DESC",nativeQuery = true)
    List<Integer> getAnnee();

    @Query(value = "select d.id,dtype as typeDemande,date_demande as dateDemande  ,nom,prenom, date_naissance as dateNaissance , telephone,email ,nombre_copie as nombreCopie ,valider ,etape_1_valider etape_2_valider,etape_3_valider,disponible ,date_validation as dateValidation ,retirer ,couleur ,tracked,tracking_notification_success ,tracking_delivery_success ,numero_demande as numeroDemande ,invalidee ,siege ,traitee ,denomination ,numero_rccm as numeroRccm ,nif ,concat( concat (d.nom, ' '), d.prenom) as name ,motif_invalidation as  motifInvalidation ," +
            "          tribunal_destination,  personne_destinataire     " +
            "            from demandes d    " +
            "            where   " +
            "             dtype =?1   " +
            "            and d.date_demande >=?2 and d.date_demande <=?3  " +
            "            and d.id_point_retrait = ?4  " +
            "            and disponible  = false and invalidee = false" +
            "            order by d.date_demande asc ", nativeQuery = true)
    List<DemandeList> findDemandesEnCours(String typeDemande, Date dateDemandeDebut, Date datedemandeFin, Long pointRetraitId);
    @Query(value = "SELECT " +
            "    gl.dtype AS type, " +
            "    nombre AS total, " +
            "    rejete AS rejetee, " +
            "    encours, " +
            "    traitee " +
            "FROM ( " +
            "    SELECT " +
            "        rr.*, " +
            "        COALESCE(encours, 0) AS encours " +
            "    FROM ( " +
            "        SELECT " +
            "            recu.dtype, " +
            "            nombre, " +
            "            rejete " +
            "        FROM ( " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS nombre " +
            "            FROM " +
            "                demandes d " +
            "                JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND p.datepayement <= ?2 " +
            "            GROUP BY " +
            "                d.dtype " +
            "            UNION " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS nombre " +
            "            FROM " +
            "                demandes d " +
            "            WHERE " +
            "                d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 " +
            "            GROUP BY " +
            "                d.dtype " +
            "        ) AS recu " +
            "        JOIN ( " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS rejete " +
            "            FROM " +
            "                demandes d " +
            "                JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = true " +
            "            GROUP BY " +
            "                d.dtype " +
            "            UNION " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS rejete " +
            "            FROM " +
            "                demandes d " +
            "            WHERE " +
            "                d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = true " +
            "            GROUP BY " +
            "                d.dtype " +
            "        ) rejete ON recu.dtype = rejete.dtype " +
            "    ) AS rr " +
            "    LEFT JOIN ( " +
            "        SELECT " +
            "            d.dtype, " +
            "            COUNT(d.id) AS encours " +
            "        FROM " +
            "            demandes d " +
            "            JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = false AND disponible = false " +
            "        GROUP BY " +
            "            d.dtype " +
            "        UNION " +
            "        SELECT " +
            "            d.dtype, " +
            "            COUNT(d.id) AS encours " +
            "        FROM " +
            "            demandes d " +
            "        WHERE " +
            "            d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = false AND disponible = false " +
            "        GROUP BY " +
            "            d.dtype " +
            "    ) encours ON rr.dtype = encours.dtype " +
            ") gl " +
            "LEFT JOIN ( " +
            "    SELECT " +
            "        d.dtype, " +
            "        COUNT(d.id) AS traitee " +
            "    FROM " +
            "        demandes d " +
            "        JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = false AND disponible = true " +
            "    GROUP BY " +
            "        d.dtype " +
            "    UNION " +
            "    SELECT " +
            "        d.dtype, " +
            "        COUNT(d.id) AS traitee " +
            "    FROM " +
            "        demandes d " +
            "    WHERE " +
            "        d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = false AND disponible = true " +
            "    GROUP BY " +
            "        d.dtype " +
            ") traitee ON gl.dtype = traitee.dtype; ",nativeQuery = true)
    List<DashboardModel> getCountDemandeByPeriodeAndDelay(Date debut, Date fin,Integer delay);
    @Query(value = "SELECT " +
            "    gl.dtype AS type, " +
            "    nombre AS total, " +
            "    rejete AS rejetee, " +
            "    encours, " +
            "    traitee " +
            "FROM ( " +
            "    SELECT " +
            "        rr.*, " +
            "        COALESCE(encours, 0) AS encours " +
            "    FROM ( " +
            "        SELECT " +
            "            recu.dtype, " +
            "            nombre, " +
            "            rejete " +
            "        FROM ( " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS nombre " +
            "            FROM " +
            "                demandes d " +
            "                JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND p.datepayement <= ?2 AND d.id_point_retrait = ?4  " +
            "            GROUP BY " +
            "                d.dtype " +
            "            UNION " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS nombre " +
            "            FROM " +
            "                demandes d " +
            "            WHERE " +
            "                d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.id_point_retrait = ?4  " +
            "            GROUP BY " +
            "                d.dtype " +
            "        ) AS recu " +
            "        JOIN ( " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS rejete " +
            "            FROM " +
            "                demandes d " +
            "                JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = true AND d.id_point_retrait = ?4  " +
            "            GROUP BY " +
            "                d.dtype " +
            "            UNION " +
            "            SELECT " +
            "                d.dtype, " +
            "                COUNT(d.id) AS rejete " +
            "            FROM " +
            "                demandes d " +
            "            WHERE " +
            "                d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = true AND d.id_point_retrait = ?4  " +
            "            GROUP BY " +
            "                d.dtype " +
            "        ) rejete ON recu.dtype = rejete.dtype " +
            "    ) AS rr " +
            "    LEFT JOIN ( " +
            "        SELECT " +
            "            d.dtype, " +
            "            COUNT(d.id) AS encours " +
            "        FROM " +
            "            demandes d " +
            "            JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = false AND disponible = false AND d.id_point_retrait = ?4  " +
            "        GROUP BY " +
            "            d.dtype " +
            "        UNION " +
            "        SELECT " +
            "            d.dtype, " +
            "            COUNT(d.id) AS encours " +
            "        FROM " +
            "            demandes d " +
            "        WHERE " +
            "            d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = false AND disponible = false AND d.id_point_retrait = ?4  " +
            "        GROUP BY d.dtype " +
            "    ) encours ON rr.dtype = encours.dtype " +
            ") gl " +
            "LEFT JOIN ( " +
            "    SELECT " +
            "        d.dtype, " +
            "        COUNT(d.id) AS traitee " +
            "    FROM " +
            "        demandes d " +
            "        JOIN payements p ON d.id_payement = p.id AND p.regler = true AND p.datepayement >= ?1 AND p.datepayement <= ?2 AND EXTRACT(DAY FROM (?2 - p.datepayement)) > ?3 AND d.invalidee = false AND disponible = true AND d.id_point_retrait = ?4 " +
            "    GROUP BY " +
            "        d.dtype " +
            "    UNION " +
            "    SELECT " +
            "        d.dtype, " +
            "        COUNT(d.id) AS traitee " +
            "    FROM " +
            "        demandes d " +
            "    WHERE " +
            "        d.dtype IN ('B1', 'B2') AND d.date_demande >= ?1 AND d.date_demande <= ?2 AND EXTRACT(DAY FROM (?2 - d.date_demande)) > ?3 AND d.invalidee = false AND disponible = true AND d.id_point_retrait = ?4  " +
            "    GROUP BY " +
            "        d.dtype " +
            ") traitee ON gl.dtype = traitee.dtype; ",nativeQuery = true)
    List<DashboardModel> getCountDemandeByPeriodeAndDelayAndPointRetrait(Date debut, Date fin,Integer delay,Integer pointRetraitId);


    @Query(value = "select " +
            "sum(case  when p.canal_payement = 0 then p.montant else 0 END)as montantFlooz , " +
            "sum(case  when p.canal_payement = 1 then p.montant else 0 END)as montantTmoney, " +
            "sum(case  when p.canal_payement  = 0 then d.nombre_copie  else 0 END)as totalCopieFlooz, " +
            "sum(case  when p.canal_payement  = 1 then d.nombre_copie  else 0 END)as totalCopieTmoney " +
            "from demandes d , payements p where d.id_payement  = p.id   and p.regler = true  and  " +
            "cast (d.date_demande as date ) >= ?1 and cast (d.date_demande as date ) <=?2 and d.id_point_retrait =?3 ", nativeQuery = true)
    ResumePaiement resumePaiement(Date dateDemandeDebut, Date datedemandeFin, Long pointRetraitId);


    @Query(value = "select  d.typeDemande as typeDemande ,d.pointRetrait.libelle as pointRetrait , " +
            "sum(case  when p.canalPayement = 0 then p.montant else 0 END)as montantFlooz," +
            " sum(case  when p.canalPayement = 1 then p.montant else 0 END)as montantTmoney, " +
            "sum(case  when p.canalPayement  = 0 then d.nombreCopie  else 0 END)as nombreCopieFlooz, " +
            "sum(case  when p.canalPayement  = 1 then d.nombreCopie  else 0 END)as nombreCopieTmoney " +
            "from Demande d , Payement p where d.payement.id  = p.id and p.regler = true " +
            " and cast(d.dateDemande as date ) >= ?1 and cast (d.dateDemande as date ) <=?2 group by " +
            "d.typeDemande , d.pointRetrait.libelle order by d.typeDemande asc "
            ,nativeQuery = true)
    List<DetailExtraitDemande> detailExtraitDemande(Date dateDemandeDebut, Date datedemandeFin);
    @Query(value = "select * from demandes where nom_fichier is null", nativeQuery = true)
    List<Demande> chargerLesAnncienneDemande();
    @Query(value = "select count(d.id) from Demande d  where d.numeroDemande=?1")
    Long countByNumeroDemande(String numero);


}