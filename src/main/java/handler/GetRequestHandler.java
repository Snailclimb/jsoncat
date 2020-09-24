package handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.util.List;
import java.util.Map;

/**
 * @author shuang.kou
 * @createTime 2020年09月24日 13:33:00
 **/
@Slf4j
public class GetRequestHandler implements RequestHandler {
    @Override
    public Object handle(FullHttpRequest fullHttpRequest) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> uriAttributes = queryDecoder.parameters();
        //此处仅打印请求参数（你可以根据业务需求自定义处理）
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            for (String attrVal : attr.getValue()) {
                log.info(attr.getKey() + "=" + attrVal);
            }
        }
        return null;
    }
}
