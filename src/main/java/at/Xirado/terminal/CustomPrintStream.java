package at.Xirado.terminal;

import org.apache.commons.io.output.WriterOutputStream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.LineReader;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CustomPrintStream
{
    public static PrintStream printStream = null;
    public static PrintStream getPrintStream()
    {
        if(printStream == null)
        {
            printStream = new PrintStream(System.out) {

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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

                @Override
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
            };
        }

        return printStream;
    }
}
