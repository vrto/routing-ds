package net.helpscout.routingds;

public class DbContextHolder {

    private static final ThreadLocal<AppConfig.DataSourceName> contextHolder = new ThreadLocal<AppConfig.DataSourceName>();

    public static void setDbType(AppConfig.DataSourceName dbType) {
        if(dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static AppConfig.DataSourceName getDbType() {
        return (AppConfig.DataSourceName) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
