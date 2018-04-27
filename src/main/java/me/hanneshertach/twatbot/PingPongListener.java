package me.hanneshertach.twatbot;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class PingPongListener implements EventListener {
  public void onEvent(Event event) {

    if (!(event instanceof MessageReceivedEvent)) {
      return;
    }

    MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;

    if (messageEvent.getAuthor().isBot()) {
      return;
    }

    Message message = messageEvent.getMessage();
    String messageString = message.getContentRaw();

    if (messageString.equals("!oi twat")) {
      message.getChannel().sendMessage("Waddup ya cunt?").queue();
    }

    if (messageString.equals("Waddup ya cunt?")) {
      message.getChannel().sendMessage("Not much, how bout yerself, je wanker?").queue();
    }

    if (messageString.equals("!help")) {
      String helpMenu = "Alright je wee babby, heres yer help:" +
          "\nCommands:" +
          "\n!oi twat" +
          "\nWaddup ya cunt?\n" +
          "But it aint nuttin for the faint o heart";
      message.getChannel().sendMessage(helpMenu).queue();
    }

    if (messageString.equals("!hot stuff")) {
      message.getChannel().sendMessage("Baby tonight, yeah!").queue();
    }

  }
}
