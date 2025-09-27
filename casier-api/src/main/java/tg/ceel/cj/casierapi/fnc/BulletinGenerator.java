package tg.ceel.cj.casierapi.fnc;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.*;
import tg.ceel.cj.casierapi.utils.CasierUtils;
import tg.ceel.cj.casierapi.utils.JRDSModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

@Service
public class BulletinGenerator {
    Logger logger = LoggerFactory.getLogger(BulletinGenerator.class);
    @Value("${b3.vierge}")
    private String b3vierge;
    @Value("${b3.avecCondamnations}")
    private String b3AvecCondamnations;
    @Value("${b3Morale.vierge}")
    private String b3MoraleVierge;
    @Value("${b3Morale.avecCondamnations}")
    private String b3MoraleAvecCondamnations;
    @Value("${tg.armoirie}")
    private String armoirieTogo;
    @Value("${tg.signature}")
    private String signatureImage;
    @Value("${tg.neant}")
    private String neantImage;
    @Value("${tg.background}")
    private String backgroundImage;

    @Value("${b1.vierge}")
    private String b1vierge;
    @Value("${b1.avecCondamnations}")
    private String b1AvecCondamnations;
    @Value("${b2.vierge}")
    private String b2vierge;
    @Value("${b2.avecCondamnations}")
    private String b2AvecCondamnations;
    @Value("${b1Morale.vierge}")
    private String b1MoraleVierge;

    @Value("${b1Morale.avecCondamnations}")
    private String b1MoraleAvecCondamnations;
    @Value("${cje.vierge}")
    private String cjevierges;

    @Value("${cje.condamnations}")
    private String cjeCondamnations;


    public InputStream generateB3(Demande demande, Collection<Condamnation> condamnations) throws IOException {
        String fileName = "bulletin_b3_" + demande.getId() + ".pdf";
        String baseTemplate = "/WEB-INF/modeles/", template;
        System.err.println(condamnations);
        if (condamnations == null || condamnations.isEmpty()) {
            template = this.b3vierge;
        } else {
            template = b3AvecCondamnations;
        }
//T5594122

        String path = Paths.get(template).toString();
        String armoirie = Paths.get(armoirieTogo).toString();
        String signature = Paths.get(signatureImage).toString();
        String neant = Paths.get(neantImage).toString();
        String background = Paths.get(backgroundImage).toString();

        // Adapt string
        String prefixNomme = "le nommé";
        String prefixNaissance = "né le";
        String prefixFiliation = "fils de ";
        if (demande.getSexe().getCode().equals("F")) {
            prefixNomme = "la nommée";
            prefixFiliation = "fille de";
            prefixNaissance = "née le";
        }

        PointRetrait pointRetrait = demande.getPointRetrait();
        String lieuRetrait = "Centre local de traitement du ";
        // Cas du centre national
        if (pointRetrait != null && pointRetrait.getId() == 34) {
            lieuRetrait = "Centre national";
        } else {
            lieuRetrait += pointRetrait.getLibelle();
        }

        // Generate bulletin
        try {
            List<JasperPrint> copies = new ArrayList<>();
            for (int i = 1; i <= demande.getNombreCopie(); i++) {

                JasperReport report = JasperCompileManager.compileReport(path);
                JRBeanCollectionDataSource ds = createDs(demande, condamnations);
                Map<String, Object> params = new HashMap<>();
                params.put("datasource", ds);
                params.put("barcode", getB3BarCodeDataSource(demande));
                params.put("nom", demande.getNom().toUpperCase());
                params.put("prenom", StringUtils.capitalize(demande.getPrenom()));
                params.put("nomPere", demande.getNomPere().toUpperCase());
                params.put("prenomPere", StringUtils.capitalize(demande.getPrenomPere()));
                params.put("nomMere", demande.getNomMere().toUpperCase());
                params.put("prenomMere", StringUtils.capitalize(demande.getPrenomMere()));
                params.put("dateNaissance", CasierUtils.dateToFrString(demande.getDateNaissance()));
                params.put("lieuNaissance", demande.getLieuNaissance() + BulletinGenerator.prefectureNaissance(demande));
                params.put("domicile", demande.getLieuResidence());
                params.put("situationMatrimoniale", demande.getSituationMatrimoniale().getLibelle());
                params.put("profession", CasierUtils.getProfession(demande));
                params.put("nationalite", demande.getPaysNationalite().getLibelleNationalite());
                params.put("numeroTraitement", String.format("%d/%s", CasierUtils.getYearOfDate(demande.getDateDemande()), demande.getNumeroTraitement()));
                params.put("numeroDemande", demande.getNumeroDemande());
                params.put("armoirie", armoirie);
                params.put("signature", signature);
                params.put("neant", neant);
                params.put("background", background);
                params.put("references", demande.getNumero_piece_personne_morale()); // Todo: add references pieces
                params.put("dateTraitement", CasierUtils.dateToFrString(demande.getDateValidation()));
                params.put("prefixe_nomme", prefixNomme);
                params.put("prefixe_filiation", prefixFiliation);
                params.put("prefixe_naissance", prefixNaissance);
                params.put("tribunal", lieuRetrait);
                params.put("copie", Integer.toString(i));

                JasperPrint jprint = JasperFillManager.fillReport(report,
                        params, new JREmptyDataSource());
                copies.add(jprint);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, copies);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes1 = out.toByteArray();
            InputStream strm = new ByteArrayInputStream(bytes1);
            return strm;
        } catch (JRException e) {
            logger.error("Erreur interne", e);
            e.printStackTrace();
            return null;
        }

    }

    private Object getLieurNaissce(Demande demande) {
        Prefecture prefecture = demande.getPrefectureNaissance();
        if (prefecture.getId()==40){
            return  demande.getLieuNaissance();
        }
        return demande.getLieuNaissance()+ " (" + demande.getPrefectureNaissance().getLibelle() + ")";

    }

    public InputStream generateB3Morale(Demande demande, Collection<Condamnation> condamnations) throws IOException {
        String fileName = "bulletin_b3_" + demande.getId() + ".pdf";
        String baseTemplate = "/WEB-INF/modeles/", template;
        System.err.println(condamnations);
        if (condamnations == null || condamnations.isEmpty()) {
            template = this.b3MoraleVierge;
        } else {
            template = b3MoraleAvecCondamnations;
        }
        String path = Paths.get(template).toString();
        String armoirie = Paths.get(armoirieTogo).toString();
        String signature = Paths.get(signatureImage).toString();
        String neant = Paths.get(neantImage).toString();
        String background = Paths.get(backgroundImage).toString();

        PointRetrait pointRetrait = demande.getPointRetrait();
        String lieuRetrait = "Centre local de traitement du ";
        // Cas du centre national
        if (pointRetrait != null && pointRetrait.getId() == 34) {
            lieuRetrait = pointRetrait.getLibelle();
        } else {
            lieuRetrait += pointRetrait.getLibelle();
        }

        // Generate bulletin
        try {
            List<JasperPrint> copies = new ArrayList<>();
            for (int i = 1; i <= demande.getNombreCopie(); i++) {
                JasperReport report = JasperCompileManager.compileReport(path);
                JRBeanCollectionDataSource ds = createDs(demande, condamnations);
                Map<String, Object> params = new HashMap<>();
                params.put("datasource", ds);
                params.put("barcode", getB3BarCodeDataSource(demande));
                params.put("nomSociete", demande.getDenomination().toUpperCase());
                params.put("numeroEnreg", demande.getNumeroRccm().toUpperCase());
                params.put("siege", demande.getSiege());
                params.put("representant",demande.getNom_complet_dirigeant().toUpperCase());
                params.put("fonctionRepresentant",demande.getTitre_dirigeant() );
                params.put("domicileRepresentant", demande.getLocalite_residence_dirigeant());
                params.put("numeroTraitement", String.format("%d/%s", CasierUtils.getYearOfDate(demande.getDateDemande()), demande.getNumeroTraitement()));
                params.put("numeroDemande", demande.getNumeroDemande());
                params.put("armoirie", armoirie);
                params.put("signature", signature);
                params.put("neant", neant);
                params.put("background", background);
                params.put("references", ""); // Todo: add references pieces
                params.put("dateTraitement", CasierUtils.dateToFrString(demande.getDateValidation()));
                params.put("tribunal", lieuRetrait);
                params.put("copie", Integer.toString(i));

                JasperPrint jprint = JasperFillManager.fillReport(report,
                        params, new JREmptyDataSource());
                copies.add(jprint);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, copies);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes1 = out.toByteArray();
            InputStream strm = new ByteArrayInputStream(bytes1);
            return strm;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }


    public  InputStream generateB1(Demande demande, Collection<Condamnation> condamnations) throws IOException {
        String fileName = "bulletin_b1_" + demande.getId() + ".pdf";
        String baseTemplate = "/WEB-INF/modeles/", template;
    
        if (condamnations == null || condamnations.isEmpty()) {
            template = this.b1vierge;
        } else {
            template = b1AvecCondamnations;
        }
        String path = Paths.get(template).toString();
        String armoirie = Paths.get(armoirieTogo).toString();
        String signature = Paths.get(signatureImage).toString();
        String neant = Paths.get(neantImage).toString();
        String background = Paths.get(backgroundImage).toString();

        PointRetrait pointRetrait = demande.getPointRetrait();

        // Adapt string 
        String prefixNomme = "le nommé";
        String prefixNaissance = "né le";
        String prefixFiliation = "fils de ";
        if (demande.getSexe().getCode().equals("F")) {
            prefixNomme = "la nommée";
            prefixFiliation = "fille de";
            prefixNaissance = "née le";
        }
        // Bulletin generation
        try {
            List<JasperPrint> copies = new ArrayList<>();
            for (int i = 1; i <= demande.getNombreCopie(); i++) {
                JasperReport report = JasperCompileManager.compileReport(path);
                JRBeanCollectionDataSource ds = createDs(demande, condamnations);
                Map<String, Object> params = new HashMap<>();
                params.put("datasource", ds);
                params.put("barcode", getB1BarCodeDataSource(demande));
                params.put("nom", demande.getNom().toUpperCase());
                params.put("prenom", StringUtils.capitalize(demande.getPrenom()));
                params.put("nomPere", demande.getNomPere().toUpperCase());
                params.put("prenomPere", StringUtils.capitalize(demande.getPrenomPere()));
                params.put("nomMere", demande.getNomMere().toUpperCase());
                params.put("prenomMere", StringUtils.capitalize(demande.getPrenomMere()));
                params.put("dateNaissance", CasierUtils.dateToFrString(demande.getDateNaissance()));
                params.put("lieuNaissance", demande.getLieuNaissance() + BulletinGenerator.prefectureNaissance(demande));
                params.put("domicile", demande.getLieuResidence());
                params.put("situationMatrimoniale", demande.getSituationMatrimoniale().getLibelle());
                params.put("profession", CasierUtils.getProfession(demande));
                params.put("nationalite", demande.getPaysNationalite().getLibelleNationalite());
                params.put("numeroTraitement", String.format("%d/%s", CasierUtils.getYearOfDate(demande.getDateDemande()), demande.getNumeroTraitement()));
                params.put("numeroDemande", demande.getNumeroDemande());
                // Todo: Handle point retrait issue
                //params.put("tribunal", demande.getDemandeurB2().getJuridiction().getLibelle());
                params.put("tribunal", "Centre national");
                params.put("copie", Integer.toString(i));
                ServiceDemandeurB2 serviceDemandeur = demande.getDemandeurB1().getServiceDemandeurB2();
                params.put("demandeur", String.format("%s (%s)",
                        serviceDemandeur.getLibelle(),
                        serviceDemandeur.getEntiteDemandeurB2().getLibelle()
                ));
                params.put("armoirie", armoirie);
                params.put("signature", signature);
                params.put("neant", neant);
                params.put("background", background);
                params.put("references", "");
                params.put("dateTraitement", CasierUtils.dateToFrString(demande.getDateValidation()));
                params.put("prefixe_nomme", prefixNomme);
                params.put("prefixe_filiation", prefixFiliation);
                params.put("prefixe_naissance", prefixNaissance);
                JasperPrint jprint = JasperFillManager.fillReport(report,
                        params, new JREmptyDataSource());
                copies.add(jprint);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, copies);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes1 = out.toByteArray();
            InputStream strm = new ByteArrayInputStream(bytes1);
            return strm;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getB1BarCodeDataSource(Demande demande) {
        UtilisateurCasier demandeur = demande.getDemandeurB1();
        ServiceDemandeurB2 serviceDemandeur = demandeur.getServiceDemandeurB2();

        StringBuilder builder = new StringBuilder();
        builder.append("Bulletin N°1 du Casier Judiciaire\n");
        builder.append(String.format("Entité demandeur: %s\n", serviceDemandeur.getEntiteDemandeurB2().getLibelle()));
        builder.append(String.format("Service demandeur: %s\n", "Justice"));
        builder.append(String.format("Agent demandeur: %s %s\n", demandeur.getPersonneInfo().getNom(), demandeur.getPersonneInfo().getPrenom()));
        builder.append(String.format("Numéro demande: %s \n", demande.getNumeroDemande()));
        builder.append(String.format("Identité du concerné: %s %s\n", demande.getNom(), demande.getPrenom()));
        builder.append(String.format("Date de la demande: %s \n", CasierUtils.datetoString(demande.getDateDemande())));
        builder.append(String.format("Nombre de copies: %d \n", demande.getNombreCopie()));
        builder.append(String.format("Téléphone demandeur: %s \n", demande.getTelephone()));
        builder.append(String.format("Date de validation: %s \n", CasierUtils.datetoString(demande.getDateValidation())));
        builder.append(String.format("Date impression: %s\n", CasierUtils.datetoString(demande.getDateValidation())));

        if (demande.getTracked() != null && demande.getTracked()) {
            builder.append(demande.getTrackingCode());
        }
        return builder.toString();
    }

    public static String getB3BarCodeDataSource(Demande demande) {
        final String NEWLINE = "\n";
        StringBuilder builder = new StringBuilder("Bulletin N°3 du Casier Judiciaire");
        builder.append(NEWLINE);
        builder.append(String.format("Numéro demande: %s \n", demande.getNumeroDemande()));
        builder.append(String.format("Identité demandeur: %s %s\n", demande.getNom(), demande.getPrenom()));
        builder.append(String.format("Date de la demande: %s \n", CasierUtils.datetoString(demande.getDateDemande())));
        builder.append(String.format("Numero pièce: ", CasierUtils.getNumeroPiece(demande)));
        builder.append(String.format("Nombre de copies: %d \n", demande.getNombreCopie()));
        builder.append(String.format("Téléphone demandeur: %s \n", demande.getTelephone()));
        builder.append(String.format("Date de validation: %s \n", CasierUtils.datetoString(demande.getDateValidation())));
        builder.append(String.format("Date impression: %s\n", CasierUtils.datetoString(new Date())));
        PointRetrait pointRetrait = demande.getPointRetrait();
        if (pointRetrait != null) {
            builder.append(String.format("Tribunal émetteur: %s\n", pointRetrait.getLibelle()));
        }

     /*   if (demande.getTracked() != null && demande.getTracked()) {
            builder.append(demande.getTrackingCode());
        }*/
        builder.append(demande.getTrackingCode());
        return builder.toString();
    }

    public static JRBeanCollectionDataSource createDs(Demande demande, Collection<Condamnation> condamnations) {
        Collection<JRDSModel> data = new ArrayList<>();
        if (condamnations == null || condamnations.isEmpty()) {
            data.add(
                    new JRDSModel("NÉANT", "NÉANT", "NÉANT", "NÉANT", "NÉANT", "NÉANT", observation(demande)
                    ));
        }
        if (condamnations != null && condamnations.size() > 0) {
            boolean isFirst = true;
            for (Condamnation c : condamnations) {
                JRDSModel model = new JRDSModel();
                model.setDateCondamnation(c.getStringDateCondamnation());
                model.setTribunal(c.getCours());
                model.setNatureCrimes(c.linearInfractions());
                model.setDateCrimes("-");
                model.setNaturePeines(CasierUtils.esimerQuantumPeine(c.getQuantumPeine()));
                model.setDateMandat(c.getStringDateMandatDepot());
                if (isFirst) {
                    model.setObservations(observation(demande));
                    isFirst = false;
                }
                data.add(model);
            }
        }
        return new JRBeanCollectionDataSource(data);
    }

    public static String observation(Demande demande) {
        if (demande.getTypeDemande().equalsIgnoreCase("B2") || demande.getTypeDemande().equalsIgnoreCase("B1")) {
            return "En application de l'article 545 de la Loi n° 83-1 instituant Code de procédure Pénale.";
        }
        TypePiece tp = demande.getTypePiece();
        // Todo: Change hardcode constants
        if (tp.getId() == 1) { // Cas acte de naissance
            return String.format("Décl. n°%s du %s de l'État-civil de %s",
                    demande.getNumeroActe(), CasierUtils.dateToFrStringWithBars(demande.getDateNaissance()),
                    demande.getEtatCivil());
        } else if (tp.getId() == 6) { // Cas du Jugement
            // Todo: controler le Jugement rectificatif
            return String.format("Jugnt. suppl. n°%s du %s du %s",
                    demande.getNumeroJugement(), CasierUtils.dateToFrStringWithBars(demande.getDateJugement()),
                    demande.getTribunalJugement());
        } else if (tp.getId() == 7) { // Cas du Jugement
            // Todo: controler le Jugement rectificatif
            return String.format("Jugnt. rect. n°%s du %s du %s",
                    demande.getNumeroJugement(), CasierUtils.dateToFrStringWithBars(demande.getDateJugement()),
                    demande.getTribunalJugement());
        }else if (tp.getId() == 5) {
            String numero = demande.getNumeroCarte();
            if (numero==null || numero.isEmpty()) {
                numero =  demande.getNumeroActe();
            }
            return String.format("Copie %s N° %s du %s", tp.getLibelle(), numero, CasierUtils.dateToFrStringWithBars(demande.getDateDelivranceCarte()));
        }else {
            String numero = demande.getNumeroCarte();
            if (tp.getId() == 4 && numero==null) {
                numero = demande.getNumeroPasseport();
            }
            return String.format("Copie %s N° %s du %s", tp.getLibelle(), numero, CasierUtils.dateToFrStringWithBars(demande.getDateDelivranceCarte()));
        }
    }

    public  InputStream generateCJE(Demande demande, Collection<Condamnation> condamnations) throws IOException {
      /*  if ((condamnations != null && !condamnations.isEmpty()) || demande.getPaysNationalite().getCode().equals("228")) {
            return generateB3(demande, condamnations);
        }*/

        if ( demande.getPaysNationalite().getCode().equals("228")) {
            return generateB3(demande, condamnations);
        }

        String fileName = "bulletin_cje_" + demande.getId() + ".pdf";
        String baseTemplate = "/WEB-INF/modeles/";
        String template = baseTemplate + "cje.jrxml";

        if (condamnations == null || condamnations.isEmpty()) {
            template = this.cjevierges;
        } else {
            template = cjeCondamnations;
        }
//T5594122

        String path = Paths.get(template).toString();
        String armoirie = Paths.get(armoirieTogo).toString();
        String signature = Paths.get(signatureImage).toString();
        String neant = Paths.get(neantImage).toString();
        String background = Paths.get(backgroundImage).toString();

        // Adapt string
        String prefixNomme = "le nommé";
        String prefixNaissance = "né le";
        String prefixFiliation = "fils de ";
        if (demande.getSexe().getCode().equals("F")) {
            prefixNomme = "la nommée";
            prefixFiliation = "fille de";
            prefixNaissance = "née le";
        }

        PointRetrait pointRetrait = demande.getPointRetrait();
        String lieuRetrait = "Centre local de traitement du ";
        // Cas du centre national
        if (pointRetrait != null && pointRetrait.getId() == 34) {
            lieuRetrait = pointRetrait.getLibelle();
        } else {
            lieuRetrait += pointRetrait.getLibelle();
        }


        // Generate bulletin
        try {
            List<JasperPrint> copies = new ArrayList<>();
            for (int i = 1; i <= demande.getNombreCopie(); i++) {
                JasperReport report = JasperCompileManager.compileReport(path);
                JRBeanCollectionDataSource ds = createDs(demande, condamnations);
                Map<String, Object> params = new HashMap<>();
                params.put("datasource", ds);
                params.put("barcode", getCJEBarCodeDataSource(demande));
                params.put("nom", demande.getNom().toUpperCase());
                params.put("prenom", StringUtils.capitalize(demande.getPrenom()));
                params.put("nomPere", demande.getNomPere().toUpperCase());
                params.put("prenomPere", StringUtils.capitalize(demande.getPrenomPere()));
                params.put("nomMere", demande.getNomMere().toUpperCase());
                params.put("prenomMere", StringUtils.capitalize(demande.getPrenomMere()));
                params.put("dateNaissance", CasierUtils.dateToFrString(demande.getDateNaissance()));
                params.put("lieuNaissance", demande.getLieuNaissance());
                params.put("domicile", demande.getLieuResidence());
                params.put("situationMatrimoniale", demande.getSituationMatrimoniale().getLibelle());
                params.put("profession", CasierUtils.getProfession(demande));
                params.put("nationalite", demande.getPaysNationalite().getLibelleNationalite());
                params.put("numeroTraitement", String.format("%d/%s", CasierUtils.getYearOfDate(demande.getDateDemande()), demande.getNumeroTraitement()));
                params.put("numeroDemande", demande.getId().toString());
                params.put("copie", Integer.toString(i));
                params.put("nomGreffier", demande.getPointRetrait().getGreffierEnChef());
                params.put("observation", observation(demande));
                params.put("piece", demande.getTypePiece().getLibelle());
                params.put("numeroDemande", demande.getNumeroDemande());
                params.put("numeroTraitement", String.valueOf(demande.getNumeroTraitement()));
             // params.put("tribunal", lieuRetrait);
                params.put("tribunal", "Centre national");
                params.put("dateEntreeTogo", CasierUtils.dateToFrString(demande.getDate_arivee_togo()));
                params.put("armoirie", armoirie);
                params.put("signature", signature);
                params.put("neant", neant);
                params.put("background", background);
                params.put("references", "");
                params.put("dateTraitement", CasierUtils.dateToFrString(demande.getDateValidation()));
                params.put("dateEntreeTogo", CasierUtils.dateToFrString(demande.getDate_arivee_togo()));
                params.put("prefixe_nomme", prefixNomme);
                params.put("prefixe_filiation", prefixFiliation);
                params.put("prefixe_naissance", prefixNaissance);
                JasperPrint jprint = JasperFillManager.fillReport(report,
                        params, new JREmptyDataSource());
                copies.add(jprint);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, copies);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes1 = out.toByteArray();
            InputStream strm = new ByteArrayInputStream(bytes1);
            return strm;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getCJEBarCodeDataSource(Demande demande) {
        StringBuilder builder = new StringBuilder("Attestation de non-condamnation\n");
        builder.append(String.format("Numéro demande: %s \n", demande.getNumeroDemande()));
        builder.append(String.format("Identité demandeur: %s %s\n", demande.getNom(), demande.getPrenom()));
        builder.append(String.format("Date de la demande: %s \n", CasierUtils.datetoString(demande.getDateDemande())));
        if (demande.getNumeroPasseport() != null) {
            builder.append(String.format("Numéro Passport: %s \n", demande.getNumeroPasseport()));
        }
        if (demande.getNumeroPasseport() == null) {
            builder.append(String.format("%s: %s \n", demande.getTypePiece().getLibelle(), demande.getNumeroCarte()));
        }
        builder.append(String.format("Nombre de copies: %d \n", demande.getNombreCopie()));
        builder.append(String.format("Téléphone demandeur: %s \n", demande.getTelephone()));
        builder.append(String.format("Date de validation: %s \n", CasierUtils.datetoString(demande.getDateValidation())));
        builder.append(String.format("Date impression: %s\n", CasierUtils.datetoString(demande.getDateValidation())));
        PointRetrait pointRetrait = demande.getPointRetrait();
        if (pointRetrait != null) {
            builder.append(String.format("Tribunal émetteur: %s\n", pointRetrait.getLibelle()));
        }
        if (demande.getTracked() != null && demande.getTracked()) {
            builder.append(demande.getTrackingCode());
        }
        return builder.toString();
    }

    public static String prefectureNaissance(Demande demande) {
        StringBuilder builder = new StringBuilder();
        if (demande.getPrefectureNaissance() != null && demande.getPrefectureNaissance().getId() != 40) {
            builder.append(String.format(" (%s)", demande.getPrefectureNaissance().getLibelle()));
        }
        return builder.toString();
    }

    public  InputStream generateB2(Demande demande, Collection<Condamnation> condamnations) throws IOException {

        String fileName = "bulletin_b1_" + demande.getId() + ".pdf";
        String baseTemplate = "/WEB-INF/modeles/", template;

        if (condamnations == null || condamnations.isEmpty()) {
            template = this.b2vierge;
        } else {
            template = b2AvecCondamnations;
        }
        String path = Paths.get(template).toString();
        String armoirie = Paths.get(armoirieTogo).toString();
        String signature = Paths.get(signatureImage).toString();
        String neant = Paths.get(neantImage).toString();
        String background = Paths.get(backgroundImage).toString();
        // Adapt string
        String prefixNomme = "le nommé";
        String prefixNaissance = "né le";
        String prefixFiliation = "fils de ";
        if (demande.getSexe().getCode().equals("F")) {
            prefixNomme = "la nommée";
            prefixFiliation = "fille de";
            prefixNaissance = "née le";
        }
        // Generate bulletin
        try {
            List<JasperPrint> copies = new ArrayList<>();
            for (int i = 1; i <= demande.getNombreCopie(); i++) {
                JasperReport report = JasperCompileManager.compileReport(path);
                JRBeanCollectionDataSource ds = createDs(demande, condamnations);
                Map<String, Object> params = new HashMap<>();
                params.put("datasource", ds);
                params.put("barcode", getB2BarCodeDataSource(demande));
                params.put("nom", demande.getNom().toUpperCase());
                params.put("prenom", StringUtils.capitalize(demande.getPrenom()));
                params.put("nomPere", demande.getNomPere().toUpperCase());
                params.put("prenomPere", StringUtils.capitalize(demande.getPrenomPere()));
                params.put("nomMere", demande.getNomMere().toUpperCase());
                params.put("prenomMere", StringUtils.capitalize(demande.getPrenomMere()));
                params.put("dateNaissance", CasierUtils.dateToFrString(demande.getDateNaissance()));
                params.put("lieuNaissance", demande.getLieuNaissance() + BulletinGenerator.prefectureNaissance(demande));
                params.put("domicile", demande.getLieuResidence());
                params.put("situationMatrimoniale", demande.getSituationMatrimoniale().getLibelle());
                params.put("profession", CasierUtils.getProfession(demande));
                params.put("nationalite", demande.getPaysNationalite().getLibelleNationalite());
                params.put("numeroTraitement", String.format("%d/%s", CasierUtils.getYearOfDate(demande.getDateDemande()), demande.getNumeroTraitement()));
                params.put("numeroDemande", demande.getNumeroDemande());
                // Todo: Handle point retrait issue
                //params.put("tribunal", demande.getDemandeurB2().getJuridiction().getLibelle());
                params.put("tribunal", demande.getPointRetrait().getLibelle());
                params.put("copie", Integer.toString(i));
                ServiceDemandeurB2 serviceDemandeur = demande.getDemandeurB1().getServiceDemandeurB2();
                params.put("demandeur", String.format("%s (%s)",
                        serviceDemandeur.getLibelle(),
                        serviceDemandeur.getEntiteDemandeurB2().getLibelle()
                ));
                params.put("armoirie", armoirie);
                params.put("signature", signature);
                params.put("neant", neant);
                params.put("background", background);
                params.put("references", "");
                params.put("dateTraitement", CasierUtils.dateToFrString(demande.getDateValidation()));
                params.put("prefixe_nomme", prefixNomme);
                params.put("prefixe_filiation", prefixFiliation);
                params.put("prefixe_naissance", prefixNaissance);
                JasperPrint jprint = JasperFillManager.fillReport(report,
                        params, new JREmptyDataSource());
                copies.add(jprint);
            }
            JRPdfExporter exporter = new JRPdfExporter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, copies);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.exportReport();
            byte[] bytes1 = out.toByteArray();
            InputStream strm = new ByteArrayInputStream(bytes1);
            return strm;
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getB2BarCodeDataSource(Demande demande) {
        UtilisateurCasier demandeur = demande.getDemandeurB1();
        ServiceDemandeurB2 serviceDemandeur = demandeur.getServiceDemandeurB2();
        StringBuilder builder = new StringBuilder("Bulletin N°2 du Casier Judiciaire\n");
        builder.append(String.format("Entité demandeur: %s\n", serviceDemandeur.getEntiteDemandeurB2().getLibelle()));
        builder.append(String.format("Service demandeur: %s\n", serviceDemandeur.getLibelle()));
        builder.append(String.format("Agent demandeur: %s %s\n", demandeur.getPersonneInfo().getNom(), demandeur.getPersonneInfo().getPrenom()));
        builder.append(String.format("Numéro demande: %s \n", demande.getNumeroDemande()));
        builder.append(String.format("Identité du concerné: %s %s\n", demande.getNom(), demande.getPrenom()));
        builder.append(String.format("Date de la demande: %s \n", CasierUtils.datetoString(demande.getDateDemande())));
        if (demande.getNumeroActe() != null && demande.getNumeroActe().trim().length() > 0) {
            builder.append(String.format("Numéro de l'acte: %s \n", demande.getNumeroActe()));
        }
        if (demande.getNumeroCarte() != null) {
            builder.append(String.format("Numéro CNI: %s \n", demande.getNumeroCarte()));
        }
        if (demande.getNumeroPasseport() != null) {
            builder.append(String.format("Numéro Passport: %s \n", demande.getNumeroPasseport()));
        }
        builder.append(String.format("Nombre de copies: %d \n", demande.getNombreCopie()));
        builder.append(String.format("Téléphone demandeur: %s \n", demande.getTelephone()));
        builder.append(String.format("Date de validation: %s \n", CasierUtils.datetoString(demande.getDateValidation())));
        builder.append(String.format("Date impression: %s\n", CasierUtils.datetoString(new Date())));
//        PointRetrait pointRetrait = demande.getPointRetrait();
//        if (pointRetrait != null){
//            dataSource += String.format("Tribunal émetteur: %s\n", pointRetrait.getLibelle());
//        }
        if (demande.getTracked() != null && demande.getTracked()) {
            builder.append(demande.getTrackingCode());
        }
        return builder.toString();
    }
}
