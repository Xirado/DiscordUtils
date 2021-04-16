package at.Xirado.terminal.events;

import org.jetbrains.annotations.NotNull;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

public class CommandCompletionListener extends CommandCompletionEvent
{
    @Override
    public void onCommandComplete(@NotNull LineReader reader, @NotNull ParsedLine line, @NotNull List<Candidate> candidateList)
    {

    }
}
