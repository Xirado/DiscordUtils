package at.Xirado.terminal.events;

import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventHandler
{

    private final ArrayList<CommandCompletionEvent> registeredCommandCompletionEvents;

    public EventHandler()
    {
        registeredCommandCompletionEvents = new ArrayList<>();
    }

    public void registerListener(CommandCompletionEvent... events)
    {
        registeredCommandCompletionEvents.addAll(Arrays.asList(events));
    }

    public void onCommandCompletion(@NotNull LineReader reader, @NotNull ParsedLine line, @NotNull List<Candidate> candidateList)
    {
        registeredCommandCompletionEvents.forEach((e) -> e.onCommandComplete(reader, line, candidateList));
    }
}
