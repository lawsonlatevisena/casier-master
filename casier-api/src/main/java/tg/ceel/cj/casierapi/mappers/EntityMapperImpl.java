package tg.ceel.cj.casierapi.mappers;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.dto.*;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.fnc.Condamnation;
import tg.ceel.cj.casierapi.fnc.models.DemandeFnc;
import tg.ceel.cj.casierapi.models.DemandeAtd;

import java.util.Date;

@Service
public class EntityMapperImpl implements EntityMapper {
    private final ModelMapper modelMapper;
    Logger logger = LoggerFactory.getLogger(EntityMapperImpl.class);

    public EntityMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Demande demandeDtoToDemande(DemandeDto dto) {
        if (dto == null) {
            return null;
        }

        return Demande.builder()
                .dateDemande(new Date())
                .autreProfession(dto.getAutreProfession())
                .dateJugement(dto.getDateJugement())
                .dateActeRectifie(dto.getDateActeRectifie())
                .dateDelivranceCarte(dto.getDateDelivranceCarte())
                .dateNaissance(dto.getDateNaissance())
                .dateTranscriptionJugement(dto.getDateTranscriptionJugement())
                .deliveryMode(dto.getDeliveryMode())
                .nif(dto.getNif())
                .nom(dto.getNom())
                .mimeType(dto.getMimeType())
                .extentionFichier(dto.getExtentionFichier())
                .prenom(dto.getPrenom())
                .denomination(dto.getDenomination())
                .email(dto.getEmail())
                .emploi(dto.getEmploi())
                // .bureauPosteRetrait(PointRetrait.builder().id(dto.getBureauPosteRetraitId()).build())
                // .categorieSocioProfessionnelle(CategorieSocioProfessionnelle.builder().id(dto.getCategorieSocioProfessionnelleId()).build())
                .feedbackTaskId(dto.getFeedbackTaskId())
                .lieuNaissance(dto.getLieuNaissance())
                .lieuResidence(dto.getLieuResidence())
                // .localiteNaissance(Localite.builder().id(dto.getLocaliteNaissanceId()).build())
                .nombreCopie(dto.getNombreCopie())
                .nombreEnfant(dto.getNombreEnfant())
                .nomMere(dto.getNomMere())
                .prenomMere(dto.getPrenomMere())
                .nomPere(dto.getNomPere())
                .prenomPere(dto.getPrenomPere())
                .numeroActe(dto.getNumeroActe())
                .numeroActeRectifie(dto.getNumeroActeRectifie())
                .numeroCarte(dto.getNumeroCarte())
                .numeroFeuillet(dto.getNumeroFeuillet())
                .numeroJugement(dto.getNumeroJugement())
                .numeroPasseport(dto.getNumeroPasseport())
                .numeroRccm(dto.getNumeroRccm())
                .numeroRegistre(dto.getNumeroRegistre())
                .numeroTranscriptionJugement(dto.getNumeroTranscriptionJugement())
                .process(dto.getProcess())
                //.prefectureNaissance(Prefecture.builder().id(dto.getPrefectureNaissanceId()).build())
                // .profession(Profession.builder().id(dto.getProfessionId()).build())
                .step(dto.getStep())
                .provenance(Provenance.ATD)
                .record(dto.getRecord())
                .refExistenceLegale(dto.getRefExistenceLegale())
                .tribunalJugement(dto.getTribunalJugement())
                .siege(dto.getSiege())
                //.situationMatrimoniale(SituationMatrimoniale.builder().id(dto.getSituationMatrimonialeId()).build())
                .telephone(dto.getTelephone())
                .typeDemande(dto.getTypeDemande())
                .username(dto.getUsername())
                .build();
    }

    @Override
    public DemandeDto demandeToDemandeDto(Demande demande) {
        if (demande == null) {
            return null;
        }
        DemandeDto dto = modelMapper.map(demande, DemandeDto.class);
        dto.setName(String.format("%s %s ", dto.getNom(), dto.getPrenom()));
        if (demande.getBureauPosteRetrait()!=null){
            dto.setBureauPosteRetraitLibelle(demande.getBureauPosteRetrait().getLibelle());
        }
        return dto;
    }

    @Override
    public Demande demandeDtoToDemande(DemandePMDto dto) {
        if (dto == null) {
            return null;
        }

        return modelMapper.map(dto, Demande.class);
    }

    @Override
    public Demande atdDtoToDemandeDto(AtdDto atdDto) {
        DemandeDto demandeDto = new DemandeDto();
        if (atdDto == null) {
            return null;
        }
        demandeDto.setTypeDemande(atdDto.getType_demande());
        demandeDto.setTypePieceId(atdDto.getId_type_piece_demande());
        demandeDto.setPointRetraitId(atdDto.getId_point_retrait_demande());
        demandeDto.setNombreCopie(atdDto.getNombre_copie_demande());
        demandeDto.setNom(atdDto.getNom_demandeur());
        demandeDto.setPrenom(atdDto.getPrenom_demandeur());
        demandeDto.setDateNaissance(atdDto.getDate_naissance_demandeur());
        demandeDto.setLieuNaissance(atdDto.getLieu_naissance_demandeur());
        demandeDto.setPrefectureNaissanceId(atdDto.getId_prefecture_naissance_demandeur());
        demandeDto.setSexeCode(atdDto.getSexe_demandeur());
        demandeDto.setNombreEnfant(atdDto.getNombre_enfant_demandeur());
        demandeDto.setTelephone(atdDto.getTelephone_demandeur());
        demandeDto.setEmail(atdDto.getEmail_demandeur());
        demandeDto.setProfessionId(atdDto.getProfession_demandeur());
        demandeDto.setLieuNaissance(atdDto.getLieu_naissance_demandeur());
        demandeDto.setSituationMatrimonialeId(atdDto.getId_situation_matrimoniale_demandeur());
        demandeDto.setNomPere(atdDto.getNom_pere_demandeur());
        demandeDto.setPrenomPere(atdDto.getPrenom_pere_demandeur());
        demandeDto.setNomMere(atdDto.getNom_mere_demandeur());
        demandeDto.setPrenomMere(atdDto.getPrenom_mere_demandeur());

        demandeDto.setNumeroActe(atdDto.getNumero_acte_naissance());
        demandeDto.setNumeroFeuillet(atdDto.getNumero_feuillet_naissance());
        demandeDto.setNumeroRegistre(atdDto.getNumero_registre_naissance());
        demandeDto.setAnnee(atdDto.getAnnee_acte_naissance());
        demandeDto.setEtatCivil(atdDto.getEtat_civil_naissance());

        demandeDto.setNumeroCarte(atdDto.getNumero_certificat_nationalite());
        demandeDto.setDateDelivranceCarte(atdDto.getDate_delivrance_nationalite());
        demandeDto.setNumeroActeRectifie(atdDto.getNumero_jugement_rectificatif());

        demandeDto.setTribunalJugement(atdDto.getTribunal_jugement_rectificatif());
        demandeDto.setNumeroActeRectifie(atdDto.getNumero_acte_rectifie());
        demandeDto.setDateActeRectifie(atdDto.getDate_etablissement_acte_rectifie());
        demandeDto.setPaysResidenceCode(atdDto.getPays_residence_demandeur());


        return modelMapper.map(demandeDto, Demande.class);


    }

    @Override
    public PrefectureDto prefectureToPrefectureDto(Prefecture prefecture) {
        if (prefecture == null) {
            return null;
        }
        return modelMapper.map(prefecture, PrefectureDto.class);
    }

    @Override
    public Prefecture prefectureDtoPrefecture(PrefectureDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Prefecture.class);
    }

    @Override
    public ProfessionDto professionToProfessionDto(Profession profession) {
        if (profession == null) {
            return null;
        }
        return modelMapper.map(profession, ProfessionDto.class);
    }

    @Override
    public Profession professionDtoToProfession(ProfessionDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Profession.class);
    }

    @Override
    public PointRetraitDto pointRetraitToPointRetraitDto(PointRetrait pointRetrait) {
        if (pointRetrait == null) {
            return null;
        }
        return modelMapper.map(pointRetrait, PointRetraitDto.class);
    }

    @Override
    public PointRetrait pointRetraitDtoToPointRetrait(PointRetraitDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, PointRetrait.class);
    }


    @Override
    public LocaliteDto localiteToLocaliteDto(Localite localite) {
        if (localite == null) {
            return null;
        }
        return modelMapper.map(localite, LocaliteDto.class);
    }

    @Override
    public Localite localiteDtoToLocalite(LocaliteDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Localite.class);
    }

    @Override
    public AutrePointRetraitDto autrePointRetraitToAutrePointRetraitDto(AutrePointRetrait autrePointRetrait) {
        if (autrePointRetrait == null) {
            return null;
        }
        return modelMapper.map(autrePointRetrait, AutrePointRetraitDto.class);
    }

    @Override
    public AutrePointRetrait autrePointRetraitDtoToAutrePointRetrait(AutrePointRetraitDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, AutrePointRetrait.class);
    }

    @Override
    public TypePieceDto typePieceToTypePieceDto(TypePiece typePiece) {
        if (typePiece == null) {
            return null;
        }
        return modelMapper.map(typePiece, TypePieceDto.class);
    }

    @Override
    public TypePiece typePieceDtoToTypePiece(TypePieceDto typePiece) {
        if (typePiece == null) {
            return null;
        }
        return modelMapper.map(typePiece, TypePiece.class);
    }

    @Override
    public TypeDemandeDto typeDemandeToTypeDemandeDto(TypeDemande typeDemande) {
        if (typeDemande == null) {
            return null;
        }
        return modelMapper.map(typeDemande, TypeDemandeDto.class);
    }

    @Override
    public TypeDemande typeDemandeDtoToTypeDemande(TypeDemandeDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, TypeDemande.class);
    }

    @Override
    public PaysDto paysToPaysDto(Pays pays) {
        if (pays == null) {
            return null;
        }
        return modelMapper.map(pays, PaysDto.class);
    }

    @Override
    public Pays paysDtoToPays(PaysDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Pays.class);
    }

    @Override
    public CategorieSocioProfessionnelleDto categorieSocioProfessionnelleToCategorieSocioProfessionnelleDto(CategorieSocioProfessionnelle categorieSocioProfessionnelle) {
        if (categorieSocioProfessionnelle == null) {
            return null;
        }
        return modelMapper.map(categorieSocioProfessionnelle, CategorieSocioProfessionnelleDto.class);
    }

    @Override
    public CategorieSocioProfessionnelle categorieSocioProfessionnelleDtoToCategorieSocioProfessionnelle(CategorieSocioProfessionnelleDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, CategorieSocioProfessionnelle.class);
    }

    @Override
    public SituationMatrimonialeDto situationMatrimonialeToSituationMatrimonialeDto(SituationMatrimoniale situationMatrimoniale) {
        if (situationMatrimoniale == null) {
            return null;
        }
        return modelMapper.map(situationMatrimoniale, SituationMatrimonialeDto.class);
    }

    @Override
    public SituationMatrimoniale situationMatrimonialeDtoToSituationMatrimoniale(SituationMatrimonialeDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, SituationMatrimoniale.class);
    }

    @Override
    public LogDto logToLogDto(Log log) {
        if (log == null) {
            return null;
        }
        return modelMapper.map(log, LogDto.class);
    }

    @Override
    public Log logDtoToLog(LogDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Log.class);
    }

    @Override
    public UserDto userToUserDto(User user) {
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User userDtoToUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, User.class);
    }

    @Override
    public Role roleDtoToRole(RoleDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Role.class);
    }

    @Override
    public RoleDto roleToRoleDto(Role role) {
        if (role == null) {
            return null;
        }
        return modelMapper.map(role, RoleDto.class);
    }

    @Override
    public FonctionDto fontionToFonctionDto(Fonction fonction) {
        if (fonction == null) {
            return null;
        }
        return modelMapper.map(fonction, FonctionDto.class);
    }

    @Override
    public Fonction fonctionDtoToFonction(FonctionDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Fonction.class);
    }

    @Override
    public PermissionDto permissionToPermissionDto(Permission permission) {
        if (permission == null) {
            return null;
        }
        return modelMapper.map(permission, PermissionDto.class);
    }

    @Override
    public Permission permissionDtoToPermission(PermissionDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Permission.class);
    }

    @Override
    public ServiceDemandeurB2Dto serviceDemendeurB2ToServiceDemendeurB2Dto(ServiceDemandeurB2 serviceDemandeurB2) {
        if (serviceDemandeurB2 == null) {
            return null;
        }
        return modelMapper.map(serviceDemandeurB2, ServiceDemandeurB2Dto.class);
    }

    @Override
    public ServiceDemandeurB2 serviceDemandeurB2DtoToServiceDemandeurB2(ServiceDemandeurB2Dto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, ServiceDemandeurB2.class);
    }

    @Override
    public PersonneInfo personneInfoDtoTPersonneInfo(PersonneInfoDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, PersonneInfo.class);
    }

    @Override
    public PersonneInfoDto personneInfoTopersonneInfoDto(PersonneInfo personne) {
        if (personne == null) {
            return null;
        }
        return modelMapper.map(personne, PersonneInfoDto.class);
    }

    @Override
    public SexeDto sexeToSexeDto(Sexe sexe) {
        if (sexe == null) {
            return null;
        }
        return modelMapper.map(sexe, SexeDto.class);
    }

    @Override
    public Sexe sexeDtoToSexe(SexeDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Sexe.class);
    }

    public PermissionCategoryDto permissionCategoryToPermissionCategoryDto(PermissionCategory permissionCategory) {
        if (permissionCategory == null) {
            return null;
        }
        return modelMapper.map(permissionCategory, PermissionCategoryDto.class);
    }

    @Override
    public PermissionCategory permissionCategoryDtoToPermissionCategory(PermissionCategoryDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, PermissionCategory.class);
    }


    @Override
    public RolesPermissions rolePermissionDtoToRolePermission(RolePermissionDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, RolesPermissions.class);
    }

    @Override
    public RolePermissionDto rolePermissionToRolePermissionDto(RolesPermissions rolesPermissions) {
        if (rolesPermissions == null) {
            return null;
        }
        return modelMapper.map(rolesPermissions, RolePermissionDto.class);
    }

    @Override
    public UserB2 userB2DtoToUserB2(UserB2Dto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, UserB2.class);
    }

    @Override
    public UserB2Dto userB2ToUserB2Dto(UserB2 userB2) {
        if (userB2 == null) {
            return null;
        }
        return modelMapper.map(userB2, UserB2Dto.class);
    }

    @Override
    public CategorieDemandeurB2 categorieDemandeurB2DtoToCategorieDemandeurB2(CategorieDemandeurB2Dto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, CategorieDemandeurB2.class);
    }

    @Override
    public CategorieDemandeurB2Dto categorieDemandeurB2ToCategorieDemandeurB2Dto(CategorieDemandeurB2 categorieDemandeurB2) {
        if (categorieDemandeurB2 == null) {
            return null;
        }
        return modelMapper.map(categorieDemandeurB2, CategorieDemandeurB2Dto.class);
    }

    @Override
    public EntiteDemandeurB2Dto entiteDemandeurB2ToEntiteDemandeurB2Dto(EntiteDemandeurB2 entiteDemandeurB2) {
        if (entiteDemandeurB2 == null) {
            return null;
        }
        return modelMapper.map(entiteDemandeurB2, EntiteDemandeurB2Dto.class);
    }

    @Override
    public EntiteDemandeurB2 entiteDemandeurB2DtoToEntiteDemandeurB2(EntiteDemandeurB2Dto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, EntiteDemandeurB2.class);
    }

    @Override
    public ServiceDemandeurB2Dto serviceDemandeurB2ToServiceDemandeurB2Dto(ServiceDemandeurB2 serviceDemandeurB2) {
        if (serviceDemandeurB2 == null) {
            return null;
        }
        return modelMapper.map(serviceDemandeurB2, ServiceDemandeurB2Dto.class);
    }

    @Override
    public ServiceDemandeurB2 ServiceDemandeurB2DtoToServiceDemandeurB2Dto(ServiceDemandeurB2Dto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, ServiceDemandeurB2.class);
    }

    @Override

    public UsersRolesDto userRoleToUserRoleDto(UsersRoles usersRoles) {
        if (usersRoles == null) {
            return null;
        }
        return modelMapper.map(usersRoles, UsersRolesDto.class);
    }

    @Override
    public UsersRoles userRoleDtoToUserRole(UsersRolesDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, UsersRoles.class);

    }

    @Override
    public TmoneyNotificationDto tmoneyNotificationToTmoneyNotificationDto(TmoneyNotification tmoneyNotification) {
        if (tmoneyNotification == null) {
            return null;
        }
        return modelMapper.map(tmoneyNotification, TmoneyNotificationDto.class);
    }

    @Override
    public TmoneyNotification tmoneyNotificationDtoToTmoneyNotification(TmoneyNotificationDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, TmoneyNotification.class);
    }

    @Override
    public CategoriePersonneMoraleDto categoriePersonneMoraleToCategoriePersonneMoraleDto(CategoriePersonneMorale categoriePersonneMorale) {
        if (categoriePersonneMorale == null) {
            return null;
        }
        return modelMapper.map(categoriePersonneMorale, CategoriePersonneMoraleDto.class);
    }

    @Override
    public CategoriePersonneMorale categoriePersonneMoraleDtoToCategoriePersonneMorale(CategoriePersonneMoraleDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, CategoriePersonneMorale.class);
    }

    @Override
    public FloozTransaction floozTransactionDtoToTFloozTransaction(FloozTransactionDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, FloozTransaction.class);
    }

    @Override
    public FloozTransactionDto floozTransactionToTFloozTransactionDto(FloozTransaction floozTransaction) {
        if (floozTransaction == null) {
            return null;
        }
        return modelMapper.map(floozTransaction, FloozTransactionDto.class);

    }

    @Override
    public UtilisateurCasier utilisateurCasierDtoToUtilisateurCasier(UtilisateurCasierDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, UtilisateurCasier.class);
    }

    @Override
    public UtilisateurCasierDto utilisateurCasierToUtilisateurCasierDto(UtilisateurCasier utilisateurCasier) {
        if (utilisateurCasier == null) {
            return null;
        }
        return modelMapper.map(utilisateurCasier, UtilisateurCasierDto.class);
    }

    @Override
    public UtilisateurCasierPointRetrait utilisateurCasierPointRetraitDtoToUtilisateurCasierPointRetrait(UtilisateurCasierPointRetraitDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, UtilisateurCasierPointRetrait.class);
    }

    @Override
    public UtilisateurCasierPointRetraitDto utilisateurCasierPointRetraitToUtilisateurCasierPointRetraitDto(UtilisateurCasierPointRetrait utilisateurCasierPointRetrait) {
        if (utilisateurCasierPointRetrait == null) {
            return null;
        }
        return modelMapper.map(utilisateurCasierPointRetrait, UtilisateurCasierPointRetraitDto.class);
    }

    @Override
    public PaiementActiveDto paiementActiceToPaiementActiveDto(PaiementActive paiementActive) {
        if (paiementActive == null) {
            return null;
        }
        return modelMapper.map(paiementActive, PaiementActiveDto.class);
    }

    @Override
    public PaiementActive paiementActiveDtoToPaiementActive(PaiementActiveDto dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, PaiementActive.class);
    }

    @Override
    public CoursAppelDto coursAppelToCoursAppelDto(CoursAppel coursAppel) {
        if (coursAppel == null) {
            return null;
        }
        return modelMapper.map(coursAppel, CoursAppelDto.class);
    }

    @Override
    public CoursAppel coursAppelDtoToCoursAppel(CoursAppelDto coursAppelDto) {
        if (coursAppelDto == null) {
            return null;
        }
        return modelMapper.map(coursAppelDto, CoursAppel.class);
    }

    @Override
    public VariableDto variableToVariableDto(Variable variable) {
        if (variable == null) {
            return null;
        }
        return modelMapper.map(variable, VariableDto.class);
    }

    @Override
    public Variable variableDtoToVariable(VariableDto variableDto) {
        if (variableDto == null) {
            return null;
        }
        return modelMapper.map(variableDto, Variable.class);
    }

    @Override
    public ModelSMSDto modelSMSToModelSMSDto(ModelSMS modelSMS) {
        if (modelSMS == null) {
            return null;
        }
        return modelMapper.map(modelSMS, ModelSMSDto.class);
    }

    @Override
    public ModelSMS modelSMSDtoToModelSMS(ModelSMSDto modelSMSDto) {
        if (modelSMSDto == null) {
            return null;
        }
        return modelMapper.map(modelSMSDto, ModelSMS.class);
    }

    @Override
    public DemandeAtd demandeToDemandeAdt(Demande demande) {
        if (demande == null) {
            return null;
        }
        try {
            DemandeAtd demandeAtd = DemandeAtd
                    .builder()
                    .date_delivrance_passeport(demande.getDateDelivranceCarte())
                    .type_demande(demande.getTypeDemande())
                    .profession_demandeur(demande.getEmploi())
                    .id_point_retrait_demande(demande.getPointRetrait().getId().intValue())
                    .etat_civil_naissance(demande.getEtatCivil())
                    .etat_civil_acte_rectifie(demande.getEtatCivilActeRectifie())
                    .date_etablissement_acte_rectifie(demande.getDateActeRectifie())
                    .date_delivrance_nationalite(demande.getDateDelivranceCarte())
                    .date_jugement_rectificatif(demande.getDateJugement())
                    .date_jugement_sup_recons(demande.getDateJugement())
                    .date_naissance_demandeur(demande.getDateNaissance())
                    .date_mention_jugement_rectificatif(demande.getDateMention())
                    .date_transcription_jugement_sup_recons(demande.getDateTranscriptionJugement())
                    .nom_demandeur(demande.getNom())
                    .prenom_demandeur(demande.getPrenom())
                    .nom_pere_demandeur(demande.getNomPere())
                    .prenom_pere_demandeur(demande.getPrenomPere())
                    .nom_mere_demandeur(demande.getNomMere())
                    .prenom_mere_demandeur(demande.getPrenomMere())
                    .email_demandeur(demande.getEmail())
                    .feedbackTaskId(demande.getFeedbackTaskId())
                    .lieu_naissance_demandeur(demande.getLieuNaissance())
                    .lieu_residence_demandeur(demande.getLieuResidence())
                    .nombre_copie_demande(demande.getNombreCopie())
                    .nombre_enfant_demandeur(demande.getNombreEnfant())
                    .numero_acte_naissance(demande.getNumeroActe())
                    .numero_acte_rectifie(demande.getNumeroActeRectifie())
                    .numero_carte(demande.getNumeroCarte())
                    .numero_certificat_nationalite(demande.getNumeroCarte())
                    .numero_carte_sejour(demande.getNumeroCarte())
                    .numero_feuillet_naissance(demande.getNumeroFeuillet())
                    .numero_jugement_rectificatif(demande.getNumeroJugement())
                    .annee_acte_naissance(demande.getAnnee())
                    .numero_demande(demande.getNumeroDemande())
                    .order(demande.getOrder())
                    .record(demande.getRecord())
                    .denomination(demande.getDenomination())
                    .nif(demande.getNif())
                    .numero_rccm(demande.getNumeroRccm())
                    .localite_residence_dirigeant(demande.getLocalite_residence_dirigeant())
                    .nom_complet_dirigeant(demande.getNom_complet_dirigeant())
                    .titre_dirigeant(demande.getTitre_dirigeant())
                    .telephone_dirigeant(demande.getTelephone_dirigeant())
                    .adresse_dirigeant(demande.getAdresse_dirigeant())
                    .mobile_demandeur_personne_morale(demande.getTelephone())
                    .ref_existence_legale(demande.getRefExistenceLegale())
                    .siege(demande.getSiege())
                    .numero_piece_personne_morale(demande.getNumero_piece_personne_morale())
                    .build();
            if (demande.getTypePiece()!=null){
                 demandeAtd.setId_type_piece_demande(demande.getTypePiece().getCode());
            }
            if (demande.getSexe() != null) {
                demandeAtd.setSexe_demandeur(demande.getSexe().getLibelle());
            }
            if (demande.getPaysResidence() != null) {
                demandeAtd.setPays_siege(demande.getPaysResidence().getCode());
            }
            if (demande.getTypePersonneMorale() != null) {
                demandeAtd.setType_personne_morale_code(demande.getTypePersonneMorale().getCode());
            }
            if (demande.getPaysNationalite() != null) {
                demandeAtd.setPays_nationalite_demandeur(demande.getPaysNationalite().getCode());
            }
            if (demande.getPaysNaissance() != null) {
                demandeAtd.setPays_naissance_demandeur(demande.getPaysNaissance().getCode());
            }
            if (demande.getSituationMatrimoniale() != null) {
                demandeAtd.setId_situation_matrimoniale_demandeur(demande.getSituationMatrimoniale().getId());
            }
            if (demande.getPrefectureNaissance() != null) {
                demandeAtd.setId_prefecture_naissance_demandeur(demande.getPrefectureNaissance().getId());
            }
            if (demande.getDemandeurB1()!=null){
                demandeAtd.setCode_certification(demande.getDemandeurB1().getCode_certification());
            }
            return demandeAtd;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }
    }

    public CondamnationModel condamnationToCondamnationModel(Condamnation condamnation) {
        if (condamnation == null) {
            return null;
        }
        return modelMapper.map(condamnation, CondamnationModel.class);
    }

    @Override
    public DemandeFnc demandeToDemandeFNC(Demande demande) {
        try {
            if (demande == null) {
                return null;
            }
            DemandeFnc demandeFnc = DemandeFnc.builder()
                    .dateNaissance(demande.getDateNaissance())
                    .nom(demande.getNom())
                    .prenom(demande.getPrenom())
                    .nomPere(demande.getNomPere())
                    .prenomPere(demande.getPrenomPere())
                    .nomMere(demande.getNomMere())
                    .prenomMere(demande.getPrenomMere())
                    .typeBulletin(demande.getTypeDemande()).build();
            switch (demande.getTypeDemande()) {
                case "B3":
                case "CJE":
                case "ANC":
                case "B2":
                case "B1": {
                    demandeFnc.setSexe(demande.getSexe().getLibelle());
                    break;
                }
                case "M3":
                case "M2":
                case "M1": {
                    System.err.println("je suis dansnnnnnnn*********************");
                    demandeFnc.setDenomination(demande.getDenomination());
                    demandeFnc.setNif(demande.getNif());
                    if (demande.getNumeroRccm() != null) {
                        demandeFnc.setNumeroIdentification(demande.getNumeroRccm());
                    } else {
                        if (demande.getRefExistenceLegale() != null) {
                            demandeFnc.setNumeroIdentification(demande.getRefExistenceLegale());
                        } else {
                            demandeFnc.setNumeroIdentification(demande.getNumero_piece_personne_morale());
                        }
                    }
                    break;
                }
                default: {
                    break;
                }
            }

            return demandeFnc;
        } catch (Exception e) {
            logger.error("Erreur interne", e);
            return null;
        }

    }

}
