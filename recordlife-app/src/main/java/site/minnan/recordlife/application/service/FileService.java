package site.minnan.recordlife.application.service;

import site.minnan.recordlife.userinterface.dto.AddImageDTO;

import java.io.IOException;

/**
 * 文件服务
 * @author Minnan on 2021/2/23
 */
public interface FileService {

    /**
     * 添加图片
     * @param dto
     */
    String addImage(AddImageDTO dto) throws IOException;
}
