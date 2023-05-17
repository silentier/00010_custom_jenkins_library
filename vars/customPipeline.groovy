def call ( Map popertyInfo ){
    node("def-container") {
        stage("Stages") {
           compile();
        }
    }
}