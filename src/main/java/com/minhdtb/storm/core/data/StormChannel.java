package com.minhdtb.storm.core.data;

import com.minhdtb.storm.entities.Channel;
import com.minhdtb.storm.entities.ChannelAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


abstract class StormChannel implements IStormChannel {

    private Channel channel;

    StormChannel(Channel channelNew) {
        channel = channelNew;
    }

    StormChannel() {
        this(new Channel());
    }

    String getAttribute(String name) {
        Optional<ChannelAttribute> found = channel.getAttributes().stream()
                .filter(item -> Objects.equals(item.getName(), name)).findFirst();

        if (found.isPresent()) {
            return found.get().getValue();
        }

        return null;
    }

    void setAttribute(String name, Object value) {
        Optional<ChannelAttribute> found = channel.getAttributes().stream()
                .filter(item -> Objects.equals(item.getName(), name)).findFirst();

        if (found.isPresent()) {
            found.get().setValue(String.valueOf(value));
        } else {
            ChannelAttribute attribute = new ChannelAttribute();
            attribute.setChannel(channel);
            attribute.setName(name);
            attribute.setValue(String.valueOf(value));
            attribute.setType(value.getClass().getTypeName());

            channel.getAttributes().add(attribute);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public List<IStormVariable> getVariables() {
        return null;
    }

    @Override
    public Channel getRaw() {
        return channel;
    }

    @Override
    public String getName() {
        return channel.getName();
    }

    @Override
    public void setName(String name) {
        channel.setName(name);
    }

    @Override
    public String getDescription() {
        return channel.getDescription();
    }

    @Override
    public void setDescription(String description) {
        channel.setDescription(description);
    }
}
