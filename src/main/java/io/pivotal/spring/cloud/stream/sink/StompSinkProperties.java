package io.pivotal.spring.cloud.stream.sink;

/**
 * Created by lei_xu on 6/26/16.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix="stomp")
public class StompSinkProperties {


    public static final String DEFAULT_ENDPOINT = "stomp";


    /**
     * the websocketPath on which a StompSink consumer needs to connect. Default is <tt>/stomp</tt>
     */
    String endpoint = DEFAULT_ENDPOINT;

    String topic;

    Boolean withSockJS = false;


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Boolean getWithSockJS() {
        return withSockJS;
    }

    public void setWithSockJS(Boolean withSockJS) {
        this.withSockJS = withSockJS;
    }
}
