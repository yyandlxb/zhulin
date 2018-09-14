import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class PoiTest {

    public static void main(String[] args) throws Exception {

        InputStream is = new FileInputStream("F:\\test.docx");
        XWPFDocument hwpfDocument = new XWPFDocument(is);
        List<XWPFPictureData> allPictures = hwpfDocument.getAllPictures();
        for (XWPFPictureData a:allPictures) {


            byte[] data = a.getData();

            String s = a.suggestFileExtension();

            BufferedImage bufferedImage = ImageIO.read(new File(a.getFileName()));
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
//            if (bufferedImage != null && height == imageHeight && width == imageWidth) {
//                result = true;
//            }

        }
//        System.out.println(count);
    }
}
