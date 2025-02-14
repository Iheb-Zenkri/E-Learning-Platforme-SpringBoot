package app.e_leaning.controllers;

import app.e_leaning.models.DocumentFile;
import app.e_leaning.services.DocumentService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentFileController {

    private final DocumentService documentService;

    public DocumentFileController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/file/{documentId}")
    public ResponseEntity<FileSystemResource> getDocumentFileById(@PathVariable Long documentId) {
        DocumentFile doc = documentService.getDocumentById(documentId);
        FileSystemResource resource = documentService.getDocumentFileById(documentId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getMimeType()))
                .body(resource);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentFile> getDocumentById(@PathVariable Long documentId) {
        DocumentFile doc = documentService.getDocumentById(documentId);
        return ResponseEntity.ok()
                .body(doc);
    }

    @PostMapping("/upload/{postId}")
    public ResponseEntity<Map<String, Object>> uploadDocument(@PathVariable Long postId,
                                                 @RequestParam("file") MultipartFile file) {
        DocumentFile document = documentService.saveFile(file, postId);

        return ResponseEntity.ok(Map.of(
                "message", "Document uploaded successfully",
                "document", document
        ));
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<Boolean> deleteDocument(@PathVariable Long documentId) {
        boolean isDeleted = documentService.deleteDocument(documentId);
        return ResponseEntity.ok(isDeleted) ;
    }
}
