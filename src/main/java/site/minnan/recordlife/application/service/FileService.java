package site.minnan.recordlife.application.service;

import site.minnan.recordlife.userinterface.dto.AddImageDTO;

import java.io.IOException;

public interface FileService {

    /**
     * 添加图片
     * @param dto
     */
    String addImage(AddImageDTO dto) throws IOException;
}
