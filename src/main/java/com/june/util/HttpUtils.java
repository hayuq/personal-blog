package com.june.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * HTTP请求工具类
 */
public @Slf4j class HttpUtils {

    private static final String CONTENT_TYPE = "Content-Type";

    public static final HttpHeader HEADER_TEXT_PLAIN = HttpHeader.of(HttpUtils.CONTENT_TYPE, "text/plain");
    public static final HttpHeader HEADER_TEXT_HTML = HttpHeader.of(HttpUtils.CONTENT_TYPE, "text/html");
    public static final HttpHeader HEADER_APPLICATION_JSON = HttpHeader.of(HttpUtils.CONTENT_TYPE, "application/json");

    /**
     * 默认超时时间30s
     */
    private static final int DEFAULT_TIMEOUT = 30000;

    private HttpUtils() {
        throw new UnsupportedOperationException("No instance for this class!");
    }

    public static String get(String url, String param, Map<String, String> headers) throws IOException {
        return getText(request(HttpMethod.GET.name(), url, param, headers));
    }

    public static String get(String url, String param, HttpHeader... headers) throws IOException {
        return getText(request(HttpMethod.GET.name(), url, param, headers));
    }

    public static String post(String url, String body, Map<String, String> headers) throws IOException {
        return getText(request(HttpMethod.POST.name(), url, body, headers));
    }

    public static String post(String url, String body, HttpHeader... headers) throws IOException {
        return getText(request(HttpMethod.POST.name(), url, body, headers));
    }

    private static String getText(InputStream in) throws IOException {
        if (in == null) {
            throw new IllegalArgumentException("parameter should not be null");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
    }

    public static InputStream request(String method, String url, String paramOrBody,
                                      HttpHeader... headers) throws IOException {
        int len = headers.length;
        Map<String, String> headerMap = new HashMap<>(len);
        if (len > 0) {
            HttpHeader header;
            for (int i = 0; i < len; i++) {
                header = headers[i];
                headerMap.put(header.getName(), header.getValue());
            }
        }
        return request(method, url, paramOrBody, headerMap);
    }

    public static InputStream request(String method, String url, String paramOrBody,
                                      Map<String, String> headers) throws IOException {
        return request(method, url, paramOrBody, HttpUtils.DEFAULT_TIMEOUT, HttpUtils.DEFAULT_TIMEOUT, headers);
    }

    public static InputStream request(String method, String url, String paramOrBody, int connectTimeout,
                                      int readTimeout, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(method);
            if (!HttpMethod.GET.matches(method)) {
                conn.setDoOutput(true);
            }
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            // 添加请求头
            Optional.ofNullable(headers).orElseGet(HashMap::new).forEach(conn::setRequestProperty);
            // 打开连接
            conn.connect();
            if (StringUtils.isNotEmpty(paramOrBody)) {
                try (OutputStream out = conn.getOutputStream();
                     OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8)) {
                    writer.write(paramOrBody);
                    writer.flush();
                }
            }
            InputStream is;
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                HttpUtils.log.error("HTTP请求失败: {}, {}", responseCode, conn.getResponseMessage());
                is = conn.getErrorStream();
            }
            is = conn.getInputStream();
            return is;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    @AllArgsConstructor
    public static @Data class HttpHeader {

        private String name;
        private String value;

        public static HttpHeader of(String name, String value) {
            return new HttpHeader(name, value);
        }

    }

    public static enum HttpMethod {

        /**
         * HTTP 方法类型
         */
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

        public boolean matches(String method) {
            if (method == null) {
                return this == null;
            }
            return this == HttpMethod.valueOf(method.toUpperCase());
        }

    }

}
