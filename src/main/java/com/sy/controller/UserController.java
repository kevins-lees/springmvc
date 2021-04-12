package com.sy.controller;

import com.sy.domain.User;
import com.sy.service.UserService;
import com.vo.ToServiceVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/sy/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/insertUser.do")
    public @ResponseBody Object login(String username, String password,
                                      @RequestParam("file") MultipartFile file ,
                                      HttpServletRequest request) throws IOException {
        System.out.println(username+"===username");
        System.out.println(password+"==password");
        String fileName = file.getOriginalFilename();
        System.out.println(fileName+"==filename");
        String path =request.getSession().getServletContext().getRealPath("/upload/");
        File files = new File(path);
        if(!files.exists()){
            files.mkdir();
        }
        String filename=file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename=uuid+"_"+filename;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setHeadImg(filename);
        ToServiceVo toServiceVo=userService.addUser(user);
        if(toServiceVo.isSuccess()){
            file.transferTo(new File(path,filename));
        }
            return toServiceVo;
    }

    @RequestMapping("/login.do")
    public @ResponseBody Object login2(HttpServletRequest request,String username,String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User user1 =userService.findUser(user);
        ToServiceVo toServiceVo = new ToServiceVo();
        if(user1!=null){
            request.getSession().setAttribute("user",user1);
        }else {
            toServiceVo.setSuccess(false);
            toServiceVo.setMsg("用户名密码错误");
        }

        return toServiceVo;
    }

    @RequestMapping("/getUser.do")
    public @ResponseBody Object login3(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        ToServiceVo toServiceVo = new ToServiceVo();
        toServiceVo.setDate(user);
        return toServiceVo;
    }

    @RequestMapping("/upload.do")
    public ResponseEntity<byte[]> doBiz10(HttpServletRequest request) throws Exception{
        //来自服务器的文件
        User user = (User) request.getSession().getAttribute("user");
        String headImg = user.getHeadImg();
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        path=path+File.separator+headImg;


        //创建字节数组,通过流读入数组
        InputStream is = new FileInputStream(new File(path));
        byte[] bs = new byte[is.available()];
        is.read(bs,0,bs.length);
        //设置头信息
        HttpHeaders headers = new HttpHeaders();
        //以附件的形式下载
        headers.setContentDispositionFormData("attachment",URLEncoder.encode(headImg,"utf-8"));

        return new ResponseEntity(bs ,headers, HttpStatus.OK);
    }
}
