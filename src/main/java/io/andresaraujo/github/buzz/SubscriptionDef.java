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

    public void unsubscribe(){
        Buzz._buzz.unsubscribe(this);
    }

    @Override
    public String toString() {
        return "SubscriptionDef{" +
                "channelName='" + channelName + '\'' +
                ", topic='" + topic + '\'' +
                ", listener=" + listener +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubscriptionDef)) return false;

        SubscriptionDef that = (SubscriptionDef) o;

        if (!channelName.equals(that.channelName)) return false;
        if (!listener.equals(that.listener)) return false;
        if (!topic.equals(that.topic)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = channelName.hashCode();
        result = 31 * result + topic.hashCode();
        result = 31 * result + listener.hashCode();
        return result;
    }
}
