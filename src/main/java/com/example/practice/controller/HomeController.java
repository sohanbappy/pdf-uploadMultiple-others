package com.example.practice.controller;

import com.example.practice.exception.CustomIOException;
import com.example.practice.model.User;
import com.example.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    public static String uploadDir = System.getProperty("user.dir")+"/uploadedFilesForProject";
    @Autowired
    UserService userService;
    @Autowired
    ServletContext context;

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public String home(Model model) {
        String start = "2020-01-20";
        String end   = "2020-01-25";
        LocalDate startDate = LocalDate.parse(start);
     //   Timestamp starTimeStamp = Timestamp.valueOf(startDate.atStartOfDay());
        LocalDate endDate = LocalDate.parse(end);
     //   Timestamp endTimeStamp = Timestamp.valueOf(endDate.atStartOfDay());
        List<LocalDate> allDates =  startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());
        model.addAttribute("allDates",allDates);
        return "home";
    }
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(Model model, @RequestParam("files") MultipartFile[] files) throws  CustomIOException {
        String msg = "";
        StringBuilder fileNames = new StringBuilder();
        for(MultipartFile file : files){
            Path fileNameAndpath = Paths.get(uploadDir, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            try{
                Files.write(fileNameAndpath,file.getBytes());
                msg = "Successfully uploaded";
            }catch (IOException e){
               throw new CustomIOException("File is not selected");
            }
        }
        model.addAttribute("msg",msg);
        return "redirectPage";
    }

    @RequestMapping(value = "/generatePdf",method = RequestMethod.GET)
    public void generatePdf(HttpServletRequest request, HttpServletResponse response){
        List<User> users = userService.getAllUsers();
        boolean flag = userService.createPdf(users,context);
        if(flag){
            String fullPath = request.getServletContext().getRealPath("/resources/reports/"+"users"+".pdf");
            fileDownLoad(fullPath,response,"users.pdf");
        }
    }

    private void fileDownLoad(String fullPath, HttpServletResponse response, String fileName) {
        File file = new File(fullPath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
            FileInputStream inputStream = new FileInputStream(file);
            String mimeType = context.getMimeType(fullPath);
            response.setContentType(mimeType);
            response.setHeader("content-disposition", "attachment; filename=" + fileName);
            OutputStream outputStream = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,bytesRead);
            }
            inputStream.close();
            outputStream.close();
            file.delete();

        } catch(IOException e){
            e.printStackTrace();
        }
       }
    }

}
