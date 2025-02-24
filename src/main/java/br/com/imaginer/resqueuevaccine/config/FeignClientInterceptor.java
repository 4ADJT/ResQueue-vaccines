package br.com.imaginer.resqueuevaccine.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String BEARER_TOKEN_PREFIX = "Bearer ";

  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      String token = request.getHeader(AUTHORIZATION_HEADER);

      if (token != null) {
        if (!token.startsWith(BEARER_TOKEN_PREFIX)) {
          token = BEARER_TOKEN_PREFIX + token;
        }
        requestTemplate.header(AUTHORIZATION_HEADER, token);
      }
    }
  }
}
