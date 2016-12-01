

/**
 * Created by lei_xu on 6/26/16.
 */

package io.pivotal.spring.cloud.stream.sink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Configuration
@EnableBinding(Sink.class)
@EnableConfigurationProperties(StompSinkProperties.class)
@EnableWebSocketMessageBroker
public class StompSinkConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

    private static final Log logger = LogFactory.getLog(StompSinkConfiguration.class);

    @Autowired
    private StompSinkProperties properties;

    @Autowired
    private SimpMessagingTemplate template;


    @ServiceActivator(inputChannel=Sink.INPUT)
    public void handle(Message<?> message) {
        if (logger.isTraceEnabled()) {
            logger.trace(String.format("Handling message: %s", message));
        }

        String topic = properties.getTopic();

        if (topic == null) {
            topic = (String)message.getHeaders().get("amqp_receivedExchange");
        }

        StompPayload stompPayload = new StompPayload(message.getPayload());

        template.convertAndSend("/topic/" + topic, stompPayload);


    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        Boolean withSockJS = properties.getWithsockjs();
        if (withSockJS) {
            registry.addEndpoint(properties.getEndpoint()).setAllowedOrigins("*").withSockJS();
        }
        else {
            registry.addEndpoint(properties.getEndpoint()).setAllowedOrigins("*");    
        }
        

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/topic");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(
                new ChannelInterceptorAdapter() {
                    @Override
                    public Message<?> preSend(Message<?> message, MessageChannel channel) {
                        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                        if (accessor.getCommand() != StompCommand.SEND) {
                            if (logger.isInfoEnabled()) {
                                logger.info(String.format("%s: %s", channel, message));
                            }
                        } else {
                            if (logger.isTraceEnabled()) {
                                logger.trace(String.format("%s: %s", channel, message));
                            }
                        }
                        return message;
                    }
                }
        );
    }
}
