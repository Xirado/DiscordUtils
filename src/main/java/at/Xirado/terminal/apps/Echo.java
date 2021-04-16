package at.Xirado.terminal.apps;

import at.Xirado.terminal.Main;
import at.Xirado.terminal.ShellCommand;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

public class Echo extends ShellCommand
{
    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {
        StringBuilder sb = new StringBuilder();
        for(String arg : args) sb.append(arg+" ");
        Main.getShell().println(sb.toString().trim());
    }
}
