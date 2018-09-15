package cn.hlvan.controller;

import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.util.Reply;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

import static cn.hlvan.manager.database.tables.Picture.PICTURE;

@RestController
@RequestMapping("/order/essay")
public class EssayController {
    @Value("${file.path}")
    private String path;
    private static Logger logger = LoggerFactory.getLogger(EssayController.class);
    private DSLContext dsl;
    @Autowired
    public void setDsl(org.jooq.DSLContext dsl) {
        this.dsl = dsl;
    }
    @GetMapping("/picture/list")
    public Reply pictureList(@RequestParam Integer orderEssayId){

        List<PictureRecord> pictureRecords = dsl.selectFrom(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(orderEssayId)).fetch();
        return Reply.success().data(pictureRecords);
    }
    @GetMapping("/download")
    public Reply downloadFile(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = path;
            File file = new File(realPath, fileName);
            // 如果文件名存在，则进行下载
            if (file.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                }
                catch (Exception e) {
                    logger.info("Download the song failed!",e);
                }
                finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
