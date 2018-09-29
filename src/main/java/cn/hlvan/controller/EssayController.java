package cn.hlvan.controller;

import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.security.AuthorizedUser;
import cn.hlvan.security.session.Authenticated;
import cn.hlvan.util.Reply;
import cn.hlvan.util.ZipUtils;
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
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
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
    public Reply pictureList(@RequestParam Integer orderEssayId) {

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
                } catch (Exception e) {
                    logger.info("Download the song failed!", e);
                } finally {
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

    @GetMapping("/downloads")
    public Reply downloadFiles(HttpServletResponse response, String id, @Authenticated AuthorizedUser user) throws IOException {
        String[] split = id.split(",");
        Integer[] ids = new Integer[split.length];
        for (int i = 0; i < split.length; i++) {
            ids[i] = Integer.parseInt(split[i]);
        }
        List<OrderEssayRecord> list = dsl.selectFrom(ORDER_ESSAY)
                                         .where(ORDER_ESSAY.ID.in(ids)).fetch();

        String fileName = System.currentTimeMillis() + ".zip";
//        File file = new File(path+fileName);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename="+fileName);
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
        try {
            for(Iterator<OrderEssayRecord> it = list.iterator(); it.hasNext();){
                OrderEssayRecord srcFile = it.next();
                ZipUtils.doCompress(path + srcFile.getEssayFile(), out);
                response.flushBuffer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            out.close();
        }

//        // 配置文件下载
//        response.setHeader("content-type", "application/octet-stream");
//        response.setContentType("application/octet-stream");
//        // 下载文件能正常显示中文
//        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        // 实现文件下载
//        byte[] buffer = new byte[1024];
//        FileInputStream fis = null;
//        BufferedInputStream bis = null;
//        try {
//            fis = new FileInputStream(file);
//            bis = new BufferedInputStream(fis);
//            OutputStream os = response.getOutputStream();
//            int i = bis.read(buffer);
//            while (i != -1) {
//                os.write(buffer, 0, i);
//                i = bis.read(buffer);
//            }
//        } catch (Exception e) {
//            logger.info("Download the song failed!", e);
//        } finally {
//            if (bis != null) {
//                try {
//                    bis.close();
//                } catch (IOException e) {
//                    logger.info("压缩失败", e);
//                }
//            }
//            if (fis != null) {
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    logger.info("压缩失败", e);
//                }
//            }
//        }

        return null;
    }

    private static void zipFile(File inputFile, ZipOutputStream outputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream in = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(in, 1024);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[1024];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    in.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (File file : files) {
                            zipFile(file, outputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            logger.info("打包错误", e);
        }
    }
}