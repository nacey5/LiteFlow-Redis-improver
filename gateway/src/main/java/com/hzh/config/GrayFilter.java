
package com.hzh.config;

/**
 * @author dahuang
 * @version : GrayFilter.java, v 0.1 2023-06-24 00:34 dahuang
 */
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class GrayFilter extends AbstractGatewayFilterFactory<GrayFilter.Config> {

    public GrayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 获取请求中的服务标签
            List<String> labels = exchange.getRequest().getHeaders().get("Service-Label");

            // 根据服务标签进行灰度处理
            if (labels != null && labels.contains(config.getLabel())) {
                // 在灰度环境中执行相应逻辑
                // ...
            } else {
                // 在正常环境中执行相应逻辑
                // ...
            }

            // 继续执行过滤器链
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
