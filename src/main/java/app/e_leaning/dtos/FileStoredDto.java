package app.e_leaning.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileStoredDto {
    private String fileName;
    private String mimeType;
}
