package tg.ceel.cj.casierapi.mappers;

import tg.ceel.cj.casierapi.entities.PaiementActive;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.fnc.Condamnation;
import tg.ceel.cj.casierapi.fnc.models.DemandeFnc;
import tg.ceel.cj.casierapi.models.DemandeAtd;
import tg.ceel.cj.casierapi.models.Demandeur;

public interface EntityMapper {
    Demande demandeDtoToDemande(DemandeDto dto);

    DemandeDto demandeToDemandeDto(Demande demande);
    Demande demandeDtoToDemande(DemandePMDto dto);

    Demande atdDtoToDemandeDto ( AtdDto atdDto);

    PrefectureDto prefectureToPrefectureDto(Prefecture prefecture);

    Prefecture prefectureDtoPrefecture(PrefectureDto dto);

    ProfessionDto professionToProfessionDto(Profession profession);

    Profession professionDtoToProfession(ProfessionDto dto);

    PointRetraitDto pointRetraitToPointRetraitDto(PointRetrait pointRetrait);

    PointRetrait pointRetraitDtoToPointRetrait(PointRetraitDto dto);

    LocaliteDto localiteToLocaliteDto(Localite localite);

    Localite localiteDtoToLocalite(LocaliteDto dto);

    AutrePointRetraitDto autrePointRetraitToAutrePointRetraitDto(AutrePointRetrait autrePointRetrait);

    AutrePointRetrait autrePointRetraitDtoToAutrePointRetrait(AutrePointRetraitDto dto);

    TypePieceDto typePieceToTypePieceDto(TypePiece typePiece);

    TypePiece typePieceDtoToTypePiece(TypePieceDto typePiece);

    TypeDemandeDto typeDemandeToTypeDemandeDto(TypeDemande typeDemande);

    TypeDemande typeDemandeDtoToTypeDemande(TypeDemandeDto dto);

    PaysDto paysToPaysDto(Pays pays);

    Pays paysDtoToPays(PaysDto dto);

    CategorieSocioProfessionnelleDto categorieSocioProfessionnelleToCategorieSocioProfessionnelleDto(CategorieSocioProfessionnelle categorieSocioProfessionnelle);

    CategorieSocioProfessionnelle categorieSocioProfessionnelleDtoToCategorieSocioProfessionnelle(CategorieSocioProfessionnelleDto dto);

    SituationMatrimonialeDto situationMatrimonialeToSituationMatrimonialeDto(SituationMatrimoniale situationMatrimoniale);

    SituationMatrimoniale situationMatrimonialeDtoToSituationMatrimoniale(SituationMatrimonialeDto dto);
    LogDto logToLogDto(Log log);

    Log logDtoToLog(LogDto logDto);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto dto);
    Role roleDtoToRole(RoleDto dto);
    RoleDto roleToRoleDto(Role role);
    FonctionDto fontionToFonctionDto(Fonction fonction);
    Fonction fonctionDtoToFonction(FonctionDto dto);
    PermissionDto permissionToPermissionDto(Permission permission);
    Permission permissionDtoToPermission(PermissionDto dto);
    ServiceDemandeurB2Dto serviceDemendeurB2ToServiceDemendeurB2Dto(ServiceDemandeurB2 serviceDemandeurB2);
    ServiceDemandeurB2 serviceDemandeurB2DtoToServiceDemandeurB2(ServiceDemandeurB2Dto dto);
    PersonneInfo personneInfoDtoTPersonneInfo(PersonneInfoDto dto);
    PersonneInfoDto personneInfoTopersonneInfoDto(PersonneInfo personne);

    SexeDto sexeToSexeDto(Sexe sexe);
    Sexe sexeDtoToSexe(SexeDto dto);

    PermissionCategoryDto permissionCategoryToPermissionCategoryDto(PermissionCategory permissionCategory);
    PermissionCategory permissionCategoryDtoToPermissionCategory(PermissionCategoryDto  dto);

    RolesPermissions rolePermissionDtoToRolePermission(RolePermissionDto dto);
    RolePermissionDto rolePermissionToRolePermissionDto(RolesPermissions rolesPermissions);

    UserB2 userB2DtoToUserB2(UserB2Dto dto);
    UserB2Dto userB2ToUserB2Dto(UserB2 userB2);
    CategorieDemandeurB2 categorieDemandeurB2DtoToCategorieDemandeurB2(CategorieDemandeurB2Dto dto);
    CategorieDemandeurB2Dto categorieDemandeurB2ToCategorieDemandeurB2Dto(CategorieDemandeurB2 categorieDemandeurB2);
    EntiteDemandeurB2Dto entiteDemandeurB2ToEntiteDemandeurB2Dto(EntiteDemandeurB2 entiteDemandeurB2);
    EntiteDemandeurB2 entiteDemandeurB2DtoToEntiteDemandeurB2(EntiteDemandeurB2Dto dto);
    ServiceDemandeurB2Dto serviceDemandeurB2ToServiceDemandeurB2Dto(ServiceDemandeurB2 serviceDemandeurB2);
    ServiceDemandeurB2 ServiceDemandeurB2DtoToServiceDemandeurB2Dto(ServiceDemandeurB2Dto dto);


    UsersRolesDto userRoleToUserRoleDto(UsersRoles usersRoles);
    UsersRoles userRoleDtoToUserRole(UsersRolesDto dto);

    TmoneyNotificationDto tmoneyNotificationToTmoneyNotificationDto(TmoneyNotification tmoneyNotification);
    TmoneyNotification tmoneyNotificationDtoToTmoneyNotification(TmoneyNotificationDto dto);

    CategoriePersonneMoraleDto categoriePersonneMoraleToCategoriePersonneMoraleDto(CategoriePersonneMorale categoriePersonneMorale);
    CategoriePersonneMorale categoriePersonneMoraleDtoToCategoriePersonneMorale(CategoriePersonneMoraleDto dto);
    FloozTransaction floozTransactionDtoToTFloozTransaction(FloozTransactionDto dto);
    FloozTransactionDto floozTransactionToTFloozTransactionDto(FloozTransaction floozTransaction);

    DemandeFnc demandeToDemandeFNC(Demande demande);

    UtilisateurCasier utilisateurCasierDtoToUtilisateurCasier(UtilisateurCasierDto dto);
    UtilisateurCasierDto utilisateurCasierToUtilisateurCasierDto(UtilisateurCasier utilisateurCasier);
    CondamnationModel condamnationToCondamnationModel(Condamnation condamnation);

    UtilisateurCasierPointRetrait utilisateurCasierPointRetraitDtoToUtilisateurCasierPointRetrait(UtilisateurCasierPointRetraitDto dto);
    UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(UtilisateurCasierPointRetrait utilisateurCasierPointRetrait);


    PaiementActiveDto paiementActiceToPaiementActiveDto(PaiementActive paiementActive);

    PaiementActive paiementActiveDtoToPaiementActive(PaiementActiveDto dto);

    CoursAppelDto coursAppelToCoursAppelDto(CoursAppel coursAppel);
    CoursAppel coursAppelDtoToCoursAppel(CoursAppelDto coursAppelDto);

    VariableDto variableToVariableDto(Variable variable);
    Variable variableDtoToVariable(VariableDto variableDto);

    ModelSMSDto modelSMSToModelSMSDto(ModelSMS modelSMS);
    ModelSMS modelSMSDtoToModelSMS(ModelSMSDto modelSMSDto);

    DemandeAtd demandeToDemandeAdt(Demande demande);
}
