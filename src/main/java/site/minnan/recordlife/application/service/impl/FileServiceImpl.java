package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.minnan.recordlife.application.service.FileService;
import site.minnan.recordlife.infrastructure.exception.OperationNotSupportException;
import site.minnan.recordlife.userinterface.dto.AddImageDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OSS oss;

    @Value("${aliyun.bucketName}")
    private String bucketName;

    @Value("${aliyun.baseUrl}")
    private String baseUrl;

    @Value("${aliyun.imageFolder}")
    private String imageFolder;

    private final static String[] availableExtension = new String[]{"png", "jpg", "jpeg"};

    /**
     * 添加图片
     *
     * @param dto
     */
    @Override
    public String addImage(AddImageDTO dto) throws IOException {
        MultipartFile file = dto.getFile();
        String extension = StrUtil.subAfter(file.getOriginalFilename(), ".", true);
        if (!ArrayUtil.contains(availableExtension, extension)) {
            throw new OperationNotSupportException(StrUtil.format("格式【{}】文件不允许上传", extension));
        }
        InputStream is = file.getInputStream();
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");
        String ossKey = StrUtil.format("{}/{}.{}", imageFolder, fileName, extension);
        oss.putObject(bucketName, ossKey, is);
        return StrUtil.format("{}/{}", baseUrl, ossKey);
    }
}
