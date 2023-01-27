package com.medicalretrieval;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class PdfTest {

    @Test
    public void testPdf() throws IOException {
        String path = "./三维标测系统和单环状标测导管指示_省略_线性消融电学隔离肺静脉方法学评价_董建增.pdf";
        File file = new File(path);
        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.execfile(".\\src\\test\\java\\com\\medicalretrieval\\C.py");
        PyFunction pyFunction = interpreter.get("extractTitle",PyFunction.class);
        PyObject pyObject = pyFunction.__call__(new PyString(path));
        System.out.println(pyObject);
//        PDDocument document1 = PDDocument.load(file);
//        PDDocumentInformation information = document1.getDocumentInformation();
//        System.out.println("页数"+document1.getNumberOfPages());
//        System.out.println("标题"+information.getTitle());
//        System.out.println("主题"+information.getSubject());
//        System.out.println("作者"+information.getAuthor());
//        System.out.println("关键字"+information.getKeywords());
//        System.out.println("应用程序"+information.getCreator());
//        System.out.println("pdf制作程序"+information.getProducer());
//        System.out.println("Trapped:"+information.getTrapped());
//        InputStream is = null;
        String fileName = path.substring(0);
        String name = fileName.substring(0,fileName.lastIndexOf("."));
        System.out.println(name);
//        try (PDDocument document = PDDocument.load(file)) {
//            int pageSize = document.getNumberOfPages();
//            // 一页一页读取
//            StringBuilder str = new StringBuilder();
//            for (int i = 0; i < pageSize; i++) {
//                // 文本内容
//                PDFTextStripper stripper = new PDFTextStripper();
//                // 设置按顺序输出
//                stripper.setSortByPosition(true);
//                stripper.setStartPage(i + 1);
//                stripper.setEndPage(i + 1);
//                String text = stripper.getText(document);
////                System.out.println(text.trim());
////                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-");
//                str.append(text.trim());
//                // 图片内容
////                PDPage page = document.getPage(i);
////                PDResources resources = page.getResources();
////                Iterable<COSName> cosNames = resources.getXObjectNames();
////                if (cosNames != null) {
////                    for (COSName cosName : cosNames) {
////                        if (resources.isImageXObject(cosName)) {
////                            PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
////                            BufferedImage image = Ipdmage.getImage();
////                            try (FileOutputStream out = new FileOutputStream("./Img/" + UUID.randomUUID() + ".png")) {
////                                ImageIO.write(image, "png", out);
////                            } catch (IOException e) {
////                            }
////                        }
////                    }
////                }
//            }
//            System.out.println(str);
//        } catch (IOException e) {
//        }

    }

}

