package io.andresaraujo.github.buzz;

public class SubscriptionDef {

    public final String channelName;
    public final String topic;
    public final BuzzListener listener;

    public SubscriptionDef(String channelName, String topic, BuzzListener listener) {
        this.channelName = channelName;
        this.topic = topic;
        this.listener = listener;
    }

    @Override
    public String toString() {
        return "SubscriptionDef{" +
                "channelName='" + channelName + '\'' +
                ", topic='" + topic + '\'' +
                ", listener=" + listener +
                '}';
    }
}
