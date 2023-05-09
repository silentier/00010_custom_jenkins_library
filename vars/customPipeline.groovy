def call ( Map popertyInfo ){
    agent none

    node("master") {
        stage("Stages") {
            unitaryTest();
            compile();
        }
    }
}