package com.example.practice.service;

import com.example.practice.model.User;
import com.example.practice.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean createPdf(List<User> users,ServletContext context){
        Document document = new Document(PageSize.A4,15,15,45,30);
        try{
            String filePath = context.getRealPath("/resources/reports");
            File file = new File(filePath);
            boolean exists = new File(filePath).exists();
            if(!exists){
                new File(filePath).mkdirs();
            }
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(file+"/"+"users"+".pdf"));
            document.open();
            Font mainFont = FontFactory.getFont("Arial",10, BaseColor.BLACK);

            Paragraph paragraph = new Paragraph("All Users",mainFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setIndentationLeft(50);
            paragraph.setIndentationRight(50);
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10);

            Font tableHeaderFont = FontFactory.getFont("Arial",10,BaseColor.BLACK);
            Font tableBodyFont = FontFactory.getFont("Arial",10,BaseColor.BLACK);

            float[] columnWidths = {2f,2f,2f,2f};
            table.setWidths(columnWidths);
            //Header
            PdfPCell idHead = new PdfPCell(new Paragraph("ID",tableHeaderFont));
            idHead.setBorderColor(BaseColor.BLACK);
            idHead.setPaddingLeft(10);
            idHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            idHead.setVerticalAlignment(Element.ALIGN_CENTER);
            idHead.setBackgroundColor(BaseColor.GRAY);
            idHead.setExtraParagraphSpace(5f);
            table.addCell(idHead);

            PdfPCell nameHead = new PdfPCell(new Paragraph("Name",tableHeaderFont));
            nameHead.setBorderColor(BaseColor.BLACK);
            nameHead.setPaddingLeft(10);
            nameHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            nameHead.setVerticalAlignment(Element.ALIGN_CENTER);
            nameHead.setBackgroundColor(BaseColor.GRAY);
            nameHead.setExtraParagraphSpace(5f);
            table.addCell(nameHead);

            PdfPCell phoneHead = new PdfPCell(new Paragraph("Phone",tableHeaderFont));
            phoneHead.setBorderColor(BaseColor.BLACK);
            phoneHead.setPaddingLeft(10);
            phoneHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            phoneHead.setVerticalAlignment(Element.ALIGN_CENTER);
            phoneHead.setBackgroundColor(BaseColor.GRAY);
            phoneHead.setExtraParagraphSpace(5f);
            table.addCell(phoneHead);

            PdfPCell passHead = new PdfPCell(new Paragraph("Password",tableHeaderFont));
            passHead.setBorderColor(BaseColor.BLACK);
            passHead.setPaddingLeft(10);
            passHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            passHead.setVerticalAlignment(Element.ALIGN_CENTER);
            passHead.setBackgroundColor(BaseColor.GRAY);
            passHead.setExtraParagraphSpace(5f);
            table.addCell(passHead);

            for (User user: users
                 ) {
                PdfPCell id = new PdfPCell(new Paragraph(String.valueOf(user.getId()),tableBodyFont));
                id.setBorderColor(BaseColor.BLACK);
                id.setPaddingLeft(10);
                id.setHorizontalAlignment(Element.ALIGN_CENTER);
                id.setVerticalAlignment(Element.ALIGN_CENTER);
                id.setBackgroundColor(BaseColor.GRAY);
                id.setExtraParagraphSpace(5f);
                table.addCell(id);

                PdfPCell name = new PdfPCell(new Paragraph(user.getName(),tableBodyFont));
                name.setBorderColor(BaseColor.BLACK);
                name.setPaddingLeft(10);
                name.setHorizontalAlignment(Element.ALIGN_CENTER);
                name.setVerticalAlignment(Element.ALIGN_CENTER);
                name.setBackgroundColor(BaseColor.GRAY);
                name.setExtraParagraphSpace(5f);
                table.addCell(name);

                PdfPCell phone = new PdfPCell(new Paragraph(user.getPhone(),tableBodyFont));
                phone.setBorderColor(BaseColor.BLACK);
                phone.setPaddingLeft(10);
                phone.setHorizontalAlignment(Element.ALIGN_CENTER);
                phone.setVerticalAlignment(Element.ALIGN_CENTER);
                phone.setBackgroundColor(BaseColor.GRAY);
                phone.setExtraParagraphSpace(5f);
                table.addCell(phone);

                PdfPCell pass = new PdfPCell(new Paragraph(user.getPassword(),tableBodyFont));
                pass.setBorderColor(BaseColor.BLACK);
                pass.setPaddingLeft(10);
                pass.setHorizontalAlignment(Element.ALIGN_CENTER);
                pass.setVerticalAlignment(Element.ALIGN_CENTER);
                pass.setBackgroundColor(BaseColor.GRAY);
                pass.setExtraParagraphSpace(5f);
                table.addCell(pass);
            }



            document.add(table);
            document.close();
            writer.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }
}
