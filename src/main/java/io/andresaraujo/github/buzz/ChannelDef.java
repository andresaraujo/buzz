package io.andresaraujo.github.buzz;

public class ChannelDef {
    private final String channelName;

    public ChannelDef(String channelName) {
        this.channelName = channelName;
    }

    public SubscriptionDef sub(String topic, BuzzListener listener) {
        return Buzz._buzz.sub(channelName, topic, listener);
    }

    public void pub(String topic, Object data) {
        Buzz._buzz.pub(channelName, topic, data);
    }

    public void pub(String topic) {
        Buzz._buzz.pub(channelName, topic, null);
    }

    public int numListeners(String topic){
        return Buzz._buzz.numListeners(channelName, topic);
    }
    public int numListeners(){
        return Buzz._buzz.numListeners(channelName);
    }
}