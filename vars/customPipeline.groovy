def call ( Map popertyInfo ){
    agent none


    node("jdk17mvn") {
        stage("Stages")

            compile();
            unitaryTest();
            packageStage();
            generateDocker();
        }
    }
}