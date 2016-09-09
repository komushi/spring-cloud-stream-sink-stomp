package io.pivotal.spring.cloud.stream.sink;

/**
 * Created by lei_xu on 6/26/16.
 */

import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix="stomp")
public class StompSinkProperties {

    /**
     * The Websocket Path on which a StompSink consumer needs to connect
     */
    private String endpoint = "stomp";

    /**
     * The topic to subscribe/publish
     */
    private String topic;

    /**
     * Whether to use SockJS
     */
    private Boolean withsockjs = false;


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

    public Boolean getWithsockjs() {
        return withsockjs;
    }

    public void setWithsockjs(Boolean withsockjs) {
        this.withsockjs = withsockjs;
    }
}
