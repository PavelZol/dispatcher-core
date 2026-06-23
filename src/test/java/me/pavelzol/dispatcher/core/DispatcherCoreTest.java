package me.pavelzol.dispatcher.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DispatcherCoreTest {

    @Test
    void exposesLibraryName() {
        assertEquals("dispatcher-core", DispatcherCore.name());
    }
}
