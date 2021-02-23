package site.minnan.recordlife.userinterface.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class AddImageDTO implements Serializable {

    /**
     * 上传的文件
     */
    private MultipartFile file;
}
