package app.e_leaning.services;

import app.e_leaning.exceptions.ObjectNotFoundException;
import app.e_leaning.models.VideoFile;
import app.e_leaning.repositories.VideoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public Page<VideoFile> getAllVideos(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    public VideoFile getVideoById(Long id) {
        return videoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Video not found"));
    }

    @Transactional
    public VideoFile saveVideo(VideoFile videoFile) {
        return videoRepository.save(videoFile);
    }

    @Transactional
    public boolean deleteVideo(Long id) {
        videoRepository.deleteById(id);
        return videoRepository.existsById(id);
    }
}

