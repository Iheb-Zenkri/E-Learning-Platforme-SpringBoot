package app.e_leaning.controllers;

import app.e_leaning.models.VideoFile;
import app.e_leaning.services.VideoService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/videos")
public class VideoFileController {

    private final VideoService videoService;

    public VideoFileController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/file/{videoId}")
    public ResponseEntity<FileSystemResource> getVideoFileById(@PathVariable Long videoId) {
        VideoFile video = videoService.getVideoById(videoId);
        FileSystemResource resource = videoService.getVideoFileById(videoId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(video.getMimeType()))
                .body(resource);
    }

    @GetMapping("/{videoId}")
    public ResponseEntity<VideoFile> getVideoById(@PathVariable Long videoId) {
        VideoFile video = videoService.getVideoById(videoId);
        return ResponseEntity.ok(video);
    }

    @PostMapping("/upload/{postId}")
    public ResponseEntity<Map<String, Object>> uploadVideo(@PathVariable Long postId,
                                                           @RequestParam("file") MultipartFile file) {
        VideoFile video = videoService.saveFile(file, postId);
        return ResponseEntity.ok(Map.of(
                "message", "Video uploaded successfully",
                "video", video
        ));
    }

    @DeleteMapping("/{videoId}")
    public ResponseEntity<Boolean> deleteVideo(@PathVariable Long videoId) {
        boolean isDeleted = videoService.deleteVideo(videoId);
        return ResponseEntity.ok(isDeleted);
    }
}
