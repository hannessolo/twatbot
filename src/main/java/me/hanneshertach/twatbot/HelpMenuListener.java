package me.hanneshertach.twatbot;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class HelpMenuListener implements EventListener {
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

    if (messageString.equals("!twat")) {
      message.getChannel().sendMessage("Waddup ya cunt?").queue();
    }

    if (messageString.equals("!twat Waddup ya cunt?")) {
      message.getChannel().sendMessage("Not much, how bout yerself, je wanker?").queue();
    }

    if (messageString.equals("!tart")) {
      message.getChannel().sendMessage("Twattart").queue();
    }

    if (messageString.equals("!twat help")) {
      String helpMenu = new StringBuilder()
          .append("Allright ye twat, heres yer help:")
          .append("\n!twat : !twat Waddup ya cunt? : !twat hotstuff")
          .toString();
      message.getChannel().sendMessage(helpMenu).queue();
    }



  }
}
