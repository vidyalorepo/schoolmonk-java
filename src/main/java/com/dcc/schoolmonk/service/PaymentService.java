package com.dcc.schoolmonk.service;

import com.ccavenue.security.AesCryptUtil;
import com.dcc.schoolmonk.common.UniqueIDGenerator;
import com.dcc.schoolmonk.config.AesUtil;
import com.dcc.schoolmonk.dao.SchoolAdmissionDtlDao;
import com.dcc.schoolmonk.dao.SchoolStudentMappingDao;
import com.dcc.schoolmonk.dao.TransactionDao;
import com.dcc.schoolmonk.vo.SchoolAdmissionDtlVo;
import com.dcc.schoolmonk.vo.SchoolStudentMappingVo;
import com.dcc.schoolmonk.vo.Transaction;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {

  @Autowired WebClient client;

  @Value("${ccavenue.merchant-id}")
  String merchantId;

  @Value("${ccavenue.redirect-url}")
  String redirectUrl;

  @Value("${ccavenue.access-code}")
  String accessCode;

  @Value("${ccavenue.working-key}")
  String workingKey;

  @Autowired SchoolAdmissionDtlDao schoolAdmissionDtlDao;

  @Autowired SchoolStudentMappingDao schoolStudentMappingDao;

  @Autowired TransactionDao transactionDao;

  @Autowired UniqueIDGenerator gen;

  public String generateEncryptedRequest(
      String registrationToken,
      String classRange,
      String academicYear,
      Long schoolId,
      Long studentId) {
    Map<String, String> requestBody = new HashMap<>();
    String orderId = UUID.randomUUID().toString();
    requestBody.put("merchant_id", merchantId);
    requestBody.put("order_id", orderId);
    requestBody.put("currency", "INR");
    requestBody.put("redirect_url", redirectUrl);
    requestBody.put(
        "amount",
        calculateFee(classRange, academicYear, schoolId, studentId, registrationToken).toString());
    schoolStudentMappingDao.updateOrderId(orderId, registrationToken);
    return buildCCAvenueRequest(requestBody);
  }

  public BigDecimal calculateFee(
      String classRange,
      String academicYear,
      Long schoolId,
      Long studentId,
      String registrationToken) {
    SchoolAdmissionDtlVo admissionDtl =
        schoolAdmissionDtlDao
            .findBySchoolIdAndAcademicYearAndClassRange(schoolId, academicYear, classRange)
            .orElseThrow(() -> new IllegalArgumentException("Admission " + "Details not found."));
    BigDecimal admissionFees =
        BigDecimal.valueOf(
                admissionDtl.getFeesAmount() == null ? 0.0 : admissionDtl.getFeesAmount())
            .setScale(2, RoundingMode.HALF_UP);
    BigDecimal convenienceFees =
        BigDecimal.valueOf(
                Double.parseDouble(
                    (admissionDtl.getConvenieceFee() == null
                            || admissionDtl.getConvenieceFee().isEmpty())
                        ? "0.0"
                        : admissionDtl.getConvenieceFee()))
            .setScale(2, RoundingMode.HALF_UP);
    BigDecimal otherCharges =
        BigDecimal.valueOf(
                admissionDtl.getOtherChargesAmount() == null
                    ? 0.0
                    : admissionDtl.getOtherChargesAmount())
            .setScale(2, RoundingMode.HALF_UP);
    String admissionId = gen.getAdmissionId(studentId, schoolId);
    String applicationId = schoolId + "_" + academicYear + "_" + admissionId;
    schoolStudentMappingDao.updatePayment(
        registrationToken,
        applicationId,
        admissionFees.toString(),
        0L,
        new Date(),
        admissionDtl.getActualConvenieceFee() == null
            ? "0.0"
            : admissionDtl.getActualConvenieceFee(),
        admissionDtl.getActualGst() == null ? "0.0" : admissionDtl.getActualGst());
    return admissionFees.add(convenienceFees).add(otherCharges);
  }

  private String buildCCAvenueRequest(Map<String, String> requestBody) {
    String ccaRequest =
        requestBody.entrySet().stream()
            .map(e -> e.getKey() + "=" + e.getValue())
            .collect(Collectors.joining("&"));
    AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
    return aesUtil.encrypt(ccaRequest);
  }

  private static String wsURL = "https://apitest.ccavenue.com/apis/servlet/DoWebTrans";

  public String getRegistrationToken(String orderId) {
    SchoolStudentMappingVo mappingVo =
        schoolStudentMappingDao
            .findByOrderId(orderId)
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "No " + "admission " + "details" + " " + "found."));
    return mappingVo.getRegistrationToken();
  }

  public void persistAdmission(Map<String, String> hs) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    schoolStudentMappingDao.updateSuccessfulPaymentStatus(hs.get("order_id"));
    Transaction transaction =
        new Transaction(
            hs.get("order_id"),
            LocalDateTime.parse(hs.get("trans_date"), formatter),
            hs.get("bank_ref_no"),
            hs.get("order_status"),
            hs.get("currency"),
            new BigDecimal(hs.get("amount")),
            hs.get("payment_mode"),
            hs.get("card_name"));
    Transaction savedTxn = transactionDao.save(transaction);
    statusApi(hs);
  }
  // Production API URL:-https://login.ccavenue.com/apis/servlet/DoWebTrans
  //   Staging API URL:-https://180.179.175.17/apis/servlet/DoWebTrans

  public void statusApi(Map<String, String> hs) {
    String pXmlData =
        "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><Order_Status_Query order_no='<order_id>'"
            + " reference_no='<ref_no>' />";
    String pAccessCode = accessCode;
    String aesKey = workingKey;
    String pCommand = "orderStatusTracker";
    String pRequestType = "xml";
    String pResponseType = "xml";
    String pVersion = "1.1";

    callCCavenueApi(
        pXmlData
            .replace("<order_id>", hs.get("order_id"))
            .replace("<ref_no>", hs.get("tracking_id")),
        pAccessCode,
        pCommand,
        aesKey,
        pRequestType,
        pResponseType,
        pVersion);
  }

  /** Method to call ccavenue api. Output is printed on console within this method. */
  public void callCCavenueApi(
	      String pXmlData,
	      String pAccessCode,
	      String pCommand,
	      String aesKey,
	      String pRequestType,
	      String pResponseType,
	      String pVersion) {
	    String vResponse = "", encXMLData = "", encResXML = "", decXML = "";
	    StringBuffer wsDataBuff = new StringBuffer();
	    try {
	      if (aesKey != null && !aesKey.equals("") && pXmlData != null && !pXmlData.equals("")) {
	        AesUtil aesUtil = new AesUtil(aesKey);
	        encXMLData = aesUtil.encrypt(pXmlData);
	      }
	      wsDataBuff.append(
	          "enc_request="
	              + encXMLData
	              + "&access_code="
	              + pAccessCode
	              + "&command="
	              + pCommand
	              + "&response_type="
	              + pResponseType
	              + "&request_type="
	              + pRequestType
	              + "&version="
	              + pVersion);
	      vResponse = processUrlConnectionReq(wsDataBuff.toString(), wsURL);
	      //      AesUtil aesUtill = new AesUtil(aesKey);
	      //      System.out.println("dec_request:  " + aesUtill.decrypt(wsDataBuff.toString()));
	      System.out.println("dec_request: " + wsDataBuff.toString());
	      if (vResponse != null && !vResponse.equals("")) {
	        Map hm = tokenizeToHashMap(vResponse, "&", "=");
	        encResXML = hm.containsKey("enc_response") ? hm.get("enc_response").toString() : "";
	        String vStatus = hm.containsKey("status") ? hm.get("status").toString() : "";
	        String vError_code =
	            hm.containsKey("enc_error_code") ? hm.get("enc_error_code").toString() : "";
	        if (vStatus.equals("1")) { // If Api call failed
	          System.out.println("enc_response : " + encResXML);
	          System.out.println("error_code : " + vError_code);
	          return;
	        }
	        if (!encResXML.equals("")) {
	          AesUtil aesUtil = new AesUtil(aesKey);
	          decXML = aesUtil.decrypt(encResXML);
	          System.out.println("enc_response : " + decXML);
	          return;
	        }
	      }
	    } catch (Exception e) {
	      System.out.println("enc_response : " + e.getMessage());
	    }
	  }

  public static String processUrlConnectionReq(String pBankData, String pBankUrl) throws Exception {
    URL vUrl = null;
    URLConnection vHttpUrlConnection = null;
    DataOutputStream vPrintout = null;
    DataInputStream vInput = null;
    StringBuffer vStringBuffer = null;
    vUrl = new URL(pBankUrl);

    if (vUrl.openConnection() instanceof HttpsURLConnection) {
      vHttpUrlConnection = (HttpsURLConnection) vUrl.openConnection();
    } else if (vUrl.openConnection() instanceof HttpsURLConnection) {
      vHttpUrlConnection = (HttpsURLConnection) vUrl.openConnection();
    } else {
      vHttpUrlConnection = (URLConnection) vUrl.openConnection();
    }
    vHttpUrlConnection.setDoInput(true);
    vHttpUrlConnection.setDoOutput(true);
    vHttpUrlConnection.setUseCaches(false);
    vHttpUrlConnection.connect();
    vPrintout = new DataOutputStream(vHttpUrlConnection.getOutputStream());
    vPrintout.writeBytes(pBankData);
    vPrintout.flush();
    vPrintout.close();
    try {
      BufferedReader bufferedreader =
          new BufferedReader(new InputStreamReader(vHttpUrlConnection.getInputStream()));
      vStringBuffer = new StringBuffer();
      String vRespData;
      while ((vRespData = bufferedreader.readLine()) != null)
        if (vRespData.length() != 0) vStringBuffer.append(vRespData.trim());
      bufferedreader.close();
      bufferedreader = null;
    } finally {
      if (vInput != null) vInput.close();
      if (vHttpUrlConnection != null) vHttpUrlConnection = null;
    }
    return vStringBuffer.toString();
  }

  public static HashMap tokenizeToHashMap(String msg, String delimPairValue, String delimKeyPair) {
    HashMap keyPair = new HashMap();
    ArrayList respList = new ArrayList();
    String part = "";
    StringTokenizer strTkn = new StringTokenizer(msg, delimPairValue, true);
    while (strTkn.hasMoreTokens()) {
      part = (String) strTkn.nextElement();
      if (part.equals(delimPairValue)) {
        part = null;
      } else {
        String str[] = part.split(delimKeyPair, 2);
        keyPair.put(str[0], str.length > 1 ? (str[1].equals("") ? null : str[1]) : null);
      }
      if (part == null) continue;
      if (strTkn.hasMoreTokens()) strTkn.nextElement();
    }
    return keyPair.size() > 0 ? keyPair : null;
  }
}
