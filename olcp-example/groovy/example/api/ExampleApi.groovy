package example.api

import top.jiangliuhong.olcp.data.annotation.Api
import top.jiangliuhong.olcp.data.annotation.Get
import top.jiangliuhong.olcp.data.annotation.Post
import top.jiangliuhong.olcp.data.function.AbstractApi

@Api("example")
class ExampleApi extends AbstractApi {

    @Get
    public String stringReturn() {
        return "this call :" + this.context.getPath();
    }

    @Post
    def mapReturn() {
        def map = this.bodyMap
        def num = map.get("num")
        num = num + 1;
        def username = ""
        def nickname = ""

        if (context.currentUser != null) {
            username = context.currentUser.username
            nickname = context.currentUser.nickname
        }
        return [num: num, user: this.context.currentUserId, username: username, nickname: nickname]
    }
}
