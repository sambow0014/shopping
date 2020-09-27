package com.xp.service;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import com.xp.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    //private static  final List<String>allow_type= Arrays.asList("image/png","image/jpg");
    @Autowired
    private UploadProperties uploadProperties;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    public String uploadImage(MultipartFile file) {

        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!uploadProperties.getAllowTypes().contains(contentType)) {
                throw new SPException(ExceptionEnum.UPLOAD_FILE_TYPE_ERROR);
            }
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image==null) {
                throw new SPException(ExceptionEnum.UPLOAD_FILE_CONTENT_ERROR);
            }
/*
            //目标路径
            File dest=new File("F:\\shopping\\shopping\\shopping\\upload",file.getOriginalFilename());
            //保存本地
            file.transferTo(dest);
*/
            //上传到FastDFS
            //String extension=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //return "http://image.shopping.com/"+storePath.getFullPath();
            return uploadProperties.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            //上传失败
           log.error("文件上传失败",e);
           throw new SPException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }


    }
}
