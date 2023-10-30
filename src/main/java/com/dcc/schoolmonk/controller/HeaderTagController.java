package com.dcc.schoolmonk.controller;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dcc.schoolmonk.common.ApiResponse;
import com.dcc.schoolmonk.dto.HeaderTagDto;
import com.dcc.schoolmonk.service.HeaderTagService;
import com.dcc.schoolmonk.vo.HeaderTagMstVo;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 4800, allowCredentials = "false", methods = {
		RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT })
@RestController
@RequestMapping("/headertagcontroller")
public class HeaderTagController {
    private static final Logger LOGGER = Logger.getLogger(HeaderTagController.class);
	@Autowired
	HeaderTagService headertagservice;

	@RequestMapping(method = RequestMethod.POST, value = "/addheadertag")
	public ResponseEntity<ApiResponse> addHeadertag(@RequestBody HeaderTagMstVo headertagmstvo,
			HttpServletRequest request) {
		return headertagservice.addheaderTag(headertagmstvo);
	}

	@PostMapping("/getallheadertag")
	public ResponseEntity<ApiResponse> getallheaderTag(@RequestBody HeaderTagDto headerTagDto) {
		return headertagservice.getallHeaderTag(headerTagDto);
	}

	@GetMapping("/updateStatusByid")
	public ResponseEntity<ApiResponse> updateStatusById(@RequestParam long id, @RequestParam boolean status) {
		return headertagservice.updateStatusByid(id, status);
	}

	@GetMapping("/fetchBytag")
	public ResponseEntity<ApiResponse> fetchBytag() {
		return headertagservice.fetchByTag();
	}

}
