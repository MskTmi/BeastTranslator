package com.msktmi

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.info


object BeastTranslator : KotlinPlugin(
    JvmPluginDescription(
        id = "net.msktmi.beast-translator",
        name = "Beast Translator",
        version = "1.1.0",
    ) {
        author("MskTmi")
    }
) {
    override fun onEnable() {
        logger.info { "兽语译者已开启！嗷~" }

        val eventChannel = GlobalEventChannel.parentScope(this)
        //群消息
        eventChannel.subscribeAlways<GroupMessageEvent> {
            //回复转译
            if (message[QuoteReply.Key].toString().isNotEmpty()) {
                if (Regex(".*兽语翻译$").matches(message.contentToString())) {
                    val str = Beast.strToBeast(message[QuoteReply.Key]?.source!!.originalMessage.contentToString())
                    group.sendMessage(str)
                }
            }
            //兽语自动转文本
            if (Beast.isBeast(message.contentToString())) {
                val str = Beast.beastToStr(message.contentToString())
                group.sendMessage(str)
            }
        }

        //好友信息
        eventChannel.subscribeAlways<FriendMessageEvent> {
            //兽语自动转文本
            if (Beast.isBeast(message.contentToString())) {
                val str = Beast.beastToStr(message.contentToString())
                sender.sendMessage(str)
            }
            //文本转兽语
            if (Beast.isTranslation(message.contentToString())) {
                //获取需要翻译的文本
                val output = message.contentToString().substring(3)
                val str = Beast.strToBeast(output)
                sender.sendMessage(str)
            }

            return@subscribeAlways
        }
    }
}