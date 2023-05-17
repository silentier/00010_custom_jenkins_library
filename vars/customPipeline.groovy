def call ( Map popertyInfo ){
    node("jdk17mvn") {
        stage("Stages") {
           compile();
        }
    }
}