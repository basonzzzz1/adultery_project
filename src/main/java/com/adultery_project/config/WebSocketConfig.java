package com.adultery_project.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");
        config.enableSimpleBroker("/chat", "/topic", "/queue","/apiLike","/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
        .setAllowedOriginPatterns("*")
        .setAllowedOrigins("http://localhost:3000")
        .withSockJS();
    }

//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
////      đăng ký một đường dẫn của websocket có địa chỉ là "/ws"
////      và đăng ký cho tất cả cõ thể truy cập vào
////      cuối cùng là bật hỗ trợ sockJs
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
////        đặt tiền tố cho đích đến cho bộ định tuyến xử lý
////        hay có thể nói đăng ký một đường dẫn đến cho bộ định tuyến sử lý
////        còn enableSimpleBroker là tạo một đường dẫn để người dùng có thể
////        đăng ký lắng nghe sự kiện tại đó
//        registry.setApplicationDestinationPrefixes("/app")
//                .enableSimpleBroker("/chat");
//    }
}