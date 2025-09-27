package tg.ceel.fnc.fnc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tg.ceel.fnc.fnc.entities.Condamnation;

import java.util.Date;
import java.util.List;

public interface CondamnationRepository extends JpaRepository<Condamnation, Long> {
    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " condamnation c ,  " +
            " personne p  " +
            "WHERE  " +
            " c.personne = p.id  " +
            " AND c.estinscriteaucasier =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  ",nativeQuery = true)
    List<Condamnation> findCondamnationsb3E1(String estIncritteB3,String nom, String prenom, String sexe);

    @Query(value = "SELECT    " +
            "             *    " +
            "            FROM    " +
            "             condamnation c ,    " +
            "             personne p    " +
            "            WHERE    " +
            "             c.personne = p.id    " +
            "             AND c.estinscriteaucasier =?1    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.denomination), ' ', '') )= ?2    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.num_identification), ' ', '') )= ?3    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.nif), ' ', '') ) = ?4",nativeQuery = true)

    List<Condamnation> findCondamnationsm3E3(String estIncritteB3,String denomination, String nuomerIdentification, String nif);

    @Query(value = "SELECT    " +
            "             *    " +
            "            FROM    " +
            "             condamnation c ,    " +
            "             personne p    " +
            "            WHERE    " +
            "             c.personne = p.id    " +
            "             AND c.estinscriteaucasier =?1    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.num_identification), ' ', '') )= ?2    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.nif), ' ', '') ) = ?3",nativeQuery = true)
    List<Condamnation> findCondamnationsm3E2(String estIncritteB3, String nuomerIdentification, String nif);

    @Query(value = "SELECT    " +
            "             *    " +
            "            FROM    " +
            "             condamnation c ,    " +
            "             personne p    " +
            "            WHERE    " +
            "             c.personne = p.id    " +
            "             AND c.estinscriteaucasier =?1    " +
            "             AND LOWER( REPLACE(TRIM (BOTH FROM p.num_identification), ' ', '') )= ?2    " ,nativeQuery = true)
    List<Condamnation> findCondamnationsm3E1(String estIncritteB3, String numeroIdentification);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " condamnation c ,  " +
            " personne p  " +
            "WHERE  " +
            " c.personne = p.id  " +
            " AND c.estinscriteaucasier =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND p.datenaissance = ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  ",nativeQuery = true)
    List<Condamnation> findCondamnationsb3E2(String estIncritteB3,String nom, String prenom, String sexe, Date dateNaissance);


    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " condamnation c ,  " +
            " personne p  " +
            "WHERE  " +
            " c.personne = p.id  " +
            " AND c.estinscriteaucasier =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )=?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )=?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) =?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )=?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )=?7  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?8",nativeQuery = true)
    List<Condamnation> findCondamnationsb3E3(String estIncritteB3,String nom, String prenom, String sexe, String nomPere,String prenomPere,String nomMere,String prenomMere);


    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " condamnation c ,  " +
            " personne p  " +
            "WHERE  " +
            " c.personne = p.id  " +
            " AND c.estinscriteaucasier =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND p.datenaissance = ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )= ?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?7  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )= ?8  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?9",nativeQuery = true)
    List<Condamnation> findCondamnationsb3(String estIncritteB3,String nom, String prenom, String sexe, Date dateNaissance,String nomPere,String prenomPere,String nomMere,String prenomMere);


    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND C.est_inscrite_au_casier_b2 =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  ",nativeQuery = true)
    List<Condamnation> findCondamnationsB2E1(String estIncritteB2,String nom, String prenom, String sexe);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND C.est_inscrite_au_casier_b2 =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND p.datenaissance = ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  " ,nativeQuery = true)
    List<Condamnation> findCondamnationsB2E2(String estIncritteB2,String nom, String prenom, String sexe, Date dateNaissance);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND C.est_inscrite_au_casier_b2 =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )= ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )= ?7  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?8",nativeQuery = true)
    List<Condamnation> findCondamnationsB2E3(String estIncritteB2,String nom, String prenom, String sexe, String nomPere,String prenomPere,String nomMere,String prenomMere);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND C.est_inscrite_au_casier_b2 =?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?3  " +
            " AND p.datenaissance = ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )= ?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?7  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )= ?8  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?9",nativeQuery = true)
    List<Condamnation> findCondamnationsB2(String estIncritteB2,String nom, String prenom, String sexe, Date dateNaissance,String nomPere,String prenomPere,String nomMere,String prenomMere);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?3  ",nativeQuery = true)
    List<Condamnation> findCondamnationsB1E1(String nom, String prenom, String sexe);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?2  " +
            " AND p.datenaissance = ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?3  ",nativeQuery = true)
    List<Condamnation> findCondamnationsB1E2(String nom, String prenom, String sexe, Date dateNaissance);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?2  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )= ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )= ?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?7",nativeQuery = true)
    List<Condamnation> findCondamnationsB1E3(String nom, String prenom, String sexe, String nomPere,String prenomPere,String nomMere,String prenomMere);

    @Query(value = "SELECT  " +
            " *  " +
            "FROM  " +
            " CONDAMNATION C ,  " +
            " personne p  " +
            "WHERE  " +
            " C.personne = p.id  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nom), ' ', '') )= ?1  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenom), ' ', '') )= ?2  " +
            " AND p.datenaissance = ?4  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.sexe), ' ', '') ) = ?3  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nompere), ' ', '') )= ?5  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenompere),' ','') )=?6  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.nommere), ' ', '') )= ?7  " +
            " AND LOWER( REPLACE(TRIM (BOTH FROM p.prenommere),' ','') )=?8",nativeQuery = true)
    List<Condamnation> findCondamnationsB1(String nom, String prenom, String sexe, Date dateNaissance,String nomPere,String prenomPere,String nomMere,String prenomMere);

}