package nextstep.jwp.controller;

import org.apache.coyote.http11.common.MediaType;
import org.apache.coyote.http11.common.StaticResource;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public class IndexController extends AbstractController {

    private static final IndexController INSTANCE = new IndexController();

    private IndexController() {
    }

    public static IndexController getInstance() {
        return INSTANCE;
    }

    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.ok(new StaticResource("Hello world!", MediaType.TEXT_HTML));
    }
}
