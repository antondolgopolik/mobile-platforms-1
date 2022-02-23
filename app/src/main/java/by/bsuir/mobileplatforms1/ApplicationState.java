package by.bsuir.mobileplatforms1;

public final class ApplicationState {
    private static String username;

    private ApplicationState() {
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ApplicationState.username = username;
    }
}
