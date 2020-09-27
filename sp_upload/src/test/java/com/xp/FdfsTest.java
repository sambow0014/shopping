package com.xp;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UploadApplication.class)
public class FdfsTest {
//    快速文件储存客户端
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
//    缩略图设置
    @Autowired
    private ThumbImageConfig thumbImageConfig;
    @Test
    public void testUpload() throws Exception {
        File file=new File("C:\\Users\\Administrator\\Desktop\\img\\center.jpg");
        //上传并生成缩略图
        StorePath storePath=this.fastFileStorageClient.uploadFile(
                new FileInputStream(file),file.length(),"jpg",null);
//        带分组的路径
        System.out.println(storePath.getFullPath());
 //        不带分组的路径
        System.out.println(storePath.getPath());
    }
    @Test
    public void testUploadAndCreateThumb() throws FileNotFoundException {
        File file=new File("C:\\Users\\Administrator\\Desktop\\img\\center.jpg");
        StorePath storePath=this.fastFileStorageClient.uploadFile(
                new FileInputStream(file),file.length(),"jpg",null);
        String path=thumbImageConfig.getThumbImagePath(storePath.getPath());
        System.out.println(path);
    }
}
