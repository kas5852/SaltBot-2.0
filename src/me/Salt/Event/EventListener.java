/*
 *
 *  * Copyright (c) 2017 DevJake
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package me.Salt.Event;


import me.Salt.Command.Container.CommandParser;
import me.Salt.Exception.Command.DisabledCommandException;
import me.Salt.Exception.Generic.MalformedParametersException;
import me.Salt.Exception.Generic.MissingDataException;
import me.Salt.Exception.Permission.LackingPermissionException;
import me.Salt.Logging.JLogger;
import me.Salt.Logging.LogUtils;
import me.Salt.Main;
import me.Salt.SaltAPI.User.JUserBuilder;
import me.Salt.Util.CommandExecutor;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Project title: SaltBot-2.0
 * Authored by Salt on 05/04/2017.
 */
public class EventListener extends ListenerAdapter {
    private ExecutorService executor = Executors.newFixedThreadPool(20);

    //TODO add support for PrivateMessageReceivedEvent (Perhaps have an interface for guild events and one for private events. Each command that implements them is then able to be called upon in their respective methods)
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {//TODO Update to MessageReceivedEvent
        Main.salt.setJUser(
                new JUserBuilder(event.getAuthor())
                        .setUser(event.getAuthor())
                        .setLastTextChannel(event.getChannel())
                        .setLastMessage(LocalDateTime.now())
                        .setLastNickname(event.getMember().getNickname() != null ? event.getMember().getNickname() : event.getAuthor().getName())
                        .setLastOnline(LocalDateTime.now())
                        .setLastSpokenGuild(event.getGuild())
                        .setLastMessage(LocalDateTime.now())
                        .build()
        );

        Main.salt.init();

        Runnable r = () -> {
            if (event.getAuthor().isBot() || event.getAuthor().getId().equals(Main.jda.getSelfUser().getId())) return;

            try {
                CommandExecutor.execute(new CommandParser().parse(event.getMessage().getRawContent()), event);
            } catch (MalformedParametersException | MissingDataException | DisabledCommandException | LackingPermissionException e) {
                JLogger.writeToConsole(JLogger.Level.WARNING, e.getMessage()); //TODO provide feedback about the error, and perhaps how to correct it/obtain help
                event.getChannel().sendMessage(e.getMessage()).queue(n -> n.delete().queueAfter(5, TimeUnit.SECONDS));
            }

//            Gson gson = new GsonBuilder()
//                    .serializeNulls()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .registerTypeAdapter(Guild.class, new GuildSerialiser())
//                    .registerTypeAdapter(TextChannel.class, new TextChannelSerialiser())
//                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerialiser())
//                    .create();
//
//            try {
//                event.getChannel().sendMessage("```" + gson.toJsonTree(Main.salt.getJUserById("112633500447838208")).toString() + "```").queue();
//            } catch (MissingDataException e) {
//                System.out.println(e.getMessage());
//            }

            //TODO reference to above code if necessary. Remove when no longer needed.
        };

        if (event.getMessage().getRawContent().startsWith(Main.salt.getCmdPrefix())) {
            Main.salt.incrementCommandCount();
            executor.execute(r);
        } else {
            Main.salt.incrementMessageCount();
        }
    }

    @Override
    public void onGenericEvent(Event event) {
        if (event instanceof GuildMessageReceivedEvent) LogUtils.log(event.getClass().getSimpleName() + " received.");
        else if (event instanceof GuildMessageDeleteEvent) LogUtils.log(event.getClass().getSimpleName() + " received.");
    }
}