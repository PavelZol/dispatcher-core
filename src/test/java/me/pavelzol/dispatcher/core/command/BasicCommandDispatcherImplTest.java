package me.pavelzol.dispatcher.core.command;

import me.pavelzol.dispatcher.core.command.first.DuplicateFirstCommandHandler;
import me.pavelzol.dispatcher.core.command.first.FirstCommand;
import me.pavelzol.dispatcher.core.command.first.FirstCommandHandler;
import me.pavelzol.dispatcher.core.command.second.SecondCommand;
import me.pavelzol.dispatcher.core.command.second.SecondCommandHandler;
import me.pavelzol.dispatcher.core.dispatcher.BasicCommandDispatcherImpl;
import me.pavelzol.dispatcher.core.dispatcher.CommandDispatcher;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasicCommandDispatcherImplTest {

    @Test
    void dispatchesCommandToRegisteredHandler() {
        List<String> handledCommands = new ArrayList<>();
        CommandDispatcher commandDispatcher = BasicCommandDispatcherImpl.builder()
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
            () -> BasicCommandDispatcherImpl.builder()
                .register(FirstCommand.class, new FirstCommandHandler(handledCommands))
                .register(FirstCommand.class, new DuplicateFirstCommandHandler(handledCommands))
        );

        assertEquals(
            "Duplicate command handler for command type: me.pavelzol.dispatcher.core.command.first.FirstCommand",
            exception.getMessage()
        );
    }

    @Test
    void rejectsCommandWithoutRegisteredHandler() {
        CommandDispatcher commandDispatcher = BasicCommandDispatcherImpl.builder().build();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> commandDispatcher.dispatch(new FirstCommand("foo"))
        );

        assertEquals(
            "No command handler registered for command type: me.pavelzol.dispatcher.core.command.first.FirstCommand",
            exception.getMessage()
        );
    }

    @Test
    void builtDispatcherIsNotAffectedByLaterBuilderRegistrations() {
        List<String> handledCommands = new ArrayList<>();
        BasicCommandDispatcherImpl.Builder builder = BasicCommandDispatcherImpl.builder()
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
            "No command handler registered for command type: me.pavelzol.dispatcher.core.command.second.SecondCommand",
            exception.getMessage()
        );
    }

    @Test
    void rejectsNullCommand() {
        CommandDispatcher commandDispatcher = BasicCommandDispatcherImpl.builder().build();

        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> commandDispatcher.dispatch(null)
        );

        assertEquals("command must not be null", exception.getMessage());
    }
}
