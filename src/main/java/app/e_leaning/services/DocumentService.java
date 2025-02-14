package app.e_leaning.services;

import app.e_leaning.dtos.FileStoredDto;
import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.DocumentFile;
import app.e_leaning.models.Post;
import app.e_leaning.repositories.DocumentRepository;
import app.e_leaning.repositories.FileService;
import app.e_leaning.repositories.PostRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentService implements FileService {
    private final DocumentRepository documentRepository;
    private final PostRepository postRepository ;
    private final FileStorageService fileStorageService ;
    public DocumentService(DocumentRepository documentRepository, PostRepository postRepository, FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.postRepository = postRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public DocumentFile saveFile(MultipartFile documentFile, Long postId) {
        try {
            // validate file type
            validateDocumentFile(documentFile);

            // Validate post
            Post post = postRepository.findById(postId).orElseThrow(() -> new ObjectNotFoundException("Post not found"));

            // Store file locally
            FileStoredDto fileStoredDto = fileStorageService.storeFile(documentFile);


            // Create DocumentFile entity
            DocumentFile document = new DocumentFile();
            document.setFileName(fileStoredDto.getFileName());
            document.setFilePath("uploads/" + fileStoredDto.getFileName());
            document.setSize(documentFile.getSize());
            document.setMimeType(fileStoredDto.getMimeType());
            document.setPost(post);

            // Save to DB
            return documentRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store document: " + e.getMessage());
        }
    }

    public FileSystemResource getDocumentFileById(Long documentId) {
        DocumentFile document = documentRepository.findById(documentId).orElseThrow(() -> new ObjectNotFoundException("Document not found"));
        try {
            return fileStorageService.getFile(document.getFileName());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DocumentFile getDocumentById(Long documentId){
        return documentRepository.findById(documentId).orElseThrow(() -> new ObjectNotFoundException("Document not found"));
    }

    public boolean deleteDocument(Long documentId) {
        DocumentFile document = documentRepository.findById(documentId).orElseThrow(() -> new ObjectNotFoundException("Document not found"));

        try {
            boolean deleted = fileStorageService.deleteFile(document.getFileName());
            if (deleted) {
                documentRepository.deleteById(documentId);
            }
            return !documentRepository.existsById(documentId);

        } catch (IOException e) {
            throw new RuntimeException("Error deleting document file");
        }
    }

    private void validateDocumentFile(MultipartFile file) {
        List<String> allowedTypes = List.of("application/pdf", "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation");

        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid document type");
        }
    }
}

