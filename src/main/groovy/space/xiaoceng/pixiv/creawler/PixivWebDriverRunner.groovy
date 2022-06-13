package space.xiaoceng.pixiv.creawler

import cn.hutool.core.util.StrUtil
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner

//@Component
class PixivWebDriverRunner implements ApplicationRunner {
    @Override
    void run(ApplicationArguments args) throws Exception {
        def file = new File(args.getSourceArgs()[0])
        file.readLines("UTF-8").each {
            if (StrUtil.isBlank(it)) return
            runJob it
        }

    }

    static void runJob(String url) {
        WebDriver webDriver = new FirefoxDriver()
        webDriver.get(url)
    }
}
