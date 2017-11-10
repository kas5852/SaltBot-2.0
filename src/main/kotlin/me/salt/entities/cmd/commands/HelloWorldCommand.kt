/*
 * Copyright 2017 DevJake
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.salt.entities.cmd.commands

import me.salt.entities.cmd.Command
import me.salt.entities.cmd.CommandParser
import me.salt.entities.permissions.Node
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent

/**
 * This command serves a simple purpose of responding with 'Hello, {username}', where username is the nickname of the command sender.
 * This exists so as to ensure the command system is functional as expected -- useful for front-end debugging.
 */
class HelloWorldCommand(cmdPrefix: String, aliases: MutableList<String>, name: String, description: String, author: String, perms: List<Node>) : Command(cmdPrefix, aliases, name, description, author, perms) {
    override fun preExecute(cmd: CommandParser.CommandContainer, event: GuildMessageReceivedEvent, instHandler: CommandParser.CmdInstanceHandle): CommandParser.CmdInstanceHandle {
        return instHandler.accept()
    }


    override fun execute(cmd: CommandParser.CommandContainer, event: GuildMessageReceivedEvent, instHandler: CommandParser.CmdInstanceHandle): CommandParser.CmdInstanceHandle {
        //Run command
        event.channel.sendMessage("Hello, ${event.author.name}").queue()
        return instHandler.accept()
    }

    override fun postExecute(cmd: CommandParser.CommandContainer, event: GuildMessageReceivedEvent) {
        //Cleanup
    }
}

