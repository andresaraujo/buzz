package io.andresaraujo.github.buzz;

import java.util.ArrayList;
import java.util.HashMap;

public class Buzz {
    private static Buzz _buzz = new Buzz();
    private HashMap<String, HashMap<String, ArrayList<SubscriptionDef>>> subscriptions = new HashMap<String, HashMap<String, ArrayList<SubscriptionDef>>>(10);

    public static ChannelDef channel(String name) {
        return new ChannelDef(_buzz, name);
    }

    public void sub(String channelName, String topic, BuzzListener listener) {
        SubscriptionDef subDef = new SubscriptionDef(channelName, topic, listener);

        HashMap<String, ArrayList<SubscriptionDef>> channel = subscriptions.get(channelName);
        if (channel == null) {
            channel = new HashMap<String, ArrayList<SubscriptionDef>>(10);
            subscriptions.put(channelName, channel);
        }

        ArrayList<SubscriptionDef> subs = channel.get(topic);
        if (subs == null) {
            subs = new ArrayList<SubscriptionDef>(20);
            channel.put(topic, subs);
        }

        subs.add(subDef);
    }

    public void pub(String channel, String topic, Object data) {
        Event event = new Event(channel, topic, data);
        if (subscriptions.get(channel) != null) {
            ArrayList<SubscriptionDef> subs = subscriptions.get(channel).get(topic);
            if (subs != null && !subs.isEmpty()) {
                int size = subs.size();
                for (int i = 0; i < size; i++) {
                    subs.get(i).listener.on(event);
                }
            }
        }
    }
}
