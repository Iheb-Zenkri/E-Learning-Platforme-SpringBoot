package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.DocumentFile;
import app.e_leaning.models.ImageFile;
import app.e_leaning.models.Post;
import app.e_leaning.models.VideoFile;
import app.e_leaning.repositories.DocumentRepository;
import app.e_leaning.repositories.ImageRepository;
import app.e_leaning.repositories.PostRepository;
import app.e_leaning.repositories.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final DocumentRepository documentRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final FileStorageService fileStorageService;

    public PostService(PostRepository postRepository, DocumentRepository documentRepository, ImageRepository imageRepository, VideoRepository videoRepository, FileStorageService fileStorageService) {
        this.postRepository = postRepository;
        this.documentRepository = documentRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.fileStorageService = fileStorageService;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post with id "+id+" not found"));
    }

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post updatedPost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatedPost.getTitle());
            post.setDescription(updatedPost.getDescription());
            return postRepository.save(post);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Transactional
    public boolean deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Post with id " + id + " not found"));

        // Get all associated files
        List<DocumentFile> documents = documentRepository.findByPost(post);
        List<ImageFile> images = imageRepository.findByPost(post);
        List<VideoFile> videos = videoRepository.findByPost(post);

        // Delete files from storage
        try {
            for (DocumentFile doc : documents) fileStorageService.deleteFile(doc.getFileName());
            for (ImageFile img : images) fileStorageService.deleteFile(img.getFileName());
            for (VideoFile vid : videos) fileStorageService.deleteFile(vid.getFileName());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting files from storage: " + e.getMessage());
        }

        // Delete post (cascade deletes from DB)
        postRepository.delete(post);

        return !postRepository.existsById(id);
    }

}

