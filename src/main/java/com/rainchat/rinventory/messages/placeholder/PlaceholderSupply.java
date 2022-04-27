package com.rainchat.rinventory.messages.placeholder;

public interface PlaceholderSupply<T> {
    Class<T> forClass();

    String getReplacement(String forKey);
}
