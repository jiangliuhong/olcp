package top.jiangliuhong.olcp.data.consts;


public enum Sorts {
    ASC, DESC;

    public static Sorts getSortsByName(String name) {
        switch (name) {
            case "ASC":
            case "asc":
            default:
                return ASC;
            case "DESC":
            case "desc":
                return DESC;
        }
    }
}
