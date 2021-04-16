package at.Xirado.terminal.apps;

import at.Xirado.terminal.ShellCommand;
import org.jline.builtins.Commands;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.nio.file.Paths;

public class Nano extends ShellCommand
{

    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {
        try
        {
            Commands.nano(terminal, System.out, System.err, Paths.get(""), args);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
