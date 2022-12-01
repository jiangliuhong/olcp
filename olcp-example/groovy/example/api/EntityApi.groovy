package example.api

import top.jiangliuhong.olcp.data.annotation.Api
import top.jiangliuhong.olcp.data.annotation.Get
import top.jiangliuhong.olcp.data.annotation.Post
import top.jiangliuhong.olcp.data.context.Context
import top.jiangliuhong.olcp.data.function.AbstractApi

@Api("/entity")
class EntityApi extends AbstractApi {

    private def tableName = "exampleTest"

    @Post("/create")
    def createEntity() {
        def map = getBodyMap()
        def value = Context.table
                .value(tableName)
                .set("testString", "123")
                .set("testInt", 123)
                .create()
        return value;
    }

    @Get("queryById")
    def queryEntityById() {
        def id = getParameter("id")
        Context.table.find(tableName)
    }
}
