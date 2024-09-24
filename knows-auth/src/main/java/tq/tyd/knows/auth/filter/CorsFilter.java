package tq.tyd.knows.auth.filter;

import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";

    public static final String OPTIONS = "OPTIONS";

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (isCorsRequest(httpRequest)) {
            // 设置允许的域，'*' 表示允许所有域
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            // 设置允许的方法
            httpResponse.setHeader("Access-Control-Allow-Methods",
                    "POST, GET, PUT, DELETE");
            // 设置是否允许发送 Cookie
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            // response.setIntHeader("Access-Control-Max-Age", 1728000);
            // 设置允许的请求头
            httpResponse
                    .setHeader(
                            "Access-Control-Allow-Headers",
                            "Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Authorization");
            // 如果是预检请求，直接返回
            if (isPreFlightRequest(httpRequest)) {
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

    public boolean isCorsRequest(HttpServletRequest request) {
        return (request.getHeader(HttpHeaders.ORIGIN) != null);
    }
    public boolean isPreFlightRequest(HttpServletRequest request) {
        return (isCorsRequest(request) && OPTIONS.equals(request.getMethod()) && request
                .getHeader(ACCESS_CONTROL_REQUEST_METHOD) != null);
    }
}
/*这个 CorsFilter 类实现了一个简单的跨域资源共享（CORS）的过滤器，允许服务器处理跨域请求。让我们逐步分析代码，并解释每个部分的作用和流程。

代码分析
1. 类声明与常量定义
java
复制代码
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter implements Filter {
    public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
    public static final String OPTIONS = "OPTIONS";
CorsFilter 类实现了 javax.servlet.Filter 接口，表示这是一个过滤器。
ACCESS_CONTROL_REQUEST_METHOD 和 OPTIONS 是常量，用于在后面的代码中检测预检请求（pre-flight request）。
2. doFilter 方法
java
复制代码
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
doFilter 方法是 Filter 接口必须实现的方法。它处理传入的请求和响应，并决定是否继续链中的下一个过滤器。
将 ServletRequest 和 ServletResponse 转换为 HttpServletRequest 和 HttpServletResponse，以便处理 HTTP 特有的请求和响应属性。
3. CORS 处理
java
复制代码
        if (isCorsRequest(httpRequest)) {
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods",
                    "POST, GET, PUT, DELETE");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            // response.setIntHeader("Access-Control-Max-Age", 1728000);
            httpResponse
                    .setHeader(
                            "Access-Control-Allow-Headers",
                            "Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Authorization");
            if (isPreFlightRequest(httpRequest)) {
                return;
            }
        }
isCorsRequest(httpRequest)：检测请求是否是跨域请求。
如果是跨域请求，设置 CORS 相关的 HTTP 响应头：
Access-Control-Allow-Origin：允许所有来源的跨域请求。
Access-Control-Allow-Methods：允许的 HTTP 方法。
Access-Control-Allow-Credentials：允许发送凭据。
Access-Control-Allow-Headers：允许的请求头。
如果是预检请求（pre-flight request），返回而不继续过滤链，因为预检请求只需要返回 CORS 头。
4. 继续过滤链
java
复制代码
        chain.doFilter(request, response);
    }
如果不是预检请求，继续过滤链，处理下一个过滤器或目标资源。
5. init 和 destroy 方法
java
复制代码
    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
init 和 destroy 是 Filter 接口必须实现的方法，可以用于初始化和清理资源，这里没有实现任何逻辑。
6. 辅助方法
java
复制代码
    public boolean isCorsRequest(HttpServletRequest request) {
        return (request.getHeader(HttpHeaders.ORIGIN) != null);
    }

    public boolean isPreFlightRequest(HttpServletRequest request) {
        return (isCorsRequest(request) && OPTIONS.equals(request.getMethod()) && request
                .getHeader(ACCESS_CONTROL_REQUEST_METHOD) != null);
    }
}
isCorsRequest：检查请求是否是跨域请求，方法是检查 Origin 头是否存在。
isPreFlightRequest：检查请求是否是预检请求，方法是检查请求方法是否是 OPTIONS，并且 Access-Control-Request-Method 头是否存在。
流程总结
过滤器捕获到一个请求。
将请求和响应转换为 HttpServletRequest 和 HttpServletResponse。
检查请求是否是跨域请求（通过 Origin 头）。
如果是跨域请求，设置 CORS 相关的响应头。
如果是预检请求（OPTIONS），返回响应，不继续过滤链。
如果不是预检请求，继续过滤链。
如何记忆
CORS 基本概念：理解跨域请求的基础知识，以及为什么需要 CORS。
过滤器的作用：了解过滤器在请求处理链中的位置和作用。
关键响应头：记住关键的 CORS 响应头，如 Access-Control-Allow-Origin、Access-Control-Allow-Methods 等。
预检请求：理解预检请求的作用和处理方式。
代码结构：分步骤理解代码逻辑，每一步的作用是什么。
通过反复阅读和理解代码，并结合对 CORS 基本概念的掌握，可以更好地记忆和理解这段代码。






*/
