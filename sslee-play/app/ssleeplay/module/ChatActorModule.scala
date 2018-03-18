package ssleeplay.module

import actors.websocketchapter.ChatActor
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import play.api.Logger

class ChatActorModule extends AbstractModule with AkkaGuiceSupport {
  def configure = {
    Logger.debug("#############################")
    bindActor[ChatActor]("chatActor")
  }
}