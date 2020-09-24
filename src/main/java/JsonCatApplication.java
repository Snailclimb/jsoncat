import common.Banner;
import server.HttpServer;

/**
 * @author shuang.kou
 * @createTime 2020年09月23日 17:30:00
 **/
public class JsonCatApplication {
    public static void main(String[] args) {
        Banner.printBanner();
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
