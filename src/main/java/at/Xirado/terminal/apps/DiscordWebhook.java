package at.Xirado.terminal.apps;

import at.Xirado.terminal.Main;
import at.Xirado.terminal.ShellCommand;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;

public class DiscordWebhook extends ShellCommand
{
    @Override
    public void execute(String[] args, Terminal terminal, LineReader reader)
    {
        try
        {
            Options options = new Options();
            options.addOption("t", false, "Use Token");
            options.addOption("auth", true, "Authorization");
            options.addOption("d", true, "Description");
            options.addOption("e", false, "Send embed");
            options.addOption("ea", true, "Embed Author");
            options.addOption("a", true, "Author");
            options.addOption("c", true, "Color");
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);
            if(!cmd.hasOption("t"))
            {
                String url = cmd.getOptionValue("auth");
                WebhookClientBuilder builder = new WebhookClientBuilder(url);
                builder.setThreadFactory((job) -> {
                    Thread thread = new Thread(job);
                    thread.setName("WebhookClientDaemon");
                    thread.setDaemon(true);
                    return thread;
                });
                builder.setWait(true);
                WebhookClient client = builder.build();
                if(cmd.hasOption("e"))
                {
                    WebhookEmbedBuilder embed = new WebhookEmbedBuilder();
                    if(cmd.hasOption("ea"))
                    {
                        embed.setAuthor(new WebhookEmbed.EmbedAuthor(cmd.getOptionValue("ea"), null, null));
                    }
                    if(cmd.hasOption("c"))
                    {
                        embed.setColor(Integer.parseInt(cmd.getOptionValue("c")));
                    }
                    if(cmd.hasOption("d"))
                    {
                        embed.setDescription(cmd.getOptionValue("d"));
                    }
                    Main.getShell().println("Sending webhook...");
                    WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
                    webhookMessageBuilder.addEmbeds(embed.build());
                    if(cmd.hasOption("a"))
                    {
                        webhookMessageBuilder.setUsername(cmd.getOptionValue("a"));
                    }

                    client.send(webhookMessageBuilder.build()).thenAcceptAsync(m -> Main.getShell().println("Sent webhook!"));
                }

            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
