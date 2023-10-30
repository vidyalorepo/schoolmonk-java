package com.dcc.schoolmonk.controller;

import com.ccavenue.security.AesCryptUtil;
import com.dcc.schoolmonk.service.PaymentService;
import com.dcc.schoolmonk.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
    origins = "*",
    allowedHeaders = "*",
    maxAge = 4800,
    allowCredentials = "false",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@Controller
@RequestMapping("/payments")
public class PaymentController {

  @Value("${ccavenue.access-code}")
  private String accessCode;

  @Value("${ccavenue.working-key}")
  private String workingKey;

  @Autowired UserService userService;

  @Autowired PaymentService paymentService;

  @GetMapping("/checkout")
  public String updatePaymentStatus(
      Model model,
      @RequestParam("registrationToken") String registrationToken,
      @RequestParam("classRange") String classRange,
      @RequestParam("academicYear") String academicYear,
      @RequestParam("schoolId") Long schoolId,
      @RequestParam("studentId") Long studentId,
      HttpServletRequest request) {
    //        final String requestTokenHeader = request.getHeader("Authorization");
    //
    //        String jwtToken = null;
    //        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
    //            jwtToken = requestTokenHeader.substring(7);
    //        }
    //        UserVo userVo = userService.getUserDetails(request, jwtToken);
    model.addAttribute("accessCode", accessCode);
    model.addAttribute(
        "encRequest",
        paymentService.generateEncryptedRequest(
            registrationToken, classRange, academicYear, schoolId, studentId));
    return "checkout";
  }

    @PostMapping("/response")
    public String handleResponse(Model model, HttpServletRequest request) {
        String encResp = request.getParameter("encResp");
        AesCryptUtil aesUtil = new AesCryptUtil(workingKey);
        String decResp = aesUtil.decrypt(encResp);
        StringTokenizer tokenizer = new StringTokenizer(decResp, "&");
        Map<String, String> hs = new HashMap<>();
        while (tokenizer.hasMoreTokens()) {
            String pair = tokenizer.nextToken();
            if (pair != null) {
                StringTokenizer strTok = new StringTokenizer(pair, "=");
        String pname = "";
        String pvalue = "";
        if (strTok.hasMoreTokens()) {
          pname = strTok.nextToken();
          if (strTok.hasMoreTokens()) pvalue = strTok.nextToken();
          hs.put(pname, pvalue);
        }
      }
    }
    paymentService.persistAdmission(hs);
    //    hs.put("registration_token", paymentService.getRegistrationToken(hs.get("order_id")));
    model.addAttribute("hs", hs);
    //    return "redirect:http://dev.vidyalo.com/#/auth/admission/preview-records/"
    //        + paymentService.getRegistrationToken(hs.get("order_id"));
    return "checkout-response";
  }
}




