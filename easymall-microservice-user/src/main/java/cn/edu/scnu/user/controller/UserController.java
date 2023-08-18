package cn.edu.scnu.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easymall.common.dao.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easymall.common.pojo.User;
import com.easymall.common.utils.CookieUtils;
import com.easymall.common.vo.SysResult;

import cn.edu.scnu.user.service.UserService;

@RestController
public class UserController {
    @Resource
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/manage/checkUserName")
    public SysResult checkUsername(String userName) {
        User userRes = userRepository.findByUserName(userName);
        if (userRes == null) {
            return SysResult.ok();
        } else {
            return SysResult.build(201, "用户名已经存在辣", null);
        }

//		Integer exist=userService.checkUsername(userName);
//		if(exist==0) {//不存在就是可用数据
//			return SysResult.ok();
//		}else {
//			return SysResult.build(201, "用户名已经存在辣",null);
//		}
    }

    @RequestMapping("/user/manage/save")
    public SysResult userSave(User user) {
        //1.username是否已经被注册
        Integer a = userService.checkUsername(user.getUserName());
        if (a > 0) {
            return SysResult.build(201, "用户名已经存在", null);
        }
        try {
            userService.userSave(user);
            return SysResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "添加失败", null);
        }
    }

    @RequestMapping("/user/manage/login")
    public SysResult doLogin(User user, HttpServletRequest request,
                             HttpServletResponse response) {
        //调用业务层确定合法且存储数据
        String ticket = userService.doLogin(user);
        //控制层将业务层存储登录成功的redisKey值
        //!"".equals(ticket)&&ticket
        if (StringUtils.isNotEmpty(ticket)) {
            //ticket非空，表示redis已经存好登录数据
            //将ticket作为cookie的值返回，cooki的名称根据接口文件的规定来定义
            //调用CookieUtils工具，将ticket添加到cookie返回前端
            CookieUtils.setCookie(request, response, "EM_TICKET", ticket);
            return SysResult.ok();
        } else {
            return SysResult.build(201, "登录失败", null);
        }

    }

    //通过cookie携带的ticket值查询redis中的user数据
    @RequestMapping("/user/manage/query/{ticket}")
    public SysResult checkLoginUser(@PathVariable String ticket) {
        String userJson = userService.queryUserJson(ticket);
        //判断非空
        System.out.println(ticket);
        if (StringUtils.isNotEmpty(userJson)) {
            //确实曾经登录过，也正在登录使用状态中
            return SysResult.build(200, "ok", userJson);
        } else {
            return SysResult.build(201, "fail", null);
        }
    }

}
