package cn.edu.scnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easymall.common.vo.PicUploadResult;

import cn.edu.scnu.Service.PicService;

@RestController
public class PicController {
	@Autowired
	private PicService picService;
	
	@RequestMapping("/pic/upload")
	public PicUploadResult picUpload(MultipartFile pic) {
		PicUploadResult result=picService.picUpload(pic);
		return result;
	}
}
