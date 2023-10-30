package com.dcc.schoolmonk.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;

import com.dcc.schoolmonk.dao.TransactionDao;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;

import com.dcc.schoolmonk.vo.Transaction;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;

@Service
public class PdfService {

    private static final Logger LOGGER = Logger.getLogger(HistoryService.class);

    @Autowired
    SchoolStudentMappingDao schoolStudentMappingDao;

    @Autowired
    TransactionDao transactionDao;

    @Value("${file.pdfTemplate}")
    private String pdfTempLoc;

    private String filePath = "src/main/resources/templates/";

    public Object downloadPDF(String admissionId, HttpServletResponse response) {
        OutputStream out = null;
        String FILE_NAME = admissionId + "_" + System.currentTimeMillis() + ".pdf";
        String content = "";
        try {
            String fileName = pdfTempLoc + "payment-receipt.html";
            LOGGER.info("PdfService :: fileName " + fileName);
            try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
                // content =
                // Files.lines(Paths.get(fileName)).collect(Collectors.joining(System.lineSeparator()));
                content = Files.lines(Paths.get(fileName))
                        .collect(Collectors.joining(System.lineSeparator()));
                SchoolStudentMappingVo schoolstudentDtl = schoolStudentMappingDao.getDetailsById(admissionId);
                Transaction transactionDtl = transactionDao.getTransationDtlByOrderId(schoolstudentDtl.getOrderId());
                String ActualGst=" ";
                String paymentDateString = "";
                String ConvenieceFee =" ";
                String totalAmountString=" ";
                String OthersChargesString =" ";
                float OthersCharges;
                try{
                    Float GstCandS=Float. parseFloat(schoolstudentDtl.getActualGst());  
                    LOGGER.info("PdfService :: GstCandS" + GstCandS);
                    Float gst= GstCandS/2;
                    ActualGst=String.valueOf(gst);
                    totalAmountString=transactionDtl.getAmount().toString();

                    float totalAmountFloat=Float. parseFloat(totalAmountString);
                    ConvenieceFee = schoolstudentDtl.getActualConvenieceFee();
                    float conConveniecefeee=Float. parseFloat(ConvenieceFee);

                    OthersCharges=totalAmountFloat - (GstCandS + conConveniecefeee);
                    OthersCharges=totalAmountFloat - OthersCharges;
                    OthersChargesString=String.valueOf(OthersCharges);
                }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
                try {
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMMM dd, yyyy");  
                    paymentDateString = transactionDtl.getTxnTs().format(myFormatObj);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
     
               
  
                LOGGER.info("PdfService :: paymentDateString " + paymentDateString);
                LOGGER.info("PdfService :: ConvenieceFee " + ConvenieceFee);
                String GST = schoolstudentDtl.getActualGst();
                LOGGER.info("PdfService :: ActualGst " + GST);
                content = content.replace("$Recipt_No", schoolstudentDtl.getAdmissionId());
                LOGGER.info("PdfService :: AdmissionId " + schoolstudentDtl.getAdmissionId());
                content = content.replace("$Invoice_ID", transactionDtl.getOrderId());
                content = content.replace("$Student_Name", schoolstudentDtl.getStudentMstVo().getStudentName());
                content = content.replace("$Student_Number",
                        schoolstudentDtl.getStudentMstVo().getParentUser().getPhone());
                content = content.replace("$Amount", schoolstudentDtl.getPaymentAmount());
                content = content.replace("$SGST", ActualGst);
                content = content.replace("$CGST", ActualGst);
                content = content.replace("Conveniecefee", ConvenieceFee);
                content = content.replace("$Total", transactionDtl.getAmount().toString());
                content = content.replace("$Payment_Date", paymentDateString);
                content = content.replace("$Recipt_Date", paymentDateString);
                content = content.replace("$Othercharges", OthersChargesString);
                // LOGGER.info("PdfService :: ActualGst " + content);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                HtmlConverter.convertToPdf(content, baos);
                if (response != null) {
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment;filename=" + FILE_NAME);
                    out = response.getOutputStream();
                    baos.writeTo(out);
                }

                return baos.toByteArray();

            } catch (Exception e) {
                LOGGER.info("PdfService :: downloadPDF() :: EXCEPTION :: " + e.getLocalizedMessage());
                LOGGER.info(
                        "PdfService :: downloadPDF() :: EXCEPTION :: " + e.getMessage() + ", "
                                + e.getStackTrace());
            }

        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

}
