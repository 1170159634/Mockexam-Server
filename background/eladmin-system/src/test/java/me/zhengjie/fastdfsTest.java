package me.zhengjie;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import me.zhengjie.modules.mockexam.utils.FastDFSUtil;
import me.zhengjie.modules.mockexam.utils.FileUtil;
import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description:
 * @Telephone :      15135964789
 * @createDate :     2021/4/10 21:37
 * @updateUser :     Mingxuan_x
 * @updateDate :     2021/4/10 21:37
 * @updateRemark :   修改内容
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class fastdfsTest {

    @Autowired
    private FastDFSUtil fastDFSUtil;

    @Test
    public void testOnePicture() throws IOException {

        String path = "http://images.juheapi.com/jztk/subject4/939.jpg";
        String suffix = path.split("\\.")[3];
        String uuid = UUID.randomUUID().toString();
        String LocalPath = "D:/upload/" + uuid + "." + suffix;
        FileUtil.download(path, LocalPath);
        StorePath storePath = fastDFSUtil.uploadByLocalFile(LocalPath);
        System.out.println(storePath.getPath());


    }
}
