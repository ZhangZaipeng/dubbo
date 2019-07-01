package com.example.template.config.monitoring;

//import net.bull.javamelody.MonitoringFilter;
//import net.bull.javamelody.SessionListener;

/**
 * Created by zhangzp on 2018/9/19.
 * java 项目监控
 */
//@Configuration
public class JavamelodyConfiguration {

    /*@Bean
    @Order(Integer.MAX_VALUE-1)
    public FilterRegistrationBean monitoringFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MonitoringFilter());
        registration.addUrlPatterns("/*");
        registration.setName("monitoring");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<SessionListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SessionListener> slrBean = new ServletListenerRegistrationBean<SessionListener>();
        slrBean.setListener(new SessionListener());
        return slrBean;
    }*/

}
