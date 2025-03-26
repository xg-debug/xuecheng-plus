package com.xuecheng.orders.config;

/**
 * @description 支付宝配置参数
 */
public class AlipayConfig {
    // 商户appid
    public static String APPID = "9021000146624599";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC3AHFnLVdfKWD6ZPGBnYbx0e0ebCVubrw6m3Vs5B+9zX1XDAI3UFoFNUm+5ISc7xW8QTCzYisF3YlrHJohdvkOJvC3GwTcEQpEfyBCJ9xYMspPz0mt2be3GNUO7lZvuIbnbcQHmJzDDtypTNg965vyJYQaD1LNHB3W3VEAhesHy0rOetyWA06GanZNWqc3OYYWtWpz3r0bmxrf89+czB+8jRaIfw9Op+WkmizDngyZbV3CEx+kbe+fOMtcX2x2MSjPRI0NuNI+JB2HFCJWSqUCjPZtHXvY7bf6HtNSJ6t6Dri07kiq7CtOTxB+V/2+aVOh7+CsZb51DEbWllRwgrfzAgMBAAECggEAGJBxqHTYnohyYQn9tPVNMNaJ6qR/ncQ1dfR6HaS9cf/MTEvrXDtn6f0OKhaIinw5QazKebm6pq3nDT6oncdXhQPLGfkBfPy4kRszXE33cMtYhr//Vtu7olMt/jQV3Cc7zILaZl6g5DnxerQ67ozawPUyN0FJTwjwJHBOuDBBrSjsz6N0IXl8IjN09IW6eSbe69ubs6OMqAJ96rBRzisSST2g707mJoo+USwPJUkEGRVzLh4MgX0XT3BMOvfn+91aJUx7HaVwHJSaujOqWB7zdmaeut9QwSacc/Wf5mBkL+AJDfHWeqjdDZF86JowzwsklXy3hMMqjqOjLwzQeZcGUQKBgQD9AH2LdrnEbLcVf79lMnL5Luw+GQz5wza42XIHImDV97dw/gojCD0UgOQFSc6AnO8IfDK4c2+OHBivRw/g05eB0xrKwNX5kaPGdVj5XbdkwZXUAybarWF8egcXiBOq80BtBu19/w1NxJYRJYOfOKIFVXGO1nGTv0AbGybjhkkW6QKBgQC5K5lkL3MMc8AmG31QMbnyl3O+/JVmPEbCqf0hpxA368Yhe62n/eLzB6W7ahdxnjhPDtcaDvyNq/6Q6lMnxI0UlsHxuyIPeFxkQfCSwjxAawrywvPRUYODgiCkesUX4wUYM2779CWln2qHGYhJpoS0dBdGAcJ7f+0ePQzrY3lGewKBgQDEHE00faMhjuOb1poVZzWX0bdtqx1TvO39+w9YWF2XfZzECSbyYMB31DDrqHpZV0wx15O5RkGCdmTr+LlmHJ2kFG8AGtnByhKZEv0UzoAJqcTpwker2hlm03AL3X7hVAzsdiJBOCKn1Wo9gEa2dL+iQsjzX2ZKkESwvis20jB1gQKBgQCtb7DUrbzMxP4Ms2LEYk8Qcdrv4BTvFTYRzrZRdvEx75w8cGNPHfYcO+V3jP84ZEHJitTei3ryF07w3QnWDbs2tZ4ilGxqoQmovjpeu59Dlv9CMme8fuT3oEKCzJserHxxFnu1chBw+MgGkluc4GMbwkCqSaunkUK/N9+J+jn1UQKBgDjPtlWUkPh9+CtdqdKwXDfqQe/+RASWyufhfaw4SbBpX6ZyuXRpIXrigrjAv/hVRD3UXmILLkHKsxdnzplZskTBN90evMggdjMScsRRSy+IZXnKdVgPtGy9ThEoFham++UkvXSyGsz6HlrlyNv7f34uIo8kr/8c+9maQGhUOYOe";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
    // 请求网关地址
    public static String URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";
}
