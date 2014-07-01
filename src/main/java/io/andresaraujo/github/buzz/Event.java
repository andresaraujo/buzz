package io.andresaraujo.github.buzz;

public class Event {

    public final String channel;
    public final String topic;
    private final Object data;
    public final long timeStamp;

    public Event(String channel, String topic, Object data) {
        this.channel = channel;
        this.topic = topic;
        this.data = data;
        this.timeStamp = System.currentTimeMillis();
    }

    public <E> E getDataOr(Class<E> clazz, E or) {
        try {
            return clazz.cast(data);
        } catch (ClassCastException e) {
            return or;
        }
    }
}
