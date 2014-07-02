package io.andresaraujo.github.buzz;

public class ChannelDef {
    private Buzz _buzz;
    private final String channelName;

    public ChannelDef(Buzz _buzz, String channelName) {
        this.channelName = channelName;
        this._buzz = _buzz;
    }

    public SubscriptionDef sub(String topic, BuzzListener listener) {
        return _buzz.sub(channelName, topic, listener);
    }

    public void pub(String topic, Object data) {
        _buzz.pub(channelName, topic, data);
    }

    public void pub(String topic) {
        _buzz.pub(channelName, topic, null);
    }
}