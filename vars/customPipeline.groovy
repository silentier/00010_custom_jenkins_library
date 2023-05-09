def call ( Map popertyInfo ){

    node("master") {
        stage("Stages") {
            unitaryTest();
            compile();
        }
    }
}