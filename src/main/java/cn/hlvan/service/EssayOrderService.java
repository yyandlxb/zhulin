package cn.hlvan.service;

import cn.hlvan.exception.ApplicationException;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.manager.database.tables.records.UserOrderRecord;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static cn.hlvan.constant.OrderEssayStatus.ADMIN_WAIT_AUDITING;
import static cn.hlvan.constant.OrderEssayStatus.SUBMIT;
import static cn.hlvan.manager.database.tables.OrderEssay.ORDER_ESSAY;
import static cn.hlvan.manager.database.tables.Picture.PICTURE;
import static cn.hlvan.manager.database.tables.UserOrder.USER_ORDER;

@Service
public class EssayOrderService {
    private static Logger logger = LoggerFactory.getLogger(EssayOrderService.class);
    @Value("${file.path}")
    private String filePath;
    private DSLContext dsl;

    @Autowired
    public void setDsl(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Transactional
    public boolean createEssay(String fileName, Integer id, Integer userOrderId, String essayTitle) throws IOException {

        UserOrderRecord userOrderRecord = dsl.selectFrom(USER_ORDER).where(USER_ORDER.ID.eq(userOrderId))
                                             .and(USER_ORDER.USER_ID.eq(id)).fetchSingle();
//        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        if (fileName.endsWith(".docx")) {
//            byte[] bytes;
//            try {
//                bytes = file.getBytes();
//                Path path = Paths.get(filePath + fileName);
//                Files.write(path, bytes);
//            } catch (IOException e) {
//                logger.info("上传文件失败", e);
//                throw new ApplicationException("上传文件失败");
//            }
            OrderEssayRecord orderEssayRecord = new OrderEssayRecord();
            orderEssayRecord.setEassyFile(fileName);
            orderEssayRecord.setEssayTitle(essayTitle);
            orderEssayRecord.setOrderCode(userOrderRecord.getOrderCode());
            dsl.executeInsert(orderEssayRecord);
            //文章读取
            InputStream is = new FileInputStream(filePath + fileName);
            XWPFDocument hwpfDocument = new XWPFDocument(is);
            List<XWPFPictureData> allPictures = hwpfDocument.getAllPictures();
            BigInteger essayId = dsl.lastID();
            for (XWPFPictureData a : allPictures) {
                String fm = filePath + System.currentTimeMillis() + a.getFileName();
                OutputStream os = new FileOutputStream(fm);
                byte[] data = a.getData();
                os.write(data);
                os.close();
                File f = new File(fm);
                BufferedImage bufferedImage = ImageIO.read(f);
                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();
                //存入图片信息
                PictureRecord pictureRecord = new PictureRecord();
                pictureRecord.setPictureName(fm);
                pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + data.length / 1024 / 1024.0 + "M");
                pictureRecord.setOrderEassyId(essayId.intValue());
                dsl.executeInsert(pictureRecord);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean updateEssay(String fileName, Integer id, Integer essayOrderId, String essayTitle) throws IOException {
        boolean exists =
            dsl.selectOne()
               .from(ORDER_ESSAY)
               .innerJoin(USER_ORDER)
               .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
               .and(ORDER_ESSAY.ID.eq(essayOrderId))
               .and(USER_ORDER.USER_ID.eq(id))
               .limit(1)
               .fetchOptional()
               .isPresent();
        if (!exists)
            return false;

        OrderEssayRecord orderEssayRecord = dsl.selectFrom(ORDER_ESSAY).where(ORDER_ESSAY.ID.eq(essayOrderId))
                                               .fetchSingle();
//        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        if (fileName.endsWith(".docx")) {
            byte[] bytes;
//            try {
//                bytes = file.getBytes();
//                Path path = Paths.get(filePath + fileName);
//                Files.write(path, bytes);
//            } catch (IOException e) {
//                logger.info("上传文件失败");
//                throw new ApplicationException("上传文件失败");
//            }
            orderEssayRecord.setEassyFile(fileName);
            orderEssayRecord.setEssayTitle(essayTitle);
            //删除之前的图片
            dsl.deleteFrom(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(orderEssayRecord.getId())).execute();
            boolean b = dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.ESSAY_TITLE, essayTitle)
                           .set(ORDER_ESSAY.EASSY_FILE, fileName)
                           .where(ORDER_ESSAY.ID.eq(essayOrderId))
                           .and(ORDER_ESSAY.STATUS.eq(ADMIN_WAIT_AUDITING)).execute() > 0;
            if (!b)
                return false;
            //文章读取
            InputStream is = new FileInputStream(filePath + fileName);
            XWPFDocument hwpfDocument = new XWPFDocument(is);
            List<XWPFPictureData> allPictures = hwpfDocument.getAllPictures();
            for (XWPFPictureData a : allPictures) {
                String fm = filePath + System.currentTimeMillis() + a.getFileName();
                OutputStream os = new FileOutputStream(fm);
                byte[] data = a.getData();
                os.write(data);
                os.close();
                File f = new File(fm);
                BufferedImage bufferedImage = ImageIO.read(f);
                int width = bufferedImage.getWidth();
                int height = bufferedImage.getHeight();
                //存入图片信息
                PictureRecord pictureRecord = new PictureRecord();
                pictureRecord.setPictureName(fm);
                pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + data.length / 1024 / 1024.0 + "M");
                pictureRecord.setOrderEassyId(orderEssayRecord.getId());
                dsl.executeInsert(pictureRecord);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean submit(Integer id, Integer essayOrderId) {
        boolean exists =
            dsl.selectOne()
               .from(ORDER_ESSAY)
               .innerJoin(USER_ORDER)
               .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
               .and(ORDER_ESSAY.ID.eq(essayOrderId))
               .and(USER_ORDER.USER_ID.eq(id))
               .limit(1)
               .fetchOptional()
               .isPresent();

        if (exists) {
            return dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.STATUS, SUBMIT)
                      .where(ORDER_ESSAY.ID.eq(essayOrderId)).execute() > 0;
        } else {
            return false;
        }
    }
}
