package com.ego.manage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author zdd
 * @date 2019-05-26 13:54
 */
public interface PicService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    Map<String, Object> upload(MultipartFile file) throws IOException;
}
