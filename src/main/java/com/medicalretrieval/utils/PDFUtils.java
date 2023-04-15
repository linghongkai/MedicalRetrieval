package com.medicalretrieval.utils;

import com.adobe.pdfservices.operation.ExecutionContext;
import com.adobe.pdfservices.operation.auth.Credentials;
import com.adobe.pdfservices.operation.exception.SdkException;
import com.adobe.pdfservices.operation.exception.ServiceApiException;
import com.adobe.pdfservices.operation.exception.ServiceUsageException;
import com.adobe.pdfservices.operation.io.FileRef;
import com.adobe.pdfservices.operation.pdfops.ExtractPDFOperation;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractElementType;
import com.adobe.pdfservices.operation.pdfops.options.extractpdf.ExtractPDFOptions;
import com.medicalretrieval.api.ocr.GetImgText;
import com.medicalretrieval.api.oss.OssDao;
import com.medicalretrieval.pojo.elasticsearch.Document;
import com.medicalretrieval.pojo.elasticsearch.ImgInfo;
import com.medicalretrieval.pojo.elasticsearch.PageContent;
import com.medicalretrieval.pojo.elasticsearch.Paragraph;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
@Component
public class PDFUtils {
/*
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
*/
    @Autowired
    private OssDao ossDao;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PDFUtils.class);
    private static final String END = "------";
    public void ReadPDFText(File file,Document document, List<Paragraph> paragraphs){
        try {
            Credentials credentials = Credentials.serviceAccountCredentialsBuilder()
                    .fromFile("pdfservices-api-credentials.json")
                    .build();
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            ExtractPDFOperation extractPDFOperation = ExtractPDFOperation.createNew();
            //提供文件
            InputStream inputStream = Files.newInputStream(file.toPath());
            FileRef source = FileRef.createFromStream(inputStream,"application/pdf");
            extractPDFOperation.setInputFile(source);


            ExtractPDFOptions extractPDFOptions = ExtractPDFOptions.extractPdfOptionsBuilder()
                    .addElementsToExtract(Collections.singletonList(ExtractElementType.TEXT))
                    .build();
            extractPDFOperation.setOptions(extractPDFOptions);


            FileRef result = extractPDFOperation.execute(executionContext);

            //将要保存的路径
            String outputFilePath = "./temp/123.zip";
            OutputStream outputStream = Files.newOutputStream(Paths.get(outputFilePath));
            //保存结果,结果为json格式
            result.saveAs(outputStream);



            //使用py代码,获得作者
            String[] args1 = new String[] { "python", "E:\\workspace\\MedicalRetrieval\\src\\main\\java\\com\\medicalretrieval\\utils\\extractJson.py", outputFilePath };
            Process proc = Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            Set<String> authors = new HashSet<>();
            while(!END.equals(line = in.readLine())&&line!=null){
                //System.out.println(line);
                if (line.length()>0)
                    document.setTitle(line);
            }
            while(!END.equals(line = in.readLine())){
                //System.out.println(line);
                authors.add(line);
            }
            document.setAuthor(authors);
            while(!END.equals(line = in.readLine())){
                //System.out.println(line);
                //摘要
            }
            while(!END.equals(line = in.readLine())){
                //System.out.println(line);
                //关键词
            }
            Set<PageContent> pageContents = new HashSet<>();
            //页码总数
            try(PDDocument document1 = PDDocument.load(file)){
                int n = Integer.parseInt(in.readLine());
                document.setPageSize(n);
                document.setDate(new Date());
                for(int i=1;i<=n;i++){//遍历pdf的每一页,获取结果
                    PageContent pageContent = new PageContent();

                    pageContent.setId((long) i);
                    int cnt = 0;
                    /*
                      保存这一页的图片
                     */
                    {
                        PDPage page = document1.getPage(i - 1);
                        PDResources resources = page.getResources();
                        Iterable<COSName> cosNames = resources.getXObjectNames();

                        if (cosNames != null) {
                            List<String> Urls = new ArrayList<>();
                            for (COSName cosName : cosNames) {
                                if (resources.isImageXObject(cosName)) {
                                    PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
                                    BufferedImage image = Ipdmage.getImage();
                                    String name = UUID.randomUUID()+".png";
                                    String filePath = "./temp/" +name;
                                    try (FileOutputStream out = new FileOutputStream(filePath)) {
                                        ImageIO.write(image, "png", out);
                                        String url = ossDao.upload(filePath, name);
                                        Urls.add(url);
                                    }
                                    //删除图片
                                    new File(filePath).delete();
                                }
                            }
                            List<ImgInfo> imgInfos = GetImgText.getText(Urls);
                            pageContent.setImgInfos(imgInfos);

                        }
                    }
                    /*
                      保存文本内容，顺带实现段落类
                    */
                    {
                        StringBuilder content = new StringBuilder();
                        while (!END.equals(line = in.readLine())) {//遍历pdf的每一段，
                            content.append(line);
                            Paragraph paragraph = new Paragraph();

                            paragraph.setId(document.getId() * 1000000 + pageContent.getId() * 100 + cnt++);
                            paragraph.setContent(line);
                            paragraphs.add(paragraph);
                        }
                        pageContent.setContent(String.valueOf(content));
                    }
                    pageContents.add(pageContent);
                }
                document.setPageContents(pageContents);

                in.close();
                proc.waitFor();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }


        } catch (ServiceApiException | IOException | SdkException | ServiceUsageException e) {
            LOGGER.error("Exception encountered while executing operation", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }




}
