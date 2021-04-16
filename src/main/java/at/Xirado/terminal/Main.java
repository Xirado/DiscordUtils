package at.Xirado.terminal;

import at.Xirado.terminal.utils.FileLoader;
import at.Xirado.terminal.utils.JSON;
import at.Xirado.terminal.apps.*;
import at.Xirado.terminal.events.CommandCompletionListener;
import at.Xirado.terminal.events.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static Shell shell = null;
    public static Shell getShell()
    {
        return shell;
    }
    public static EventHandler eventHandler;
    public static JSON jsonConfig = null;

    public static void main(String[] args)
    {
        eventHandler = new EventHandler();
        eventHandler.registerListener(new CommandCompletionListener());
        shell = new Shell();
        shell.startShell(() ->
        {
            ApplicationLoader.loadApp("var", new Variable());
            ApplicationLoader.loadApp("echo", new Echo());
            ApplicationLoader.loadApp("hook", new DiscordWebhook());
            ApplicationLoader.loadApp("user", new UserInfo());
            boolean x = FileLoader.copyFileFromResources("DiscordUtil.json");
            if(x){
                getShell().println("Config file DiscordUtil.json has been created.\nPlease configure a Discord Bot Token to use this application");
                try
                {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e)
                {
                    System.exit(0);
                }
                System.exit(0);
            }
            jsonConfig = JSON.parse(new File("DiscordUtil.json"));
        });
    }

}
