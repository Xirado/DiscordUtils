package at.Xirado.terminal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationLoader
{
    public static final ConcurrentHashMap<String, ShellCommand> loadedApps = new ConcurrentHashMap<>();

    public static void loadApp(String invoke, ShellCommand shellCommand)
    {
        if(loadedApps.containsKey(invoke)) return;
        loadedApps.put(invoke, shellCommand);
    }

    public static void unloadApp(String invoke)
    {
        loadedApps.remove(invoke);
    }




    public static boolean startCommand(String invoke, String[] args)
    {
        for(Map.Entry<String, ShellCommand> entrySet : loadedApps.entrySet())
        {
            String inv = entrySet.getKey();
            ShellCommand app = entrySet.getValue();
            if(inv.equalsIgnoreCase(invoke))
            {
                app.execute(args, Main.getShell().terminal, Main.getShell().reader);
                return true;
            }
        }
        return false;
    }

}
