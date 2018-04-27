package me.hanneshertach.twatbot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.concurrent.TimeUnit;

public class HotStuffHandler implements EventListener {

  public void onEvent(final Event event) {

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

    Guild guild = ((MessageReceivedEvent) event).getMessage().getGuild();

    final VoiceChannel channel = guild
        .getVoiceChannelsByName("General", true).get(0);

    final AudioManager manager = guild.getAudioManager();

    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);

    final AudioPlayer player = playerManager.createPlayer();

    manager.setSendingHandler(new HotStuffSendHandler(player));
    manager.openAudioConnection(channel);

    playerManager.loadItem("https://youtu.be/1IdEhvuNxV8?t=51s", new AudioLoadResultHandler() {

      public void trackLoaded(AudioTrack audioTrack) {

        audioTrack.setPosition(51000);

        player.playTrack(audioTrack);
        ((MessageReceivedEvent) event)
            .getChannel()
            .sendMessage("Playing hot stuff")
            .queue();

        Thread timer = new Thread(new Runnable() {
          public void run() {

            System.out.println("Thread started");

            while (player.getPlayingTrack().getPosition() < 60000) {
              try {
                System.out.println("Will sleep");
                Thread.sleep(1000);
                System.out.println(player.getPlayingTrack().getPosition());
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }

            System.out.println("While loop finished");

            manager.closeAudioConnection();
            player.destroy();

          }
        });

        timer.start();

        try {
          timer.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          manager.closeAudioConnection();
          player.destroy();
        }

      }

      public void playlistLoaded(AudioPlaylist audioPlaylist) {

      }

      public void noMatches() {
        ((MessageReceivedEvent) event)
            .getChannel()
            .sendMessage("Hot stuff doesnt exist :(")
            .queue();
      }

      public void loadFailed(FriendlyException e) {
        ((MessageReceivedEvent) event)
            .getChannel()
            .sendMessage("Load Failed")
            .queue();

        throw e;
      }
    });


  }
}
