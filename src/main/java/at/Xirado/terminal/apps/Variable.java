package at.Xirado.terminal.apps;

import at.Xirado.terminal.Main;
import at.Xirado.terminal.ShellCommand;
import org.jline.builtins.Options;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Variable extends ShellCommand
{
    public ConcurrentHashMap<String, String> variables = new ConcurrentHashMap<>();

    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {

        if(args.length == 0)
        {
            if (variables.size() == 0)
            {
                Main.getShell().println("There are no registered system-variables!");
            } else
            {
                Main.getShell().println("All registered system-variables:");
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> entrySet : variables.entrySet())
                {
                    String key = entrySet.getKey();
                    String value = entrySet.getValue();
                    sb.append(key).append("=").append(value).append("\n");
                }
                Main.getShell().println(sb.toString().trim());
            }
            return;
        }else if(args.length == 1)
        {
            if (variables.containsKey(args[0].toLowerCase()))
            {
                Main.getShell().println(variables.get(args[0].toLowerCase()));
            } else
            {
                Main.getShell().println("%" + args[0].toLowerCase() + "%");
            }
            return;
        }
        Main.getShell().println(Arrays.toString(args));
        String key = args[0].toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < args.length; i++)
        {
            sb.append(args[i]).append(" ");
        }
        String value = sb.toString().trim();
        if(variables.containsKey(key))
        {
            variables.replace(key, value);
        }else {
            variables.put(key, value);
        }
        Main.getShell().println(key+"="+value);
    }
}
