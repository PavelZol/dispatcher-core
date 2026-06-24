package io.easycqrs.core.command;

import io.easycqrs.core.command.first.DuplicateFirstCommandHandler;
import io.easycqrs.core.command.first.FirstCommand;
import io.easycqrs.core.command.first.FirstCommandHandler;
import io.easycqrs.core.command.second.SecondCommand;
import io.easycqrs.core.command.second.SecondCommandHandler;
import io.easycqrs.core.dispatcher.DefaultCommandDispatcher;
import io.easycqrs.core.dispatcher.CommandDispatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultCommandDispatcherTest {

    @Test
    void dispatchesCommandToRegisteredHandler() {
        List<String> handledCommands = new ArrayList<>();
        CommandDispatcher commandDispatcher = DefaultCommandDispatcher.builder()
            .register(FirstCommand.class, new FirstCommandHandler(handledCommands))
            .register(SecondCommand.class, new SecondCommandHandler(handledCommands))
            .build();

        commandDispatcher.dispatch(new FirstCommand("foo"));
        commandDispatcher.dispatch(new SecondCommand("bar"));

        assertEquals(List.of("first:foo", "second:bar"), handledCommands);
    }

    @Test
    void rejectsDuplicateHandlersForSameCommandType() {
        List<String> handledCommands = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> DefaultCommandDispatcher.builder()
                .register(FirstCommand.class, new FirstCommandHandler(handledCommands))
                .register(FirstCommand.class, new DuplicateFirstCommandHandler(handledCommands))
        );

        assertEquals(
            "Duplicate command handler for command type: io.easycqrs.core.command.first.FirstCommand",
            exception.getMessage()
        );
    }

    @Test
    void rejectsCommandWithoutRegisteredHandler() {
        CommandDispatcher commandDispatcher = DefaultCommandDispatcher.builder().build();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> commandDispatcher.dispatch(new FirstCommand("foo"))
        );

        assertEquals(
            "No command handler registered for command type: io.easycqrs.core.command.first.FirstCommand",
            exception.getMessage()
        );
    }

    @Test
    void builtDispatcherIsNotAffectedByLaterBuilderRegistrations() {
        List<String> handledCommands = new ArrayList<>();
        DefaultCommandDispatcher.Builder builder = DefaultCommandDispatcher.builder()
            .register(FirstCommand.class, new FirstCommandHandler(handledCommands));
        CommandDispatcher commandDispatcher = builder.build();

        builder.register(SecondCommand.class, new SecondCommandHandler(handledCommands));
        commandDispatcher.dispatch(new FirstCommand("foo"));

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> commandDispatcher.dispatch(new SecondCommand("bar"))
        );

        assertEquals(List.of("first:foo"), handledCommands);
        assertEquals(
            "No command handler registered for command type: io.easycqrs.core.command.second.SecondCommand",
            exception.getMessage()
        );
    }

    @Test
    void rejectsNullCommand() {
        CommandDispatcher commandDispatcher = DefaultCommandDispatcher.builder().build();

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> commandDispatcher.dispatch(null)
        );

        assertEquals("command must not be null", exception.getMessage());
    }
}
