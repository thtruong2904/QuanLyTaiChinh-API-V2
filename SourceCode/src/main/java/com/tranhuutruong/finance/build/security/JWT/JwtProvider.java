package com.tranhuutruong.finance.build.security.JWT;

import com.google.api.client.http.HttpStatusCodes;
import com.tranhuutruong.finance.build.entities.users.UserInformation;
import io.jsonwebtoken.*;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

//    public static final String jwtSecret = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final int jwtExpirationMs = 3600000;

    public String createToken(UserInformation userModel)
    {
        return Jwts.builder().setSubject(userModel.getAccountModel().getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .claim("authorities", userModel.getAccountModel().getRoleModel().getName())
                .compact();
    }

    public String refreshToken(String oldToken) {
        try
        {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(oldToken).getBody();
            String username = claims.getSubject();
            String authorities = (String) claims.get("authorities");
            Date issuedAt = claims.getIssuedAt();

            return Jwts.builder().setSubject(username).setIssuedAt(issuedAt).setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .claim("authorities", authorities)
                    .compact();
        }
        catch (Exception ex)
        {
            return "Có lỗi. Vui lòng thử lại";
        }
    }

    public boolean validateToken(String token)
    {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e)
        {
            logger.error("Invalid JWT signature!", e);
        }
        catch (MalformedJwtException e)
        {
            logger.error("The token invalid format!", e);
        }
        catch (UnsupportedJwtException e)
        {
            logger.error("Unsupported jwt token", e);
        }
        catch (ExpiredJwtException e)
        {
            logger.error("Expired jwt token", e);
        }
        catch (IllegalArgumentException e)
        {
            logger.error("JWT claims string is empty", e);
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }

    public Long getIdUserFromToken(String token)
    {
        String id = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        return Long.parseLong(id);
    }
}
