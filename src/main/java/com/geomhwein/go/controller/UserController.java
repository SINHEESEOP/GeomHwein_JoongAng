package com.geomhwein.go.controller;

import java.io.File;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.geomhwein.go.command.ComunityUploadVO;
import com.geomhwein.go.command.ReplyVO;
import com.geomhwein.go.command.comunityVO;
import com.geomhwein.go.user.service.UserService;
import com.geomhwein.go.util.Criteria;
import com.geomhwein.go.util.PageVO;

import ch.qos.logback.core.status.Status;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Value("${project.upload.path}")
	private String uploadPath;
	
	@GetMapping("/cart")
	public String cart() {
		return "user/cart";
	}

	@GetMapping("/billing")
	public String billing() {
		return "/user/billing";
	}

	@GetMapping("profile")
	public String profile() {
		return "/user/profile";
	}
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	

	@GetMapping("/comunityList")
	public String userComunityList(Model model , Criteria cri) {
		
		List<comunityVO> list = userService.getComunityList(cri);
		
		int total = userService.comunityTotal(cri);
		
		PageVO vo = new PageVO(cri, total);
		
		model.addAttribute("pageVO",vo);
		
		model.addAttribute("list" , list);
		
		return "user/comunityList";
	}
	
	@GetMapping("/comunityDetail")
	public String comunityDetail(@RequestParam("pst_ttl_no") int pst_ttl_no , Model model, HttpServletRequest request , HttpServletResponse response ) {
		
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			
			for(Cookie cookie : cookies) {
				
				if(!cookie.getValue().contains(request.getParameter("pst_ttl_no"))) {
					cookie.setValue(cookie.getValue() + "_" + request.getParameter("pst_ttl_no"));
					cookie.setMaxAge(3600);
					response.addCookie(cookie);
					userService.updateHit(pst_ttl_no);
				}
			}
			
		}else {
			Cookie newcookie = new Cookie("visit_cookie" , request.getParameter("pst_ttl_no"));
			newcookie.setMaxAge(3600);
			response.addCookie(newcookie);
			userService.updateHit(pst_ttl_no);
		}
		
		
		comunityVO vo = userService.getComunityDetail(pst_ttl_no);
		List<ComunityUploadVO> list = userService.getFile(pst_ttl_no);
		
		model.addAttribute("vo",vo);
		model.addAttribute("list",list); 
		
		return "user/comunityDetail";
	}
	
	@GetMapping("/comunityReg")
	public String comunityReg() {
		return "user/comunityReg";
	}
	
	@GetMapping("/comunityModify")
	public String comunityModify(@RequestParam("pst_ttl_no") int pst_ttl_no , Model model) {
		
		comunityVO vo = userService.getComunityDetail(pst_ttl_no);
		
		model.addAttribute("vo",vo);
		
		return "user/comunityModify";
	}
	
	@GetMapping("/groupList")
	public String userGroupList() {
		return "user/groupList";
	}
	
	@GetMapping("/groupApplyList")
	public String groupApplyList() {
		return "user/groupApplyList";
	}
	
	@GetMapping("/questionList")
	public String questionList() {
		return "user/questionList";
	}
	
	@GetMapping("/homeworkReg")
	public String homeworkReg() {
		return "user/homeworkReg";
	}
	
	@GetMapping("/homeworkList")
	public String homeworkList() {
		return "user/homeworkList";
	}

	@GetMapping("/makeQuestion")
	public String makeQuestion(@RequestParam("username")String username,Model model){
		model.addAttribute("username",username);
		return "user/makeQuestion";
	}
	@PostMapping("/registQuestionForm")
	public String registQuestionForm(@RequestParam("userId")String userId,@RequestParam("questionDate")String questionDate,@RequestParam("questionCn")String questionCn) {
		
		
		return "";
	}
	

	@GetMapping("/questionReg")
	public String questionReg() {
		return "user/questionReg";
	}
	
	@GetMapping("/questionDetail")
	public String questionDetail() {
		return "user/questionDetail";
	}
	
	@GetMapping("/questionModify")
	public String questionModify() {
		return "user/questionModify";
	}
	
	@PostMapping("/comunityForm")
	public String comunityForm(comunityVO vo , RedirectAttributes rec,
			MultipartHttpServletRequest part) {
		
		
		List<MultipartFile> list = part.getFiles("file");
	

		int result = userService.comunityForm(vo , list);
		
		if(result == 1 ) {
			rec.addFlashAttribute("msg", "등록성공");
		}else {
			rec.addFlashAttribute("msg", "등록실패");
		}
		
		return "redirect:/user/comunityList";
	}
	
	@PostMapping("/comunityModifyForm")
	public String comunityModifyForm(comunityVO vo , RedirectAttributes rec) {
		
		int result = userService.comunityModifyForm(vo);
		
		if(result == 1 ) {
			rec.addFlashAttribute("msg", "수정성공");
		}else {
			rec.addFlashAttribute("msg", "수정실패");
		}
		
		return "redirect:/user/comunityList";
	}
	
	@GetMapping("/comunityDelete")
	public String comunityDelete(@RequestParam("pst_ttl_no") int pst_ttl_no , RedirectAttributes rec) {
		
		int result = userService.comunityDelete(pst_ttl_no);
		
		if(result == 1 ) {
			rec.addFlashAttribute("msg", "삭제성공");
		}else {
			rec.addFlashAttribute("msg", "삭제실패");
		}
		
		return "redirect:/user/comunityList";
	}
	
	@GetMapping("/attachment")
	@ResponseBody
	public ResponseEntity<File> attachment(@RequestParam("filepath") String filepath , @RequestParam("uuid") String uuid , @RequestParam("filename") String filename
			) {
		
		
			
		 String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
		
		 File file = new File(savepath);
		
		 ResponseEntity<File> result = null;
		 
		try {

			HttpHeaders header = new HttpHeaders();
			header.add("Content-Disposition", "attachment; filename=" + filename);
			
			//filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
			result = new ResponseEntity<>(file, header,HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
//		response.setHeader("Content-Disposition", "attachment; filename="+filename);
		
		 return result;
	}
	
	@PostMapping("/replyAdd")
	@ResponseBody
	public String replayAdd(@RequestBody ReplyVO vo) {
		
		
		userService.replyAdd(vo);
		
		return "success";
	}
	
	
}
