package com.medicalretrieval.utils;

import com.medicalretrieval.pojo.Document;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jetbrains.annotations.NotNull;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class PDFUtils {
    public static void ReadPDF(@NotNull File file, @NotNull Document documents){
        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.execfile(".\\src\\test\\java\\com\\medicalretrieval\\C.py");
//        PyFunction pyFunction = interpreter.get("extractTitle",PyFunction.class);
//        PyObject pyObject = pyFunction.__call__(new PyString(file.getPath()));
//        System.out.println(pyObject);
        try (PDDocument document = PDDocument.load(file)) {
            int pageSize = document.getNumberOfPages();
            // 一页一页读取
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < pageSize; i++) {
                // 文本内容
                PDFTextStripper stripper = new PDFTextStripper();
                // 设置按顺序输出
                stripper.setSortByPosition(true);
                stripper.setStartPage(i + 1);
                stripper.setEndPage(i + 1);
                String text = stripper.getText(document);
//                System.out.println(text.trim());
//                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-");
//                str.append(text.trim());
                str.append(text);
                // 图片内容
                PDPage page = document.getPage(i);
                PDResources resources = page.getResources();
                Iterable<COSName> cosNames = resources.getXObjectNames();
                if (cosNames != null) {
                    for (COSName cosName : cosNames) {
                        if (resources.isImageXObject(cosName)) {
                            PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
                            BufferedImage image = Ipdmage.getImage();
                            try (FileOutputStream out = new FileOutputStream("./Img/" + UUID.randomUUID() + ".png")) {
                                ImageIO.write(image, "png", out);
                            } catch (IOException ignored) {
                            }
                        }
                    }
                }
            }
            System.out.println(str);
        } catch (IOException ignored) {
        }

    }
}
