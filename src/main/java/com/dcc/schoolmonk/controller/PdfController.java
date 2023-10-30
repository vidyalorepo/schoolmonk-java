package com.dcc.schoolmonk.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcc.schoolmonk.service.PdfService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
    RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })

@RestController
@RequestMapping("/pdfcontroller")
public class PdfController {
    
    @Autowired
    PdfService pdfService;
    
	@RequestMapping(method = RequestMethod.GET, value = "/downloadPaymentPDF")
	public Object downloadPDF(@RequestParam("admissionId") String admissionId,
			HttpServletResponse response) {
		System.out.println("PdfController:: downloadPDF() called......");
		return this.pdfService.downloadPDF(admissionId, response);
	}

}
