package top.jiangliuhong.olcp.auth.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenResult {

    private String token;
    private Date expiration;
}
