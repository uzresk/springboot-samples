package jp.gr.java_conf.uzresk.springboot.framework.dao;

import lombok.Data;
import lombok.ToString;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;

import java.time.LocalDateTime;

@Data
@Entity(naming = NamingType.SNAKE_UPPER_CASE, listener = BaseEntityListener.class)
public class BaseEntity {

    private String registUser;

    private LocalDateTime registDate;

}
