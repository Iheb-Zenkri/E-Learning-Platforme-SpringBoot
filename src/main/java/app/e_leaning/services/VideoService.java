package app.e_leaning.services;

import app.e_leaning.dtos.FileStoredDto;
import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.Post;
import app.e_leaning.models.VideoFile;
import app.e_leaning.repositories.FileService;
import app.e_leaning.repositories.PostRepository;
import app.e_leaning.repositories.VideoRepository;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VideoService implements FileService {
    private final VideoRepository videoRepository;
    private final PostRepository postRepository;
    private final FileStorageService fileStorageService;

    public VideoService(VideoRepository videoRepository, PostRepository postRepository, FileStorageService fileStorageService) {
        this.videoRepository = videoRepository;
        this.postRepository = postRepository;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public VideoFile saveFile(MultipartFile videoFile, Long postId) {
        try {
            validateVideoFile(videoFile);

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ObjectNotFoundException("Post not found"));

            FileStoredDto fileStoredDto = fileStorageService.storeFile(videoFile);

            VideoFile video = new VideoFile();
            video.setFileName(fileStoredDto.getFileName());
            video.setFilePath("uploads/" + fileStoredDto.getFileName());
            video.setSize(videoFile.getSize());
            video.setMimeType(fileStoredDto.getMimeType());
            video.setDuration(0L); // Implement duration calculation if needed
            video.setPost(post);

            return videoRepository.save(video);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store video: " + e.getMessage());
        }
    }

    public FileSystemResource getVideoFileById(Long videoId) {
        VideoFile video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ObjectNotFoundException("Video not found"));
        try {
            return fileStorageService.getFile(video.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VideoFile getVideoById(Long videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new ObjectNotFoundException("Video not found"));
    }

    public boolean deleteVideo(Long videoId) {
        VideoFile video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ObjectNotFoundException("Video not found"));

        try {
            boolean deleted = fileStorageService.deleteFile(video.getFileName());
            if (deleted) {
                videoRepository.deleteById(videoId);
            }
            return !videoRepository.existsById(videoId);
        } catch (IOException e) {
            throw new RuntimeException("Error deleting video file");
        }
    }

    private void validateVideoFile(MultipartFile file) {
        List<String> allowedTypes = List.of("video/mp4", "video/mpeg", "video/avi");

        if (!allowedTypes.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid video type");
        }
    }
}
