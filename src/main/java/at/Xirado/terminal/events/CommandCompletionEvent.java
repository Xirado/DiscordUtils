package at.Xirado.terminal.events;

import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public abstract class CommandCompletionEvent extends GenericEvent
{
    public abstract void onCommandComplete(@NotNull LineReader reader, @NotNull ParsedLine line, @NotNull List<Candidate> candidateList);
}
