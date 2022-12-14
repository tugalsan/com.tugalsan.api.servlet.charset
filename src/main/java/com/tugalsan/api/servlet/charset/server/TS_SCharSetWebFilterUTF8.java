package com.tugalsan.api.servlet.charset.server;

import com.tugalsan.api.charset.client.*;
import com.tugalsan.api.log.server.*;
import com.tugalsan.api.unsafe.client.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebFilter(
        urlPatterns = {"/*"},
        initParams = {
            @WebInitParam(name = "requestEncoding", value = TGS_CharSetUTF8.UTF8)
        }
)
public class TS_SCharSetWebFilterUTF8 implements Filter {

    final private static TS_Log d = TS_Log.of(TS_SCharSetWebFilterUTF8.class);

    @Override
    public void init(FilterConfig config) {
        TGS_UnSafe.execute(() -> {
            encoding = config.getInitParameter("requestEncoding");
            if (encoding == null) {
                encoding = TGS_CharSetUTF8.UTF8;
            }
        });
    }
    private String encoding;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) {
        TGS_UnSafe.execute(() -> {

            //REQUEST.COMMON
            if (null == request.getCharacterEncoding()) {
                request.setCharacterEncoding(encoding);
            }

            //REQUEST.HTML
            if (String.valueOf(response.getContentType()).startsWith("text/html")) {
                response.setContentType("text/html; charset=" + TGS_CharSetUTF8.UTF8);
            }

            //RESPONSE
            response.setCharacterEncoding(TGS_CharSetUTF8.UTF8);

            //ESCALATE
            next.doFilter(request, response);
        }, e -> {
            if (e.getClass().getName().equals("org.apache.catalina.connector.ClientAbortException")) {
                if (request instanceof HttpServletRequest hsr) {
                    d.cr("doFilter", "CLIENT GAVE UP", e.getMessage(), hsr.getRequestURL().toString() + "?" + hsr.getQueryString());
                } else {
                    d.cr("doFilter", "CLIENT GAVE UP", e.getMessage());
                }
                return;
            }
            TGS_UnSafe.execute(() -> next.doFilter(request, response));//ESCALATE WITHOUT DEF_CHARSET
        });
    }

    @Override
    public void destroy() {
    }
}
