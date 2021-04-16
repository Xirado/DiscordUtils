package at.Xirado.terminal;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

public abstract class ShellCommand
{
    public abstract void execute(String[] args, Terminal terminal, LineReader reader);
}
