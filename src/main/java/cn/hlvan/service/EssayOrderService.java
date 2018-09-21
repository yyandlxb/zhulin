package cn.hlvan.service;

import cn.hlvan.exception.ApplicationException;
import cn.hlvan.manager.database.tables.records.OrderEssayRecord;
import cn.hlvan.manager.database.tables.records.PictureRecord;
import cn.hlvan.manager.database.tables.records.UserOrderRecord;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.lang.RandomStringUtils;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static cn.hlvan.constant.OrderEssayStatus.ADMIN_WAIT_AUDITING;
import static cn.hlvan.constant.WriterOrderStatus.ALREADY_END;
import static cn.hlvan.constant.WriterOrderStatus.CARRY_OUT;
import static cn.hlvan.constant.WriterOrderStatus.WAIT_APPOINT;
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
        if (userOrderRecord.getStatus().equals(ALREADY_END))
            throw new ApplicationException("订单已截稿");
        OrderEssayRecord orderEssayRecord = new OrderEssayRecord();
        orderEssayRecord.setEassyFile(fileName);
        orderEssayRecord.setEssayTitle(essayTitle);
        orderEssayRecord.setUserOrderId(userOrderId);
        orderEssayRecord.setOrderCode(userOrderRecord.getOrderCode());
        dsl.executeInsert(orderEssayRecord);
        BigInteger essayId = dsl.lastID();
        if (fileName.endsWith(".rar")) {
            //解压文件
            List<String> list;
            try {
                list = extractRar(filePath + fileName);
            } catch (RarException e) {
                throw new ApplicationException("文件解析失败");
            }
            for (String fileN:list) {
                File file = new File(filePath + fileN);
                if (file.getName().contains(".jpg") || file.getName().contains(".png")){
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    //存入图片信息
                    PictureRecord pictureRecord = new PictureRecord();
                    pictureRecord.setPictureName(file.getName());
                    pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + file.getTotalSpace() / 1024 / 1024.0 + "M");
                    pictureRecord.setOrderEassyId(essayId.intValue());
                    dsl.executeInsert(pictureRecord);
                }
            }
            //更新订单表
            dsl.update(USER_ORDER).set(USER_ORDER.COMPLETE,userOrderRecord.getComplete() + 1).where(USER_ORDER.ID.eq(userOrderId)).execute();
            //判断是否已经完成
            if ((userOrderRecord.getComplete() + 1) >= userOrderRecord.getReserveTotal()){
                dsl.update(USER_ORDER).set(USER_ORDER.STATUS,CARRY_OUT).where(USER_ORDER.ID.eq(userOrderId)).execute();
            }
            Integer count = dsl.selectCount().from(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayId.intValue())).fetchOne().value1();
            dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.PICTURE_TOTAL, count).where(ORDER_ESSAY.ID.eq(essayId.intValue())).execute();
            return true;
        } else if (fileName.endsWith(".zip")){
            List<String> list = extractZip(fileName);
            for (String fileN:list) {
                File file = new File(filePath + fileN);
                if (file.getName().contains(".jpg") || file.getName().contains(".png")){
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    //存入图片信息
                    PictureRecord pictureRecord = new PictureRecord();
                    pictureRecord.setPictureName(file.getName());
                    pictureRecord.setOrderEassyId(essayId.intValue());
                    pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + file.getTotalSpace() / 1024 / 1024.0 + "M");
                    dsl.executeInsert(pictureRecord);
                }
            }
            //更新订单表
            dsl.update(USER_ORDER).set(USER_ORDER.COMPLETE,userOrderRecord.getComplete() + 1).where(USER_ORDER.ID.eq(userOrderId)).execute();
            //判断是否已经完成
            if ((userOrderRecord.getComplete() + 1) >= userOrderRecord.getReserveTotal()){
                dsl.update(USER_ORDER).set(USER_ORDER.STATUS,CARRY_OUT).where(USER_ORDER.ID.eq(userOrderId)).execute();
            }
            Integer count = dsl.selectCount().from(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayId.intValue())).fetchOne().value1();
            dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.PICTURE_TOTAL, count).where(ORDER_ESSAY.ID.eq(essayId.intValue())).execute();
            return true;
        }else {
            return false;

        }
    }


//    public void insertWord(String fileName,Integer essayId) throws IOException {
//        //文章读取
//        InputStream is = new FileInputStream(filePath + fileName);
//        XWPFDocument hwpfDocument = new XWPFDocument(is);
//        List<XWPFPictureData> allPictures = hwpfDocument.getAllPictures();
//
//        for (XWPFPictureData a : allPictures) {
//            String fmn = System.currentTimeMillis() + a.getFileName();
//            String fm = filePath + fmn;
//            OutputStream os = new FileOutputStream(fm);
//            byte[] data = a.getData();
//            os.write(data);
//            os.close();
//            File f = new File(fm);
//            BufferedImage bufferedImage = ImageIO.read(f);
//            int width = bufferedImage.getWidth();
//            int height = bufferedImage.getHeight();
//            //存入图片信息
//            PictureRecord pictureRecord = new PictureRecord();
//            pictureRecord.setPictureName(fmn);
//            pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + data.length / 1024 / 1024.0 + "M");
//            pictureRecord.setOrderEassyId(essayId);
//            dsl.executeInsert(pictureRecord);
//            Integer count = dsl.selectCount().from(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayId.intValue())).fetchOne().value1();
//            dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.PICTURE_TOTAL, count).where(ORDER_ESSAY.ID.eq(essayId.intValue())).execute();
//        }
//    }


    @Transactional
    public boolean updateEssay(String fileName, Integer id, Integer essayOrderId, String essayTitle) throws IOException {
        Integer count = dsl.selectCount().from(ORDER_ESSAY)
                           .innerJoin(USER_ORDER)
                           .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
                           .and(ORDER_ESSAY.ID.eq(essayOrderId))
                           .and(ORDER_ESSAY.STATUS.eq(ADMIN_WAIT_AUDITING))
                           .and(USER_ORDER.USER_ID.eq(id)).fetchOne().value1();
        if (count <= 0)
            return false;

        OrderEssayRecord orderEssayRecord = dsl.selectFrom(ORDER_ESSAY).where(ORDER_ESSAY.ID.eq(essayOrderId))
                                               .fetchSingle();
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
        if (fileName.endsWith(".rar")) {
            //解压文件
            List<String> list;
            try {
                list = extractRar(filePath + fileName);
            } catch (RarException e) {
                throw new ApplicationException("文件解析失败");
            }
            for (String fileN:list) {
                File file = new File(filePath + fileN);
                if (file.getName().contains(".jpg") || file.getName().contains(".png")){
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    //存入图片信息
                    PictureRecord pictureRecord = new PictureRecord();
                    pictureRecord.setPictureName(file.getName());
                    pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + file.getTotalSpace() / 1024 / 1024.0 + "M");
                    pictureRecord.setOrderEassyId(orderEssayRecord.getId());
                    dsl.executeInsert(pictureRecord);
                }
            }

            Integer ct = dsl.selectCount().from(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayOrderId)).fetchOne().value1();
            dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.PICTURE_TOTAL,ct).where(ORDER_ESSAY.ID.eq(essayOrderId)).execute();
            return true;
        } else if (fileName.endsWith(".zip")) {
            List<String> list = extractZip(fileName);
            for (String fileN : list) {
                File file = new File(filePath + fileN);
                if (file.getName().contains(".jpg") || file.getName().contains(".png")) {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    int width = bufferedImage.getWidth();
                    int height = bufferedImage.getHeight();
                    //存入图片信息
                    PictureRecord pictureRecord = new PictureRecord();
                    pictureRecord.setPictureName(file.getName());
                    pictureRecord.setOrderEassyId(orderEssayRecord.getId());
                    pictureRecord.setPicturePixel(width + "×" + height + "图片大小" + file.getTotalSpace() / 1024 / 1024.0 + "M");
                    dsl.executeInsert(pictureRecord);
                }
            }
            Integer ct = dsl.selectCount().from(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayOrderId)).fetchOne().value1();
            dsl.update(ORDER_ESSAY).set(ORDER_ESSAY.PICTURE_TOTAL,ct).where(ORDER_ESSAY.ID.eq(essayOrderId)).execute();
            return true;
        }else{
            throw new ApplicationException("上传文件格式不正确");
        }
    }

    @Transactional
    public boolean delete(Integer id, Integer essayOrderId) {
        UserOrderRecord userOrder =
            dsl.select(USER_ORDER.fields()).from(ORDER_ESSAY)
               .innerJoin(USER_ORDER)
               .on(ORDER_ESSAY.USER_ORDER_ID.eq(USER_ORDER.ID))
               .and(ORDER_ESSAY.ID.eq(essayOrderId))
               .and(ORDER_ESSAY.STATUS.eq(ADMIN_WAIT_AUDITING))
               .and(USER_ORDER.USER_ID.eq(id)).fetchOneInto(UserOrderRecord.class);

        if (userOrder != null) {
            Map<Object,Object> map = new HashMap<>();
            //更新订单表
            if (userOrder.getReserveTotal() > userOrder.getComplete() - 1){
                map.put(USER_ORDER.STATUS,WAIT_APPOINT);
            }
            dsl.update(USER_ORDER).set(USER_ORDER.COMPLETE,userOrder.getComplete() - 1).set(map)
               .where(USER_ORDER.ID.eq(id)).execute();
            //删除照片
            dsl.deleteFrom(PICTURE).where(PICTURE.ORDER_EASSY_ID.eq(essayOrderId)).execute();
            return dsl.deleteFrom(ORDER_ESSAY).where(ORDER_ESSAY.ID.eq(essayOrderId)).execute() > 0;
        } else {
            return false;
        }
    }

    private List<String> extractRar(String file) throws IOException, RarException {
        List<String> list = new ArrayList<>();
        Archive arch = new Archive(new File(file));
        List<FileHeader> headers = arch.getFileHeaders();
        Iterator<FileHeader> iterator = headers.iterator();
        while ( iterator.hasNext() ){
            FileHeader next = iterator.next();
            int i = next.getFileNameW().lastIndexOf(".");
            int length = next.getFileNameW().length();
            if (!next.isDirectory()){
                String fileName = System.currentTimeMillis() +
                RandomStringUtils.randomNumeric(3) + next.getFileNameW().substring(i,length);
                File fileExtract = new File(filePath + fileName);
                list.add(fileName);
                if (fileExtract.exists()){
                    FileOutputStream os = new FileOutputStream(fileExtract);
                    arch.extractFile(next, os);
                }else {
                    fileExtract.createNewFile();
                    FileOutputStream os = new FileOutputStream(fileExtract);
                    arch.extractFile(next, os);
                }
            }
        }
        return list;
    }
    private List<String> extractZip(String file) throws IOException {
        List<String> list = new ArrayList<>();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            if (!zipEntry.isDirectory()){
                String fileName = System.currentTimeMillis()+ zipEntry.getName().substring (zipEntry.getName().lastIndexOf("/")+1, zipEntry.getName().length());
                File newFile = new File(filePath + fileName);
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                list.add(fileName);
            }
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        return list;
    }
}
