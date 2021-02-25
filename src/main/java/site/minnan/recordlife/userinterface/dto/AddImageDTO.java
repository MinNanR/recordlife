package site.minnan.recordlife.userinterface.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddImageDTO {

    /**
     * 上传的文件
     */
    private MultipartFile file;
}
