package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.DocumentFile;
import app.e_leaning.repositories.DocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Page<DocumentFile> getAllDocuments(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    public DocumentFile getDocumentById(Long id) {
        return documentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Document not found"));
    }

    @Transactional
    public DocumentFile saveDocument(DocumentFile documentFile) {
        return documentRepository.save(documentFile);
    }

    @Transactional
    public boolean deleteDocument(Long id) {
        documentRepository.deleteById(id);
        return documentRepository.existsById(id);
    }
}

