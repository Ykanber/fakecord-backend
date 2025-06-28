package org.yk.fakecordbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final String SECRET_KEY =
      "yavuz123456yavuz123456yavuz123456yavuz123456yavuz123456yavuz123456";

  // Token geçerlilik süresi (örneğin 10 saat)
  private final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

  // Token oluşturma
  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  // Token'dan username çıkarma
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Genel claim çıkarma metodu
  public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Tüm claimleri çıkar
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  // Token süresinin dolup dolmadığını kontrol et
  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Token expiration bilgisini çıkar
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Token doğrulama metodu
  public boolean isTokenValid(String token, UserDetails userDetails) {
    try {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    } catch (ExpiredJwtException
        | UnsupportedJwtException
        | MalformedJwtException
        | SignatureException
        | IllegalArgumentException e) {
      // Geçersiz token
      return false;
    }
  }
}
