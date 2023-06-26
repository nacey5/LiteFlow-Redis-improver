
package com.hzh.config;

/**
 * @author dahuang
 * @version : GrayFilter.java, v 0.1 2023-06-24 00:34 dahuang
 */
import com.hzh.util.ColorAloUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class GrayFilter extends AbstractGatewayFilterFactory<GrayFilter.Config> {

    public GrayFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String label = ColorAloUtil.selectColor(new String[] {"GREEN", "BLUE"}, new int[] {50, 100});
            String targetUri="";
            // 根据服务标签进行灰度处理
            if (label != null && label.contains(config.getLabel())) {
                // 在灰度环境中执行相应逻辑
                targetUri ="http://127.0.0.1:9888";
                // ...
            } else {
                // 在正常环境中执行相应逻辑
                targetUri ="http://127.0.0.1:9899";
                // ...
            }

            // 修改请求的目标地址
            exchange.getAttributes().put("custom_target_uri", targetUri);

            // 继续执行过滤器链
            return chain.filter(exchange);
        };
    }

    public static class Config {

        public static String COMMON_COLOR="BLUE";
        private String label;

        public Config(String label) {
            this.label = COMMON_COLOR;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
