def call ( Map popertyInfo ){
    node("jdk17mvn") {
        stage("Stages") {

            sh("cat /etc/os-release")

            compile();
            unitaryTest();
            packageStage();
            generateDocker();
        }
    }
}