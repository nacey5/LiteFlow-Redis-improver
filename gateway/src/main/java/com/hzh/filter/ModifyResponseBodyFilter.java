package com.hzh.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzh.mmon.BaseResponse;
import com.hzh.util.OrderedUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * 修改响应体过滤器
 *
 * @author MinWeikai
 * @date 2019-12-21 15:01:15
 */
@Slf4j
@Component
public class ModifyResponseBodyFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                        DataBuffer join = dataBufferFactory.join(dataBuffers);

                        byte[] content = new byte[join.readableByteCount()];

                        join.read(content);
                        // 释放掉内存
                        DataBufferUtils.release(join);
                        String str = new String(content, Charset.forName("UTF-8"));

                        // 构造自定义的响应数据对象
                        BaseResponse<Object> success = BaseResponse.success(str);

                        // 将自定义响应对象转换为 JSON 字符串
                        String responseJson = null;
                        try {
                            responseJson = new ObjectMapper().writeValueAsString(success);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }

                        log.info("返回体：{}", responseJson);
                        originalResponse.getHeaders().setContentLength(responseJson.getBytes().length);
                        return bufferFactory.wrap(responseJson.getBytes());
                    }));

                }
                // if body is not a flux. never got there.
                return super.writeWith(body);
            }
        };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());

    }

    @Override
    public int getOrder() {
        return OrderedUtil.MIN_CURE;
    }
}
