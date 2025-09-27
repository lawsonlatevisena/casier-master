package tg.ceel.ftp.ftpapi.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

@Service
public class FileManager {
    Logger logger = LoggerFactory.getLogger(FileManager.class);
 /*   @Value("${ftp.server.repository}")
    String basePath;

    @Value("${ftp.project.repository}")
    String projectPath;*/

    public boolean existe(File file) {
        if (file == null) {
            return false;
        }
        if (file.length() <= 0) {
            return false;
        }
        return file.exists();
    }

    public boolean isReadable(File file) {
        if (!existe(file)) {
            return false;
        }
        return Files.isReadable(file.toPath());
    }

    public boolean createFolder(String racine, String folders) {
        try {
            String[] subFolders;
            int subFolderNumber = 1;
            int createdFolderNumber = 0;
            int existentFolderNumber = 0;
            String currentFolder = racine;

            if (folders == null || folders.isEmpty()) {
                File file = new File(currentFolder);
                if (!file.exists()) {
                    if (file.mkdir()) {
                        createdFolderNumber++;
                    }
                } else {
                    existentFolderNumber++;
                }

            } else {
                subFolders = folders.split("/");
                subFolderNumber = subFolders.length;
                for (String subFolder : subFolders) {
                    currentFolder = currentFolder + "/" + subFolder;
                    File file = new File(currentFolder);
                    if (!file.exists()) {
                        if (file.mkdir()) {
                            createdFolderNumber++;
                        }
                    } else {
                        existentFolderNumber++;
                    }

                }
            }


            return ((createdFolderNumber + existentFolderNumber) == subFolderNumber);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return false;

        }
    }

    public boolean transfertFile(String racine, String folder, String submittedFileName, MultipartFile multipartFile) {
        try {
            if (multipartFile == null) {
                return false;
            }

            System.err.println("folder " + folder);
            return upload(racine, folder, submittedFileName, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return false;
        }

    }

    public Boolean delete(String racine, String folder, String submittedFileName) {
        String chemin;
        if (folder != null) {
            chemin = racine + '/' + folder + '/' + submittedFileName;
        } else {
            chemin = racine + '/' + submittedFileName;
        }

        Path path = Paths.get(chemin);
        File file = new File(path.toString());
        boolean deleted = true;
        if (file.exists()) {
            deleted = file.delete();
        }
        return deleted;
    }

    public boolean transfertFileUpdate(String racine, String folder, String submittedFileName, MultipartFile multipartFile) {
        try {
            if (multipartFile == null) {
                return false;
            }
            if (delete(racine,folder,submittedFileName)){
                return upload(racine, folder, submittedFileName, multipartFile);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return false;
        }

    }

    private boolean upload(String racine, String folder, String submittedFileName, MultipartFile multipartFile) throws IOException {
        if (createFolder(racine, folder)) {
            String chemin = racine;
            if (folder != null) {
                chemin = racine + '/' + folder + '/' + submittedFileName;
            } else {
                chemin = racine + '/' + submittedFileName;
            }
            Path path = Paths.get(chemin);
            File file = new File(path.toString());
            multipartFile.transferTo(file);
            return existe(file) && isReadable(file);
        } else {
            return false;
        }
    }

    public Boolean saveImage(String racine, String folder, String name, MultipartFile file) {
        try {
            System.err.println("Début de transfert du fichier");
            if (file != null) {
                String fileName = sansAccent(name).replace(' ', '_') + "." + getExtension(file.getOriginalFilename());
                return this.transfertFile(racine, folder, fileName, file);

            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return false;
        }
    }

    public String sansAccent(String chaine) {
        return Normalizer.normalize(chaine, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public String getExtension(String filename) {
        String[] ext = filename.split("\\.");
        String extension;
        if (ext.length > 0) {
            extension = ext[ext.length - 1];
        } else {
            extension = "";
        }
        return extension;
    }

    public Boolean saveImageUpdate(String racine, String folder, String name, MultipartFile file) {
        try {
            System.err.println("Début de transfert du fichier");
            if (file != null) {
                String fileName = sansAccent(name).replace(' ', '_') + "." + getExtension(file.getOriginalFilename());
                return this.transfertFileUpdate(racine, folder, fileName, file);

            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Erreur interne", e);
            return false;
        }
    }
}
