package olcp.test;

class TestUser {
    def getUserInfo(Map<String, Object> params) {
        def info = [:]
        info.put("id", "1");
        info.put("name", "test");
        return info
    }
}