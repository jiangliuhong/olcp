package example.api

import top.jiangliuhong.olcp.sdk.annotation.Api
import top.jiangliuhong.olcp.sdk.annotation.Get
import top.jiangliuhong.olcp.sdk.function.AbstractApi

@Api("example")
class ExampleApi extends AbstractApi {

    @Get
    public String stringReturn() {
        return "this call :" + this.context.getPath();
    }
}
