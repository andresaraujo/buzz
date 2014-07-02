package io.andresaraujo.github.buzz

import spock.lang.Specification

class BuzzSpec extends Specification {
    class DummyData {}

    class DummyData2 {}

    def "Pub/Sub to one topic"() {
        def channel = Buzz.channel("game")
        Event event = null
        channel.sub("game.saved", { e -> event = e })
        channel.pub("game.saved", new DummyData())

        expect:
        event != null
        event.topic == "game.saved"
        event.channel == "game"
        event.timeStamp <= System.currentTimeMillis()
        event.getDataOr(DummyData.class, null) != null
        event.getDataOr(DummyData2.class, null) == null
        event.getDataOr(DummyData2.class, new DummyData2()) != null
    }

    def "Pub/Sub to multiple topics"() {
        def channel = Buzz.channel("game")
        Event eventSaved = null
        Event eventPaused = null
        Event eventLoading = null
        channel.sub("game.saved", { e -> eventSaved = e })
        channel.sub("game.paused", { e -> eventPaused = e })
        channel.sub("game.loading", { e -> eventLoading = e })
        channel.pub("game.saved", new DummyData())
        channel.pub("game.paused", [id: 99])

        expect:
        eventSaved != null
        eventSaved.topic == "game.saved"
        eventSaved.channel == "game"
        eventSaved.timeStamp <= System.currentTimeMillis()
        eventSaved.getDataOr(DummyData.class, null) != null
        eventPaused != null
        eventPaused.topic == "game.paused"
        eventPaused.channel == "game"
        eventPaused.timeStamp <= System.currentTimeMillis()
        eventPaused.getDataOr(LinkedHashMap.class, [id: 0]).id == 99
        eventLoading == null
    }

    def "Get data or default value"() {

        expect:
        e.getDataOr(String.class, "default") == "data"
        e2.getDataOr(String.class, "default") == "default"

        where:
        e = new Event("channel", "topic", "data")
        e2 = new Event("channel", "topic", [:])
    }

    def "Pub/Sub to default channel"() {
        def channel = Buzz.channel()
        Event event = null

        channel.sub("game.init", { e -> event = e })
        channel.pub("game.init")

        expect:
        event != null
        event.topic == "game.init"
        event.channel == "_"
        event.timeStamp <= System.currentTimeMillis()
        event.getDataOr(DummyData.class, null) == null
    }

    def "Pub/Sub cancel subscription"() {
        def channel = Buzz.channel()
        def count = 0
        def sub = channel.sub("game.init", { ++count })

        sub.unsubscribe();
        channel.pub("game.init")

        expect:
        count == 0
    }
}
