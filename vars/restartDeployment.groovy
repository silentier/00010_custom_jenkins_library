def call ( Map popertyInfo ) {
    node("K8s_master") {
        stage("restart service") {
            steps {

                def conf = "app/conf.txt"
                props = readProperties file: conf
                DEPLOYMENT_NAME = readProperties.deploymentName


                sh(" kubectl rollout restart deployment ${DEPLOYMENT_NAME} -n silentier ")
            }
        }
    }
}