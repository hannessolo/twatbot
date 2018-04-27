package me.hanneshertach.twatbot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class TwatBot {

  private static JDA jda;

  public static void main(String[] args) {

    String token = args[0];

    try {
      jda = new JDABuilder(AccountType.BOT)
          .setToken(token)
          .addEventListener(new PingPongListener())
          .buildBlocking();
    } catch (LoginException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

}
