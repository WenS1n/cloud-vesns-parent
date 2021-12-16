package cn.vesns.ossservoce.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 86504
 */
public interface FileService {
    String uploadFileOss(MultipartFile file);
}
