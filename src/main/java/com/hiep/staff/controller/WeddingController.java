package com.hiep.staff.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hiep.staff.entity.WeddingEntity;
import com.hiep.staff.entity.WeddingViewerEntity;
import com.hiep.staff.mapper.WeddingMapper;
import com.hiep.staff.mapper.WeddingViewerMapper;
import com.hiep.staff.model.WeddingModel;
import com.hiep.staff.model.WeddingViewerModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/wedding")
@CrossOrigin("*")
public class WeddingController {

    @Autowired
    private WeddingMapper weddingMapper;

    @Autowired
    private WeddingViewerMapper weddingViewerMapper;

    @Autowired
    private JavaMailSender mailSender;

    private static final Logger log = LoggerFactory.getLogger(WeddingController.class);

    /**
     * API: Lưu lời chúc vào DB và gửi email thông báo
     */
    @PostMapping("/accept-wedding")
    public List<WeddingEntity> insertAcceptWedding(@RequestBody WeddingModel weddingModel) {
        log.info("Nhận request từ user: {}", weddingModel.getUser_name());

        if (weddingModel.getUser_name() == null || weddingModel.getUser_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên người gửi không được để trống!");
        }

        weddingMapper.insertAcceptWedding(weddingModel);
        log.info("Đã lưu lời chúc vào database: {}", weddingModel.getWish());

        List<WeddingEntity> allWishes = weddingMapper.getAllWish();
        sendMail(weddingModel.getUser_name(), allWishes);

        return allWishes;
    }

    /**
     * API: Lưu thông tin người dùng khi họ xem thiệp
     */
    @PostMapping("/view-wedding")
    public List<WeddingViewerEntity> insertViewer(@RequestBody WeddingViewerModel viewerModel) {
        log.info("Người dùng {} đang xem thiệp.", viewerModel.getUser_name());

        if (viewerModel.getUser_name() == null || viewerModel.getUser_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên người dùng không được để trống!");
        }

        // Chuẩn hóa giá trị relation trước khi lưu vào database
        String relation = viewerModel.getRelation();
        if ("bride".equalsIgnoreCase(relation)) {
            relation = "Cô Dâu";
        } else if ("groom".equalsIgnoreCase(relation)) {
            relation = "Chú Rể";
        } else if ("groom-bride".equalsIgnoreCase(relation)) {
            relation = "Dâu và Rể";
        }
        viewerModel.setRelation(relation);

        weddingViewerMapper.insertViewer(viewerModel);
        log.info("Đã lưu thông tin người xem vào database: {} - Quan hệ: {}", viewerModel.getUser_name(), relation);

        List<WeddingViewerEntity> allViewers = weddingViewerMapper.getAllViewers();
        sendViewerMail(viewerModel.getUser_name(), allViewers);

        return allViewers;
    }

    /**
     * Gửi email thông báo khi có người gửi lời chúc
     */
    private void sendMail(String userName, List<WeddingEntity> allWishes) {
        String recipientEmail = "hiepdeptrai0908@gmail.com";
        String subject = "📩 Bạn có một lời chúc mới từ " + userName;

        StringBuilder messageContent = new StringBuilder();
        messageContent.append("<html><body>");
        messageContent.append("<p>Nội dung:").append(allWishes.get(0).getWish()).append("</p>");
        messageContent.append("<br />");
        messageContent.append("<p>💌 Danh sách tất cả lời chúc:</p>");
        
        messageContent.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 100%;'>");
        messageContent.append("<tr><th>STT</th><th>Tên</th><th>Người Thân</th><th>Lời Chúc</th><th>Thời Gian</th><th>Tham Dự</th></tr>");
        
        int index = 1;
        for (WeddingEntity wish : allWishes) {
            String relation;
            switch (wish.getRelation()) {
                case "groom-bride":
                    relation = "dâu - rể";
                    break;
                case "groom":
                    relation = "chú rể";
                    break;
                case "bride":
                    relation = "cô dâu";
                    break;
                default:
                    relation = wish.getRelation();
                    break;
            }

            LocalDateTime time = wish.getCreated_at();
            String formattedTime = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            String attendance = wish.getAttendance() != null ? wish.getAttendance() : "Không rõ";

            messageContent.append("<tr>")
                          .append("<td>").append(index++).append("</td>")
                          .append("<td>").append(wish.getUser_name()).append("</td>")
                          .append("<td>").append(relation).append("</td>")
                          .append("<td>").append(wish.getWish()).append("</td>")
                          .append("<td>").append(formattedTime).append("</td>")
                          .append("<td>").append(attendance).append("</td>")
                          .append("</tr>");
        }
        messageContent.append("</table>");
        messageContent.append("</body></html>");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(messageContent.toString(), true);

            mailSender.send(message);
            log.info("📧 Email đã được gửi thành công đến: {}", recipientEmail);
        } catch (Exception e) {
            log.error("❌ Lỗi khi gửi email: {}", e.getMessage());
        }
    }

    /**
     * Gửi email khi có người xem thiệp
     */
    private void sendViewerMail(String userName, List<WeddingViewerEntity> allViewers) {
        String recipientEmail = "hiepdeptrai0908@gmail.com";
        String subject = "👀 Có người đang xem thiệp: " + userName;

        StringBuilder messageContent = new StringBuilder();
        messageContent.append("<html><body>");
        messageContent.append("<p>📜 Danh sách người đã xem thiệp:</p>");
        messageContent.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 100%;'>");
        messageContent.append("<tr><th>STT</th><th>Tên</th><th>Người Thân</th><th>Thời Gian Xem</th></tr>");

        int index = 1;
        for (WeddingViewerEntity viewer : allViewers) {
            LocalDateTime time = viewer.getViewed_at();
            String formattedTime = time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));

            messageContent.append("<tr>")
                          .append("<td>").append(index++).append("</td>")
                          .append("<td>").append(viewer.getUser_name()).append("</td>")
                          .append("<td>").append(viewer.getRelation()).append("</td>")
                          .append("<td>").append(formattedTime).append("</td>")
                          .append("</tr>");
        }
        messageContent.append("</table>");
        messageContent.append("</body></html>");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(messageContent.toString(), true);

            mailSender.send(message);
            log.info("📧 Email thông báo người xem thiệp đã được gửi đến: {}", recipientEmail);
        } catch (Exception e) {
            log.error("❌ Lỗi khi gửi email thông báo người xem: {}", e.getMessage());
        }
    }
    
    @GetMapping("/delete-username/{user_name}")
    public String deleteViewerByUsername(@PathVariable String user_name) {
    	weddingViewerMapper.deleteViewer(user_name);
    	return "Đã xoá người xem tên: " + user_name;
    }
    
    @GetMapping("/delete-id/{id}")
    public String deleteViewerById(@PathVariable Integer id) {
    	weddingViewerMapper.deleteViewerById(id);
    	return "Đã xoá người xem có id là: " + id;
    }
    
    @GetMapping("/show-viewers")
    public List<WeddingViewerEntity> getAllViewers(){
    	return weddingViewerMapper.getAllViewers();
    }
}
