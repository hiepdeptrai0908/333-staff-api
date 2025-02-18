package com.hiep.staff.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import com.hiep.staff.entity.WeddingEntity;
import com.hiep.staff.mapper.WeddingMapper;
import com.hiep.staff.model.WeddingModel;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/wedding")
@CrossOrigin
public class WeddingController {

    @Autowired
    private WeddingMapper weddingMapper;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/accept-wedding")
    public List<WeddingEntity> insertAcceptWedding(@RequestBody WeddingModel weddingModel) {
        log.info("Nhận request từ user: {}", weddingModel.getUser_name());

        // Kiểm tra dữ liệu đầu vào
        if (weddingModel.getUser_name() == null || weddingModel.getUser_name().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên người gửi không được để trống!");
        }

        // Lưu lời chúc vào database
        weddingMapper.insertAcceptWedding(weddingModel);
        log.info("Đã lưu lời chúc vào database: {}", weddingModel.getWish());

        // Lấy toàn bộ danh sách lời chúc
        List<WeddingEntity> allWishes = weddingMapper.getAllWish();

        // Gửi email thông báo
        sendMail(weddingModel.getUser_name(), allWishes);

        return allWishes;
    }

    private void sendMail(String userName, List<WeddingEntity> allWishes) {
        String recipientEmail = "hiepdeptrai0908@gmail.com";
        String subject = "📩 Bạn có một lời chúc mới từ " + userName;

        // Xây dựng nội dung email với HTML
        StringBuilder messageContent = new StringBuilder();
        messageContent.append("<html><body>");
        messageContent.append("<p>💌 Danh sách tất cả lời chúc:</p>");
        
        // Tạo bảng HTML
        messageContent.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse; width: 100%;'>");
        messageContent.append("<tr><th>STT</th><th>Tên</th><th>Người Thân</th><th>Lời Chúc</th><th>Thời Gian</th><th>Tham Dự</th></tr>");
        
        // Thêm các hàng vào bảng
        int index = 1;
        for (WeddingEntity wish : allWishes) {
            // Tùy chỉnh relation
            String relation = wish.getRelation();
            if ("groom-bride".equals(relation)) {
                relation = "dâu - rể";
            } else if ("groom".equals(relation)) {
                relation = "chú rể";
            } else if ("bride".equals(relation)) {
                relation = "cô dâu";
            }

            // Format thời gian 
            LocalDateTime time = wish.getCreated_at();
            String day = String.format("%02d", time.getDayOfMonth());  // Thêm số 0 nếu ngày < 10
            String month = String.format("%02d", time.getMonthValue());  // Thêm số 0 nếu tháng < 10
            String year = String.valueOf(time.getYear());
            String hour = String.format("%02d", time.getHour());  // Thêm số 0 nếu giờ < 10
            String minute = String.format("%02d", time.getMinute());  // Thêm số 0 nếu phút < 10
            String formatTime = day + "-" + month + "-" + year + " " + hour + ":" + minute;

            // Lấy thông tin Tham Dự (attendance)
            String attendance = wish.getAttendance() != null ? wish.getAttendance() : "Không rõ";

            // Thêm vào bảng
            messageContent.append("<tr>")
                          .append("<td>").append(index++).append("</td>")
                          .append("<td>").append(wish.getUser_name()).append("</td>")
                          .append("<td>").append(relation).append("</td>")
                          .append("<td>").append(wish.getWish()).append("</td>")
                          .append("<td>").append(formatTime).append("</td>")
                          .append("<td>").append(attendance).append("</td>")
                          .append("</tr>");
        }
        messageContent.append("</table>");
        
        messageContent.append("</body></html>");

        // Tạo email dưới dạng MimeMessage
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(messageContent.toString(), true);  // true cho phép gửi nội dung dạng HTML

            // Gửi email
            mailSender.send(message);
            log.info("📧 Email đã được gửi thành công đến: {}", recipientEmail);
        } catch (Exception e) {
            log.error("❌ Lỗi khi gửi email: {}", e.getMessage());
        }
    }

}
