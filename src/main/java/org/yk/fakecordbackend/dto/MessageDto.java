package org.yk.fakecordbackend.dto;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MessageDto {

  private String author;

  private String messageText;

  private LocalDateTime createTime;
}
