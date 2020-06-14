package com.ego.manage.service.impl;

import com.ego.commons.utils.FtpUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.manage.service.PicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zdd
 * @date 2019-05-26 13:57
 */
@Service
public class PicServiceImpl implements PicService {

    @Value("${ftpclient.host}")
    private String host;
    @Value("${ftpclient.ftpport}")
    private int ftpport;
    @Value("${ftpclient.nginxport}")
    private int nginxport;
    @Value("${ftpclient.username}")
    private String username;
    @Value("${ftpclient.password}")
    private String password;
    @Value("${ftpclient.basepath}")
    private String basepath;
    @Value("${ftpclient.filepath}")
    private String filepath;




    @Override
    public Map<String, Object> upload(MultipartFile file) throws IOException {
        String genImageName = IDUtils.genImageName() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean result = FtpUtil.uploadFile(host, ftpport, username, password,basepath, filepath, genImageName, file.getInputStream());
        Map<String, Object> map = new HashMap<>();
        if (result){
            map.put("error", 0);
//            map.put("url", "http://" + host /*+ ":" + port*/ + "/" + genImageName);
            map.put("url", "http://" + host + ":" + nginxport + "/" + genImageName);
        }else{
            map.put("error", 1);
            map.put("message", "图片上传失败");
        }
        return map;
    }
}
