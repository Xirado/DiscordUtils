package at.Xirado.terminal;

import at.Xirado.terminal.apps.Variable;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.AnsiConsole;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.jline.utils.InfoCmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Shell
{
    public LineReader reader = null;

    private static final Logger logger = LoggerFactory.getLogger(Shell.class);

    public Terminal terminal = null;
    public volatile boolean startedSuccessfully = false;

    public final AttributedStyle BLUE = AttributedStyle.DEFAULT.foreground(0x00, 0xDB, 0xFF);
    public final AttributedStyle PINK = AttributedStyle.DEFAULT.foreground(0xFF, 0x00, 0xFF);


    public final int LOGO_LENGTH = 64;
    public final int LOGO_SPACING = 3;
    public final String LOGO = ""+
            "    ___    _                            _   _   _   _     _   _ \n" +
            "   |   \\  (_)  ___  __   ___   _ _   __| | | | | | | |_  (_) | |\n" +
            "   | |) | | | (_-< / _| / _ \\ | '_| / _` | | |_| | |  _| | | | |\n" +
            "   |___/  |_| /__/ \\__| \\___/ |_|   \\__,_|  \\___/   \\__| |_| |_|";
    public final String WELCOME = new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0x3E, 0xB4, 0x89))
            .append(LOGO)
            .append("\n\n"+centerStringToLogo("DiscordUtil 1.0 by Xirado"))
            .append("\n"+centerStringToLogo("https://github.com/Xirado/DiscordUtils"))
            .style(AttributedStyle.DEFAULT)
            .append("\n\n\nUse \"help\" or \"?\" for a list of commands\n")
            .toAnsi();

    public Shell()
    {

    }

    public String centerStringToLogo(String str)
    {
        int strlen = str.length();
        if(strlen >= LOGO_LENGTH) return str;
        int spaces = (LOGO_LENGTH-strlen)/2;
        StringBuilder builder = new StringBuilder();
        builder.append(" ".repeat(spaces+2));

        builder.append(str);
        return builder.toString();
    }


    public void startShell(Runnable success)
    {
        Thread t = new Thread(() ->
        {
            AnsiConsole.systemInstall();
            String prompt = new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(0,255,255)).append("» ").style(AttributedStyle.DEFAULT).toAnsi();
            TerminalBuilder builder = TerminalBuilder.builder();
            builder.system(true);

            try
            {
                terminal = builder.build();
            } catch (IOException e)
            {
                logger.error("Could not build Terminal!", e);
            }
            terminal.puts(InfoCmp.Capability.clear_screen);
            terminal.flush();
            reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer((reader, line, candidates) -> {
                        Main.eventHandler.onCommandCompletion(reader, line, candidates);
                    })
                    .parser(null)
                    .variable(LineReader.SECONDARY_PROMPT_PATTERN, "%M%P > ")
                    .variable(LineReader.INDENTATION, 2)
                    .option(LineReader.Option.INSERT_BRACKET, true)
                    .build();
            println(WELCOME);
            System.setOut(CustomPrintStream.getPrintStream());
            System.setErr(CustomPrintStream.getPrintStream());
            startedSuccessfully = true;
            success.run();
            while (true) {
                String line;
                try {
                    line = reader.readLine(prompt, null, (MaskingCallback) null, null);
                    line = line.trim();
                    String[] variables = StringUtils.substringsBetween(line, "%", "%");
                    if(variables != null && variables.length > 0)
                    {
                        Variable variableCommand = (Variable) ApplicationLoader.loadedApps.get("var");
                        for(String variable : variables)
                        {

                            if(!variableCommand.variables.containsKey(variable.toLowerCase())) continue;
                            line = line.replaceAll("%"+variable+"%", variableCommand.variables.get(variable.toLowerCase()));
                        }
                    }
                    terminal.flush();
                    if(line.length() == 0) continue;
                    ParsedLine pl = reader.getParser().parse(line, 0);
                    String[] argv = pl.words().subList(1, pl.words().size()).toArray(new String[0]);
                    boolean x = ApplicationLoader.startCommand(pl.word(), argv);
                    if(!x) println("Unknown command.");

                } catch (UserInterruptException e)
                {
                    System.exit(0);
                }
                catch (Exception e)
                {
                    logger.error("An error occured", e);
                }
            }
        });
        t.setName("Terminal Worker");
        t.start();
    }

    public void write(int b)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().write(b);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().write(b);
        }

    }

    public void write(@NotNull byte[] buf, int off, int len)
    {
        if(Main.getShell().reader.isReading())
        {
            String text = new String(buf, StandardCharsets.UTF_8);
            char[] chars = text.toCharArray();
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().write(chars, off, len);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            String text = new String(buf, StandardCharsets.UTF_8);
            char[] chars = text.toCharArray();
            Main.getShell().reader.getTerminal().writer().write(chars, off, len);
        }

    }

    public void print(boolean b)
    {
        if(Main.getShell().reader.isReading()){
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(b);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(b);
        }

    }

    public void print(char c)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(c);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(c);
        }
    }

    public void print(int i)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(i);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(i);
        }
    }

    public void print(long l)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(l);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(l);
        }
    }

    public void print(float f)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(f);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(f);
        }
    }

    public void print(double d)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(d);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(d);
        }
    }

    public void print(@NotNull char[] s)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(s);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(s);
        }
    }

    public void print(@Nullable String s)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(s);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(s);
        }
    }

    public void print(@Nullable Object obj)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().print(obj);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().print(obj);
        }
    }

    public void println()
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println();
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println();
        }
    }

    public void println(boolean x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    public void println(char x)
    {

        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(int x)
    {

        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(long x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(float x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        };
    }

    
    public void println(double x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(@NotNull char[] x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(@Nullable String x)
    {

        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public void println(@Nullable Object x)
    {
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            Main.getShell().reader.getTerminal().writer().println(x);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            Main.getShell().reader.getTerminal().writer().println(x);
        }
    }

    
    public PrintStream printf(@NotNull String format, Object... args)
    {
        PrintWriter ps;
        if(Main.getShell().reader.isReading())
        {
            Main.getShell().reader.callWidget(LineReader.CLEAR);
            ps = Main.getShell().reader.getTerminal().writer().printf(format, args);
            Main.getShell().reader.callWidget(LineReader.REDRAW_LINE);
            Main.getShell().reader.callWidget(LineReader.REDISPLAY);
            Main.getShell().reader.getTerminal().writer().flush();
        }else
        {
            ps = Main.getShell().reader.getTerminal().writer().printf(format, args);
        }
        OutputStream os = new WriterOutputStream(ps);
        return new PrintStream(os);
    }

}
