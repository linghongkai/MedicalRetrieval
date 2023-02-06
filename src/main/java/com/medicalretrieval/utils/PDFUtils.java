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
import com.medicalretrieval.pojo.Document;
import org.python.antlr.ast.Str;
import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PDFUtils.class);

    public static void ReadPDFText(Document document){
        try {

            Credentials credentials = Credentials.serviceAccountCredentialsBuilder()
                    .fromFile("pdfservices-api-credentials.json")
                    .build();
            ExecutionContext executionContext = ExecutionContext.create(credentials);
            ExtractPDFOperation extractPDFOperation = ExtractPDFOperation.createNew();
            //提供文件
            FileRef source = FileRef.createFromLocalFile("./影响人工关节置换术后下肢深静脉血栓形成的临床风险因素分析_关振鹏.pdf");
            extractPDFOperation.setInputFile(source);


            ExtractPDFOptions extractPDFOptions = ExtractPDFOptions.extractPdfOptionsBuilder()
                    .addElementsToExtract(Arrays.asList(ExtractElementType.TEXT))
                    .build();
            extractPDFOperation.setOptions(extractPDFOptions);


            FileRef result = extractPDFOperation.execute(executionContext);

            //将要保存的路径
            String outputFilePath = createOutputFilePath();
            //保存结果,结果为json格式
            result.saveAs(outputFilePath);

            //使用py代码,获得作者
            String[] args1 = new String[] { "python", "E:\\workspace\\MedicalRetrieval\\src\\main\\java\\com\\medicalretrieval\\utils\\extractJson.py", outputFilePath };
            Process proc = Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            Set<String> authors = new HashSet<>();
            while(!Objects.equals(line = in.readLine(), "------")){
                document.setTitle(line);
            }
            while(!Objects.equals(line = in.readLine(), "------")){
                authors.add(line);
            }
            document.setAuthor(authors);
            while(!Objects.equals(line=in.readLine(),"------")){
                //摘要
            }
            while(!Objects.equals(line=in.readLine(),"------")){
                //关键词
            }
            in.close();
            proc.waitFor();


        } catch (ServiceApiException | IOException | SdkException | ServiceUsageException e) {
            LOGGER.error("Exception encountered while executing operation", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static String createOutputFilePath() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        String timeStamp = dateTimeFormatter.format(now);
        return("./temp/extract" + timeStamp + ".zip");
    }

}
