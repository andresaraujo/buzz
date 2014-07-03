package io.andresaraujo.github.buzz;

import java.util.ArrayList;
import java.util.HashMap;

public class Buzz {
    public static final String DEFAULT_CHANNEL = "_";
    static Buzz _buzz = new Buzz();
    private HashMap<String, HashMap<String, ArrayList<SubscriptionDef>>> subscriptions = new HashMap<String, HashMap<String, ArrayList<SubscriptionDef>>>(10);

    public static ChannelDef channel(String name) {
        return new ChannelDef(name);
    }

    public static ChannelDef channel(){
        return channel(DEFAULT_CHANNEL);
    }

    public SubscriptionDef sub(String channelName, String topic, BuzzListener listener) {
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
        if(subs.indexOf(subDef) == -1) {
            subs.add(subDef);
        }
        return subDef;
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

    public void unsubscribe(SubscriptionDef subDef) {
        HashMap<String, ArrayList<SubscriptionDef>> channel = subscriptions.get(subDef.channelName);
        if(channel != null){
            ArrayList<SubscriptionDef> subs = channel.get(subDef.topic);
            subs.remove(subDef);
        }
    }

    public int numListeners(String channelName) {
        return numListeners(channelName, null);
    }
    public int numListeners(String channelName, String topic) {
        HashMap<String, ArrayList<SubscriptionDef>> channel = subscriptions.get(channelName);

        if(channel == null){
            return 0;
        }

        if(topic != null){
            ArrayList<SubscriptionDef> subs = channel.get(topic);
            return subs == null ? 0 : subs.size();
        }else{
            int total = 0;
            for (String s : channel.keySet()) {
                ArrayList<SubscriptionDef> subs = channel.get(s);
                if(subs != null){
                    total += subs.size();
                }
            }
            return total;
        }
    }
}
