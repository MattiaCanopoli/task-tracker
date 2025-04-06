package tasktracker.model;

public enum Color {
    RESET("\033[0m"), RED_BOLD("\033[1;31m"), CYAN_BOLD("\033[1;36m"), RED(
            "\033[0;31m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
