package top.jiangliuhong.olcp.auth.handler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import top.jiangliuhong.olcp.auth.bean.TokenResult;
import top.jiangliuhong.olcp.auth.properties.AuthProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTokenHandler {
    @Autowired
    private AuthProperties authProperties;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            log.error("parse token error",e);
            username = null;
        }
        return username;
    }

    /**
     * 校验token
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 根据用户信息生成token
     */
    public TokenResult generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getClaimsFromToken(token).getExpiration();
        return expiredDate.before(new Date());
    }

    private TokenResult generateToken(Map<String, Object> claims) {
        Date expirationDate = generateExpirationDate();
        String token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                //签名算法
                .signWith(SignatureAlgorithm.HS512, authProperties.getJwt().getSecret())
                .compact();
        TokenResult result = new TokenResult();
        result.setToken(token);
        result.setExpiration(expirationDate);
        return result;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + authProperties.getJwt().getExpiration() * 1000);
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(authProperties.getJwt().getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("JWT格式验证失败");
        }
    }

}
