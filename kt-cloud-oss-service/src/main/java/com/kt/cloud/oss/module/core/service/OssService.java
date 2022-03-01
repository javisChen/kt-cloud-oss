package com.kt.cloud.oss.module.core.service;

import com.kt.cloud.oss.module.core.dto.OssUploadReqDTO;
import com.kt.cloud.oss.module.core.dto.OssUploadRespDTO;
import com.kt.component.exception.ExceptionFactory;
import com.kt.component.oss.IObjectStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@Service
public class OssService {

    private final Map<String, IObjectStorageService> objectStorageServiceMap;

    public OssService(Map<String, IObjectStorageService> objectStorageServiceMap) {
        this.objectStorageServiceMap = objectStorageServiceMap;
    }

    public OssUploadRespDTO upload(OssUploadReqDTO ossUploadDTO) {
        IObjectStorageService storageService = getStorageService(ossUploadDTO);
        if (storageService == null) {
            throw ExceptionFactory.userException("无效的OSS类型");
        }
        MultipartFile file = ossUploadDTO.getFile();
        String ossUrl = "";
        try {
            ossUrl = storageService.put(ossUploadDTO.getBucketName(), file.getOriginalFilename(), file.getInputStream());
        } catch (Exception e) {
            throw ExceptionFactory.sysException("上传失败", e);
        }
        return toOssUploadRespDTO(ossUrl);
    }

    private IObjectStorageService getStorageService(OssUploadReqDTO ossUploadDTO) {
        String type = ossUploadDTO.getType();
        if (type.equals("MINIO")) {
            return objectStorageServiceMap.get("minIoOssObjectStorageService");
        } else if (type.equals("ALIYUN")) {
            return objectStorageServiceMap.get("aliYunOssObjectStorageService");
        }
        return null;
    }

    private OssUploadRespDTO toOssUploadRespDTO(String ossUrl) {
        OssUploadRespDTO respDTO = new OssUploadRespDTO();
        respDTO.setUrl(ossUrl);
        return respDTO;
    }
}
