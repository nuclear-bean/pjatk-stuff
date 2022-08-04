class Text {
    String language;
    double [] occurrences;

    Text(Language language) {
        this.language = language.name;
        this.occurrences = language.normalizedVector;
    }
}
