package me.hanneshertach.twatbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.managers.AudioManager;

public class HotStuffHandler implements EventListener {
  public void onEvent(Event event) {

    if (!(event instanceof MessageReceivedEvent)) {
      return;
    }

    if (((MessageReceivedEvent) event).getAuthor().isBot()) {
      return;
    }

    Message message = ((MessageReceivedEvent) event).getMessage();
    String text = message.getContentRaw();

    if (!text.equals("!twat hotstuff")) {
      return;
    }

    System.out.println("Hotstuff");

    Guild guild = ((MessageReceivedEvent) event).getMessage().getGuild();

    VoiceChannel channel = guild
        .getVoiceChannelsByName("General", true).get(0);

    AudioManager manager = guild.getAudioManager();

    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);

    AudioPlayer player = playerManager.createPlayer();

    manager.setSendingHandler(new HotStuffSendHandler(player));
    manager.openAudioConnection(channel);

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    manager.closeAudioConnection();

  }
}
