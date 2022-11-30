package example.api

import top.jiangliuhong.olcp.data.annotation.Api
import top.jiangliuhong.olcp.data.annotation.Get
import top.jiangliuhong.olcp.data.context.Context
import top.jiangliuhong.olcp.data.function.AbstractApi

@Api("/entity")
class EntityApi extends AbstractApi {

    @Get("/create")
    def createEntity() {
        def value = Context.table
                .value("exampleTest")
                .set("testString", "123")
                .set("testInt", 123)
                .genId().create()
        return value;
    }
}
