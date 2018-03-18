package ssleeakka.barcode.module

import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import play.api.Logger
import ssleeakka.barcode.actors.BarcodeCacheActor

class BarcodeActorModule extends AbstractModule with AkkaGuiceSupport {
  def configure = {
    Logger.debug("#############################")
    bindActor[BarcodeCacheActor]("barcodeCacheActor")
  }
}
