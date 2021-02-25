package site.minnan.recordlife.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.recordlife.application.service.FileService;
import site.minnan.recordlife.userinterface.dto.AddImageDTO;
import site.minnan.recordlife.userinterface.response.ResponseEntity;

import java.io.IOException;

@RestController
@RequestMapping("/recordApplets/public")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("uploadFile")
    public ResponseEntity<?> insertImage(AddImageDTO dto) throws IOException {
        fileService.addImage(dto);
        return ResponseEntity.success();
    }
}
