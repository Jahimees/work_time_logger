package com.example.worktime.service;

import com.example.worktime.entity.Document;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис отвечающий за загрузку и сохранение файлов в системе
 */
@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final DocumentDataService documentDataService;

    @Value("${url.user.folder}")
    private String USER_FOLDER;

    /**
     * Загружает документ в систмеу и сохраняет в папку /src/main/webapp/user/{idAccount}/docs/{idDocument}
     *
     * @param idAccount id пользователя
     * @param idDocument id документа
     * @param multipartFile объект файла
     * @return связанный объект документа
     */
    public Document uploadDocument(int idAccount, int idDocument, MultipartFile multipartFile) {
        Optional<Document> documentOptional = documentDataService.findById(idDocument);

        if (documentOptional.isEmpty()) {
            throw new NotFoundException("Document not found");
        }

        Document documentFromDb = documentOptional.get();

        String newFileName = UUID.randomUUID() + "." + multipartFile.getOriginalFilename().split("\\.")[1];
        File path = new File(USER_FOLDER + idAccount + "/docs");

        if (!path.exists()) {
            new File(path.getPath()).mkdirs();
        }
        try (BufferedOutputStream stream = new BufferedOutputStream(
                new FileOutputStream(
                        new File(path.getPath() + "/" + newFileName)))) {

            byte[] bytes = multipartFile.getBytes();
            stream.write(bytes);

            documentFromDb.setFile(newFileName);
        } catch (FileNotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return documentDataService.update(documentFromDb);
    }
}
