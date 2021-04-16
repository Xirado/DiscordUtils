package at.Xirado.terminal.apps;

import at.Xirado.terminal.ShellCommand;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Login extends ShellCommand
{

    private static final Logger LOGGER = LoggerFactory.getLogger(Login.class);

    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {
        String username = reader.readLine("username: ");
        String password = reader.readLine(username+"'s password: ", '*');
        if(username.equals("root") && password.equals("password"))
        {

        }
    }
}
