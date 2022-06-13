package space.xiaoceng.pixiv.creawler

import me.friwi.jcefmaven.CefAppBuilder
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter
import me.friwi.jcefmaven.impl.progress.ConsoleProgressHandler
import org.cef.CefApp
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class JCefRunner implements ApplicationRunner {
    @Override
    void run(ApplicationArguments args) throws Exception {
        CefAppBuilder builder = new CefAppBuilder()
        //Configure the builder instance
        builder.setInstallDir(new File("jcef-bundle")) //Default
        builder.setProgressHandler(new ConsoleProgressHandler()) //Default
        builder.addJcefArgs("--disable-gpu") //Just an example
        builder.getCefSettings().windowless_rendering_enabled = true //Default - select OSR mode

//Set an app handler. Do not use CefApp.addAppHandler(...), it will break your code on MacOSX!
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {

        })

//Build a CefApp instance using the configuration above
        CefApp app = builder.build()
        println app.getVersion()
    }
}
