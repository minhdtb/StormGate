package com.minhdtb.storm.core;

import com.minhdtb.storm.entities.Channel;
import com.minhdtb.storm.entities.ChannelAttribute;
import lombok.Data;

import java.util.Objects;
import java.util.Optional;

@Data
class CoreChannel {

    protected Channel channel;

    String getAttribute(String name) {
        Optional<ChannelAttribute> found = channel.getAttributes().stream()
                .filter(item -> Objects.equals(item.getName(), name)).findFirst();

        if (found.isPresent()) {
            return found.get().getValue();
        }

        return null;
    }

    void setAttribute(String name, String value) {
        Optional<ChannelAttribute> found = channel.getAttributes().stream()
                .filter(item -> Objects.equals(item.getName(), name)).findFirst();

        if (found.isPresent()) {
            found.get().setValue(value);
        } else {
            ChannelAttribute attribute = new ChannelAttribute();
            attribute.setChannel(channel);
            attribute.setName(name);
            attribute.setValue(value);

            channel.getAttributes().add(attribute);
        }
    }

    public Long getId() {
        return channel.getId();
    }

    CoreChannel() {
        channel = new Channel();
    }

    protected CoreChannel(Channel channelNew) {
        channel = channelNew;
    }
}
