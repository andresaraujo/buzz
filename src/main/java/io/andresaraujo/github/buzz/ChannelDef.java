package io.andresaraujo.github.buzz;

public class ChannelDef {
    private Buzz _buzz;
    private final String channelName;

    public ChannelDef(Buzz _buzz, String channelName) {
        this.channelName = channelName;
        this._buzz = _buzz;
    }

    public void sub(String topic, BuzzListener listener) {
        _buzz.sub(channelName, topic, listener);
    }

    public <T> void pub(String topic, T data) {
        _buzz.pub(channelName, topic, data);
    }
}