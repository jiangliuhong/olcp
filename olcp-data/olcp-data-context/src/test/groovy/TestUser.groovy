package test;

import olcp.annotation.Api;
import olcp.annotation.Post;
import olcp.function.AbstractApi;

@Api
class TestUser extends AbstractApi {

    @Post("test")
    def getUserInfo() {
        def info = [:]
        info.put("id", "1");
        info.put("name", "test");
        return info
    }

}