package com.xp.service;

import com.xp.common.Exception.SPException;
import com.xp.common.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
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
public class UploadService {

    private static  final List<String>allow_type= Arrays.asList("image/png","image/jpg");

    public String uploadImage(MultipartFile file) {

        try {
            //校验文件类型
            String contentType = file.getContentType();
            if (!allow_type.contains(contentType)) {
                throw new SPException(ExceptionEnum.UPLOAD_FILE_TYPE_ERROR);
            }
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image==null) {
                throw new SPException(ExceptionEnum.UPLOAD_FILE_CONTENT_ERROR);
            }
            //目标路径
            File dest=new File("F:\\shopping\\shopping\\shopping\\upload",file.getOriginalFilename());

            //保存本地
            file.transferTo(dest);

            return "http://image.shopping.com/"+file.getOriginalFilename();
        } catch (IOException e) {
            //上传失败
           log.error("文件上传失败",e);
           throw new SPException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }


    }
}
