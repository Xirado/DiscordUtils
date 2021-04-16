package at.Xirado.terminal.apps;

import at.Xirado.terminal.Main;
import at.Xirado.terminal.ShellCommand;
import at.Xirado.terminal.utils.JSON;
import org.apache.commons.io.IOUtils;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserInfo extends ShellCommand
{
    public static final String USER_AGENT = "DiscordUtil (https://github.com/Xirado/DiscordUtil)";

    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {
        try
        {
            URL url = new URL("https://discord.com/api/v8/users/"+args[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("user-agent", USER_AGENT);
            conn.setRequestProperty("authorization", "Bot "+Main.jsonConfig.getString("token"));


            var bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String x = IOUtils.toString(bufferedReader);
            JSON json = JSON.parse(x);
            if(json == null) return;
            String id = json.getString("id");
            String username = json.getString("username");
            String avatarhash = json.getString("avatar");
            String avatarurl = null;
            if(avatarhash.startsWith("a_"))
            {
                avatarurl = "https://cdn.discordapp.com/avatars/"+id+"/"+avatarhash+".gif";
            }else {
                avatarurl = "https://cdn.discordapp.com/avatars/"+id+"/"+avatarhash+".png";
            }
            String descriminator = json.getString("descriminator");
            String s = "Username: "+username+"\nID: "+id+"\nAvatar-URL: "+avatarurl;
            Main.getShell().println(s);
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
